package su.terrafirmagreg.modules.animal.client.gui;

import net.dries007.tfc.client.gui.GuiContainerTFC;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import su.terrafirmagreg.api.util.ModUtils;

public class GuiNestBox extends GuiContainerTFC {

	public static final ResourceLocation BACKGROUND = ModUtils.getID("textures/gui/small_inventory.png");

	public GuiNestBox(Container container, InventoryPlayer playerInv) {
		super(container, playerInv, BACKGROUND);
	}
}
