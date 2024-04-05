package net.dries007.tfc.client.render.animal;

import net.dries007.tfc.client.model.animal.ModelGrizzlyBearTFC;
import net.dries007.tfc.objects.entity.animal.EntityGrizzlyBearTFC;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;


import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderGrizzlyBearTFC extends RenderLiving<EntityGrizzlyBearTFC> {
	private static final ResourceLocation BEAR_TEXTURE = new ResourceLocation(MODID_TFC, "textures/entity/animal/predators/grizzlybear.png");

	public RenderGrizzlyBearTFC(RenderManager renderManager) {
		super(renderManager, new ModelGrizzlyBearTFC(), 0.7F);
	}

	@Override
	public void doRender(@NotNull EntityGrizzlyBearTFC bear, double par2, double par4, double par6, float par8, float par9) {
		this.shadowSize = (float) (0.35f + (bear.getPercentToAdulthood() * 0.35f));
		super.doRender(bear, par2, par4, par6, par8, par9);
	}

	@Override
	protected float handleRotationFloat(EntityGrizzlyBearTFC par1EntityLiving, float par2) {
		return 1.0f;
	}

	@Override
	protected void preRenderCallback(EntityGrizzlyBearTFC bearTFC, float par2) {
		GlStateManager.scale(1.4f, 1.4f, 1.4f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGrizzlyBearTFC entity) {
		return BEAR_TEXTURE;
	}
}
