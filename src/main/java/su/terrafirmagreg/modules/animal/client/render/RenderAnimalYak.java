package su.terrafirmagreg.modules.animal.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalYak;
import su.terrafirmagreg.modules.animal.objects.entities.TFCEntityAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalYak;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalYak extends RenderAnimal<EntityAnimalYak> {
	private static final ResourceLocation TEXTURE_YOUNG = ModUtils.getID("textures/entity/animal/livestock/yak_young.png");
	private static final ResourceLocation TEXTURE_OLD = ModUtils.getID("textures/entity/animal/livestock/yak_young.png");

	public RenderAnimalYak(RenderManager renderManager) {
		super(renderManager, new ModelAnimalYak(), 0.7F, TEXTURE_YOUNG, TEXTURE_OLD);
	}

	protected void preRenderCallback(EntityAnimalYak yakTFC, float par2) {
		if (yakTFC.getGender() == TFCEntityAnimal.Gender.MALE)
			GlStateManager.scale(1.2f, 1.2f, 1.2f);
		else {
			GlStateManager.scale(1.15f, 1.15f, 1.15f);
		}
	}
}
