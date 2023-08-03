package net.dries007.tfc.client.render.animal;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

import javax.annotation.ParametersAreNonnullByDefault;
import net.dries007.tfc.client.model.animal.ModelCowTFC;
import net.dries007.tfc.objects.entity.animal.EntityCowTFC;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderCowTFC extends RenderAnimalTFC<EntityCowTFC> {
    private static final ResourceLocation COW_YOUNG = new ResourceLocation(MOD_ID, "textures/entity/animal/livestock/cow_young.png");
    private static final ResourceLocation COW_OLD = new ResourceLocation(MOD_ID, "textures/entity/animal/livestock/cow_old.png");

    public RenderCowTFC(RenderManager renderManager) {
        super(renderManager, new ModelCowTFC(), 0.7F, COW_YOUNG, COW_OLD);
    }
}
