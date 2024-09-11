package su.terrafirmagreg.modules.device.client.render;

import su.terrafirmagreg.modules.device.objects.tiles.TileOven;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import static su.terrafirmagreg.data.Properties.CURED;
import static su.terrafirmagreg.data.Properties.LIT;
import static su.terrafirmagreg.modules.device.objects.tiles.TileOven.SLOT_FUEL_1;
import static su.terrafirmagreg.modules.device.objects.tiles.TileOven.SLOT_FUEL_2;
import static su.terrafirmagreg.modules.device.objects.tiles.TileOven.SLOT_MAIN;

@SideOnly(Side.CLIENT)
public class TESROven extends TileEntitySpecialRenderer<TileOven> {

  @Override
  public void render(TileOven tile, double x, double y, double z, float partialTicks,
          int destroyStage, float alpha) {
    super.render(tile, x, y, z, partialTicks, destroyStage, alpha);

    if (tile.hasWorld()) {
      IBlockState state = tile.getWorld().getBlockState(tile.getPos());
      IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
      if (cap != null) {
        int rotation = tile.getBlockMetadata();
        if (state.getValue(LIT)) {
          rotation -= 4;
        }
        if (state.getValue(CURED)) {
          rotation -= 8;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.25, y + 0.103125D, z + 0.35);
        GlStateManager.scale(0.3f, 0.3f, 0.3f);
        GlStateManager.rotate(90f, 1f, 0f, 0f);
        GlStateManager.rotate(90f * rotation, 0f, 0f, 1f);

        switch (rotation) {
          case 0: //south
            GlStateManager.translate(0.5f, 1.3f, 0f);
          case 1: //west
            GlStateManager.translate(1.25f, -0.7f, 0f);
          case 2: //north
            GlStateManager.translate(-0.2f, -0.9f, 0f);
        }

        ItemStack fuel1 = cap.getStackInSlot(SLOT_FUEL_1);
        ItemStack fuel2 = cap.getStackInSlot(SLOT_FUEL_2);
        ItemStack main = cap.getStackInSlot(SLOT_MAIN);
        RenderItem renderer = Minecraft.getMinecraft().getRenderItem();
        if (!fuel1.isEmpty()) {
          renderer.renderItem(fuel1, ItemCameraTransforms.TransformType.FIXED);
        }
        GlStateManager.translate(-1f, 0f, 0f);
        if (!fuel2.isEmpty()) {
          renderer.renderItem(fuel2, ItemCameraTransforms.TransformType.FIXED);
        }
        GlStateManager.translate(0.5f, 1.8f, 0f);
        if (!main.isEmpty()) {
          renderer.renderItem(main, ItemCameraTransforms.TransformType.FIXED);
        }
        GlStateManager.popMatrix();
      }
    }
  }
}
