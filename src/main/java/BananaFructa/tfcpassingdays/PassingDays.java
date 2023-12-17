package BananaFructa.tfcpassingdays;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@Mod(modid = PassingDays.modId,name = PassingDays.name,version = PassingDays.version,dependencies = "after:galacticraftcore;after:immersivetech")
public class PassingDays {
    public static final String modId = "tfcpassingdays";
    public static final String name = "TFC Passing Days";
    public static final String version = "1.0.0";

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    boolean await = false;
    boolean awaitingServer = false;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (await && Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().world.provider != null) {
            Utils.writeDeclaredField(World.class, Minecraft.getMinecraft().world, "provider", new PassingDayWorldProviderClient(Minecraft.getMinecraft().world.provider), true);
            await = false;
        }
    }


    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (awaitingServer) {
            World world = DimensionManager.getWorld(0);
            if (world != null && !world.isRemote && world.provider != null) {
                Utils.writeDeclaredField(World.class, world, "provider", new PassingDayWorldProviderServer(world.provider), true);
                awaitingServer = false;
                List<TileEntity> teList = world.tickableTileEntities;
                List<TileEntity> newTeList = new TETriggerList(world);
                newTeList.addAll(teList);
                Utils.writeDeclaredField(World.class,world,"tickableTileEntities",newTeList,true);
            }
        }
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartedEvent event) {
        awaitingServer = true;
    }

    @SubscribeEvent
    public void onJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        await = true;
    }

}
