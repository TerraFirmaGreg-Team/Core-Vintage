package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.network.SCPacketChunkData;
import su.terrafirmagreg.modules.world.ModuleWorld;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import net.dries007.tfc.util.climate.Climate;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class EventHandlerCapabilitiesChunk {

    @SubscribeEvent
    public void attachChunkCapabilities(AttachCapabilitiesEvent<Chunk> event) {
        // Per #922, if there's no world or no world type, something is seriously violating our assumptions and we will just fail.
        //noinspection ConstantConditions

        World world = event.getObject().getWorld();

        if (event.getObject().getWorld() == null) return;

        chunkData(event, world);
    }

    @SubscribeEvent
    public static void onChunkWatchWatch(ChunkWatchEvent.Watch event) {
        Chunk chunk = event.getChunkInstance();
        if (chunk != null) {

            var data = CapabilityChunkData.get(chunk);
            if (data != null && data.isInitialized()) {
                // Update server side climate
                Climate.update(chunk.getPos(), data.getRegionalTemp(), data.getRainfall());

                // Update client side data
                NBTTagCompound nbt = (NBTTagCompound) CapabilityChunkData.CAPABILITY.writeNBT(data, null);
                ModuleCore.getPacketService()
                        .sendTo(new SCPacketChunkData(chunk.getPos(), nbt, data.getRegionalTemp(), data.getRainfall()), event.getPlayer());
            }
        }
    }

    public void chunkData(AttachCapabilitiesEvent<Chunk> event, @NotNull World world) {

        if (world.getWorldType() != ModuleWorld.WORLD_TYPE_CLASSIC) return;

        event.addCapability(CapabilityChunkData.KEY, new ProviderChunkData());
    }

}
