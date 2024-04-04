package su.terrafirmagreg.modules.animal.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalSaberTooth;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalSaberTooth;

@SideOnly(Side.CLIENT)

public class RenderAnimalSaberTooth extends RenderLiving<EntityAnimalSaberTooth> {
	private static final ResourceLocation TEXTURE = ModUtils.getID("textures/entity/animal/predators/sabertooth.png");

	public RenderAnimalSaberTooth(RenderManager renderManager) {
		super(renderManager, new ModelAnimalSaberTooth(), 0.7F);
	}

	@Override
	public void doRender(@NotNull EntityAnimalSaberTooth sabertooth, double par2, double par4, double par6, float par8, float par9) {
		this.shadowSize = (float) (0.35f + (sabertooth.getPercentToAdulthood() * 0.35f));
		super.doRender(sabertooth, par2, par4, par6, par8, par9);
	}

	@Override
	protected float handleRotationFloat(@NotNull EntityAnimalSaberTooth par1EntityLiving, float par2) {
		return 1.0f;
	}

	@Override
	protected void preRenderCallback(@NotNull EntityAnimalSaberTooth sabertooth, float par2) {
		GlStateManager.scale(1.3f, 1.3f, 1.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(@NotNull EntityAnimalSaberTooth entity) {
		return TEXTURE;
	}
}
