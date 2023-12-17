package tfctech.client.render.models;

import net.dries007.tfc.api.types.Metal;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;

/**
 * ModelWireDrawBench - TFCTech
 * Created using Tabula 7.0.0
 */

public class ModelWireDrawBench extends ModelBase {
    private static final float P = 0.0625F;
    private static final ModelRenderer[] DRAW_PLATES = new ModelRenderer[3];

    private final ModelRenderer Leg1;
    private final ModelRenderer Leg2;
    private final ModelRenderer Tabletop;
    private final ModelRenderer Support1;
    private final ModelRenderer Support2;
    private final ModelRenderer Stop1;
    private final ModelRenderer Stop2;

    private final ModelRenderer Barrel;
    private final ModelRenderer Shaft;
    private final ModelRenderer Holder;
    private final ModelRenderer Handle1;
    private final ModelRenderer Handle2;

    private final ModelRenderer Belt;
    private final ModelRenderer Belt0;
    private final ModelRenderer Belt1;
    private final ModelRenderer Belt2;
    private final ModelRenderer Belt3;
    private final ModelRenderer Ring1;
    private final ModelRenderer Ring2;
    private final ModelRenderer Ring3;
    private final ModelRenderer Ring4;
    private final ModelRenderer Tongs1;
    private final ModelRenderer Tongs2;
    private final ModelRenderer Tongs3;

    private final ModelRenderer wire;

    private float progress;

    private boolean isDrawplateInstalled = false;
    private int drawplateMetal;
    private int wireColor = 0x00000000;

    private EnumFacing rotation = EnumFacing.NORTH;

