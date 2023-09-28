package net.dries007.tfc.module.devices.client.gui;

import net.dries007.tfc.TerraFirmaGreg;
import net.dries007.tfc.api.capability.heat.Heat;
import net.dries007.tfc.client.gui.GuiContainerTE;
import net.dries007.tfc.module.devices.common.tile.TECharcoalForge;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCharcoalForge extends GuiContainerTE<TECharcoalForge> {
    private static final ResourceLocation CHARCOAL_FORGE_BACKGROUND = TerraFirmaGreg.getID("textures/gui/charcoal_forge.png");

    public GuiCharcoalForge(Container container, InventoryPlayer playerInv, TECharcoalForge tile) {
        super(container, playerInv, tile, CHARCOAL_FORGE_BACKGROUND);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        // Draw the temperature indicator
        int temperature = (int) (51 * tile.getField(TECharcoalForge.FIELD_TEMPERATURE) / Heat.maxVisibleTemperature());
        if (temperature > 0) {
            if (temperature > 51) {
                temperature = 51;
            }
            drawTexturedModalRect(guiLeft + 8, guiTop + 66 - temperature, 176, 0, 15, 5);
        }
    }
}
