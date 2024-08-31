package su.terrafirmagreg.modules.core.objects.command;

import su.terrafirmagreg.api.base.command.BaseCommand;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistry;


import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Rock;

/**
 * This is part of an effort to find the cause of TerraFirmaCraft#355 and TerraFirmaCraft#361
 */

public class CommandDebugInfo extends BaseCommand {

    @Override
    public String getName() {
        return "debuginfo";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return ModUtils.localize("command", "debuginfo.usage");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        World world = sender.getEntityWorld();
        BlockPos pos = sender.getPosition();

        // Chunk data
        var chunkData = CapabilityChunkData.get(world, pos);

        ModuleCore.LOGGER.info("ROCK LAYER DATA");
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                ModuleCore.LOGGER.info("Pos: {} {} - Rock 1: {}, Rock 2: {}, Rock 3: {}",
                        x, z, chunkData.getRock1(x, z), chunkData.getRock2(x, z), chunkData.getRock3(x, z));
            }
        }

        ModuleCore.LOGGER.info("RAW CHUNK DATA VIEW");
        var nbt = CapabilityChunkData.CAPABILITY.writeNBT(chunkData, null);
        ModuleCore.LOGGER.info(nbt == null ? "writeNBT returned null" : nbt.toString());

        // Rock Registry Information
        ModuleCore.LOGGER.info("ROCK REGISTRY");
        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
            //noinspection ConstantConditions
            ModuleCore.LOGGER.info("Rock: {} -> Id: {}",
                    rock.getRegistryName().getPath(), ((ForgeRegistry<Rock>) TFCRegistries.ROCKS).getID(rock));
        }
    }
}
