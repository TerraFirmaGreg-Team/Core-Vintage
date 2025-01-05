package su.terrafirmagreg.framework.generator.spi;

import su.terrafirmagreg.api.data.enums.ResourceType;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.module.api.IModule;


public abstract class ResourceGenerator {

  protected final String modid;
  protected final String modName;
  protected final ResourceCache cache;

  public ResourceGenerator(IModule module, ResourceCache cache) {

    this.modid = module.getIdentifier().getNamespace();
    this.modName = ModUtils.getModName(modid);
    this.cache = cache;
    
  }

  /**
   * Generates all data. All files that will be generated should be tracked using
   * {@link ResourceCache#trackToBeGeneratedResource(ResourceType, String, String, String, String)}.
   */
  public abstract void generate();

  /**
   * Saves any generated resources. {@link #cache} may be used to check for existing files and to save the generated files.
   */
  public void save() {
  }

  /**
   * Gives the name of this data generator. A good name should include the name of the owning mod and the type of data the generator generates, e.g. Your Mod's
   * model generator.
   */
  public String getName() {
    return this.modName + " Resource Generator";
  }

  /**
   * Gives the modid of the mod which owns this generator.
   */
  public final String getOwnerModid() {
    return this.modid;
  }
}
