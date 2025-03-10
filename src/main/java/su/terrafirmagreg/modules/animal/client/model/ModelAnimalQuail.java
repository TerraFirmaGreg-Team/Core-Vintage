package su.terrafirmagreg.modules.animal.client.model;

import su.terrafirmagreg.api.util.MathUtils;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalQuail;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

/**
 * ModelQuailTFC Created using Tabula 7.1.0
 */

@SideOnly(Side.CLIENT)

public class ModelAnimalQuail extends ModelBase {

  public ModelRenderer head;
  public ModelRenderer bodyTop;
  public ModelRenderer wingR;
  public ModelRenderer body;
  public ModelRenderer wingL;
  public ModelRenderer beak1;
  public ModelRenderer beak2;
  public ModelRenderer crown;
  public ModelRenderer wingR2;
  public ModelRenderer body2;
  public ModelRenderer tail;
  public ModelRenderer wingL2;
  public ModelRenderer legL1;
  public ModelRenderer legL2;
  public ModelRenderer feetL;
  public ModelRenderer legR1;
  public ModelRenderer legR2;
  public ModelRenderer feetR;

  public ModelAnimalQuail() {
    textureWidth = 64;
    textureHeight = 32;

    head = new ModelRenderer(this, 2, 4);
    head.setRotationPoint(0.0F, 17.0F, -3.2F);
    head.addBox(-2.0F, -6.0F, -2.0F, 4, 5, 3, 0.0F);
    crown = new ModelRenderer(this, 0, -3);
    crown.setRotationPoint(0.0F, -7.5F, -2.0F);
    crown.addBox(0.0F, -1.5F, -1.5F, 0, 3, 3, 0.0F);
    body2 = new ModelRenderer(this, 37, 12);
    body2.setRotationPoint(0.0F, 4.0F, 0.5F);
    body2.addBox(-1.5F, -2.0F, -2.0F, 3, 3, 4, 0.0F);
    setRotateAngle(body2, 0.08726646259971647F, 0.0F, 0.0F);
    wingL = new ModelRenderer(this, 16, 23);
    wingL.mirror = true;
    wingL.setRotationPoint(3.5F, 16.0F, 1.5F);
    wingL.addBox(-0.5F, 0.0F, -3.0F, 1, 4, 5, 0.0F);
    setRotateAngle(wingL, -0.2617993877991494F, 0.0F, -0.0F);
    wingL2 = new ModelRenderer(this, 20, 20);
    wingL2.setRotationPoint(0.0F, 0.0F, 2.0F);
    wingL2.addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
    wingR2 = new ModelRenderer(this, 20, 20);
    wingR2.setRotationPoint(0.0F, 0.0F, 2.0F);
    wingR2.addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
    wingR = new ModelRenderer(this, 16, 23);
    wingR.setRotationPoint(-3.5F, 16.0F, 1.5F);
    wingR.addBox(-0.5F, 0.0F, -3.0F, 1, 4, 5, 0.0F);
    setRotateAngle(wingR, -0.2617993877991494F, 0.0F, 0.0F);
    tail = new ModelRenderer(this, 40, 6);
    tail.setRotationPoint(0.0F, 1.0F, -0.2F);
    tail.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
    setRotateAngle(tail, 0.08726646259971647F, 0.0F, 0.0F);
    body = new ModelRenderer(this, 32, 19);
    body.setRotationPoint(0.0F, 18.0F, 1.0F);
    body.addBox(-3.0F, -4.0F, -3.0F, 6, 7, 6, 0.0F);
    setRotateAngle(body, 1.3962634015954636F, 0.0F, 0.0F);
    bodyTop = new ModelRenderer(this, 0, 12);
    bodyTop.setRotationPoint(0.0F, 17.0F, -3.1F);
    bodyTop.addBox(-2.5F, -2.0F, -1.5F, 5, 4, 4, 0.0F);
    setRotateAngle(bodyTop, 0.6108652381980153F, 0.0F, 0.0F);
    beak1 = new ModelRenderer(this, 20, 14);
    beak1.setRotationPoint(0.0F, -3.5F, -2.8F);
    beak1.addBox(-1.0F, -1.0F, -1.0F, 2, 1, 2, 0.0F);
    setRotateAngle(beak1, 0.17453292519943295F, 0.0F, 0.0F);
    beak2 = new ModelRenderer(this, 20, 14);
    beak2.setRotationPoint(0.0F, -3.5F, -2.8F);
    beak2.addBox(-1.0F, -0.5F, -1.0F, 2, 1, 2, 0.0F);

    legR1 = new ModelRenderer(this, 5, 29);
    legR1.setRotationPoint(-1.5F, 21.0F, 0.0F);
    legR1.addBox(-0.5F, -1.0F, 0.0F, 1, 2, 1, 0.2F);
    setRotateAngle(legR1, 0.08726646259971647F, 0.0F, 0.0F);
    legR2 = new ModelRenderer(this, 5, 26);
    legR2.setRotationPoint(0.0F, 0.8F, 0.0F);
    legR2.addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
    setRotateAngle(legR2, -0.17453292519943295F, 0.0F, 0.0F);
    feetR = new ModelRenderer(this, 3, 22);
    feetR.setRotationPoint(1.0F, 1.8F, 1.8F);
    feetR.addBox(-2.5F, 0.0F, -3.0F, 3, 1, 3, 0.0F);
    setRotateAngle(feetR, 0.04363323129985824F, 0.0F, 0.0F);
    legL1 = new ModelRenderer(this, 5, 29);
    legL1.mirror = true;
    legL1.setRotationPoint(1.5F, 21.0F, 0.0F);
    legL1.addBox(-0.5F, -1.0F, 0.0F, 1, 2, 1, 0.2F);
    setRotateAngle(legL1, 0.08726646259971647F, 0.0F, 0.0F);
    legL2 = new ModelRenderer(this, 5, 26);
    legL2.mirror = true;
    legL2.setRotationPoint(0.0F, 0.8F, 0.0F);
    legL2.addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
    setRotateAngle(legL2, -0.17453292519943295F, 0.0F, 0.0F);
    feetL = new ModelRenderer(this, 3, 22);
    feetL.mirror = true;
    feetL.setRotationPoint(1.0F, 1.8F, 1.8F);
    feetL.addBox(-2.5F, 0.0F, -3.0F, 3, 1, 3, 0.0F);
    setRotateAngle(feetL, 0.04363323129985824F, 0.0F, 0.0F);

    head.addChild(crown);
    body.addChild(body2);
    wingL.addChild(wingL2);
    wingR.addChild(wingR2);
    body2.addChild(tail);
    head.addChild(beak1);
    head.addChild(beak2);
    legR1.addChild(legR2);
    legR2.addChild(feetR);
    legL1.addChild(legL2);
    legL2.addChild(feetL);
  }

  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.rotateAngleX = x;
    modelRenderer.rotateAngleY = y;
    modelRenderer.rotateAngleZ = z;
  }

  @Override
  public void render(@NotNull Entity entity, float par2, float par3, float par4, float par5,
                     float par6, float par7) {
    this.setRotationAngles(par2, par3, par4, par5, par6, par7, entity);

    EntityAnimalQuail quail = ((EntityAnimalQuail) entity);

    float percent = (float) quail.getPercentToAdulthood();
    float ageScale = 2.0F - percent;

    if (percent < 0.5) {
      tail.isHidden = true;
    }

    GlStateManager.pushMatrix();
    GlStateManager.scale(1 / ageScale, 1 / ageScale, 1 / ageScale);
    GlStateManager.translate(0.0F, 1.5f - (1.5f * percent), 0f);

    head.render(par7);
    body.render(par7);
    bodyTop.render(par7);
    legR1.render(par7);
    legL1.render(par7);
    wingR.render(par7);
    wingL.render(par7);
    GlStateManager.popMatrix();
  }

  @Override
  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5,
                                float par6, Entity ent) {
    this.head.rotateAngleX = -(par5 / (180F / MathUtils.PI));
    this.head.rotateAngleY = par4 / (180F / MathUtils.PI);

    setRotateAngle(wingR, -0.2617993877991494F, 0.0F, 0.0F);
    setRotateAngle(wingL, -0.2617993877991494F, 0.0F, -0.0F);

    this.legR1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
    this.legL1.rotateAngleX = MathHelper.cos(par1 * 0.6662F + MathUtils.PI) * 1.4F * par2;
    this.wingR.rotateAngleZ = par3;
    this.wingL.rotateAngleZ = -par3;

    tail.isHidden = false;
  }
}
