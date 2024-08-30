package se.gory_moon.horsepower.client.renderer;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;


import se.gory_moon.horsepower.tileentity.TileEntityFiller;

public class TileEntityFillerRender extends TileEntitySpecialRenderer<TileEntityFiller> {

    @Override
    public void render(TileEntityFiller tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        drawDisplayText(tile, x, y, z);
        super.render(tile, x, y, z, partialTicks, destroyStage, alpha);
    }

    public void drawDisplayText(TileEntity tile, double x, double y, double z) {
        ITextComponent itextcomponent = tile.getDisplayName();

        if (itextcomponent != null && this.rendererDispatcher.cameraHitResult != null && tile.getPos()
                .equals(this.rendererDispatcher.cameraHitResult.getBlockPos())) {
            this.setLightmapDisabled(true);
            this.drawCustomNameplate(tile, itextcomponent.getFormattedText(), x, y, z, 12, 0);
            this.drawCustomNameplate(tile, TileEntityHPBaseRenderer.LEAD_LOOKUP.getFormattedText(), x, y, z, 12, -0.25F);
            this.setLightmapDisabled(false);
        }
    }

    protected void drawCustomNameplate(TileEntity tile, String str, double x, double y, double z, int maxDistance, float offset) {
        Entity entity = this.rendererDispatcher.entity;
        double d0 = tile.getDistanceSq(entity.posX, entity.posY, entity.posZ);

        if (d0 <= (double) (maxDistance * maxDistance)) {
            float f = this.rendererDispatcher.entityYaw;
            float f1 = this.rendererDispatcher.entityPitch;
            EntityRenderer.drawNameplate(this.getFontRenderer(), str, (float) x + 0.5F, (float) y + 1.5F + offset, (float) z + 0.5F, 0, f, f1, false,
                    false);
        }
    }
}
