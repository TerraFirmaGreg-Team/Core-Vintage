package su.terrafirmagreg.api.library.json.creator;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.launchwrapper.Launch;

import com.google.common.collect.ImmutableMap;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class PatternLoader {

  public static File rootFolder = Launch.minecraftHome == null ? new File(".") : Launch.minecraftHome;

  public static void createBlock(String fileName, String modid, String texturepath) {
    JsonCreator.set(JsonTemplateType.BLOCK, "src/main/resources/assets/" + modid + "/models/block/" + fileName + ".json");
    JsonCreator.replace("modid", modid); //заменяем все modid на наш аргумент, который мы сюда подадим
    JsonCreator.replace("texturepath", texturepath);
    JsonCreator.end(); // генерируем файл по пути, который мы указали выше
  }

  public static void createItemBlock(String fileName, String modid, String name) {
    JsonCreator.set(JsonTemplateType.ITEMBLOCK, "src/main/resources/assets/" + modid + "/models/item/" + fileName + ".json");
    JsonCreator.replace("modid", modid);
    JsonCreator.replace("name", name);
    JsonCreator.end();
  }

  public static void createItem(String fileName, String modid, String texturepath) {
    JsonCreator.set(JsonTemplateType.ITEM, "src/main/resources/assets/" + modid + "/models/item/" + fileName + ".json");
    JsonCreator.replace("modid", modid);
    JsonCreator.replace("texturepath", texturepath);
    JsonCreator.end();
  }

  public static void genForgeBlockStates(String object) throws IOException {

  }

  public static void createBlockStateJson(BlockStateContainer blockStateContainer) {
    ImmutableMap<IProperty<?>, Comparable<?>> properties = blockStateContainer.getBaseState().getProperties();
    List<Map<IProperty<?>, Comparable<?>>> allVariants = getAllBlockStateVariants(properties);

    JsonObject blockstateJson = new JsonObject();
    JsonObject variants = new JsonObject();

    for (Map<IProperty<?>, Comparable<?>> variantProperties : allVariants) {

      for (Map.Entry<IProperty<?>, Comparable<?>> entry : variantProperties.entrySet()) {
        String propertyName = entry.getKey().getName();
        Comparable<?> value = entry.getValue();

        JsonObject variant = new JsonObject();
        variant.addProperty(propertyName, value.toString());

        variants.add(propertyName, variant);
      }

      blockstateJson.add("variants", variants);

      // Запись JSON объекта в файл
      //createBlockState(blockStateContainer.getBlock().getRegistryName().getPath(), "tfg", blockStateContainer.getBlock().getRegistryName().getPath());
      //File folders = new File(rootFolder + "/resources/assets/tfg/blockstates/" + object.replace(" ", "_").toLowerCase() + "/");
      File foldersWood = new File(rootFolder + "/resources/assets/tfg/blockstates/wood/log/");
      foldersWood.mkdirs();
      File outputFile = new File(rootFolder, "resources/assets/tfg/blockstates/" + blockStateContainer.getBlock().getRegistryName().getPath() + ".json");
      try (FileWriter writer = new FileWriter(outputFile)) {
        new GsonBuilder().setPrettyPrinting().create().toJson(blockstateJson, writer);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  public static void createBlockState(String fileName, String modid, String name) {
    JsonCreator.set(JsonTemplateType.BLOCKSTATE, "src/main/resources/assets/" + modid + "/blockstates/" + fileName + ".json");
    JsonCreator.replace("modid", modid);
    JsonCreator.replace("name", name);
    JsonCreator.end();
  }

  private static List<Map<IProperty<?>, Comparable<?>>> getAllBlockStateVariants(ImmutableMap<IProperty<?>, Comparable<?>> properties) {
    List<Map<IProperty<?>, Comparable<?>>> allVariants = new ArrayList<>();
    List<IProperty<?>> propertyList = new ArrayList<>(properties.keySet());
    List<List<Comparable<?>>> propertyValues = new ArrayList<>();

    for (IProperty<?> property : propertyList) {
      propertyValues.add(new ArrayList<>(property.getAllowedValues()));
    }

    List<List<Comparable<?>>> combinations = generateCombinations(propertyValues);

    for (List<Comparable<?>> combination : combinations) {
      Map<IProperty<?>, Comparable<?>> variant = new LinkedHashMap<>();
      for (int i = 0; i < propertyList.size(); i++) {
        variant.put(propertyList.get(i), combination.get(i));
      }
      allVariants.add(variant);
    }

    return allVariants;
  }

  private static List<List<Comparable<?>>> generateCombinations(List<List<Comparable<?>>> lists) {
    List<List<Comparable<?>>> combinations = new ArrayList<>();
    generateCombinationsHelper(combinations, lists, 0, new ArrayList<>());
    return combinations;
  }

  private static void generateCombinationsHelper(List<List<Comparable<?>>> result, List<List<Comparable<?>>> lists, int depth, List<Comparable<?>> current) {
    if (depth == lists.size()) {
      result.add(new ArrayList<>(current));
      return;
    }

    for (Comparable<?> value : lists.get(depth)) {
      current.add(value);
      generateCombinationsHelper(result, lists, depth + 1, current);
      current.remove(current.size() - 1);
    }
  }

  private String getVariantName(Map<IProperty<?>, Comparable<?>> variantProperties) {
    return variantProperties.entrySet().stream()
                            .map(entry -> entry.getKey().getName() + "=" + entry.getValue())
                            .collect(Collectors.joining(","));
  }


}
