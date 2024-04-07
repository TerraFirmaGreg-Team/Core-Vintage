package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.modules.animal.client.model.ModelAnimalHorse;

import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAnimalHorse extends RenderHorse {

    public RenderAnimalHorse(RenderManager renderManager) {
        super(renderManager);
        this.mainModel = new ModelAnimalHorse();
    }
}
