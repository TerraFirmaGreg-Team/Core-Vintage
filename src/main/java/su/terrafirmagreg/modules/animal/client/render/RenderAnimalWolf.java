package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.modules.animal.client.model.ModelAnimalWolf;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAnimalWolf extends RenderWolf {

  public RenderAnimalWolf(RenderManager renderManager) {
    super(renderManager);
    this.mainModel = new ModelAnimalWolf();
  }
}
