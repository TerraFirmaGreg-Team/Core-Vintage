package su.terrafirmagreg.old.api.library.json;

import net.minecraft.util.ResourceLocation;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public final class ResourceLocationJson implements JsonDeserializer<ResourceLocation>, JsonSerializer<ResourceLocation> {

  @Override
  public ResourceLocation deserialize(JsonElement json, Type typeOfT,
                                      JsonDeserializationContext context) throws JsonParseException {
    return new ResourceLocation(json.getAsString());
  }

  @Override
  public JsonElement serialize(ResourceLocation src, Type typeOfSrc,
                               JsonSerializationContext context) {
    return new JsonPrimitive(src.toString());
  }
}
