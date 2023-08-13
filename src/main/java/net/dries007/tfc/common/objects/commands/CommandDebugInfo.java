package net.dries007.tfc.common.objects.commands;

import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * This is part of an effort to find the cause of TerraFirmaCraft#355 and TerraFirmaCraft#361
 */
@ParametersAreNonnullByDefault
public class CommandDebugInfo extends CommandBase {
    private static final Logger LOGGER = LogManager.getLogger("tfc-infodump");

    @Override
    @Nonnull
    public String getName() {
        return "tfcdebuginfodump";
    }

    @Override
    @Nonnull
    public String getUsage(ICommandSender sender) {
        return "tfc.command.debuginfo.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        World world = sender.getEntityWorld();
        BlockPos pos = sender.getPosition();

        // Chunk data
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, pos);

        LOGGER.info("ROCK LAYER DATA");
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                LOGGER.info("Pos: {} {} - Rock 1: {}, Rock 2: {}, Rock 3: {}", x, z, chunkData.getRock1(x, z), chunkData.getRock2(x, z), chunkData.getRock3(x, z));
            }
        }

        LOGGER.info("RAW CHUNK DATA VIEW");
        NBTBase nbt = ChunkDataProvider.CHUNK_DATA_CAPABILITY.writeNBT(chunkData, null);
        LOGGER.info(nbt == null ? "writeNBT returned null" : nbt.toString());
    }
}
