package su.terrafirmagreg.modules.wood.client.model;

import su.terrafirmagreg.data.MathConstants;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodAnimalCart;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class ModelWoodAnimalCart extends ModelBase {

  private final ModelRenderer axis;
  private final ModelRenderer boardBottom;
  private final ModelRenderer shaft;
  private final ModelRenderer boardLeft;
  private final ModelRenderer boardRight;
  private final ModelRenderer boardBack;
  private final ModelRenderer boardFront;
  private final ModelRenderer leftWheel;
  private final ModelRenderer rightWheel;

  public ModelWoodAnimalCart() {
    this.textureWidth = 128;
    this.textureHeight = 64;

    this.axis = new ModelRenderer(this, 0, 21);
    this.axis.addBox(-12.5F, 4.0F, 0.0F, 25, 2, 2);

    this.boardBottom = new ModelRenderer(this, 0, 0);
    this.boardBottom.addBox(-14.5F, -10.0F, 3.0F, 29, 20, 1);
    this.boardBottom.rotateAngleX = -((float)Math.PI / 2F);
    this.boardBottom.rotateAngleY = -((float)Math.PI / 2F);

    this.shaft = new ModelRenderer(this, 0, 25);
    this.shaft.setRotationPoint(0.0F, 0.0F, -14.0F);
    this.shaft.rotateAngleY = MathConstants.PI / 2.0F;
    this.shaft.rotateAngleZ = -0.07F;
    this.shaft.addBox(0.0F, 0.0F, -8.0F, 20, 2, 1);
    this.shaft.addBox(0.0F, 0.0F, 7.0F, 20, 2, 1);

    this.boardLeft = new ModelRenderer(this, 0, 28);
    this.boardLeft.addBox(-5F, -15.5F, 9F, 8, 31, 2);
    this.boardLeft.rotateAngleX = -((float)Math.PI / 2F);
    this.boardLeft.rotateAngleZ = ((float)Math.PI / 2F);

    this.boardRight = new ModelRenderer(this, 0, 28);
    this.boardRight.addBox(-5F, -15.5F, -11F, 8, 31, 2);
    this.boardRight.rotateAngleX = -((float)Math.PI / 2F);
    this.boardRight.rotateAngleZ = ((float)Math.PI / 2F);

    this.boardBack = new ModelRenderer(this, 20, 28);
    this.boardBack.addBox(-9F, -5F, 13.5F, 18, 8, 2);

    this.boardFront = new ModelRenderer(this, 20, 28);
    this.boardFront.addBox(-9F, -5F, -15.5F, 18, 8, 2);

    // --LEFT-WHEEL----------------------------------
    this.leftWheel = new ModelRenderer(this, 54, 21);
    this.leftWheel.setRotationPoint(14.5F, 5.0F, 1.0F);
    this.leftWheel.addBox(-2.0F, -1.0F, -1.0F, 2, 2, 2);
    for (int i = 0; i < 8; i++) {
      ModelRenderer rim = new ModelRenderer(this, 0, 11);
      rim.addBox(-1.5F, -4.5F, 9.86F, 2, 9, 1);
      rim.rotateAngleX = i * MathConstants.PI / 4.0F;
      this.leftWheel.addChild(rim);

      ModelRenderer spoke = new ModelRenderer(this, 4, 11);
      spoke.addBox(-1.5F, 1.0F, -0.5F, 2, 9, 1);
      spoke.rotateAngleX = i * MathConstants.PI / 4.0F;
      this.leftWheel.addChild(spoke);
    }

    // --RIGHT-WHEEL---------------------------------
    this.rightWheel = new ModelRenderer(this, 0, 21);
    this.rightWheel.mirror = true;
    this.rightWheel.setRotationPoint(-14.5F, 5.0F, 1.0F);
    this.rightWheel.addBox(1.0F, -1.0F, -1.0F, 2, 2, 2);
    for (int i = 0; i < 8; i++) {
      ModelRenderer rim = new ModelRenderer(this, 0, 11);
      rim.addBox(0.5F, -4.5F, 9.86F, 2, 9, 1);
      rim.rotateAngleX = i * MathConstants.PI / 4.0F;
      this.rightWheel.addChild(rim);

      ModelRenderer spoke = new ModelRenderer(this, 4, 11);
      spoke.addBox(0.5F, 1.0F, -0.5F, 2, 9, 1);
      spoke.rotateAngleX = i * MathConstants.PI / 4.0F;
      this.rightWheel.addChild(spoke);
    }

  }

  @Override
  public void render(@NotNull Entity entity, float limbSwing, float limbSwingAmount,
                     float ageInTicks, float rotationYaw, float rotationPitch,
                     float scale) {
    this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch,
                           scale, entity);

    this.leftWheel.rotateAngleX = ((EntityWoodAnimalCart) entity).getWheelRotation();
    this.rightWheel.rotateAngleX = this.leftWheel.rotateAngleX;

    this.leftWheel.render(scale);
    this.rightWheel.render(scale);

    this.axis.render(scale);
    this.boardBottom.render(scale);
    this.shaft.render(scale);
    this.boardLeft.render(scale);
    this.boardRight.render(scale);
    this.boardBack.render(scale);
    this.boardFront.render(scale);
  }
}
