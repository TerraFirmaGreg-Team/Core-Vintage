package su.terrafirmagreg.framework.manager.command.api;

import su.terrafirmagreg.api.util.CommandUtils.Level;
import su.terrafirmagreg.framework.manager.api.IBaseEntry;
import su.terrafirmagreg.framework.manager.command.api.ICommandEntry.Settings;

import net.minecraft.command.CommandBase;
import net.minecraft.util.ResourceLocation;

import lombok.Getter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public interface ICommandEntry extends IBaseEntry<Settings, CommandBase> {


  @Nullable
  ResourceLocation getRegistryName();

  void setRegistryName(ResourceLocation name);

  @Getter
  class Settings extends BaseSettings<Settings> {

    final List<String> aliases = new ArrayList<>();

    String name;
    Level level = Level.ALL;

    protected Settings() {}

    public static Settings of() {
      return new Settings();
    }

    public Settings name(String name) {
      this.name = name;
      return this;
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
