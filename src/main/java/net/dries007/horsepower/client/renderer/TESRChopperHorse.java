package net.dries007.horsepower.client.renderer;

import su.terrafirmagreg.api.util.RenderUtils;
import su.terrafirmagreg.api.data.enums.EnumChopperPart;
import su.terrafirmagreg.modules.device.object.tile.TileChopperHorse;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import org.lwjgl.opengl.GL11;
import net.dries007.horsepower.blocks.BlockChopperHorse;

import static su.terrafirmagreg.api.data.Properties.EnumProp.CHOPPER_PART;

public class TESRChopperHorse extends TESRHPBase<TileChopperHorse> {

  @Override
  public void render(TileChopperHorse tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder buffer = tessellator.getBuffer();
    BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
    IBlockState blockState = tile.getWorld().getBlockState(tile.getPos());
    if (!(blockState.getBlock() instanceof BlockChopperHorse)) {
      return;
    }
    IBlockState bladeState = blockState.withProperty(CHOPPER_PART, EnumChopperPart.BLADE);
    if (!(bladeState.getBlock() instanceof BlockChopperHorse)) {
      return;
    }
    IBakedModel bladeModel = dispatcher.getBlockModelShapes().getModelForState(bladeState);

    preDestroyRender(destroyStage);
    setRenderSettings();

    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
    // The translation ensures the vertex buffer positions are relative to 0,0,0 instead of the block pos
    // This makes the translations that follow much easier
    buffer.setTranslation(-tile.getPos().getX(), -tile.getPos().getY(), -tile.getPos().getZ());

    if (destroyStage >= 0) {
      buffer.noColor();
      renderBlockDamage(bladeState, tile.getPos(), getDestroyBlockIcon(destroyStage), tile.getWorld());
    } else {
      dispatcher.getBlockModelRenderer()
                .renderModel(tile.getWorld(), bladeModel, blockState, tile.getPos(), buffer, false);
    }

    buffer.setTranslation(0, 0, 0);

    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);

    // Apply GL transformations relative to the center of the block: 1) TE rotation and 2) crank rotation
    GlStateManager.translate(0.5, 0.5, 0.5);
    GlStateManager.translate(0, tile.getVisualWindup(), 0);
    GlStateManager.translate(-0.5, -0.5, -0.5);

    tessellator.draw();
    GlStateManager.popMatrix();
    buffer.setTranslation(0.0D, 0.0D, 0.0D);
    postDestroyRender(destroyStage);
    RenderHelper.enableStandardItemLighting();

    renderLeach(x + 0.5, y + 2.9 + tile.getVisualWindup(), z + 0.5, x + 0.5, y + 0.2, z + 0.5, x + 0.5, y + 1.7, z + 0.5);

    if (tile.hasWorker()) {
      renderLeash(tile.getWorker(), x, y, z, 0D, 1.1D, 0D, partialTicks, tile.getPos());
    }

    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);
    if (!tile.getStackInSlot(0).isEmpty()) {
      renderStillItem(tile, tile.getStackInSlot(0), 0.5F, 0.54F, 0.5F, 1.3F);
    }
    GlStateManager.popMatrix();

    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);
    if (!tile.getStackInSlot(1).isEmpty()) {
      renderStillItem(tile, tile.getStackInSlot(1), 0.5F, 0.54F, 0.5F, 1.3F);
    }
    GlStateManager.popMatrix();

    GlStateManager.pushMatrix();

    drawDisplayText(tile, x, y + 1, z);

    if (!tile.isValid()) {
      RenderUtils.renderInvalidArea(tile.getWorld(), tile.getPos(), 0);
    }
    GlStateManager.popMatrix();
  }
}