    public ModelWireDrawBench() {
        this.textureWidth = 128;
        this.textureHeight = 64;

        this.Tongs1 = new ModelRenderer(this, 100, 14);
        this.Tongs1.setRotationPoint(-1.0F, 13.5F, 13.0F);
        this.Tongs1.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);
        this.Belt1 = new ModelRenderer(this, 90, 4);
        this.Belt1.setRotationPoint(-2.5F, 13.5F, -1.0F);
        this.Belt1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
        this.Holder = new ModelRenderer(this, 54, 11);
        this.Holder.setRotationPoint(5.5F, 13.5F, -3.5F);
        this.Holder.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(Holder, -5.235987755982989F, 0.0F, 0.0F);
        this.Support1 = new ModelRenderer(this, 0, 5);
        this.Support1.setRotationPoint(2.5F, 11.5F, -5.0F);
        this.Support1.addBox(0.0F, 0.0F, 0.0F, 1, 4, 3, 0.0F);
        this.Support2 = new ModelRenderer(this, 0, 5);
        this.Support2.setRotationPoint(-5.5F, 11.5F, -5.0F);
        this.Support2.addBox(0.0F, 0.0F, 0.0F, 1, 4, 3, 0.0F);
        this.Belt = new ModelRenderer(this, 100, 0);
        this.Belt.setRotationPoint(-2.5F, 13.5F, 5.0F);
        this.Belt.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
        this.setRotateAngle(Belt, -0.2617993950843811F, 0.0F, 0.0F);
        this.Leg2 = new ModelRenderer(this, 0, 12);
        this.Leg2.setRotationPoint(-5.0F, 15.5F, 1.5F);
        this.Leg2.addBox(0.0F, 0.0F, 0.0F, 8, 16, 2, 0.0F);
        this.setRotateAngle(Leg2, -0.39269908169872414F, 0.0F, 0.0F);
        this.Tongs2 = new ModelRenderer(this, 100, 18);
        this.Tongs2.setRotationPoint(-2.5F, 13.5F, 10.0F);
        this.Tongs2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
        this.Ring1 = new ModelRenderer(this, 83, 20);
        this.Ring1.setRotationPoint(-1.5F, 13.5F, 11.0F);
        this.Ring1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.Ring2 = new ModelRenderer(this, 83, 26);
        this.Ring2.setRotationPoint(0.5F, 13.5F, 8.0F);
        this.Ring2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.Handle2 = new ModelRenderer(this, 54, 16);
        this.Handle2.setRotationPoint(6.0F, 13.5F, -3.5F);
        this.Handle2.addBox(0.0F, -0.5F, -6.5F, 1, 1, 13, 0.0F);
        this.setRotateAngle(Handle2, -5.235987755982989F, 0.0F, 0.0F);
        this.Belt0 = new ModelRenderer(this, 90, 1);
        this.Belt0.setRotationPoint(-2.5F, 13.5F, -3.0F);
        this.Belt0.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
        this.Leg1 = new ModelRenderer(this, 0, 12);
        this.Leg1.setRotationPoint(-5.0F, 16.5F, 13.0F);
        this.Leg1.addBox(0.0F, 0.0F, 0.0F, 8, 16, 2, 0.0F);
        this.setRotateAngle(Leg1, 0.39269908169872414F, 0.0F, 0.0F);
        this.Stop1 = new ModelRenderer(this, 0, 0);
        this.Stop1.setRotationPoint(-4.5F, 12.5F, 14.0F);
        this.Stop1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.Belt3 = new ModelRenderer(this, 90, 10);
        this.Belt3.setRotationPoint(-2.5F, 13.5F, 3.0F);
        this.Belt3.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
        this.Tabletop = new ModelRenderer(this, 0, 0);
        this.Tabletop.setRotationPoint(-7.0F, 15.5F, -7.0F);
        this.Tabletop.addBox(0.0F, 0.0F, 0.0F, 12, 2, 30, 0.0F);
        this.Ring4 = new ModelRenderer(this, 84, 12);
        this.Ring4.setRotationPoint(-2.5F, 13.5F, 8.0F);
        this.Ring4.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
        this.Shaft = new ModelRenderer(this, 54, 0);
        this.Shaft.setRotationPoint(-5.0F, 13.5F, -3.5F);
        this.Shaft.addBox(-2.0F, -0.5F, -0.5F, 15, 1, 1, 0.0F);
        this.setRotateAngle(Shaft, -5.235987755982989F, 0.0F, 0.0F);
        this.Tongs3 = new ModelRenderer(this, 110, 18);
        this.Tongs3.setRotationPoint(-0.5F, 13.5F, 10.0F);
        this.Tongs3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
        this.Ring3 = new ModelRenderer(this, 84, 26);
        this.Ring3.setRotationPoint(-3.5F, 13.5F, 8.0F);
        this.Ring3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.Barrel = new ModelRenderer(this, 100, 10);
        this.Barrel.setRotationPoint(-4.5F, 13.5F, -3.5F);
        this.Barrel.addBox(0.0F, -1.0F, -1.0F, 7, 2, 2, 0.0F);
        this.setRotateAngle(Barrel, -5.235987755982989F, 0.0F, 0.0F);
        this.Stop2 = new ModelRenderer(this, 0, 0);
        this.Stop2.setRotationPoint(0.5F, 12.5F, 14.0F);
        this.Stop2.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.Handle1 = new ModelRenderer(this, 54, 15);
        this.Handle1.setRotationPoint(6.0F, 13.5F, -3.5F);
        this.Handle1.addBox(0.0F, -6.5F, -0.5F, 1, 13, 1, 0.0F);
        this.setRotateAngle(Handle1, -5.235987755982989F, 0.0F, 0.0F);
        this.wire = new ModelRenderer(this, 0, 34);
        this.wire.setRotationPoint(-1.0F, 13.65F, 13.0F);
        this.wire.addBox(-0.5F, 0.0F, 2.0F, 1, 1, 8, 0.0F);
        this.Belt2 = new ModelRenderer(this, 90, 7);
        this.Belt2.setRotationPoint(-2.5F, 13.5F, 1.0F);
        this.Belt2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);

        // Drawplates
        DRAW_PLATES[0] = getDrawplateModel(23);
        DRAW_PLATES[1] = getDrawplateModel(26);
        DRAW_PLATES[2] = getDrawplateModel(29);

    }

    public void setRotation(EnumFacing rotation) {
        this.rotation = rotation;
    }

    public void setWire(int color) {
        wireColor = color;
    }

    public void setProgress(float value, float lastValue, float partialTicks) {
        if (value == lastValue) {
            progress = value;
        } else {
            progress = lastValue + (value - lastValue) * partialTicks;
        }
    }

    @Override
    public void render(@Nullable Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        //noinspection ConstantConditions
        super.render(entity, f, f1, f2, f3, f4, f5);

        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5f, 1.86f, 0.5f);
        switch (this.rotation) {
            case SOUTH:
                GlStateManager.rotate(-180F, 0, 1, 0);
                break;
            case EAST:
                GlStateManager.rotate(-90F, 0, 1, 0);
                break;
            case WEST:
                GlStateManager.rotate(-270F, 0, 1, 0);
                break;
            default:
                break;
        }

        GlStateManager.pushMatrix();
        GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);

        this.Leg1.render(f5);
        this.Leg2.render(f5);
        this.Tabletop.render(f5);
        this.Support1.render(f5);
        this.Support2.render(f5);
        this.Stop1.render(f5);
        this.Stop2.render(f5);

        float angle = -360.0F / 100.0F * this.progress;
        setRotateAngleInDegrees(this.Barrel, angle, 0, 0);
        setRotateAngleInDegrees(this.Shaft, angle, 0, 0);
        setRotateAngleInDegrees(this.Holder, angle, 0, 0);
        setRotateAngleInDegrees(this.Handle1, angle, 0, 0);
        setRotateAngleInDegrees(this.Handle2, angle, 0, 0);

        this.Barrel.render(f5);
        this.Shaft.render(f5);
        this.Holder.render(f5);
        this.Handle1.render(f5);
        this.Handle2.render(f5);

        setBeltOffset(this.Ring1, 99);
        setBeltOffset(this.Ring2, 99);
        setBeltOffset(this.Ring3, 99);
        setBeltOffset(this.Ring4, 99);
        setBeltOffset(this.Tongs1, 99);
        setBeltOffset(this.Tongs2, 99);
        setBeltOffset(this.Tongs3, 99);
        setBeltOffset(this.wire, 99);
        setBeltOffset(this.Belt, 99);
        setBeltOffset(this.Belt0, 15);
        setBeltOffset(this.Belt1, 45);
        setBeltOffset(this.Belt2, 75);
        setBeltOffset(this.Belt3, 90);

        if (this.progress > 99) {

            setBeltFall(this.Ring1, false);
            setBeltFall(this.Ring2, false);
            setBeltFall(this.Ring3, false);
            setBeltFall(this.Ring4, false);
            setBeltFall(this.Tongs1, false);
            setBeltFall(this.Tongs2, false);
            setBeltFall(this.Tongs3, false);
            setBeltFall(this.wire, false);
            setBeltFall(this.Belt, true);

        } else {

            this.Ring1.offsetY = 0;
            this.Ring2.offsetY = 0;
            this.Ring3.offsetY = 0;
            this.Ring4.offsetY = 0;
            this.Tongs1.offsetY = 0;
            this.Tongs2.offsetY = 0;
            this.Tongs3.offsetY = 0;
            this.wire.offsetY = 0;
            setRotateAngleInDegrees(this.Belt, 0, 0, 0);
        }

        this.Belt.render(f5);
        this.Belt0.render(f5);
        this.Belt1.render(f5);
        this.Belt2.render(f5);
        this.Belt3.render(f5);
        this.Ring1.render(f5);
        this.Ring2.render(f5);
        this.Ring3.render(f5);
        this.Ring4.render(f5);
        this.Tongs1.render(f5);
        this.Tongs2.render(f5);
        this.Tongs3.render(f5);

        if (isDrawplateInstalled) {
            ModelRenderer drawplate = DRAW_PLATES[this.drawplateMetal];
            drawplate.render(f5);
        }


        if (wireColor != 0x00000000) {
            float r = ((wireColor >> 16) & 0xFF) / 255F;
            float g = ((wireColor >> 8) & 0xFF) / 255F;
            float b = (wireColor & 0xFF) / 255F;
            float a = 0xFF; // ignore alpha, make it always 100%

            GlStateManager.color(r, g, b, a);
            this.wire.render(f5);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }

        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    public void setDrawplateMetal(@Nullable Metal metal) {
        drawplateMetal = 0;
        isDrawplateInstalled = false;
        if (metal != null) {
            if (metal.getTier() == Metal.Tier.TIER_III) {
                drawplateMetal = 0;
            } else if (metal.getTier() == Metal.Tier.TIER_IV) {
                drawplateMetal = 1;
            } else if (metal.getTier() == Metal.Tier.TIER_V) {
                drawplateMetal = 2;
            }
            isDrawplateInstalled = true;
        }
    }

    private ModelRenderer getDrawplateModel(int offsetY) {
        ModelRenderer result = new ModelRenderer(this, 95, offsetY);
        result.setRotationPoint(-4.5F, 13.5F, 16.0F);
        result.addBox(0.0F, 0.0F, 0.0F, 7, 2, 1, 0.0F);
        return result;
    }

    private void setBeltOffset(ModelRenderer modelRenderer, int maxProgress) {
        float off = 7F * P / 99F * Math.min(this.progress, maxProgress);
        modelRenderer.offsetZ = -off;
    }

    private void setBeltFall(ModelRenderer modelRenderer, boolean belt) {
        if (belt) {
            setRotateAngleInDegrees(modelRenderer, -15, 0, 0);
        } else {
            modelRenderer.offsetZ = -8F * P;
            modelRenderer.offsetY = P;
        }
    }

    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    private void setRotateAngleInDegrees(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = (float) (x * Math.PI / 180);
        modelRenderer.rotateAngleY = (float) (y * Math.PI / 180);
        modelRenderer.rotateAngleZ = (float) (z * Math.PI / 180);
    }
}
