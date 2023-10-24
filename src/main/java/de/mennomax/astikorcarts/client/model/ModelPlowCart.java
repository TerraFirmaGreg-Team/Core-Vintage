package de.mennomax.astikorcarts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import de.mennomax.astikorcarts.entity.EntityPlowCart;

public class ModelPlowCart extends ModelBase
{
    private final ModelRenderer axis;
    private final ModelRenderer[] triangle = new ModelRenderer[3];
    private final ModelRenderer shaft;
    private final ModelRenderer shaftConnector;
    private final ModelRenderer[] plowShaftUpper = new ModelRenderer[3];
    private final ModelRenderer[] plowShaftLower = new ModelRenderer[3];
    private final ModelRenderer plowHandle;
    private final ModelRenderer plowHandleGrip;
    private final ModelRenderer leftWheel;
    private final ModelRenderer rightWheel;

    public ModelPlowCart()
    {
        // --AXIS--------------------------------------
        this.axis = new ModelRenderer(this, 0, 0);
        this.axis.addBox(-12.5F, 4.0F, 0.0F, 25, 2, 2);

        // --BOTTOM-BOARD--------------------------------------
        this.triangle[0] = new ModelRenderer(this, 0, 4);
        this.triangle[0].addBox(-7.5F, -9.0F, 0.0F, 15, 2, 2);

        this.triangle[1] = new ModelRenderer(this, 8, 11);
        this.triangle[1].addBox(-5.0F, -9.0F, 0.5F, 2, 14, 2);
        this.triangle[1].rotateAngleZ = -0.175F;

        this.triangle[2] = new ModelRenderer(this, 8, 11);
        this.triangle[2].addBox(3.0F, -9.0F, 0.5F, 2, 14, 2);
        this.triangle[2].rotateAngleZ = 0.175F;
        this.triangle[2].mirror = true;

        // --Horse shafts--------------------------------------
        this.shaft = new ModelRenderer(this, 0, 8);
        this.shaft.setRotationPoint(0.0F, 0.0F, -14.0F);
        this.shaft.rotateAngleY = (float) Math.PI / 2.0F;
        this.shaft.rotateAngleZ = -0.07F;
        this.shaft.addBox(0.0F, 0.0F, -8.0F, 20, 2, 1);
        this.shaft.addBox(0.0F, 0.0F, 7.0F, 20, 2, 1);

        this.shaftConnector = new ModelRenderer(this, 0, 27);
        this.shaftConnector.setRotationPoint(0.0F, 0.0F, -14.0F);
        this.shaftConnector.rotateAngleY = (float) Math.PI / 2.0F;
        this.shaftConnector.rotateAngleZ = -0.26F;
        this.shaftConnector.addBox(-16.0F, 0.0F, -8.0F, 16, 2, 1);
        this.shaftConnector.addBox(-16.0F, 0.0F, 7.0F, 16, 2, 1);

        // --PLOW-SHAFT---------------------------------
        for (int i = 0; i < this.plowShaftUpper.length; i++)
        {
            this.plowShaftUpper[i] = new ModelRenderer(this, 56, 0);
            this.plowShaftUpper[i].addBox(-1.0F, -2.0F, -2.0F, 2, 30, 2);
            this.plowShaftUpper[i].setRotationPoint(-3.0F + 3 * i, -7.0F, 0.0F);
            this.plowShaftUpper[i].rotateAngleY = -0.523599F + (float) Math.PI / 6.0F * i;

            this.plowShaftLower[i] = new ModelRenderer(this, 42, 4);
            this.plowShaftLower[i].addBox(-1.0F, -0.7F, -0.7F, 2, 10, 2);
            this.plowShaftLower[i].setRotationPoint(0.0F, 28.0F, -1.0F);
            this.plowShaftLower[i].rotateAngleX = (float) Math.PI / 4.0F;
            this.plowShaftUpper[i].addChild(plowShaftLower[i]);
        }

        this.plowHandle = new ModelRenderer(this, 50, 4);
        this.plowHandle.addBox(-0.5F, 0.0F, -0.5F, 1, 18, 1);
        this.plowHandle.setRotationPoint(0.0F, 33.0F, 5.0F);
        this.plowHandle.rotateAngleX = (float) Math.PI / 2.0F;
        this.plowShaftUpper[1].addChild(plowHandle);

        this.plowHandleGrip = new ModelRenderer(this, 50, 23);
        this.plowHandleGrip.addBox(-0.5F, 0.0F, -1.0F, 1, 5, 1);
        this.plowHandleGrip.setRotationPoint(0.0F, 32.8F, 21.0F);
        this.plowHandleGrip.rotateAngleX = (float) Math.PI / 4.0F;
        this.plowShaftUpper[1].addChild(plowHandleGrip);

        // --LEFT-WHEEL----------------------------------
        this.leftWheel = new ModelRenderer(this, 34, 4);
        this.leftWheel.setRotationPoint(14.5F, 5.0F, 1.0F);
        this.leftWheel.addBox(-2.0F, -1.0F, -1.0F, 1, 2, 2);
        for (int i = 0; i < 8; i++)
        {
            ModelRenderer rim = new ModelRenderer(this, 0, 11);
            rim.addBox(-1.5F, -4.5F, 9.86F, 1, 9, 1);
            rim.rotateAngleX = i * (float) Math.PI / 4.0F;
            this.leftWheel.addChild(rim);

            ModelRenderer spoke = new ModelRenderer(this, 4, 11);
            spoke.addBox(-1.5F, 1.0F, -0.5F, 1, 9, 1);
            spoke.rotateAngleX = i * (float) Math.PI / 4.0F;
            this.leftWheel.addChild(spoke);
        }

        // --RIGHT-WHEEL---------------------------------
        this.rightWheel = new ModelRenderer(this, 34, 4);
        this.rightWheel.mirror = true;
        this.rightWheel.setRotationPoint(-14.5F, 5.0F, 1.0F);
        this.rightWheel.addBox(1.0F, -1.0F, -1.0F, 1, 2, 2);
        for (int i = 0; i < 8; i++)
        {
            ModelRenderer rim = new ModelRenderer(this, 0, 11);
            rim.addBox(0.5F, -4.5F, 9.86F, 1, 9, 1);
            rim.rotateAngleX = i * (float) Math.PI / 4.0F;
            this.rightWheel.addChild(rim);

            ModelRenderer spoke = new ModelRenderer(this, 4, 11);
            spoke.addBox(0.5F, 1.0F, -0.5F, 1, 9, 1);
            spoke.rotateAngleX = i * (float) Math.PI / 4.0F;
            this.rightWheel.addChild(spoke);
        }
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        this.axis.render(scale);
        this.shaft.renderWithRotation(scale);
        this.shaftConnector.renderWithRotation(scale);
        for (int i = 0; i < 3; ++i)
        {
            this.triangle[i].render(scale);
        }
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale, Entity entity)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        this.leftWheel.rotateAngleX = ((EntityPlowCart) entity).getWheelRotation();
        this.rightWheel.rotateAngleX = this.leftWheel.rotateAngleX;

        this.leftWheel.render(scale);
        this.rightWheel.render(scale);

        if (((EntityPlowCart) entity).getPlowing())
        {
            for (ModelRenderer renderer : this.plowShaftUpper)
            {
                renderer.rotateAngleX = (float) Math.PI / 4.0F;
            }
        }
        else
        {
            for (ModelRenderer renderer : this.plowShaftUpper)
            {
                renderer.rotateAngleX = (float) Math.PI / 2.5F;
            }
        }
        for (ModelRenderer renderer : this.plowShaftUpper)
        {
            renderer.render(scale);
        }
    }
}