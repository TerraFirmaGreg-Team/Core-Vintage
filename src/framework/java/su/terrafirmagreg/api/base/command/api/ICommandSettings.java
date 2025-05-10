package su.terrafirmagreg.api.base.command.api;

import su.terrafirmagreg.api.base.IBaseSettings;
import su.terrafirmagreg.api.base.command.api.ICommandSettings.Settings;
import su.terrafirmagreg.api.util.CommandUtils.Level;

import net.minecraft.command.CommandBase;
import net.minecraft.util.ResourceLocation;

import lombok.Getter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public interface ICommandSettings extends IBaseSettings<Settings, CommandBase> {

  void setRegistryName(ResourceLocation name);

  @Nullable
  ResourceLocation getRegistryName();

  @Getter
  class Settings extends BaseSettings<Settings> {

    final List<String> aliases;

    Level level;

    protected Settings() {

      this.aliases = new ArrayList<>();

      this.level = Level.ALL;
    }

    public static Settings of() {
      return new Settings();
    }

    public Settings level(Level level) {
      this.level = level;
      return this;
    }

    public Settings alias(String alias) {
      this.aliases.add(alias);
      return this;
    }
  }
}
