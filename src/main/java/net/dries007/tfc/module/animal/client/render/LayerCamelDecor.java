package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.animal.client.model.ModelAnimalCamel;
import net.dries007.tfc.module.animal.common.entities.livestock.EntityAnimalCamel;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.stream.IntStream;


@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class LayerCamelDecor implements LayerRenderer<EntityAnimalCamel> {
    private static final ResourceLocation[] CAMEL_DECOR_TEXTURES = IntStream.range(0, 16).mapToObj(i -> TerraFirmaCraft.getID("textures/entity/animal/livestock/decor/" + EnumDyeColor.byMetadata(i).getName() + ".png")).toArray(ResourceLocation[]::new);
    private final RenderAnimalCamel renderer;
    private final ModelAnimalCamel model = new ModelAnimalCamel(0.51F);

    public LayerCamelDecor(RenderAnimalCamel p_i47184_1_) {
        this.renderer = p_i47184_1_;
    }

    public void doRenderLayer(EntityAnimalCamel entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entitylivingbaseIn.hasColor()) {
            this.renderer.bindTexture(CAMEL_DECOR_TEXTURES[entitylivingbaseIn.getColor().getMetadata()]);
            this.model.setModelAttributes(this.renderer.getMainModel());
            this.model.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }

    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
