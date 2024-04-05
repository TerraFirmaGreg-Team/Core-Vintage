package net.dries007.tfc.client.render.animal;

import net.dries007.tfc.client.model.animal.ModelCamelTFC;
import net.dries007.tfc.objects.entity.animal.EntityCamelTFC;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderCamelTFC extends RenderAnimalTFC<EntityCamelTFC> {
	private static final ResourceLocation OLD = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/camel_old.png");
	private static final ResourceLocation YOUNG = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/camel_young.png");

	public RenderCamelTFC(RenderManager p_i47203_1_) {
		super(p_i47203_1_, new ModelCamelTFC(0.0F), 0.7F, YOUNG, OLD);
		this.addLayer(new LayerCamelDecor(this));
	}

}
