package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.module.animal.client.model.ModelAnimalOcelot;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderOcelot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAnimalOcelot extends RenderOcelot {
    public RenderAnimalOcelot(RenderManager renderManager) {
        super(renderManager);
        this.mainModel = new ModelAnimalOcelot();
    }
}
