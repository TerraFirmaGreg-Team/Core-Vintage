package su.terrafirmagreg.modules.device.client.render;

import su.terrafirmagreg.api.base.client.tesr.spi.BaseTESR;
import su.terrafirmagreg.modules.device.object.tile.TileCrucible;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.client.FluidSpriteCache;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import org.lwjgl.opengl.GL11;

/**
 * Render molten metal inside crucible
 */
@SideOnly(Side.CLIENT)
public class TESRCrucible extends BaseTESR<TileCrucible> {

  @Override
  public void render(TileCrucible tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    int amount = tile.getAlloy().getAmount();
    if (amount < 1) {
      return;
    }
    Metal metal = tile.getAlloyResult();
    Fluid metalFluid = FluidsTFC.getFluidFromMetal(metal);

    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);

    TextureAtlasSprite sprite = FluidSpriteCache.getStillSprite(metalFluid);

    GlStateManager.enableAlpha();
    GlStateManager.enableBlend();
    GlStateManager.tryBlendFuncSeparate(
      GlStateManager.SourceFactor.SRC_ALPHA,
      GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
      GlStateManager.SourceFactor.ONE,
      GlStateManager.DestFactor.ZERO
    );

    int color = metalFluid.getColor();

    float r = ((color >> 16) & 0xFF) / 255F;
    float g = ((color >> 8) & 0xFF) / 255F;
    float b = (color & 0xFF) / 255F;
    float a = ((color >> 24) & 0xFF) / 255F;

    GlStateManager.color(r, g, b, a);

    rendererDispatcher.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);

    double height = 0.140625D + (0.75D - 0.015625D) * amount / tile.getAlloy().getMaxAmount();

    buffer.pos(0.1875D, height, 0.1875D)
      .tex(sprite.getInterpolatedU(3), sprite.getInterpolatedV(3))
      .normal(0, 0, 1)
      .endVertex();
    buffer.pos(0.1875D, height, 0.8125D)
      .tex(sprite.getInterpolatedU(3), sprite.getInterpolatedV(13))
      .normal(0, 0, 1)
      .endVertex();
    buffer.pos(0.8125D, height, 0.8125D)
      .tex(sprite.getInterpolatedU(13), sprite.getInterpolatedV(13))
      .normal(0, 0, 1)
      .endVertex();
    buffer.pos(0.8125D, height, 0.1875D)
      .tex(sprite.getInterpolatedU(13), sprite.getInterpolatedV(3))
      .normal(0, 0, 1)
      .endVertex();

    Tessellator.getInstance().draw();

    GlStateManager.popMatrix();
  }
}
