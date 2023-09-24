package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.module.animal.api.type.IAnimal;
import net.dries007.tfc.module.animal.client.model.ModelAnimalSheepWool;
import net.dries007.tfc.module.animal.common.entity.livestock.EntityAnimalSheep;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class LayerSheepWool implements LayerRenderer<EntityAnimalSheep> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft:textures/entity/sheep/sheep_fur.png");
    private static final ResourceLocation OLD_TEXTURE = new ResourceLocation("tfc:textures/entity/animal/livestock/sheep_fur_old.png");
    private final RenderAnimalSheep sheepRenderer;
    private final ModelAnimalSheepWool sheepModel = new ModelAnimalSheepWool();

    public LayerSheepWool(RenderAnimalSheep sheepRendererIn) {
        this.sheepRenderer = sheepRendererIn;
    }

    @Override
    public void doRenderLayer(EntityAnimalSheep sheep, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (sheep.hasWool() && !sheep.isInvisible()) {
            this.sheepRenderer.bindTexture(sheep.getAge() == IAnimal.Age.OLD ? OLD_TEXTURE : TEXTURE);

            float[] afloat = EntitySheep.getDyeRgb(sheep.getDyeColor());
            GlStateManager.color(afloat[0], afloat[1], afloat[2]);

            this.sheepModel.setModelAttributes(this.sheepRenderer.getMainModel());
            this.sheepModel.setLivingAnimations(sheep, limbSwing, limbSwingAmount, partialTicks);
            this.sheepModel.render(sheep, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
}
