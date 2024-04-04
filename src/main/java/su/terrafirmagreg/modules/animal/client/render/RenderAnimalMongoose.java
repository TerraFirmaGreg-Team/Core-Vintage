package su.terrafirmagreg.modules.animal.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalMongoose;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalMongoose;

@SideOnly(Side.CLIENT)

public class RenderAnimalMongoose extends RenderLiving<EntityAnimalMongoose> {
	private static final ResourceLocation TEXTURE = ModUtils.getID("textures/entity/animal/huntable/mongoose.png");

	public RenderAnimalMongoose(RenderManager renderManager) {
		super(renderManager, new ModelAnimalMongoose(), 0.7F);
	}

	@Override
	public void doRender(@NotNull EntityAnimalMongoose mongoose, double par2, double par4, double par6, float par8, float par9) {
		this.shadowSize = (float) (0.35f + (mongoose.getPercentToAdulthood() * 0.35f));
		super.doRender(mongoose, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAnimalMongoose entity) {
		return TEXTURE;
	}
}
