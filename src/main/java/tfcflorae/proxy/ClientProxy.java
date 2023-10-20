package tfcflorae.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tfcflorae.TFCFlorae;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static tfcflorae.TFCFlorae.MODID;

@Mod.EventBusSubscriber(value = {Side.CLIENT}, modid = TFCFlorae.MODID)
public class ClientProxy extends CommonProxy implements IProxy {
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        OBJLoader.INSTANCE.addDomain(MODID);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Nonnull
    @Override
    public IThreadListener getThreadListener(MessageContext context) {
        if (context.side.isClient()) {
            return Minecraft.getMinecraft();
        } else {
            return context.getServerHandler().player.server;
        }
    }

    @Override
    @Nullable
    public EntityPlayer getPlayer(MessageContext context) {
        if (context.side.isClient()) {
            return Minecraft.getMinecraft().player;
        } else {
            return context.getServerHandler().player;
        }
    }

    @Override
    @Nullable
    public World getWorld(MessageContext context) {
        if (context.side.isClient()) {
            return Minecraft.getMinecraft().world;
        } else {
            return context.getServerHandler().player.getEntityWorld();
        }
    }
}
