package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.objects.command.CommandDebugInfo;
import su.terrafirmagreg.modules.core.objects.command.CommandGenTree;
import su.terrafirmagreg.modules.core.objects.command.CommandHeat;
import su.terrafirmagreg.modules.core.objects.command.CommandHorsePower;
import su.terrafirmagreg.modules.core.objects.command.CommandPlayer;
import su.terrafirmagreg.modules.core.objects.command.CommandStripWorld;
import su.terrafirmagreg.modules.core.objects.command.CommandTime;
import su.terrafirmagreg.modules.core.objects.command.CommandWorkChunk;

import net.minecraft.command.ICommand;

public final class CommandsCore {

    public static ICommand DEBUG_INFO;
    public static ICommand STRIP_WORLD;
    public static ICommand MAKE_TREE;
    public static ICommand HEAT;
    public static ICommand PLAYER;
    public static ICommand TIME;
    public static ICommand WORK_CHUNK;
    public static ICommand HORSE_POWER;

    public static void onRegister(RegistryManager registry) {

        DEBUG_INFO = registry.command(new CommandDebugInfo());
        STRIP_WORLD = registry.command(new CommandStripWorld());
        MAKE_TREE = registry.command(new CommandGenTree());
        HEAT = registry.command(new CommandHeat());
        PLAYER = registry.command(new CommandPlayer());
        TIME = registry.command(new CommandTime());
        WORK_CHUNK = registry.command(new CommandWorkChunk());
        HORSE_POWER = registry.command(new CommandHorsePower());

    }
}
