package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.animal.client.model.ModelAnimalWildebeest;
import net.dries007.tfc.module.animal.common.entity.huntable.EntityAnimalWildebeest;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalWildebeest extends RenderLiving<EntityAnimalWildebeest> {
    private static final ResourceLocation TEXTURE = TerraFirmaCraft.getID("textures/entity/animal/huntable/wildebeest.png");

    public RenderAnimalWildebeest(RenderManager manager) {
        super(manager, new ModelAnimalWildebeest(), 0.7F);
    }

    @Override
    protected float handleRotationFloat(EntityAnimalWildebeest wildebeest, float par2) {
        return 1.0f;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnimalWildebeest entity) {
        return TEXTURE;
    }
}
