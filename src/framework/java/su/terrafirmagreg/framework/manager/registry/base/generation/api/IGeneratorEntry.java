package su.terrafirmagreg.framework.manager.registry.base.generation.api;

import su.terrafirmagreg.framework.manager.api.IBaseEntry;
import su.terrafirmagreg.framework.manager.registry.base.generation.api.IGeneratorEntry.Settings;

import net.minecraft.world.gen.feature.WorldGenerator;

public interface IGeneratorEntry extends IBaseEntry<Settings, WorldGenerator> {


  class Settings extends BaseSettings<Settings> {

    boolean doBlockNotify;

    protected Settings() {
      this.doBlockNotify = false;
    }

    public static Settings of() {
      return new Settings();
    }

    public Settings doBlockNotify() {
      this.doBlockNotify = true;
      return this.self();
    }


  }
}
