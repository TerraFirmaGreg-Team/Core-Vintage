package net.dries007.tfc.client.render.animal;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.client.model.animal.ModelWildebeestTFC;
import net.dries007.tfc.common.objects.entity.animal.EntityWildebeestTFC;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderWildebeestTFC extends RenderLiving<EntityWildebeestTFC> {
    private static final ResourceLocation TEXTURE = TerraFirmaCraft.getID("textures/entity/animal/huntable/wildebeest.png");

    public RenderWildebeestTFC(RenderManager manager) {
        super(manager, new ModelWildebeestTFC(), 0.7F);
    }

    @Override
    protected float handleRotationFloat(EntityWildebeestTFC wildebeest, float par2) {
        return 1.0f;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityWildebeestTFC entity) {
        return TEXTURE;
    }
}
