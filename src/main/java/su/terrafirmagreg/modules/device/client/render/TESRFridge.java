package su.terrafirmagreg.modules.device.client.render;

import su.terrafirmagreg.modules.device.client.model.ModelFridge;
import su.terrafirmagreg.modules.device.object.tile.TileFridge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFCTECH;

public class TESRFridge extends TileEntitySpecialRenderer<TileFridge> {

  private static final ResourceLocation FRIDGE_TEXTURES = new ResourceLocation(TFCTECH, "textures/models/fridge.png");
  private final ModelFridge model = new ModelFridge();

  @Override
  public void render(TileFridge te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    if (te.hasWorld()) {
      if (!te.isMainBlock()) {return;}
      GlStateManager.pushMatrix(); // start
      GlStateManager.translate(x, y - 1, z); // position

      //Render itemstacks
      Vec3d[] items = te.getItems();
      for (int i = 0; i < 8; i++) {
        ItemStack stack = te.getSlot(i);
        if (!stack.isEmpty()) {
          GlStateManager.pushMatrix();
          //noinspection ConstantConditions
          Vec3d item = items[i];
          GlStateManager.translate(item.x, item.y, item.z); // position
          switch (te.getRotation()) {
            case NORTH:
              break;
            case SOUTH:
              GlStateManager.rotate(-180, 0, 1, 0);
              break;
            case EAST:
              GlStateManager.rotate(-90, 0, 1, 0);
              break;
            case WEST:
              GlStateManager.rotate(-270, 0, 1, 0);
              break;
          }
          GlStateManager.rotate(90, 1, 0, 0);
          GlStateManager.scale(0.25F, 0.25F, 0.25F);
          Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
          GlStateManager.popMatrix();
        }
      }

      GlStateManager.translate(0.5F, 1.5F, 0.5F);

      bindTexture(FRIDGE_TEXTURES); // texture

      GlStateManager.rotate(180, 1, 0, 0);
      switch (te.getRotation()) {
        case NORTH:
          GlStateManager.rotate(-180, 0, 1, 0);
          break;
        case SOUTH:
          break;
        case EAST:
          GlStateManager.rotate(-90, 0, 1, 0);
          break;
        case WEST:
          GlStateManager.rotate(-270, 0, 1, 0);
          break;
      }

      model.setOpen(te.getOpen(), te.getLastOpen(), partialTicks);

      model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

      GlStateManager.popMatrix(); // end
    } else {
      //For itemstacks rendering

      GlStateManager.pushMatrix(); // start
      GlStateManager.translate(0.75F, 0.5F, 0.5F);
      GlStateManager.rotate(180, 1, 0, 0);
      GlStateManager.rotate(180, 0, 1, 0);
      GlStateManager.scale(0.8F, 0.8F, 0.8F);

      bindTexture(FRIDGE_TEXTURES); // texture

      model.setOpen(0, 0, 1.0F);

      model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

      GlStateManager.popMatrix(); // end
    }
  }

  @Override
  public boolean isGlobalRenderer(TileFridge te) {
    return true;
  }
}
