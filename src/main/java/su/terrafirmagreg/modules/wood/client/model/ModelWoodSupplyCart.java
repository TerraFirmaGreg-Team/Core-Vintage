package su.terrafirmagreg.modules.wood.client.model;

import su.terrafirmagreg.api.lib.MathConstants;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodSupplyCart;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class ModelWoodSupplyCart extends ModelBase {

    private final ModelRenderer boardBottom;
    private final ModelRenderer axis;
    private final ModelRenderer shaft;
    private final ModelRenderer boardFront;
    private final ModelRenderer[] boardsSide = new ModelRenderer[4];
    private final ModelRenderer[] boardsRear = new ModelRenderer[2];
    private final ModelRenderer[] cargo = new ModelRenderer[4];
    private final ModelRenderer leftWheel;
    private final ModelRenderer rightWheel;

    public ModelWoodSupplyCart() {
        this.textureWidth = 128;
        this.textureHeight = 64;

        // --BOTTOM-BOARD--------------------------------------
        this.boardBottom = new ModelRenderer(this, 0, 0);
        this.boardBottom.addBox(-14.5F, -11.0F, 3.0F, 29, 22, 1);
        this.boardBottom.rotateAngleY = MathConstants.PI / -2F;
        this.boardBottom.rotateAngleX = MathConstants.PI / -2F;

        // --AXIS--------------------------------------
        this.axis = new ModelRenderer(this, 0, 23);
        this.axis.addBox(-12.5F, 4.0F, 0.0F, 25, 2, 2);

        // --SHAFTS--------------------------------------
        this.shaft = new ModelRenderer(this, 0, 35);
        this.shaft.setRotationPoint(0.0F, 0.0F, -14.0F);
        this.shaft.rotateAngleY = MathConstants.PI / 2.0F;
        this.shaft.rotateAngleZ = -0.07F;
        this.shaft.addBox(0.0F, 0.0F, -8.0F, 20, 2, 1);
        this.shaft.addBox(0.0F, 0.0F, 7.0F, 20, 2, 1);

        // --FRONT-BOARD---------------------------------
        this.boardFront = new ModelRenderer(this, 0, 38);
        this.boardFront.addBox(-12.0F, -7.0F, -14.5F, 24, 10, 1);

        // --BOARDS-SIDE---------------------------------
        this.boardsSide[0] = new ModelRenderer(this, 0, 27);
        this.boardsSide[0].addBox(-13.5F, -7.0F, 0.0F, 28, 3, 1);
        this.boardsSide[0].setRotationPoint(-11.0F, 0.0F, 0.0F);
        this.boardsSide[0].rotateAngleY = MathConstants.PI / -2.0F;
        this.boardsSide[0].mirror = true;

        this.boardsSide[1] = new ModelRenderer(this, 0, 27);
        this.boardsSide[1].addBox(-14.5F, -7.0F, 0.0F, 28, 3, 1);
        this.boardsSide[1].setRotationPoint(11.0F, 0.0F, 0.0F);
        this.boardsSide[1].rotateAngleY = MathConstants.PI / 2.0F;

        this.boardsSide[2] = new ModelRenderer(this, 0, 31);
        this.boardsSide[2].addBox(-13.5F, -2.0F, 0.0F, 28, 3, 1);
        this.boardsSide[2].setRotationPoint(-11.0F, 0.0F, 0.0F);
        this.boardsSide[2].rotateAngleY = MathConstants.PI / -2.0F;
        this.boardsSide[2].mirror = true;

        this.boardsSide[3] = new ModelRenderer(this, 0, 31);
        this.boardsSide[3].addBox(-14.5F, -2.0F, 0.0F, 28, 3, 1);
        this.boardsSide[3].setRotationPoint(11.0F, 0.0F, 0.0F);
        this.boardsSide[3].rotateAngleY = MathConstants.PI / 2.0F;

        // --REAR-BOARDS---------------------------------
        this.boardsRear[0] = new ModelRenderer(this, 50, 35);
        this.boardsRear[0].addBox(10.0F, -7.0F, 14.5F, 2, 11, 1);

        this.boardsRear[1] = new ModelRenderer(this, 50, 35);
        this.boardsRear[1].addBox(-12.0F, -7.0F, 14.5F, 2, 11, 1);
        this.boardsRear[1].mirror = true;

        // --CARGO---------------------------------------
        this.cargo[0] = new ModelRenderer(this, 64, 38);
        this.cargo[0].addBox(-9.5F, -5.0F, -12.5F, 8, 8, 8);
        this.cargo[0].rotateAngleY = 0.05F;

        this.cargo[1] = new ModelRenderer(this, 64, 18);
        this.cargo[1].addBox(-1.0F, -7.0F, -12.5F, 11, 10, 10);

        this.cargo[2] = new ModelRenderer(this, 64, 0);
        this.cargo[2].addBox(-10.5F, -5.0F, -8.5F, 20, 8, 10);
        this.cargo[2].rotateAngleY = MathConstants.PI;

        this.cargo[3] = new ModelRenderer(this, 64, 54);
        this.cargo[3].addBox(-12.0F, -7.0F, 1.0F, 20, 2, 3);
        this.cargo[3].rotateAngleY = -1.067F;

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
        this.rightWheel = new ModelRenderer(this, 54, 21);
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
    public void render(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch,
                       float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        this.axis.render(scale);
        this.shaft.renderWithRotation(scale);
        this.boardBottom.render(scale);
        this.boardFront.render(scale);
        for (int i = 0; i < 2; ++i) {
            this.boardsRear[i].render(scale);
        }
        for (int i = 0; i < 4; ++i) {
            this.boardsSide[i].render(scale);
        }
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale,
                                  @NotNull Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        this.leftWheel.rotateAngleX = ((EntityWoodSupplyCart) entity).getWheelRotation();
        this.rightWheel.rotateAngleX = this.leftWheel.rotateAngleX;

        this.leftWheel.render(scale);
        this.rightWheel.render(scale);

        for (int i = 0; i < ((EntityWoodSupplyCart) entity).getCargo(); i++) {
            this.cargo[i].render(scale);
        }
    }
}
