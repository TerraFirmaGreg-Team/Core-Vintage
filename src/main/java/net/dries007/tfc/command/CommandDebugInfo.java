package net.dries007.tfc.command;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.capability.chunkdata.ChunkDataProvider;
import net.dries007.tfc.api.capability.chunkdata.ChunkData;


import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistry;


import org.jetbrains.annotations.NotNull;


import su.terrafirmagreg.api.lib.LoggingHelper;

/**
 * This is part of an effort to find the cause of TerraFirmaCraft#355 and TerraFirmaCraft#361
 */

public class CommandDebugInfo extends CommandBase {

    private static final LoggingHelper LOGGER = new LoggingHelper("tfc-infodump");

    @Override
    @NotNull
    public String getName() {
        return "tfcdebuginfodump";
    }

    @Override
    @NotNull
    public String getUsage(ICommandSender sender) {
        return "tfc.command.debuginfo.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        World world = sender.getEntityWorld();
        BlockPos pos = sender.getPosition();

        // Chunk data
        ChunkData chunkData = ChunkData.get(world, pos);

        LOGGER.info("ROCK LAYER DATA");
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                LOGGER.info("Pos: {} {} - Rock 1: {}, Rock 2: {}, Rock 3: {}", x, z, chunkData.getRock1(x, z), chunkData.getRock2(x, z),
                        chunkData.getRock3(x, z));
            }
        }

        LOGGER.info("RAW CHUNK DATA VIEW");
        NBTBase nbt = ChunkDataProvider.CHUNK_DATA_CAPABILITY.writeNBT(chunkData, null);
        LOGGER.info(nbt == null ? "writeNBT returned null" : nbt.toString());

        // Rock Registry Information
        LOGGER.info("ROCK REGISTRY");
        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
            //noinspection ConstantConditions
            LOGGER.info("Rock: {} -> Id: {}", rock.getRegistryName()
                    .getPath(), ((ForgeRegistry<Rock>) TFCRegistries.ROCKS).getID(rock));
        }
    }
}
