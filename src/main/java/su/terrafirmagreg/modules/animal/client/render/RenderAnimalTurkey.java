package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalTurkey;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalTurkey;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)

public class RenderAnimalTurkey extends RenderLiving<EntityAnimalTurkey> {

    private static final ResourceLocation MALE = ModUtils.getID("textures/entity/animal/huntable/turkeym.png");
    private static final ResourceLocation FEMALE = ModUtils.getID("textures/entity/animal/huntable/turkeyf.png");

    public RenderAnimalTurkey(RenderManager manager) {
        super(manager, new ModelAnimalTurkey(), 0.5F);
    }

    @Override
    public void doRender(@NotNull EntityAnimalTurkey turkey, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.35f + (turkey.getPercentToAdulthood() * 0.35f));
        super.doRender(turkey, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnimalTurkey turkey) {
        if (turkey.getGender() == EntityAnimalBase.Gender.MALE) {
            return MALE;
        } else {
            return FEMALE;
        }
    }

    @Override
    protected void preRenderCallback(EntityAnimalTurkey tukeyTFC, float par2) {
        GlStateManager.scale(0.8f, 0.8f, 0.8f);
    }
}
