package su.terrafirmagreg.api.lib.jsoncreator;

public enum JsonTemplateType {
    BLOCK("/templates/block.json"),
    BLOCKSTATE("/templates/blockstate.json"),
    ITEMBLOCK("/templates/itemblock.json"),
    ITEM("/templates/item.json"),
    HANDHELD_ITEM("/templates/handheld.json");

    private final String fileName; // путь до файла, который будет скопирован

    JsonTemplateType(String fileName) {
        this.fileName = fileName;
    }

    public String getTemplateFileName() {
        return fileName;
    }

}
