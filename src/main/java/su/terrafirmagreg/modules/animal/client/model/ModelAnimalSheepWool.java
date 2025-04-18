package su.terrafirmagreg.modules.animal.client.model;

import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalSheep;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class ModelAnimalSheepWool extends ModelQuadruped {

  public ModelAnimalSheepWool() {
    super(12, 0.0F);
    this.head = new ModelRenderer(this, 0, 0);
    this.head.addBox(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
    this.head.setRotationPoint(0.0F, 6.0F, -8.0F);
    this.body = new ModelRenderer(this, 28, 8);
    this.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 1.75F);
    this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
    this.leg1 = new ModelRenderer(this, 0, 16);
    this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
    this.leg1.setRotationPoint(-3.0F, 12.0F, 7.0F);
    this.leg2 = new ModelRenderer(this, 0, 16);
    this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
    this.leg2.setRotationPoint(3.0F, 12.0F, 7.0F);
    this.leg3 = new ModelRenderer(this, 0, 16);
    this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
    this.leg3.setRotationPoint(-3.0F, 12.0F, -5.0F);
    this.leg4 = new ModelRenderer(this, 0, 16);
    this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
    this.leg4.setRotationPoint(3.0F, 12.0F, -5.0F);
  }

  @Override
  public void render(Entity entity, float par2, float par3, float par4, float par5, float par6,
                     float par7) {
    this.setRotationAngles(par2, par3, par4, par5, par6, par7, entity);
    EntityAnimalSheep sheep = ((EntityAnimalSheep) entity);

    float percent = (float) sheep.getPercentToAdulthood();
    float ageScale = 2.0F - percent;

    GlStateManager.pushMatrix();
    GlStateManager.scale(1 / ageScale, 1 / ageScale, 1 / ageScale);
    GlStateManager.translate(0.0F, 1.5f - (1.5f * percent), 0f);
    this.head.render(par7);
    this.body.render(par7);
    this.leg1.render(par7);
    this.leg2.render(par7);
    this.leg3.render(par7);
    this.leg4.render(par7);
    GlStateManager.popMatrix();
  }
}
