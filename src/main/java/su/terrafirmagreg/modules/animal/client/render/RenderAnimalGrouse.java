package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalGrouse;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalGrouse;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderAnimalGrouse extends RenderLiving<EntityAnimalGrouse> {

    private static final ResourceLocation FEMALE_YOUNG = ModUtils.resource("textures/entity/animal/livestock/grousef_young.png");
    private static final ResourceLocation FEMALE_OLD = ModUtils.resource("textures/entity/animal/livestock/grousef_old.png");

    private static final ResourceLocation MALE_YOUNG = ModUtils.resource("textures/entity/animal/livestock/grousem_young.png");
    private static final ResourceLocation MALE_OLD = ModUtils.resource("textures/entity/animal/livestock/grousem_old.png");

    private static final ResourceLocation CHICK_TEXTURE = ModUtils.resource("textures/entity/animal/livestock/grouse_chick.png");

    public RenderAnimalGrouse(RenderManager manager) {
        super(manager, new ModelAnimalGrouse(), 0.3F);
    }

    @Override
    public void doRender(EntityAnimalGrouse grouse, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.15f + grouse.getPercentToAdulthood() * 0.15f);
        super.doRender(grouse, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnimalGrouse grouse) {
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
    protected float handleRotationFloat(EntityAnimalGrouse livingBase, float partialTicks) {
        float f = livingBase.oFlap + (livingBase.wingRotation - livingBase.oFlap) * partialTicks;
        float f1 = livingBase.oFlapSpeed + (livingBase.destPos - livingBase.oFlapSpeed) * partialTicks;
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

    @Override
    protected void preRenderCallback(EntityAnimalGrouse grouse, float par2) {
        GlStateManager.scale(0.85f, 0.85f, 0.85f);
    }
}
