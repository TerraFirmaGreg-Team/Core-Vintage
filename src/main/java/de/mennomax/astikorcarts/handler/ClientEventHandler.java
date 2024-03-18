package de.mennomax.astikorcarts.handler;

import de.mennomax.astikorcarts.init.ModKeybindings;
import de.mennomax.astikorcarts.packets.CPacketActionKey;
import de.mennomax.astikorcarts.packets.CPacketToggleSlow;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;

import static su.terrafirmagreg.api.lib.Constants.MODID_ASTIKORCARTS;

@EventBusSubscriber(modid = MODID_ASTIKORCARTS, value = {Side.CLIENT})
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
}
