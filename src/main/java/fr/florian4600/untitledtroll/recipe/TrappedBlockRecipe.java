package fr.florian4600.untitledtroll.recipe;

import fr.florian4600.untitledtroll.utils.TrappedBlockUtils;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.HashMap;

public class TrappedBlockRecipe extends SpecialCraftingRecipe {

    protected final HashMap<Identifier, Identifier> trappedMap;
    protected final Item surrounding;

    public TrappedBlockRecipe(Identifier id, CraftingRecipeCategory category, HashMap<Identifier, Identifier> trappedMap, Item surrounding) {
        super(id, category);
        this.trappedMap = trappedMap;
        this.surrounding = surrounding;
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        int j = 0;
        int k = 0;
        for(int i = 0; i < inventory.size(); i++) {
            ItemStack invItem = inventory.getStack(i);
            if(!invItem.isEmpty()) {
                if(invItem.isOf(surrounding)) {
                    k++;
                }else {
                    if(!trappedMap.containsKey(TrappedBlockUtils.item2Id(invItem.getItem())) || j > 0 || !TrappedBlockUtils.doSurroundingMatches(inventory, surrounding, i)) {
                        return false;
                    }
                    j++;
                }
            }
        }
        return k == 8 && j==1;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        ItemStack itemStack = ItemStack.EMPTY;
        for(int i = 0; i < inventory.size(); i++) {
            ItemStack invItem = inventory.getStack(i);
            if(!invItem.isEmpty() && !invItem.isOf(surrounding)) {
                itemStack = new ItemStack(TrappedBlockUtils.getItemFromIdMap(trappedMap, invItem.getItem()), 1);
                itemStack.setNbt(invItem.getNbt());
                break;
            }
        }
        return itemStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return UTRecipes.TRAPPED_BLOCK;
    }
}
