package net.dries007.tfc.module.core.client.render.animal;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.core.client.model.animal.ModelGoatTFC;
import net.dries007.tfc.module.core.common.objects.entity.animal.EntityGoatTFC;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderGoatTFC extends RenderAnimalTFC<EntityGoatTFC> {
    private static final ResourceLocation GOAT_OLD = TerraFirmaCraft.getID("textures/entity/animal/livestock/goat_old.png");
    private static final ResourceLocation GOAT_YOUNG = TerraFirmaCraft.getID("textures/entity/animal/livestock/goat_young.png");

    public RenderGoatTFC(RenderManager renderManager) {
        super(renderManager, new ModelGoatTFC(), 0.7F, GOAT_YOUNG, GOAT_OLD);
    }
}
