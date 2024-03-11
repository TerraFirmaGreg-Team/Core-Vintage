package su.terrafirmagreg.modules.animal.client.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalWolf;

@SideOnly(Side.CLIENT)
public class RenderAnimalWolf extends RenderWolf {
	public RenderAnimalWolf(RenderManager renderManager) {
		super(renderManager);
		this.mainModel = new ModelAnimalWolf();
	}
}
