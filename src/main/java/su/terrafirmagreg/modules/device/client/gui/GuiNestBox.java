package su.terrafirmagreg.modules.device.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import su.terrafirmagreg.api.gui.GuiContainerBase;
import su.terrafirmagreg.api.util.ModUtils;

public class GuiNestBox extends GuiContainerBase {

	public static final ResourceLocation BACKGROUND = ModUtils.getID("textures/gui/container/small_inventory.png");

	public GuiNestBox(Container container, InventoryPlayer playerInv) {
		super(container, playerInv, BACKGROUND);
	}
}
