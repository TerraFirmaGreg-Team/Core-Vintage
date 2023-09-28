package net.dries007.tfc.mixins.minecraft;

import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.Heat;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@SuppressWarnings("all")
@Mixin(value = RenderItem.class)
public abstract class RenderItemMixin {

    @Shadow
    public abstract void draw(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha);

    @Inject(method = "renderItemOverlayIntoGUI", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"))
    private void onRenderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, @Nullable String text, CallbackInfo callbackInfo) {
        var heatCap = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);

        if (heatCap != null) {
            var maxHeat = Heat.maxVisibleTemperature();
            var currentHeat = heatCap.getTemperature();

            if (currentHeat <= 0) return;

            // Copied from vanilla
            // Start
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            // End

            double health = 1 - (currentHeat / maxHeat);
            int rgbfordisplay = Heat.getColor(currentHeat);

            int i = Math.round(13F - (float) health * 13F);
            int j = rgbfordisplay;

            // Copied from vanilla
            // Start
            draw(bufferbuilder, xPosition + 2, yPosition + 13, 13, 2, 0, 0, 0, 255);
            draw(bufferbuilder, xPosition + 2, yPosition + 13, i, 1, j >> 16 & 255, j >> 8 & 255, j & 255, 255);
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            // End
        }
    }
}
