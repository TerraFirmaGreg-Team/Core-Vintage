package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalWildebeest;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalWildebeest;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderAnimalWildebeest extends RenderLiving<EntityAnimalWildebeest> {

    private static final ResourceLocation TEXTURE = ModUtils.resource("textures/entity/animal/huntable/wildebeest.png");

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
