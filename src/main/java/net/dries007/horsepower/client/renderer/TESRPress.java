package net.dries007.horsepower.client.renderer;

import su.terrafirmagreg.api.util.RenderUtils;
import su.terrafirmagreg.api.data.enums.EnumPressPart;
import su.terrafirmagreg.modules.device.object.tile.TilePressHorse;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import org.lwjgl.opengl.GL11;
import net.dries007.horsepower.Configs;
import net.dries007.horsepower.blocks.BlockPress;

import static su.terrafirmagreg.api.data.Properties.EnumProp.PRESS_PART;

public class TESRPress extends TESRHPBase<TilePressHorse> {

  @Override
  public void render(TilePressHorse tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder buffer = tessellator.getBuffer();
    BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
    IBlockState blockState = tile.getWorld().getBlockState(tile.getPos());
    if (!(blockState.getBlock() instanceof BlockPress)) {
      return;
    }
    IBlockState topState = blockState.withProperty(PRESS_PART, EnumPressPart.TOP);
    if (!(topState.getBlock() instanceof BlockPress)) {
      return;
    }
    IBakedModel pressModel = dispatcher.getBlockModelShapes().getModelForState(topState);

    preDestroyRender(destroyStage);
    setRenderSettings();

    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
    // The translation ensures the vertex buffer positions are relative to 0,0,0 instead of the block pos
    // This makes the translations that follow much easier
    buffer.setTranslation(-tile.getPos().getX(), -tile.getPos().getY(), -tile.getPos().getZ());

    if (destroyStage >= 0) {
      buffer.noColor();
      renderBlockDamage(topState, tile.getPos(), getDestroyBlockIcon(destroyStage), tile.getWorld());
    } else {
      dispatcher.getBlockModelRenderer()
                .renderModel(tile.getWorld(), pressModel, blockState, tile.getPos(), buffer, false);
    }

    buffer.setTranslation(0, 0, 0);

    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);

    // Apply GL transformations relative to the center of the block: 1) TE rotation and 2) crank rotation
    float move = (tile.getField(0) / (float) (Configs.general.pointsForPress > 0 ? Configs.general.pointsForPress : 1));
    GlStateManager.translate(0.5, 0.5, 0.5);
    GlStateManager.translate(0, -(0.58 * move), 0);
    GlStateManager.translate(-0.5, -0.5, -0.5);

    tessellator.draw();
    GlStateManager.popMatrix();
    postDestroyRender(destroyStage);
    RenderHelper.enableStandardItemLighting();

    if (tile.hasWorker()) {
      renderLeash(tile.getWorker(), x, y, z, 0D, 0.4D, 0D, partialTicks, tile.getPos());
    }

    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);
    if (!tile.getStackInSlot(0).isEmpty() && move <= 0.25) {
      renderItem(tile, tile.getStackInSlot(0), 0.5F, 0.5F, 0.5F, 1F);
      drawString(tile, String.valueOf(tile.getStackInSlot(0).getCount()), 0, 0.35, 0);
    }

    if (!tile.getStackInSlot(1).isEmpty() && move <= 0.25) {
      renderItem(tile, tile.getStackInSlot(1), 0.5F, 0.5F, 0.5F, 1F);
      drawString(tile, String.valueOf(tile.getStackInSlot(1).getCount()), 0, 0.35, 0);
    }
    GlStateManager.popMatrix();

    IFluidTankProperties tankProperties = tile.getTankFluidStack()[0];
    FluidStack stack = tankProperties.getContents();
    if (stack != null && move <= 0.25) {
      float amount = (0.75F / ((float) tankProperties.getCapacity())) * stack.amount;
      TextureAtlasSprite sprite = Minecraft.getMinecraft()
                                           .getTextureMapBlocks()
                                           .getAtlasSprite(stack.getFluid().getStill().toString());
      int fluidColor = stack.getFluid().getColor(stack);

      GlStateManager.disableLighting();
      GlStateManager.pushMatrix();
      GlStateManager.enableBlend();
      GlStateManager.translate(x, y + 0.07, z);
      Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      float red = (fluidColor >> 16 & 0xFF) / 255.0F;
      float green = (fluidColor >> 8 & 0xFF) / 255.0F;
      float blue = (fluidColor & 0xFF) / 255.0F;
      GlStateManager.color(red, green, blue, 1.0F);
      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

      float xMax = 0.9f;
      float zMax = 0.9f;
      float xMin = 0.1f;
      float zMin = 0.1f;
      double uMin = sprite.getMinU();
      double uMax = sprite.getMaxU();
      double vMin = sprite.getMinV();
      double vMax = sprite.getMaxV();

      buffer.pos(xMax, amount, zMax).tex(uMax, vMin).endVertex();
      buffer.pos(xMax, amount, zMin).tex(uMin, vMax).endVertex();
      buffer.pos(xMin, amount, zMin).tex(uMin, vMax).endVertex();
      buffer.pos(xMin, amount, zMax).tex(uMax, vMin).endVertex();

      tessellator.draw();
      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
      GlStateManager.enableLighting();

    }

    GlStateManager.pushMatrix();
    drawDisplayText(tile, x, y + 1, z);

    if (!tile.isValid()) {
      RenderUtils.renderInvalidArea(tile.getWorld(), tile.getPos(), 0);
    }
    GlStateManager.popMatrix();
  }
}
