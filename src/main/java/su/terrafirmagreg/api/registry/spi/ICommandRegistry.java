package su.terrafirmagreg.api.registry.spi;

import net.minecraft.command.ICommand;

public interface ICommandRegistry
        extends IBaseRegistry {

    /**
     * Registers a new command. Registration will be handled for you.
     *
     * @param command The command to add.
     */
    default <C extends ICommand> C command(C command) {

        this.getRegistry().getCommands().add(command);
        return command;
    }

}
