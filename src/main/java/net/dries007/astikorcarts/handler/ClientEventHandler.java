package net.dries007.astikorcarts.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;

import net.dries007.astikorcarts.AstikorCarts;
import net.dries007.astikorcarts.entity.EntityCargoCart;
import net.dries007.astikorcarts.init.ModKeybindings;
import net.dries007.astikorcarts.packets.CPacketActionKey;
import net.dries007.astikorcarts.packets.CPacketOpenCartGui;
import net.dries007.astikorcarts.packets.CPacketToggleSlow;

@EventBusSubscriber(modid = AstikorCarts.MODID, value = {Side.CLIENT})
public class ClientEventHandler {

  @SubscribeEvent
  public static void onClientTickEvent(ClientTickEvent event) {
    if (event.phase == TickEvent.Phase.END) {
      if (Minecraft.getMinecraft().world != null) {
        if (ModKeybindings.keybindings.get(0).isPressed()) {
          PacketHandler.INSTANCE.sendToServer(new CPacketActionKey());
        }
        if (Minecraft.getMinecraft().gameSettings.keyBindSprint.isPressed()) {
          PacketHandler.INSTANCE.sendToServer(new CPacketToggleSlow());
        }
      }
    }
  }

  @SubscribeEvent
  public static void onGuiOpen(GuiOpenEvent event) {
    if (event.getGui() instanceof GuiInventory) {
      EntityPlayerSP player = Minecraft.getMinecraft().player;
      if (player.getRidingEntity() instanceof EntityCargoCart) {
        event.setCanceled(true);
        player.world.sendPacketToServer(PacketHandler.INSTANCE.getPacketFrom(new CPacketOpenCartGui(0, player.getRidingEntity().getEntityId())));
      }
    }
  }
}
