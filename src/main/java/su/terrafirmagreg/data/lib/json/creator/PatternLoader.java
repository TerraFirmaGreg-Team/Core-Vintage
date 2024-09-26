package su.terrafirmagreg.data.lib.json.creator;

public final class PatternLoader {

  public static void createBlock(String fileName, String modid, String texturepath) {
    JsonCreator.set(JsonTemplateType.BLOCK, /* это путь до файла, который будет создан*/
                    "src/main/resources/assets/" + modid + "/models/block/" + fileName + ".json");
    JsonCreator.replace("modid",
                        modid); //заменяем все modid на наш аргумент, который мы сюда подадим
    JsonCreator.replace("texturepath", texturepath);
    JsonCreator.end(); // генерируем файл по пути, который мы указали выше
  }

  public static void createBlockState(String fileName, String modid, String name) {
    JsonCreator.set(JsonTemplateType.BLOCKSTATE,
                    "src/main/resources/assets/" + modid + "/blockstates/" + fileName + ".json");
    JsonCreator.replace("modid", modid);
    JsonCreator.replace("name", name);
    JsonCreator.end();
  }

  public static void createItemBlock(String fileName, String modid, String name) {
    JsonCreator.set(JsonTemplateType.ITEMBLOCK,
                    "src/main/resources/assets/" + modid + "/models/item/" + fileName + ".json");
    JsonCreator.replace("modid", modid);
    JsonCreator.replace("name", name);
    JsonCreator.end();
  }

  public static void createItem(String fileName, String modid, String texturepath) {
    JsonCreator.set(JsonTemplateType.ITEM,
                    "src/main/resources/assets/" + modid + "/models/item/" + fileName + ".json");
    JsonCreator.replace("modid", modid);
    JsonCreator.replace("texturepath", texturepath);
    JsonCreator.end();
  }

}
