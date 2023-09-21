package net.dries007.tfc.module.core.client.render.animal;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.core.client.model.animal.ModelPigTFC;
import net.dries007.tfc.module.core.common.objects.entity.animal.EntityPigTFC;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderPigTFC extends RenderAnimalTFC<EntityPigTFC> {
    private static final ResourceLocation PIG_YOUNG = TerraFirmaCraft.getID("textures/entity/animal/livestock/pig_young.png");
    private static final ResourceLocation PIG_OLD = TerraFirmaCraft.getID("textures/entity/animal/livestock/pig_old.png");

    public RenderPigTFC(RenderManager renderManager) {
        super(renderManager, new ModelPigTFC(), 0.7F, PIG_YOUNG, PIG_OLD);
    }

    @Override
    public void doRender(EntityPigTFC pig, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.35f + pig.getPercentToAdulthood() * 0.35f);
        super.doRender(pig, par2, par4, par6, par8, par9);
    }
}
