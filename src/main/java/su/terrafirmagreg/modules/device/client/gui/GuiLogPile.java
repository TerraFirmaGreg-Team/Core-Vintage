package su.terrafirmagreg.modules.device.client.gui;

import net.dries007.tfc.client.gui.GuiContainerTFC;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import su.terrafirmagreg.api.util.ModUtils;

public class GuiLogPile extends GuiContainerTFC {

	public static final ResourceLocation BACKGROUND = ModUtils.getID("textures/gui/container/small_inventory.png");

	public GuiLogPile(Container container, InventoryPlayer playerInv) {
		super(container, playerInv, BACKGROUND);
	}
}
