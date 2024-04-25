package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalChicken;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalChicken;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderAnimalChicken extends RenderLiving<EntityAnimalChicken> {

    private static final ResourceLocation CHICKEN_YOUNG = ModUtils.id("textures/entity/animal/livestock/chicken_young.png");
    private static final ResourceLocation CHICKEN_OLD = ModUtils.id("textures/entity/animal/livestock/chicken_old.png");

    private static final ResourceLocation ROOSTER_YOUNG = ModUtils.id("textures/entity/animal/livestock/rooster_young.png");
    private static final ResourceLocation ROOSTER_OLD = ModUtils.id("textures/entity/animal/livestock/rooster_old.png");

    private static final ResourceLocation CHICK_TEXTURE = ModUtils.id("textures/entity/animal/livestock/chick.png");

    public RenderAnimalChicken(RenderManager manager) {
        super(manager, new ModelAnimalChicken(), 0.3F);
    }

    @Override
    public void doRender(EntityAnimalChicken chicken, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.15f + chicken.getPercentToAdulthood() * 0.15f);
        super.doRender(chicken, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnimalChicken chicken) {
        float percent = (float) chicken.getPercentToAdulthood();

        if (percent < 0.65f) {
            return CHICK_TEXTURE;
        } else if (chicken.getGender() == EntityAnimalBase.Gender.MALE) {
            return chicken.getAge() == IAnimal.Age.OLD ? ROOSTER_OLD : ROOSTER_YOUNG;
        } else {
            return chicken.getAge() == IAnimal.Age.OLD ? CHICKEN_OLD : CHICKEN_YOUNG;
        }
    }

    @Override
    protected float handleRotationFloat(EntityAnimalChicken livingBase, float partialTicks) {
        float f = livingBase.oFlap + (livingBase.wingRotation - livingBase.oFlap) * partialTicks;
        float f1 = livingBase.oFlapSpeed + (livingBase.destPos - livingBase.oFlapSpeed) * partialTicks;
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

    @Override
    protected void preRenderCallback(EntityAnimalChicken bear, float par2) {
        GlStateManager.scale(0.7f, 0.7f, 0.7f);
    }
}
