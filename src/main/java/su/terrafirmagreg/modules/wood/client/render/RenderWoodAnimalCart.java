package su.terrafirmagreg.modules.wood.client.render;

import su.terrafirmagreg.api.util.ColourUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.client.model.ModelWoodAnimalCart;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodAnimalCart;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class RenderWoodAnimalCart extends Render<EntityWoodAnimalCart> {

  protected ModelBase model = new ModelWoodAnimalCart();

  public RenderWoodAnimalCart(RenderManager renderManager) {
    super(renderManager);
    this.shadowSize = 1.0F;
  }

  @Override
  public void doRender(EntityWoodAnimalCart entity, double x, double y, double z, float entityYaw,
          float partialTicks) {
    var woodType = entity.getWood();
    GlStateManager.pushMatrix();
    this.setupTranslation(x, y, z);
    this.setupRotation(entityYaw);
    this.bindEntityTexture(entity);

    if (this.renderOutlines) {
      GlStateManager.enableColorMaterial();
      GlStateManager.enableOutlineMode(this.getTeamColor(entity));
    }

    ColourUtils.setGlColor(woodType.getColor());

    this.model.render(entity, partialTicks, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

    if (this.renderOutlines) {
      GlStateManager.disableOutlineMode();
      GlStateManager.disableColorMaterial();
    }
    GlStateManager.popMatrix();
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
  }

  @Override
  protected ResourceLocation getEntityTexture(@NotNull EntityWoodAnimalCart entity) {
    return ModUtils.resource("textures/entity/wood/animal_cart.png");
  }

  public void setupTranslation(double x, double y, double z) {
    GlStateManager.translate((float) x, (float) y + 1.0F, (float) z);
  }

  public void setupRotation(float entityYaw) {
    GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
    GlStateManager.scale(-1.0F, -1.0F, 1.0F);
  }

}
