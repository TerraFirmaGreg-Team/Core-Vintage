package net.dries007.tfc.client.render.animal;

import net.dries007.tfc.client.model.animal.ModelPantherTFC;
import net.dries007.tfc.objects.entity.animal.EntityPantherTFC;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;


import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderPantherTFC extends RenderLiving<EntityPantherTFC> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(MODID_TFC, "textures/entity/animal/predators/panther.png");

	public RenderPantherTFC(RenderManager renderManager) {
		super(renderManager, new ModelPantherTFC(), 0.7F);
	}

	@Override
	public void doRender(@NotNull EntityPantherTFC panther, double par2, double par4, double par6, float par8, float par9) {
		this.shadowSize = (float) (0.35f + (panther.getPercentToAdulthood() * 0.35f));
		super.doRender(panther, par2, par4, par6, par8, par9);
	}

	@Override
	protected float handleRotationFloat(EntityPantherTFC par1EntityLiving, float par2) {
		return 1.0f;
	}

	@Override
	protected void preRenderCallback(EntityPantherTFC pantherTFC, float par2) {
		GlStateManager.scale(1.1f, 1.1f, 1.1f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPantherTFC entity) {
		return TEXTURE;
	}
}
