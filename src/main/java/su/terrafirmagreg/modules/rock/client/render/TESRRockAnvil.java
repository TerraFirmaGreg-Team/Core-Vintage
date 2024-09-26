package su.terrafirmagreg.modules.rock.client.render;

import su.terrafirmagreg.modules.rock.object.tile.TileRockAnvil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;

public class TESRRockAnvil
  extends TileEntitySpecialRenderer<TileRockAnvil> {

  @Override
  public void render(@NotNull TileRockAnvil tile, double x, double y, double z, float partialTicks,
                     int destroyStage, float alpha) {
    super.render(tile, x, y, z, partialTicks, destroyStage, alpha);

    IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    if (cap != null) {
      int rotation = tile.getBlockMetadata();

      GlStateManager.pushMatrix();
      GlStateManager.translate(x + 0.5, y + 0.003125D + 0.875f, z + 0.5);
      GlStateManager.scale(0.3f, 0.3f, 0.3f);
      GlStateManager.rotate(90f, 1f, 0f, 0f);
      GlStateManager.rotate(90f * rotation, 0f, 0f, 1f);
      GlStateManager.translate(1.2f, 0, 0);

      // Hammer Item
      ItemStack hammer = cap.getStackInSlot(TileRockAnvil.SLOT_HAMMER);
      if (!hammer.isEmpty()) {
        Minecraft.getMinecraft().getRenderItem()
                 .renderItem(hammer, ItemCameraTransforms.TransformType.FIXED);
      }

      GlStateManager.translate(-1.3f, 0, 0);
      ItemStack input1 = cap.getStackInSlot(TileRockAnvil.SLOT_INPUT_1);
      if (!input1.isEmpty()) {
        Minecraft.getMinecraft().getRenderItem()
                 .renderItem(input1, ItemCameraTransforms.TransformType.FIXED);
      }

      GlStateManager.translate(-0.4f, 0, -0.05f);
      ItemStack input2 = cap.getStackInSlot(TileRockAnvil.SLOT_INPUT_2);
      if (!input2.isEmpty()) {
        Minecraft.getMinecraft().getRenderItem()
                 .renderItem(input2, ItemCameraTransforms.TransformType.FIXED);
      }

      ItemStack flux = cap.getStackInSlot(TileRockAnvil.SLOT_FLUX);
      if (!flux.isEmpty()) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.9f, -0.25f, 0.05f);
        GlStateManager.scale(0.6f, 0.6f, 0.6f);
        Minecraft.getMinecraft().getRenderItem()
                 .renderItem(flux, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
      }

      GlStateManager.popMatrix();
    }
  }

}
