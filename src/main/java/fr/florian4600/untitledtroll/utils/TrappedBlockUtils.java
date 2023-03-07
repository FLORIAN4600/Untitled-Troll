package fr.florian4600.untitledtroll.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TrappedBlockUtils {

    public TrappedBlockUtils() {

    }

    public static JsonObject trappedToJson(HashMap<Identifier, Identifier> map) {
        JsonObject jsonObject = new JsonObject();
        for(Map.Entry<Identifier, Identifier> mapEntry : map.entrySet()) {
            jsonObject.addProperty(mapEntry.getKey().toString(), mapEntry.getValue().toString());
        }
        return jsonObject;
    }

    public static HashMap<Identifier, Identifier> jsonToTrapped(JsonObject in) {
        HashMap<Identifier, Identifier> map = new HashMap<>();
        for(Map.Entry<String, JsonElement> mapEntry : in.asMap().entrySet()) {
            map.put(new Identifier(mapEntry.getKey()), new Identifier(mapEntry.getValue().getAsString()));
        }
        return map;
    }

    public static Item getItemFromIdMap(HashMap<Identifier, Identifier> map, Item item) {
        return id2Item(map.get(item2Id(item)));
    }

    public static Item getItemFromIdMap(HashMap<Identifier, Identifier> map, Identifier id) {
        return id2Item(map.get(id));
    }

    public static Identifier item2Id(Item item) {
        return Registries.ITEM.getId(item);
    }

    public static String item2String(Item item) {
        return Registries.ITEM.getId(item).toString();
    }

    public static Item id2Item(Identifier id) {
        return Registries.ITEM.get(id);
    }

    public static Item string2Item(String id) {
        return Registries.ITEM.get(new Identifier(id));
    }

    public static Stream<Integer> getSurroundingList(int pos, int width) {
        return Stream.of(pos - width - 1, pos - width, pos - width + 1, pos - 1, pos + 1, pos + width - 1, pos + width, pos + width + 1);
    }

    public static boolean doSurroundingMatches(CraftingInventory inv, Item surrounding, int pos) {
        int invWidth = inv.getWidth();
        return pos - invWidth > 0 && pos + invWidth + 1 < inv.size() && getSurroundingList(pos, invWidth).allMatch(number -> inv.getStack(number).isOf(surrounding));
    }
}
