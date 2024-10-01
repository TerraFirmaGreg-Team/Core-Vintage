package su.terrafirmagreg.modules.wood.event;

import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.modules.wood.ModuleWood;
import su.terrafirmagreg.modules.wood.init.KeybindingsWood;
import su.terrafirmagreg.modules.wood.network.CSPacketActionKey;
import su.terrafirmagreg.modules.wood.network.CSPacketOpenCartGui;
import su.terrafirmagreg.modules.wood.network.CSPacketToggleSlow;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodSupplyCart;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class KeyEventHandler {

  @SubscribeEvent
  public void on(TickEvent.ClientTickEvent event) {
    if (event.phase == TickEvent.Phase.END) {
      if (Minecraft.getMinecraft().world != null) {
        if (KeybindingsWood.ACTION_CART.isPressed()) {
          ModuleWood.PACKET_SERVICE.sendToServer(new CSPacketActionKey());
        }
        if (GameUtils.getGameSettings().keyBindSprint.isPressed()) {
          ModuleWood.PACKET_SERVICE.sendToServer(new CSPacketToggleSlow());
        }
      }
    }
  }

  //	@SubscribeEvent
  //	public static void on(InputEvent event) {
  //		if (KeybindingsWood.ACTION_CART.isPressed()) {
  //			ModuleWood.PACKET_SERVICE.sendToServer(new CSPacketActionKey());
  //			ModuleWood.LOGGER.info("key pressed");
  //		}
  //		if (GameUtils.getGameSettings().keyBindSprint.isPressed()) {
  //			ModuleWood.PACKET_SERVICE.sendToServer(new CSPacketToggleSlow());
  //			ModuleWood.LOGGER.info("key not pressed");
  //		}
  //	}

  @SubscribeEvent
  public void on(GuiOpenEvent event) {
    if (event.getGui() instanceof GuiInventory) {
      EntityPlayerSP player = Minecraft.getMinecraft().player;
      if (player.getRidingEntity() instanceof EntityWoodSupplyCart entityWoodSupplyCart) {
        event.setCanceled(true);
        player.world.sendPacketToServer(ModuleWood.PACKET_SERVICE.getPacketFrom(new CSPacketOpenCartGui(0, entityWoodSupplyCart.getEntityId())));
      }
    }
  }
}
