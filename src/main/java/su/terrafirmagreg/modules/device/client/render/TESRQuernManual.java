package su.terrafirmagreg.modules.device.client.render;

import su.terrafirmagreg.modules.device.object.tile.TileQuernManual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import org.lwjgl.opengl.GL11;

import org.jetbrains.annotations.NotNull;

public class TESRQuernManual extends TileEntitySpecialRenderer<TileQuernManual> {

  @Override
  public void render(@NotNull TileQuernManual tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    super.render(tile, x, y, z, partialTicks, destroyStage, alpha);

    IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    if (cap != null) {
      ItemStack input = cap.getStackInSlot(TileQuernManual.SLOT_INPUT);
      ItemStack output = cap.getStackInSlot(TileQuernManual.SLOT_OUTPUT);
      ItemStack handstone = cap.getStackInSlot(TileQuernManual.SLOT_HANDSTONE);
      var mc = Minecraft.getMinecraft();

      if (!output.isEmpty()) {
        for (int i = 0; i < output.getCount(); i++) {
          double yPos = y + 0.625;
          GlStateManager.enableRescaleNormal();
          GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
          GlStateManager.enableBlend();
          RenderHelper.enableStandardItemLighting();
          GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
          GlStateManager.pushMatrix();

          switch (Math.floorDiv(i, 16)) {
            case 0: {
              GlStateManager.translate(x + 0.125, yPos, z + 0.125 + (0.046875 * i));
              GlStateManager.rotate(75, 1, 0, 0);
              break;
            }
            case 1: {
              GlStateManager.translate(x + 0.125 + (0.046875 * (i - 16)), yPos, z + 0.875);
              GlStateManager.rotate(90, 0, 1, 0);
              GlStateManager.rotate(75, 1, 0, 0);
              break;
            }
            case 2: {
              GlStateManager.translate(x + 0.875, yPos, z + 0.875 - (0.046875 * (i - 32)));
              GlStateManager.rotate(180, 0, 1, 0);
              GlStateManager.rotate(75, 1, 0, 0);
              break;
            }
            case 3: {
              GlStateManager.translate(x + 0.875 - (0.046875 * (i - 48)), yPos, z + 0.125);
              GlStateManager.rotate(270, 0, 1, 0);
              GlStateManager.rotate(75, 1, 0, 0);
              break;
            }
            default: {
              GlStateManager.translate(x + 0.5, y + 1.0, z + 0.5);
              GlStateManager.rotate((tile.getWorld().getTotalWorldTime() + partialTicks) * 4, 0, 1, 0);
            }
          }

          GlStateManager.scale(0.125, 0.125, 0.125);

          IBakedModel outputModel = mc.getRenderItem().getItemModelWithOverrides(output, tile.getWorld(), null);
          outputModel = ForgeHooksClient.handleCameraTransforms(outputModel, ItemCameraTransforms.TransformType.FIXED, false);

          mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
          mc.getRenderItem().renderItem(output, outputModel);

          GlStateManager.popMatrix();
          GlStateManager.disableRescaleNormal();
          GlStateManager.disableBlend();
        }
      }

      if (!handstone.isEmpty()) {
        int rotationTicks = tile.getRotationTimer();
        double center = (rotationTicks > 0) ? 0.497 + (tile.getWorld().rand.nextDouble() * 0.006) : 0.5;

        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
        GlStateManager.enableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + center, y + 0.75, z + center);

        if (rotationTicks > 0) {
          GlStateManager.rotate((rotationTicks - partialTicks) * 4, 0, 1, 0);
        }

        IBakedModel handstoneModel = mc.getRenderItem().getItemModelWithOverrides(handstone, tile.getWorld(), null);
        handstoneModel = ForgeHooksClient.handleCameraTransforms(handstoneModel, ItemCameraTransforms.TransformType.FIXED, false);

        GlStateManager.scale(1.25, 1.25, 1.25);
        mc.getTextureManager()
          .bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        mc.getRenderItem().renderItem(handstone, handstoneModel);

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
      }

      if (!input.isEmpty()) {
        double height = (handstone.isEmpty()) ? 0.75 : 0.875;
        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
        GlStateManager.enableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y + height, z + 0.5);
        GlStateManager.rotate(45, 0, 1, 0);
        GlStateManager.scale(0.5, 0.5, 0.5);

        IBakedModel inputModel = mc.getRenderItem().getItemModelWithOverrides(input, tile.getWorld(), null);
        inputModel = ForgeHooksClient.handleCameraTransforms(inputModel,
                                                             ItemCameraTransforms.TransformType.GROUND, false);

        mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        mc.getRenderItem().renderItem(input, inputModel);

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
      }
    }
  }
}
