package net.dries007.tfc.client.button;

import net.dries007.tfc.module.core.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.module.metal.client.gui.GuiMetalAnvil;
import net.dries007.tfc.module.metal.objects.tiles.TEMetalAnvil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class GuiButtonAnvilPlan extends GuiButtonTFC implements IButtonTooltip {
    private final String tooltip;
    private final TEMetalAnvil tile;

    public GuiButtonAnvilPlan(TEMetalAnvil tile, int id, int guiLeft, int guiTop) {
        // Plan Button
        super(id, guiLeft + 21, guiTop + 40, 18, 18, "");
        this.tooltip = I18n.format("tfc.tooltip.anvil_plan");
        this.tile = tile;
    }

    @Override
    public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            GlStateManager.color(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(GuiMetalAnvil.ANVIL_BACKGROUND);
            hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            drawModalRectWithCustomSizedTexture(x, y, 218, 0, 18, 18, 256, 256);
            AnvilRecipe recipe = tile.getRecipe();
            if (recipe != null) {
                ItemStack stack = recipe.getPlanIcon();
                drawItemStack(stack, x + 1, y + 1);
            } else {
                drawTexturedModalRect(x + 1, y + 1, 236, 0, 16, 16);
            }
            mouseDragged(mc, mouseX, mouseY);
        }
    }

    @Override
    public String getTooltip() {
        return tooltip;
    }

    @Override
    public boolean hasTooltip() {
        return tooltip != null;
    }
}
