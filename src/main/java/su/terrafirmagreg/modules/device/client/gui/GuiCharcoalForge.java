package su.terrafirmagreg.modules.device.client.gui;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.device.objects.tiles.TileCharcoalForge;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.modules.core.capabilities.heat.spi.Heat;


import net.dries007.tfc.client.gui.GuiContainerTE;

@SideOnly(Side.CLIENT)
public class GuiCharcoalForge extends GuiContainerTE<TileCharcoalForge> {

    private static final ResourceLocation BACKGROUND = ModUtils.resource("textures/gui/container/charcoal_forge.png");

    public GuiCharcoalForge(Container container, InventoryPlayer playerInv, TileCharcoalForge tile) {
        super(container, playerInv, tile, BACKGROUND);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        // Draw the temperature indicator
        int temperature = (int) (51 * tile.getField(TileCharcoalForge.FIELD_TEMPERATURE) / Heat.maxVisibleTemperature());
        if (temperature > 0) {
            if (temperature > 51) {
                temperature = 51;
            }
            drawTexturedModalRect(guiLeft + 8, guiTop + 66 - temperature, 176, 0, 15, 5);
        }
    }
}
