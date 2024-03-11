package su.terrafirmagreg.modules.animal.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalMuskOxBody;
import su.terrafirmagreg.modules.animal.objects.entities.TFCEntityAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalMuskOx;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalMuskOx extends RenderAnimal<EntityAnimalMuskOx> {
	private static final ResourceLocation TEXTURE_YOUNG = ModUtils.getID("textures/entity/animal/livestock/muskox_young.png");
	private static final ResourceLocation TEXTURE_OLD = ModUtils.getID("textures/entity/animal/livestock/muskox_old.png");

	public RenderAnimalMuskOx(RenderManager renderManager) {
		super(renderManager, new ModelAnimalMuskOxBody(), 0.8F, TEXTURE_YOUNG, TEXTURE_OLD);
		this.addLayer(new LayerMuskOxWool(this));
	}

	@Override
	protected void preRenderCallback(EntityAnimalMuskOx muskoxTFC, float par2) {
		if (muskoxTFC.getGender() == TFCEntityAnimal.Gender.MALE)
			GlStateManager.scale(1.2f, 1.2f, 1.2f);
		else {
			GlStateManager.scale(1.0f, 1.0f, 1.0f);
		}
	}
}
