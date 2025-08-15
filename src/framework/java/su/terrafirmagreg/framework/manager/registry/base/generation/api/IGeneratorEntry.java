package su.terrafirmagreg.framework.manager.registry.base.generation.api;

import su.terrafirmagreg.framework.manager.api.IBaseEntry;
import su.terrafirmagreg.framework.manager.registry.base.generation.api.IGeneratorEntry.GeneratorSettings;

import net.minecraft.world.gen.feature.WorldGenerator;

public interface IGeneratorEntry extends IBaseEntry<GeneratorSettings, WorldGenerator> {


  class GeneratorSettings extends BaseSettings<GeneratorSettings> {

    protected boolean doBlockNotify;

    protected GeneratorSettings() {
      this.doBlockNotify = false;
    }

    public static GeneratorSettings of() {
      return new GeneratorSettings();
    }

    public GeneratorSettings doBlockNotify() {
      this.doBlockNotify = true;
      return this.self();
    }


  }
}
