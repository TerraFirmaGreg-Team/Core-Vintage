/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.client.render.animal;

import net.dries007.tfc.client.model.animal.ModelDeerTFC;
import net.dries007.tfc.objects.entity.animal.EntityDeerTFC;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderDeerTFC extends RenderLiving<EntityDeerTFC> {
	private static final ResourceLocation DEER_TEXTURE = new ResourceLocation(MODID_TFC, "textures/entity/animal/huntable/deer.png");

	private static final ResourceLocation FAWN_TEXTURE = new ResourceLocation(MODID_TFC, "textures/entity/animal/huntable/deer_fawn.png");

	public RenderDeerTFC(RenderManager manager) {
		super(manager, new ModelDeerTFC(), 0.7F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDeerTFC deer) {
		if (deer.isChild()) {
			return FAWN_TEXTURE;
		} else {
			return DEER_TEXTURE;
		}
	}

	@Override
	protected float handleRotationFloat(EntityDeerTFC deer, float par2) {
		return 1.0f;
	}

	protected void preRenderCallback(EntityDeerTFC deerTFC, float par2) {
		GlStateManager.scale(0.8f, 0.8f, 0.8f);
	}
}
