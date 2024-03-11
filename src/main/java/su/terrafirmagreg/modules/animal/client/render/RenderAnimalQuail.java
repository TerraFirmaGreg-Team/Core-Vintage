package su.terrafirmagreg.modules.animal.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalQuail;
import su.terrafirmagreg.modules.animal.objects.entities.TFCEntityAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalQuail;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalQuail extends RenderLiving<EntityAnimalQuail> {
	private static final ResourceLocation FEMALE_YOUNG = ModUtils.getID("textures/entity/animal/livestock/quailf_young.png");
	private static final ResourceLocation FEMALE_OLD = ModUtils.getID("textures/entity/animal/livestock/quailf_old.png");

	private static final ResourceLocation MALE_YOUNG = ModUtils.getID("textures/entity/animal/livestock/quailm_young.png");
	private static final ResourceLocation MALE_OLD = ModUtils.getID("textures/entity/animal/livestock/quailm_old.png");

	private static final ResourceLocation CHICK_TEXTURE = ModUtils.getID("textures/entity/animal/livestock/quail_chick.png");

	public RenderAnimalQuail(RenderManager manager) {
		super(manager, new ModelAnimalQuail(), 0.3F);
	}

	@Override
	public void doRender(EntityAnimalQuail quail, double par2, double par4, double par6, float par8, float par9) {
		this.shadowSize = (float) (0.15f + quail.getPercentToAdulthood() * 0.15f);
		super.doRender(quail, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAnimalQuail quail) {
		float percent = (float) quail.getPercentToAdulthood();

		if (percent < 0.65f) {
			return CHICK_TEXTURE;
		} else if (quail.getGender() == TFCEntityAnimal.Gender.MALE) {
			return quail.getAge() == IAnimal.Age.OLD ? MALE_OLD : MALE_YOUNG;
		} else {
			return quail.getAge() == IAnimal.Age.OLD ? FEMALE_OLD : FEMALE_YOUNG;
		}
	}

	@Override
	protected float handleRotationFloat(EntityAnimalQuail livingBase, float partialTicks) {
		float f = livingBase.oFlap + (livingBase.wingRotation - livingBase.oFlap) * partialTicks;
		float f1 = livingBase.oFlapSpeed + (livingBase.destPos - livingBase.oFlapSpeed) * partialTicks;
		return (MathHelper.sin(f) + 1.0F) * f1;
	}

	@Override
	protected void preRenderCallback(EntityAnimalQuail quail, float par2) {
		GlStateManager.scale(0.8f, 0.8f, 0.8f);
	}
}
