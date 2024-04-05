package net.dries007.tfc.client.render.animal;

import net.dries007.tfc.client.model.animal.ModelChickenTFC;
import net.dries007.tfc.objects.entity.animal.EntityChickenTFC;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderChickenTFC extends RenderLiving<EntityChickenTFC> {
	private static final ResourceLocation CHICKEN_YOUNG = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/chicken_young.png");
	private static final ResourceLocation CHICKEN_OLD = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/chicken_old.png");

	private static final ResourceLocation ROOSTER_YOUNG = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/rooster_young.png");
	private static final ResourceLocation ROOSTER_OLD = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/rooster_old.png");

	private static final ResourceLocation CHICK_TEXTURE = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/chick.png");

	public RenderChickenTFC(RenderManager manager) {
		super(manager, new ModelChickenTFC(), 0.3F);
	}

	@Override
	public void doRender(EntityChickenTFC chicken, double par2, double par4, double par6, float par8, float par9) {
		this.shadowSize = (float) (0.15f + chicken.getPercentToAdulthood() * 0.15f);
		super.doRender(chicken, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityChickenTFC chicken) {
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
	protected float handleRotationFloat(EntityChickenTFC livingBase, float partialTicks) {
		float f = livingBase.oFlap + (livingBase.wingRotation - livingBase.oFlap) * partialTicks;
		float f1 = livingBase.oFlapSpeed + (livingBase.destPos - livingBase.oFlapSpeed) * partialTicks;
		return (MathHelper.sin(f) + 1.0F) * f1;
	}

	@Override
	protected void preRenderCallback(EntityChickenTFC bear, float par2) {
		GlStateManager.scale(0.7f, 0.7f, 0.7f);
	}
}
