package su.terrafirmagreg.modules.device.client.render;

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

import net.dries007.horsepower.blocks.BlockHPBase;
import net.dries007.horsepower.client.model.modelvariants.GrindStoneModels;
import net.dries007.horsepower.client.renderer.TileEntityHPBaseRenderer;
import net.dries007.horsepower.util.RenderUtils;
import org.lwjgl.opengl.GL11;

public class TESRQuernHorse extends TileEntityHPBaseRenderer<TileQuernHorse> {

  @Override
  public void render(TileQuernHorse te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    IBlockState blockState = te.getWorld().getBlockState(te.getPos());
    if (!(blockState.getBlock() instanceof BlockHPBase)) {return;}
    ItemStack outputStack = te.getStackInSlot(1);
    ItemStack secondaryStack = te.getStackInSlot(2);
    if (outputStack.getCount() < secondaryStack.getCount()) {outputStack = secondaryStack;}

    if (blockState.getValue(BlockQuernHorse.FILLED)) {
      IBlockState filledState = blockState.withProperty(BlockQuernHorse.PART, GrindStoneModels.FILLED);
      if (!(filledState.getBlock() instanceof BlockHPBase)) {return;}

      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder buffer = tessellator.getBuffer();
      BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

      IBakedModel filledModel = dispatcher.getBlockModelShapes().getModelForState(filledState);

      setRenderSettings();

      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
      buffer.setTranslation(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());

      dispatcher.getBlockModelRenderer().renderModel(te.getWorld(), filledModel, filledState, te.getPos(), buffer, false);

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
      te.renderStack = ItemStack.EMPTY;
      te.grindColor = null;
    }

    if (te.hasWorker()) {renderLeash(te.getWorker(), x, y, z, 0D, 0D, 0D, partialTicks, te.getPos());}

    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);
    if (!te.getStackInSlot(0).isEmpty()) {
      renderItem(te, te.getStackInSlot(0), 0.5F, 1F, 0.5F, 1F);
      if (getWorld().isAirBlock(te.getPos().up())) {drawString(te, String.valueOf(te.getStackInSlot(0).getCount()), 0, 0.35, 0);}
    }
    GlStateManager.popMatrix();

    GlStateManager.pushMatrix();

    drawDisplayText(te, x, y, z);

    if (!te.isValid()) {RenderUtils.renderInvalidArea(te.getWorld(), te.getPos(), -1);}
    GlStateManager.popMatrix();
  }
}
