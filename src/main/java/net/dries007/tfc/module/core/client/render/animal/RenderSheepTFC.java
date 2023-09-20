package net.dries007.tfc.module.core.client.render.animal;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.core.client.model.animal.ModelSheepBodyTFC;
import net.dries007.tfc.module.core.common.objects.entity.animal.EntitySheepTFC;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderSheepTFC extends RenderAnimalTFC<EntitySheepTFC> {
    private static final ResourceLocation SHEEP_YOUNG = TerraFirmaCraft.identifier("textures/entity/animal/livestock/sheep_young.png");
    private static final ResourceLocation SHEEP_OLD = TerraFirmaCraft.identifier("textures/entity/animal/livestock/sheep_old.png");

    public RenderSheepTFC(RenderManager renderManager) {
        super(renderManager, new ModelSheepBodyTFC(), 0.7F, SHEEP_YOUNG, SHEEP_OLD);
        this.addLayer(new LayerSheepWoolTFC(this));
    }
}
