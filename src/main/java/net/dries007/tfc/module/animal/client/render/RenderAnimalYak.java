package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.animal.client.model.ModelAnimalYak;
import net.dries007.tfc.module.animal.common.entities.TFCEntityAnimal;
import net.dries007.tfc.module.animal.common.entities.livestock.EntityAnimalYak;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalYak extends RenderAnimal<EntityAnimalYak> {
    private static final ResourceLocation TEXTURE_YOUNG = TerraFirmaCraft.getID("textures/entity/animal/livestock/yak_young.png");
    private static final ResourceLocation TEXTURE_OLD = TerraFirmaCraft.getID("textures/entity/animal/livestock/yak_young.png");

    public RenderAnimalYak(RenderManager renderManager) {
        super(renderManager, new ModelAnimalYak(), 0.7F, TEXTURE_YOUNG, TEXTURE_OLD);
    }

    protected void preRenderCallback(EntityAnimalYak yakTFC, float par2) {
        if (yakTFC.getGender() == TFCEntityAnimal.Gender.MALE)
            GlStateManager.scale(1.2f, 1.2f, 1.2f);
        else {
            GlStateManager.scale(1.15f, 1.15f, 1.15f);
        }
    }
}
