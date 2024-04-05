package net.dries007.tfc.client.render.animal;

import net.dries007.tfc.client.model.animal.ModelZebuTFC;
import net.dries007.tfc.objects.entity.animal.EntityZebuTFC;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderZebuTFC extends RenderAnimalTFC<EntityZebuTFC> {
	private static final ResourceLocation ZEBU_YOUNG = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/zebu_young.png");
	private static final ResourceLocation ZEBU_OLD = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/zebu_old.png");

	public RenderZebuTFC(RenderManager renderManager) {
		super(renderManager, new ModelZebuTFC(), 0.7F, ZEBU_YOUNG, ZEBU_OLD);
	}

	protected void preRenderCallback(EntityZebuTFC zebuTFC, float par2) {
		GlStateManager.scale(0.9f, 0.9f, 0.9f);
	}
}
