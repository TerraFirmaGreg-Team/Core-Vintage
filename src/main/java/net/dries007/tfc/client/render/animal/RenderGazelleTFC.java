package net.dries007.tfc.client.render.animal;

import net.dries007.tfc.client.model.animal.ModelGazelleTFC;
import net.dries007.tfc.objects.entity.animal.EntityGazelleTFC;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderGazelleTFC extends RenderLiving<EntityGazelleTFC> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(MODID_TFC, "textures/entity/animal/huntable/gazelle.png");

	public RenderGazelleTFC(RenderManager manager) {
		super(manager, new ModelGazelleTFC(), 0.7F);
	}

	@Override
	protected float handleRotationFloat(EntityGazelleTFC gazelle, float par2) {
		return 1.0f;
	}

	@Override
	protected void preRenderCallback(EntityGazelleTFC gazelleTFC, float par2) {
		GlStateManager.scale(0.9f, 0.9f, 0.9f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGazelleTFC entity) {
		return TEXTURE;
	}
}
