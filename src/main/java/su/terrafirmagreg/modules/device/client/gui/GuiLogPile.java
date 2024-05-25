package su.terrafirmagreg.modules.device.client.gui;

import su.terrafirmagreg.api.spi.gui.BaseGuiContainer;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiLogPile extends BaseGuiContainer {

    public static final ResourceLocation BACKGROUND = ModUtils.resource("textures/gui/container/small_inventory.png");

    public GuiLogPile(Container container, InventoryPlayer playerInv) {
        super(container, playerInv, BACKGROUND);
    }
}
