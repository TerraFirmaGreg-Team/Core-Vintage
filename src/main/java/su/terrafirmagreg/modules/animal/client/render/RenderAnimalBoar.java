package su.terrafirmagreg.modules.animal.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalBoar;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalBoar;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalBoar extends RenderLiving<EntityAnimalBoar> {
	private static final ResourceLocation TEXTURE = ModUtils.getID("textures/entity/animal/huntable/boar.png");

	public RenderAnimalBoar(RenderManager renderManager) {
		super(renderManager, new ModelAnimalBoar(), 0.7F);
	}

	@Override
	public void doRender(@Nonnull EntityAnimalBoar hog, double par2, double par4, double par6, float par8, float par9) {
		this.shadowSize = (float) (0.35f + (hog.getPercentToAdulthood() * 0.35f));
		super.doRender(hog, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAnimalBoar entity) {
		return TEXTURE;
	}
}
