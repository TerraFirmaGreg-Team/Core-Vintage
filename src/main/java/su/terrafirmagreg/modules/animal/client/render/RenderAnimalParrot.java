package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.modules.animal.client.model.ModelAnimalParrot;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderParrot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAnimalParrot extends RenderParrot {

    public RenderAnimalParrot(RenderManager renderManager) {
        super(renderManager);
        this.mainModel = new ModelAnimalParrot();
    }
}
