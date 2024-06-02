package su.terrafirmagreg.modules.device.client.gui;

import su.terrafirmagreg.api.data.Constants;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.device.objects.tiles.TileSmelteryCauldron;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;

import su.terrafirmagreg.api.capabilities.heat.spi.Heat;


import net.dries007.tfc.client.FluidSpriteCache;
import net.dries007.tfc.client.gui.GuiContainerTE;
import org.lwjgl.opengl.GL11;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static tfctech.client.TechGuiHandler.GUI_ELEMENTS;

public class GuiSmelteryCauldron extends GuiContainerTE<TileSmelteryCauldron> {

    private static final ResourceLocation BACKGROUND = ModUtils.resource("textures/gui/container/smeltery_cauldron.png");

    public GuiSmelteryCauldron(Container container, InventoryPlayer playerInv, TileSmelteryCauldron tile) {
        super(container, playerInv, tile, BACKGROUND);
    }

    /**
     * Draws tooltip if not null
     *
     * @param fluid  the fluid
     * @param mouseX the mouseX relation to GUI (mouseX - guiLeft)
     * @param mouseY the mouseY relation to GUI (mouseY - guiTop)
     * @param posX   the tank's x coords (without guiLeft!)
     * @param posY   the tank's y coords (without guiTop!)
     */
    @Nullable
    public static List<String> getFluidTooltip(@Nullable FluidStack fluid, int mouseX, int mouseY, int posX, int posY) {
        if (fluid != null && mouseX >= posX && mouseX <= posX + 18 && mouseY >= posY && mouseY <= posY + 49) {
            List<String> tooltip = new ArrayList<>();
            tooltip.add(fluid.getLocalizedName());
            tooltip.add(fluid.amount + " / " + TileSmelteryCauldron.FLUID_CAPACITY);
            return tooltip;
        }
        return null;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        // Draw elements

        //Draw the temperature bar
        // The bar
        mc.getTextureManager().bindTexture(GUI_ELEMENTS);
        this.drawTexturedModalRect(this.guiLeft + 26, this.guiTop + 13, 39, 1, 9, 52);

        // the temperature indicator <->
        int temperaturePixels = (int) (51 * Math.min(Heat.maxVisibleTemperature(), tile.getField(0)) /
                Heat.maxVisibleTemperature()); //Max temperature is brilliant white in tfc
        this.drawTexturedModalRect(this.guiLeft + 26 - 3, this.guiTop + 13 + 49 - temperaturePixels, 36, 54, 15, 5);

        // Draw tank
        mc.getTextureManager().bindTexture(GUI_ELEMENTS);

        // Draw the background
        this.drawTexturedModalRect(this.guiLeft + 141, this.guiTop + 16, 0, 102, 18, 49);

        // Draw fluid
        if (tile.getFluid() != null) {
            // Fluid
            int fillPixels = (int) Math.min(Math.ceil((tile.getFluid().amount / (float) 4000) * 47), 47);
            TextureAtlasSprite sprite = FluidSpriteCache.getStillSprite(tile.getFluid().getFluid());
            mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            BufferBuilder buffer = Tessellator.getInstance().getBuffer();

            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                    GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

            int color = tile.getFluid().getFluid().getColor();

            float r = ((color >> 16) & 0xFF) / 255f;
            float g = ((color >> 8) & 0xFF) / 255f;
            float b = (color & 0xFF) / 255f;
            float a = ((color >> 24) & 0xFF) / 255f;

            GlStateManager.color(r, g, b, a);

            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

            int startX = this.guiLeft + 141 + 1;
            int startY = this.guiTop + 16 + 48 - fillPixels;
            int endX = this.guiLeft + 141 + 17;
            int endY = this.guiTop + 16 + 48;

            buffer.pos(startX, startY, 0).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
            buffer.pos(startX, endY, 0).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
            buffer.pos(endX, endY, 0).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
            buffer.pos(endX, startY, 0).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();

            Tessellator.getInstance().draw();

            mc.renderEngine.bindTexture(GUI_ELEMENTS);
            GlStateManager.color(1, 1, 1, 1);

            // Overlay
            this.drawTexturedModalRect(this.guiLeft + 141, this.guiTop + 16, 18, 102, 18, 49);
        }

        // If on fire
        if (tile.getField(0) > 0) {
            mc.getTextureManager().bindTexture(BACKGROUND);
            for (int i = 0; i < 4; i++) {
                this.drawTexturedModalRect(guiLeft + 53 + (i * 18), guiTop + 57, 0, 166, 14, 14);
            }
        }
    }

    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        super.renderHoveredToolTip(mouseX, mouseY);
        int relX = mouseX - guiLeft;
        int relY = mouseY - guiTop;
        List<String> tooltip = getFluidTooltip(tile.getFluid(), relX, relY, 141, 16);
        if (tooltip != null) {
            String formatted = Heat.getTooltip(tile.getTemp());
            formatted += TextFormatting.WHITE;
            if (tile.isSolidified()) {
                formatted += I18n.format(Constants.MODID_TFC + ".tooltip.solid");
            } else {
                formatted += I18n.format(Constants.MODID_TFC + ".tooltip.liquid");
            }
            tooltip.add(formatted);
            this.drawHoveringText(tooltip, mouseX, mouseY, fontRenderer);
        }
    }
}
