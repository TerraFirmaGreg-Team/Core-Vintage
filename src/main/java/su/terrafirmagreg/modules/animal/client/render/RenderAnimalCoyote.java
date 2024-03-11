package su.terrafirmagreg.modules.animal.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalCoyote;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalCoyote;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalCoyote extends RenderLiving<EntityAnimalCoyote> {
	private static final ResourceLocation TEXTURE = ModUtils.getID("textures/entity/animal/predators/coyote.png");

	public RenderAnimalCoyote(RenderManager renderManager) {
		super(renderManager, new ModelAnimalCoyote(), 0.7F);
	}

	@Override
	public void doRender(@Nonnull EntityAnimalCoyote coyote, double par2, double par4, double par6, float par8, float par9) {
		this.shadowSize = (float) (0.35f + (coyote.getPercentToAdulthood() * 0.35f));
		super.doRender(coyote, par2, par4, par6, par8, par9);
	}

	@Override
	protected float handleRotationFloat(EntityAnimalCoyote par1EntityLiving, float par2) {
		return 1.0f;
	}

	@Override
	protected void preRenderCallback(EntityAnimalCoyote coyoteTFC, float par2) {
		GlStateManager.scale(1.0f, 1.0f, 1.0f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAnimalCoyote entity) {
		return TEXTURE;
	}
}
