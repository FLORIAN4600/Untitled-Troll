package fr.florian4600.untitledtroll.data.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class UTTextureModelSupplier implements Supplier<JsonElement> {
    private final Identifier parent;
    private final HashMap<String, Identifier> textures;

    public UTTextureModelSupplier(Identifier parent, HashMap<String, Identifier> textures) {
        this.parent = parent;
        this.textures = textures;
    }

    public JsonElement get() {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonTextures = new JsonObject();
        jsonObject.addProperty("parent", this.parent.toString());
        for(Map.Entry<String, Identifier> map : textures.entrySet()) {
            jsonTextures.addProperty(map.getKey(), map.getValue().toString());
        }
        jsonObject.add("textures", jsonTextures);
        return jsonObject;
    }
}
