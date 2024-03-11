package su.terrafirmagreg.modules.animal.client.render;

import net.minecraft.client.renderer.entity.RenderLlama;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalLlama;

@SideOnly(Side.CLIENT)
public class RenderAnimalLlama extends RenderLlama {
	public RenderAnimalLlama(RenderManager renderManager) {
		super(renderManager);
		this.mainModel = new ModelAnimalLlama(0);
	}
}
