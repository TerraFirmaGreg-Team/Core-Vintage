package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.module.animal.api.type.IAnimal;
import net.dries007.tfc.module.animal.client.model.ModelAnimalDuck;
import net.dries007.tfc.module.animal.common.entities.TFCEntityAnimal;
import net.dries007.tfc.module.animal.common.entities.livestock.EntityAnimalDuck;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalDuck extends RenderLiving<EntityAnimalDuck> {
    private static final ResourceLocation DUCK_OLD = Helpers.getID("textures/entity/animal/livestock/duck_old.png");
    private static final ResourceLocation DUCK_YOUNG = Helpers.getID("textures/entity/animal/livestock/duck_young.png");

    private static final ResourceLocation DRAKE_OLD = Helpers.getID("textures/entity/animal/livestock/drake_old.png");
    private static final ResourceLocation DRAKE_YOUNG = Helpers.getID("textures/entity/animal/livestock/drake_young.png");

    private static final ResourceLocation DUCKLING_TEXTURE = Helpers.getID("textures/entity/animal/livestock/duckling.png");

    public RenderAnimalDuck(RenderManager manager) {
        super(manager, new ModelAnimalDuck(), 0.3F);
    }

    @Override
    public void doRender(EntityAnimalDuck duck, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.15f + duck.getPercentToAdulthood() * 0.15f);
        super.doRender(duck, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnimalDuck duck) {
        float percent = (float) duck.getPercentToAdulthood();

        if (percent < 0.65f) {
            return DUCKLING_TEXTURE;
        } else if (duck.getGender() == TFCEntityAnimal.Gender.MALE) {
            return duck.getAge() == IAnimal.Age.OLD ? DRAKE_OLD : DRAKE_YOUNG;
        } else {
            return duck.getAge() == IAnimal.Age.OLD ? DUCK_OLD : DUCK_YOUNG;
        }
    }

    @Override
    protected float handleRotationFloat(EntityAnimalDuck livingBase, float partialTicks) {
        float f = livingBase.oFlap + (livingBase.wingRotation - livingBase.oFlap) * partialTicks;
        float f1 = livingBase.oFlapSpeed + (livingBase.destPos - livingBase.oFlapSpeed) * partialTicks;
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

    @Override
    protected void preRenderCallback(EntityAnimalDuck bear, float par2) {
        GlStateManager.scale(0.7f, 0.7f, 0.7f);
    }
}
