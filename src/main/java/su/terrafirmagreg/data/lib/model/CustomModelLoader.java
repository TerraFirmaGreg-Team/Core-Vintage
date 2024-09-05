package su.terrafirmagreg.data.lib.model;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;


import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class CustomModelLoader implements ICustomModelLoader {

  private static final Map<ResourceLocation, IModel> UNBAKED_MODEL_CACHE = new Object2ObjectOpenHashMap<>();

  public static void registerModel(ResourceLocation location, IModel model) {
    UNBAKED_MODEL_CACHE.put(location, model);
  }

  @Override
  public boolean accepts(@NotNull ResourceLocation modelLocation) {
    /*
     * напрямую modelLocation'ы лучше не сравнивать, т.к.
     * в процессе загрузки моделей они несколько раз пересоздаются
     */
    for (var rec : UNBAKED_MODEL_CACHE.entrySet()) {
      return modelLocation.toString().contains(rec.getKey().toString());
    }

    //return modelLocation.toString().contains("testitem") || modelLocation.toString().contains("testname");
    return false;
  }

  @Override
  public @NotNull IModel loadModel(@NotNull ResourceLocation modelLocation) {

    //		for (var rec : UNBAKED_MODEL_CACHE.entrySet()) {
    //			if (modelLocation.toString().contains(rec.getKey().toString())) {
    //				return rec.getValue();
    //			}
    //		}
    //		if (modelLocation.toString().contains("testitem")) {
    //			/*Путь до текстуры предмета*/
    //			return new ItemModel(new ResourceLocation("items/diamond_sword"));
    //		} else if (modelLocation.toString().contains("testname")) {
    //			/*Путь до текстуры блока*/
    //			return new BlockModel(new ResourceLocation("blocks/enchanting_table_top"));
    //		}
    return null;
  }

  @Override
  public void onResourceManagerReload(@NotNull IResourceManager resourceManager) {
  }
}

