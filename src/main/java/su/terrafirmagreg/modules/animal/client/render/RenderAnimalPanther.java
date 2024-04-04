package su.terrafirmagreg.modules.animal.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalPanther;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalPanther;

@SideOnly(Side.CLIENT)

public class RenderAnimalPanther extends RenderLiving<EntityAnimalPanther> {
	private static final ResourceLocation TEXTURE = ModUtils.getID("textures/entity/animal/predators/panther.png");

	public RenderAnimalPanther(RenderManager renderManager) {
		super(renderManager, new ModelAnimalPanther(), 0.7F);
	}

	@Override
	public void doRender(@NotNull EntityAnimalPanther panther, double par2, double par4, double par6, float par8, float par9) {
		this.shadowSize = (float) (0.35f + (panther.getPercentToAdulthood() * 0.35f));
		super.doRender(panther, par2, par4, par6, par8, par9);
	}

	@Override
	protected float handleRotationFloat(EntityAnimalPanther par1EntityLiving, float par2) {
		return 1.0f;
	}

	@Override
	protected void preRenderCallback(EntityAnimalPanther pantherTFC, float par2) {
		GlStateManager.scale(1.1f, 1.1f, 1.1f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAnimalPanther entity) {
		return TEXTURE;
	}
}
