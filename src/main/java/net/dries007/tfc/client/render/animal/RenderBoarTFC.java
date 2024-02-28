/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.client.render.animal;

import net.dries007.tfc.client.model.animal.ModelBoarTFC;
import net.dries007.tfc.objects.entity.animal.EntityBoarTFC;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderBoarTFC extends RenderLiving<EntityBoarTFC> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(MODID_TFC, "textures/entity/animal/huntable/boar.png");

	public RenderBoarTFC(RenderManager renderManager) {super(renderManager, new ModelBoarTFC(), 0.7F);}

	@Override
	public void doRender(@Nonnull EntityBoarTFC hog, double par2, double par4, double par6, float par8, float par9) {
		this.shadowSize = (float) (0.35f + (hog.getPercentToAdulthood() * 0.35f));
		super.doRender(hog, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBoarTFC entity) {
		return TEXTURE;
	}
}
