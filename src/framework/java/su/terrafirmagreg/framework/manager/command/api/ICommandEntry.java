package su.terrafirmagreg.framework.manager.command.api;

import su.terrafirmagreg.api.util.CommandUtils.Level;
import su.terrafirmagreg.framework.manager.api.IBaseEntry;
import su.terrafirmagreg.framework.manager.command.api.ICommandEntry.CommandSettings;

import net.minecraft.command.CommandBase;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public interface ICommandEntry extends IBaseEntry<CommandSettings, CommandBase> {


  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  class CommandSettings extends BaseSettings<CommandSettings> {

    protected final List<String> aliases = new ArrayList<>();

    protected Level level = Level.ALL;
    
    public static CommandSettings of() {
      return new CommandSettings();
    }

    public CommandSettings level(Level level) {
      this.level = level;
      return this.self();
    }

    public CommandSettings alias(String alias) {
      this.aliases.add(alias);
      return this.self();
    }
  }
}
