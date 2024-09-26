package su.terrafirmagreg.modules.device.client.render;

import su.terrafirmagreg.modules.device.object.block.BlockFirePit;
import su.terrafirmagreg.modules.device.object.tile.TileFirePit;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.dries007.tfc.client.FluidSpriteCache;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import org.lwjgl.opengl.GL11;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.data.Properties.LIT;
import static su.terrafirmagreg.modules.device.object.tile.TileFirePit.SLOT_EXTRA_INPUT_END;
import static su.terrafirmagreg.modules.device.object.tile.TileFirePit.SLOT_EXTRA_INPUT_START;

/**
 * Render water in the cooking pot
 */

@SideOnly(Side.CLIENT)
public class TESRFirePit extends TileEntitySpecialRenderer<TileFirePit> {

  @Override
  public void render(@NotNull TileFirePit tile, double x, double y, double z, float partialTicks,
                     int destroyStage, float alpha) {
    super.render(tile, x, y, z, partialTicks, destroyStage, alpha);

    // Rendering liquid in the soup pot
    if (tile.getCookingPotStage() != TileFirePit.CookingPotStage.EMPTY) {
      Fluid water = FluidsTFC.FRESH_WATER.get();

      GlStateManager.pushMatrix();
      GlStateManager.translate(x, y, z);

      TextureAtlasSprite sprite = FluidSpriteCache.getStillSprite(water);

      GlStateManager.enableAlpha();
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                                          GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                                          GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

      int color = water.getColor();

      float r = ((color >> 16) & 0xFF) / 255F;
      float g = ((color >> 8) & 0xFF) / 255F;
      float b = (color & 0xFF) / 255F;
      float a = ((color >> 24) & 0xFF) / 255F;

      if (tile.getCookingPotStage() == TileFirePit.CookingPotStage.FINISHED) {
        b = 0;
        g /= 4;
        r *= 3;
      }

      GlStateManager.color(r, g, b, a);

      rendererDispatcher.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

      BufferBuilder buffer = Tessellator.getInstance().getBuffer();
      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);

      double height = 0.625D;

      buffer.pos(0.3125D, height, 0.3125D)
            .tex(sprite.getInterpolatedU(5), sprite.getInterpolatedV(5))
            .normal(0, 0, 1)
            .endVertex();
      buffer.pos(0.3125D, height, 0.6875D)
            .tex(sprite.getInterpolatedU(5), sprite.getInterpolatedV(11))
            .normal(0, 0, 1)
            .endVertex();
      buffer.pos(0.6875D, height, 0.6875D)
            .tex(sprite.getInterpolatedU(11), sprite.getInterpolatedV(11))
            .normal(0, 0, 1)
            .endVertex();
      buffer.pos(0.6875D, height, 0.3125D)
            .tex(sprite.getInterpolatedU(11), sprite.getInterpolatedV(5))
            .normal(0, 0, 1)
            .endVertex();

      Tessellator.getInstance().draw();

      GlStateManager.popMatrix();
    }
    // Render food on the grill
    if (tile.hasWorld()) {
      IBlockState state = tile.getWorld().getBlockState(tile.getPos());
      if (state.getBlock() instanceof BlockFirePit
          && state.getValue(BlockFirePit.ATTACHMENT) == BlockFirePit.FirePitAttachment.GRILL) {
        IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (cap != null) {
          int rotation = tile.getBlockMetadata();
          if (state.getValue(LIT)) {
            rotation -= 1;
          }
          float yOffset = 0.625f;

          GlStateManager.pushMatrix();
          GlStateManager.translate(x + 0.3, y + 0.003125D + yOffset, z + 0.28);
          GlStateManager.scale(0.3f, 0.3f, 0.3f);
          GlStateManager.rotate(90f, 1f, 0f, 0f);
          GlStateManager.rotate(90f * rotation, 0f, 0f, 1f);
          float leftTranslate = 1.1F;

          for (int i = SLOT_EXTRA_INPUT_START; i <= SLOT_EXTRA_INPUT_END; i++) {
            ItemStack item = cap.getStackInSlot(i);
            if (!item.isEmpty()) {
              Minecraft.getMinecraft()
                       .getRenderItem()
                       .renderItem(item, ItemCameraTransforms.TransformType.FIXED);
            }

            GlStateManager.translate(-leftTranslate, 0, 0);
            if ((i == SLOT_EXTRA_INPUT_START + 1) || (i == SLOT_EXTRA_INPUT_START + 3)) {
              GlStateManager.translate(2 * leftTranslate, -0.7f, 0);
            }
          }

          GlStateManager.popMatrix();
        }
      }
    }
  }
}
