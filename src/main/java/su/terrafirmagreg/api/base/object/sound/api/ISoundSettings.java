package su.terrafirmagreg.api.base.object.sound.api;

import su.terrafirmagreg.api.base.object.sound.api.ISoundSettings.Settings;
import su.terrafirmagreg.api.library.IBaseSettings;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.util.ResourceLocation;

import lombok.Getter;

public interface ISoundSettings extends IBaseSettings<Settings> {

  @Getter
  class Settings extends BaseSettings<Settings> {


    final ResourceLocation name;

    protected Settings(ResourceLocation name) {

      this.name = name;
      this.registryKey = name.getPath();
    }

    public static Settings of(ResourceLocation name) {
      return new Settings(name);
    }

    public static Settings of(String modId, String name) {
      return new Settings(ModUtils.resource(modId, name));
    }

    public static Settings of(String name) {
      return new Settings(ModUtils.resource(name));
    }

  }
}
