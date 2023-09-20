package net.dries007.tfc.module.core.client.render.animal;

import net.dries007.tfc.module.core.client.model.animal.ModelParrotTFC;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderParrot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderParrotTFC extends RenderParrot {
    public RenderParrotTFC(RenderManager renderManager) {
        super(renderManager);
        this.mainModel = new ModelParrotTFC();
    }
}
