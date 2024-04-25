package net.dries007.tfc.compat.jei.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;


import mezz.jei.api.gui.IDrawable;

import org.jetbrains.annotations.NotNull;

public class BackgroundDrawable implements IDrawable {

    private final int width;
    private final int height;
    private final ResourceLocation location;

    public BackgroundDrawable(ResourceLocation location, int width, int height) {
        this.location = location;
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void draw(@NotNull Minecraft minecraft, int xOffset, int yOffset) {
        GlStateManager.resetColor();
        minecraft.getTextureManager().bindTexture(this.location);
        GuiUtils.drawTexturedModalRect(xOffset, yOffset, 0, 0, this.width, this.height, 0);
    }
}
