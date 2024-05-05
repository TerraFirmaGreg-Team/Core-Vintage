package tfctech.client.gui;

import su.terrafirmagreg.api.data.Constants;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;


import net.dries007.tfc.client.button.GuiButtonKnapping;
import net.dries007.tfc.client.gui.GuiContainerTFC;
import tfctech.objects.container.ContainerGlassworking;

import static su.terrafirmagreg.api.data.Constants.MODID_TFCTECH;

public class GuiGlassworking extends GuiContainerTFC {

    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Constants.MODID_TFC, "textures/gui/knapping.png");
    private static final ResourceLocation GLASS_TEXTURE = new ResourceLocation(MODID_TFCTECH, "textures/gui/glassworking/button.png");
    private static final ResourceLocation GLASS_DISABLED_TEXTURE = new ResourceLocation(MODID_TFCTECH, "textures/gui/glassworking/disabled.png");

    public GuiGlassworking(Container container, EntityPlayer player) {
        super(container, player.inventory, BG_TEXTURE);
        ySize = 184;
    }

    @Override
    public void initGui() {
        super.initGui();
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                int bx = (width - xSize) / 2 + 12 + 16 * x;
                int by = (height - ySize) / 2 + 12 + 16 * y;
                addButton(new GuiButtonKnapping(x + 5 * y, bx, by, 16, 16, GLASS_TEXTURE));
            }
        }
        // JEI reloads this after it's recipe gui is closed
        if (inventorySlots instanceof ContainerGlassworking) {
            ((ContainerGlassworking) inventorySlots).setRequiresReset(true);
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (clickedMouseButton == 0) {
            for (GuiButton button : this.buttonList) {
                if (button instanceof GuiButtonKnapping && button.mousePressed(mc, mouseX, mouseY)) {
                    GuiScreenEvent.ActionPerformedEvent.Pre event = new GuiScreenEvent.ActionPerformedEvent.Pre(this, button, buttonList);
                    if (MinecraftForge.EVENT_BUS.post(event))
                        break;
                    else if (selectedButton == event.getButton())
                        continue;

                    selectedButton = event.getButton();
                    event.getButton().mousePressed(mc, mouseX, mouseY);
                    actionPerformed(event.getButton());
                    MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.ActionPerformedEvent.Post(this, event.getButton(), buttonList));
                }
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        // Check if the container has been updated
        if (inventorySlots instanceof ContainerGlassworking && ((ContainerGlassworking) inventorySlots).requiresReset()) {
            updateButtons();
            ((ContainerGlassworking) inventorySlots).setRequiresReset(false);
        }
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(GLASS_DISABLED_TEXTURE);
        for (GuiButton button : buttonList) {
            if (!button.visible) {
                Gui.drawModalRectWithCustomSizedTexture(button.x, button.y, 0, 0, 16, 16, 16, 16);
            }
        }

        // Solidified?
        if (inventorySlots instanceof ContainerGlassworking && ((ContainerGlassworking) inventorySlots).isSolidified()) {
            float x = 135F;
            float y = 30F;
            String text = TextFormatting.DARK_GRAY + I18n.format("tooltip.tfctech.smeltery.solid");
            x = x - fontRenderer.getStringWidth(text) / 2.0f;
            fontRenderer.drawString(text, guiLeft + x, guiTop + y, 0xFFFFFF, false);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button instanceof GuiButtonKnapping) {
            ((GuiButtonKnapping) button).onClick();
            button.playPressSound(mc.getSoundHandler());
            // Set the client-side matrix
            if (inventorySlots instanceof ContainerGlassworking) {
                ((ContainerGlassworking) inventorySlots).setSlotState(button.id, false);
            }
            updateButtons();
        }
    }

    private void updateButtons() {
        // Update button states
        for (GuiButton button : buttonList) {
            if (button instanceof GuiButtonKnapping) {
                button.visible = ((ContainerGlassworking) inventorySlots).getSlotState(button.id);
            }
        }
    }

}
