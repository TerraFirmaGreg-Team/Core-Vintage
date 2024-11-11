package su.terrafirmagreg.modules.device.client.render;

import su.terrafirmagreg.api.util.RenderUtils;
import su.terrafirmagreg.modules.device.object.block.BlockQuernHorse;
import su.terrafirmagreg.modules.device.object.tile.TileQuernHorse;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;
import se.gory_moon.horsepower.client.renderer.TESRHPBase;

import static su.terrafirmagreg.api.data.Properties.BoolProp.FILLED;

public class TESRQuernHorse extends TESRHPBase<TileQuernHorse> {

  @Override
  public void render(TileQuernHorse tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    IBlockState blockState = tile.getWorld().getBlockState(tile.getPos());
    if (!(blockState.getBlock() instanceof BlockQuernHorse)) {
      return;
    }
    ItemStack outputStack = tile.getStackInSlot(1);
    ItemStack secondaryStack = tile.getStackInSlot(2);
    if (outputStack.getCount() < secondaryStack.getCount()) {
      outputStack = secondaryStack;
    }

    if (blockState.getValue(FILLED)) {
      IBlockState filledState = blockState.withProperty(FILLED, true);
      if (!(filledState.getBlock() instanceof BlockQuernHorse)) {
        return;
      }

      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder buffer = tessellator.getBuffer();
      BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

      IBakedModel filledModel = dispatcher.getBlockModelShapes().getModelForState(filledState);

      setRenderSettings();

      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
      buffer.setTranslation(-tile.getPos().getX(), -tile.getPos().getY(), -tile.getPos().getZ());

      dispatcher.getBlockModelRenderer()
                .renderModel(tile.getWorld(), filledModel, filledState, tile.getPos(), buffer, false);

      GlStateManager.pushMatrix();
      GlStateManager.translate(x, y, z);

      GlStateManager.translate(0.5, 0.5, 0.5);
      float maxStackSize = outputStack.getMaxStackSize() > 0 ? outputStack.getMaxStackSize() : 1F;
      float fillState = 0.23F * (((float) outputStack.getCount()) / maxStackSize);
      GlStateManager.translate(0, -0.187 + fillState, 0);
      GlStateManager.translate(-0.5, -0.5, -0.5);

      tessellator.draw();
      GlStateManager.popMatrix();
      buffer.setTranslation(0.0D, 0.0D, 0.0D);
      RenderHelper.enableStandardItemLighting();
    } else if (outputStack.isEmpty()) {
      tile.renderStack = ItemStack.EMPTY;
      tile.grindColor = null;
    }

    if (tile.hasWorker()) {
      renderLeash(tile.getWorker(), x, y, z, 0D, 0D, 0D, partialTicks, tile.getPos());
    }

    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);
    if (!tile.getStackInSlot(0).isEmpty()) {
      renderItem(tile, tile.getStackInSlot(0), 0.5F, 1F, 0.5F, 1F);
      if (getWorld().isAirBlock(tile.getPos().up())) {
        drawString(tile, String.valueOf(tile.getStackInSlot(0).getCount()), 0, 0.35, 0);
      }
    }
    GlStateManager.popMatrix();

    GlStateManager.pushMatrix();

    drawDisplayText(tile, x, y, z);

    if (!tile.isValid()) {
      RenderUtils.renderInvalidArea(tile.getWorld(), tile.getPos(), -1);
    }
    GlStateManager.popMatrix();
  }
}
