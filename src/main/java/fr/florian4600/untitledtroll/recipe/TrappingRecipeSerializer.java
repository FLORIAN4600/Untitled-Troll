package fr.florian4600.untitledtroll.recipe;

import com.google.gson.JsonObject;
import fr.florian4600.untitledtroll.utils.TrappedBlockUtils;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.util.HashMap;

public class TrappingRecipeSerializer<T extends TrappedBlockRecipe> implements RecipeSerializer<T> {

    private final Factory<T> factory;

    public TrappingRecipeSerializer(Factory<T> factory) {
        this.factory = factory;
    }

    public T read(Identifier identifier, JsonObject jsonObject) {
        CraftingRecipeCategory craftingRecipeCategory = CraftingRecipeCategory.CODEC.byId(JsonHelper.getString(jsonObject, "category", null), CraftingRecipeCategory.MISC);
        HashMap<Identifier, Identifier> trappedMap = TrappedBlockUtils.jsonToTrapped(jsonObject.getAsJsonObject("trappedversion"));
        Item surrounding = JsonHelper.getItem(jsonObject, "surrounding");
        return this.factory.create(identifier, craftingRecipeCategory, trappedMap, surrounding);
    }

    public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
        CraftingRecipeCategory craftingRecipeCategory = packetByteBuf.readEnumConstant(CraftingRecipeCategory.class);
        HashMap<Identifier, Identifier> trappedMap = (HashMap<Identifier, Identifier>) packetByteBuf.readMap(PacketByteBuf::readIdentifier, PacketByteBuf::readIdentifier);
        Item surrounding = TrappedBlockUtils.id2Item(packetByteBuf.readIdentifier());
        return this.factory.create(identifier, craftingRecipeCategory, trappedMap, surrounding);
    }

    public void write(PacketByteBuf packetByteBuf, T trappingRecipe) {
        packetByteBuf.writeEnumConstant(trappingRecipe.getCategory());
        packetByteBuf.writeMap(trappingRecipe.trappedMap, PacketByteBuf::writeIdentifier, PacketByteBuf::writeIdentifier);
        packetByteBuf.writeIdentifier(TrappedBlockUtils.item2Id(trappingRecipe.surrounding));
    }

    @FunctionalInterface
    public interface Factory<T extends TrappedBlockRecipe> {
        T create(Identifier id, CraftingRecipeCategory category, HashMap<Identifier, Identifier> trappedMap, Item surrounding);
    }

}
