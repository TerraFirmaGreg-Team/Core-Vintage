package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.animal.client.model.ModelAnimalGazelle;
import net.dries007.tfc.module.animal.common.entities.huntable.EntityAnimalGazelle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalGazelle extends RenderLiving<EntityAnimalGazelle> {
    private static final ResourceLocation TEXTURE = TerraFirmaCraft.getID("textures/entity/animal/huntable/gazelle.png");

    public RenderAnimalGazelle(RenderManager manager) {
        super(manager, new ModelAnimalGazelle(), 0.7F);
    }

    @Override
    protected float handleRotationFloat(EntityAnimalGazelle gazelle, float par2) {
        return 1.0f;
    }

    @Override
    protected void preRenderCallback(EntityAnimalGazelle gazelleTFC, float par2) {
        GlStateManager.scale(0.9f, 0.9f, 0.9f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnimalGazelle entity) {
        return TEXTURE;
    }
}
