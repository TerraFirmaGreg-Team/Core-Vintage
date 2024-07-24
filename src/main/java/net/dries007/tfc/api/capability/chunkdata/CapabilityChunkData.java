package net.dries007.tfc.api.capability.chunkdata;

import su.terrafirmagreg.modules.world.ModuleWorld;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.network.PacketChunkData;
import net.dries007.tfc.util.climate.Climate;

import static su.terrafirmagreg.api.data.Constants.MODID_TFC;

@Mod.EventBusSubscriber(modid = MODID_TFC)
public final class CapabilityChunkData {

    public static final ResourceLocation CHUNK_DATA = new ResourceLocation(MODID_TFC, "chunkdata");

    public static void preInit() {
        CapabilityManager.INSTANCE.register(ChunkData.class, new ChunkData.ChunkDataStorage(), ChunkData::new);
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesChunk(AttachCapabilitiesEvent<Chunk> event) {
        // Per #922, if there's no world or no world type, something is seriously violating our assumptions and we will just fail.
        //noinspection ConstantConditions
        if (event.getObject().getWorld() != null && event.getObject()
                .getWorld()
                .getWorldType() == ModuleWorld.WORLD_TYPE_CLASSIC) {
            event.addCapability(CHUNK_DATA, new ChunkDataProvider());
        }
    }

    @SubscribeEvent
    public static void onChunkWatchWatch(ChunkWatchEvent.Watch event) {
        Chunk chunk = event.getChunkInstance();
        if (chunk != null) {
            ChunkData data = chunk.getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);
            if (data != null && data.isInitialized()) {
                // Update server side climate
                Climate.update(chunk.getPos(), data.getRegionalTemp(), data.getRainfall());

                // Update client side data
                NBTTagCompound nbt = (NBTTagCompound) ChunkDataProvider.CHUNK_DATA_CAPABILITY.writeNBT(data, null);
                TerraFirmaCraft.getNetwork()
                        .sendTo(new PacketChunkData(chunk.getPos(), nbt, data.getRegionalTemp(), data.getRainfall()), event.getPlayer());
            }
        }
    }
}
