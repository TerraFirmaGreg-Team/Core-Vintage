package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalHare;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalHare;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderAnimalHare extends RenderLiving<EntityAnimalHare> {

    private static final ResourceLocation BROWN = ModUtils.id("textures/entity/animal/huntable/hare/brown.png");
    private static final ResourceLocation SPOTTED = ModUtils.id("textures/entity/animal/huntable/hare/spotted.png");
    private static final ResourceLocation BLACK = ModUtils.id("textures/entity/animal/huntable/hare/black.png");
    private static final ResourceLocation CREAM = ModUtils.id("textures/entity/animal/huntable/hare/cream.png");

    public RenderAnimalHare(RenderManager renderManager) {
        super(renderManager, new ModelAnimalHare(), 0.3F);
    }

    @Override
    public void doRender(EntityAnimalHare hare, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.15f + hare.getPercentToAdulthood() * 0.15f);
        super.doRender(hare, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnimalHare entity) {
        switch (entity.getHareType()) {
            case 0:
            default:
                return BROWN;
            case 1:
                return SPOTTED;
            case 2:
                return BLACK;
            case 3:
                return CREAM;
        }
    }
}
