package net.dries007.tfc.client.render.animal;

import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.client.model.animal.ModelGrouseTFC;
import net.dries007.tfc.objects.entity.animal.EntityGrouseTFC;

import static su.terrafirmagreg.api.data.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderGrouseTFC extends RenderLiving<EntityGrouseTFC> {

    private static final ResourceLocation FEMALE_YOUNG = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/grousef_young.png");
    private static final ResourceLocation FEMALE_OLD = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/grousef_old.png");

    private static final ResourceLocation MALE_YOUNG = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/grousem_young.png");
    private static final ResourceLocation MALE_OLD = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/grousem_old.png");

    private static final ResourceLocation CHICK_TEXTURE = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/grouse_chick.png");

    public RenderGrouseTFC(RenderManager manager) {
        super(manager, new ModelGrouseTFC(), 0.3F);
    }

    @Override
    public void doRender(EntityGrouseTFC grouse, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.15f + grouse.getPercentToAdulthood() * 0.15f);
        super.doRender(grouse, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityGrouseTFC grouse) {
        float percent = (float) grouse.getPercentToAdulthood();

        if (percent < 0.65f) {
            return CHICK_TEXTURE;
        } else if (grouse.getGender() == EntityAnimalBase.Gender.MALE) {
            return grouse.getAge() == IAnimal.Age.OLD ? MALE_YOUNG : MALE_OLD;
        } else {
            return grouse.getAge() == IAnimal.Age.OLD ? FEMALE_YOUNG : FEMALE_OLD;
        }
    }

    @Override
    protected float handleRotationFloat(EntityGrouseTFC livingBase, float partialTicks) {
        float f = livingBase.oFlap + (livingBase.wingRotation - livingBase.oFlap) * partialTicks;
        float f1 = livingBase.oFlapSpeed + (livingBase.destPos - livingBase.oFlapSpeed) * partialTicks;
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

    @Override
    protected void preRenderCallback(EntityGrouseTFC grouse, float par2) {
        GlStateManager.scale(0.85f, 0.85f, 0.85f);
    }
}
