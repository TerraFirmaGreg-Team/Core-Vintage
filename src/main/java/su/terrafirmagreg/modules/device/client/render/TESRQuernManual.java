package su.terrafirmagreg.modules.device.client.render;

import su.terrafirmagreg.modules.device.object.block.BlockQuernManual;
import su.terrafirmagreg.modules.device.object.tile.TileQuernManual;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import net.dries007.horsepower.blocks.BlockHPBase;
import net.dries007.horsepower.client.model.modelvariants.HandGrindstoneModels;
import net.dries007.horsepower.client.renderer.FacingToRotation;
import net.dries007.horsepower.client.renderer.TileEntityHPBaseRenderer;
import org.lwjgl.opengl.GL11;

public class TESRQuernManual extends TileEntityHPBaseRenderer<TileQuernManual> {

  @Override
  public void render(TileQuernManual te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder buffer = tessellator.getBuffer();
    BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
    IBlockState blockState = te.getWorld().getBlockState(te.getPos());
    if (!(blockState.getBlock() instanceof BlockHPBase)) {return;}
    IBlockState centerState = blockState.withProperty(BlockQuernManual.PART, HandGrindstoneModels.CENTER);
    if (!(centerState.getBlock() instanceof BlockHPBase)) {return;}
    IBakedModel centerModel = dispatcher.getBlockModelShapes().getModelForState(centerState);

    preDestroyRender(destroyStage);
    setRenderSettings();

    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
    // The translation ensures the vertex buffer positions are relative to 0,0,0 instead of the block pos
    // This makes the translations that follow much easier
    buffer.setTranslation(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());

    if (destroyStage >= 0) {
      buffer.noColor();
      renderBlockDamage(centerState, te.getPos(), getDestroyBlockIcon(destroyStage), te.getWorld());
    } else {dispatcher.getBlockModelRenderer().renderModel(te.getWorld(), centerModel, centerState, te.getPos(), buffer, false);}

    buffer.setTranslation(0, 0, 0);

    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);

    // Apply GL transformations relative to the center of the block: 1) TE rotation and 2) crank rotation
    GlStateManager.translate(0.5, 0.5, 0.5);
    FacingToRotation.get(te.getForward()).glRotateCurrentMat();
    float rotation = te.getVisibleRotation();
    GlStateManager.rotate(rotation, 0, 1, 0);
    GlStateManager.translate(-0.5, -0.5, -0.5);

    tessellator.draw();
    GlStateManager.popMatrix();
    buffer.setTranslation(0.0D, 0.0D, 0.0D);
    postDestroyRender(destroyStage);
    RenderHelper.enableStandardItemLighting();

    renderItemWithFacing(te.getWorld(), te, te.getStackInSlot(0), x, y, z, 0.8F, 0.7F, 0.5F, 0.7F);
    renderItemWithFacing(te.getWorld(), te, te.getStackInSlot(1), x, y, z, 0.2F, 0.7F, 0.5F, 0.7F);
    renderItemWithFacing(te.getWorld(), te, te.getStackInSlot(2), x, y, z, 0.5F, 0.7F, 0.2F, 0.7F);

    super.render(te, x, y, z, partialTicks, destroyStage, alpha);
  }
}
