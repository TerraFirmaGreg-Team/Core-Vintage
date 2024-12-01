package su.terrafirmagreg.modules.device.client.render;

import su.terrafirmagreg.api.base.tesr.BaseTESR;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.object.tile.TileIngotPile;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.client.model.ModelIngotPile;

@SideOnly(Side.CLIENT)
public class TESRIngotPile extends BaseTESR<TileIngotPile> {

  private static final ResourceLocation TEXTURES = ModUtils.resource("textures/blocks/metal/base.png");

  private final ModelIngotPile model = new ModelIngotPile();

  @Override
  public void render(TileIngotPile tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    try {
      GlStateManager.color(1, 1, 1, 1);

      Metal metal = tile.getMetal();
      int count = tile.getCount();
      //noinspection ConstantConditions
//      bindTexture(new ResourceLocation(MODID_TFC, "textures/blocks/metal/" + metal.getRegistryName().getPath() + ".png"));
      bindTexture(TEXTURES);
      GlStateManager.pushMatrix();
      GlStateManager.translate(x, y, z);

      // Render Ingot Pile here
      model.renderIngots(count);
    } finally {
      GlStateManager.popMatrix();
    }
  }
}
