package su.terrafirmagreg.api.library.types;

import net.minecraft.util.ResourceLocation;

public abstract class Type {


  public final ResourceLocation id;

  protected Type(ResourceLocation id) {
    this.id = id;
  }


  public String getTypeName() {
    String path = id.getPath();
    if (path.contains("/")) {
      return path.substring(path.lastIndexOf("/") + 1);
    }
    return path;
  }

  public String getNamespace() {
    return id.getNamespace();
  }

  /**
   * @return namespace/TYPENAME
   */
  public String getAppendableId() {
    return this.getNamespace() + "/" + this.getTypeName();
  }

  @Override
  public String toString() {
    return this.id.toString();
  }

  public boolean isVanilla() {
    return this.getNamespace().equals("minecraft");
  }

  public abstract String getTranslationKey();
}
