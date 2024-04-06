package su.terrafirmagreg.modules.wood.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.util.ColourUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.client.model.ModelWoodPlowCart;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodPlowCart;

@SideOnly(Side.CLIENT)
public class RenderWoodPlowCart extends Render<EntityWoodPlowCart> {

	protected ModelBase model = new ModelWoodPlowCart();

	public RenderWoodPlowCart(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.6F;
	}

	@Override
	public void doRender(EntityWoodPlowCart entity, double x, double y, double z, float entityYaw, float partialTicks) {
		var woodType = entity.getWood();
		GlStateManager.pushMatrix();
		this.setupTranslation(x, y, z);
		this.setupRotation(entityYaw);
		this.bindEntityTexture(entity);

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		ColourUtils.setColor(woodType.getColor());

		this.model.render(entity, partialTicks, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}
		GlStateManager.popMatrix();

		// Render the tools on the plow
		for (int i = 0; i < entity.inventory.getSizeInventory(); i++) {
			GlStateManager.pushMatrix();
			double offsetSides = 0.1D * ((i + 1) & 1);
			if (entity.getPlowing()) {
				GlStateManager.translate(x + (1.45D + offsetSides) * MathHelper.sin((-36.0F + entityYaw + i * 36.0F) * 0.017453292F), y + 0.10D, z - (1.45D + offsetSides) * MathHelper.cos((-36.0F + entityYaw + i * 36.0F) * 0.017453292F));
				GlStateManager.rotate(120.0F - entityYaw - 30.0F * i, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(181.0F, 0.0F, 0.0F, 1.0F);
			} else {
				GlStateManager.translate(x + (1.9D + offsetSides) * MathHelper.sin((-34.7F + entityYaw + i * 34.7F) * 0.017453292F), y + 0.90D, z - (1.9D + offsetSides) * MathHelper.cos((-34.7F + entityYaw + i * 34.7F) * 0.017453292F));
				GlStateManager.rotate(120.0F - entityYaw - 30.0F * i, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(207.0F, 0.0F, 0.0F, 1.0F);
			}
			Minecraft.getMinecraft()
			         .getRenderItem()
			         .renderItem(entity.getTool(i), ItemCameraTransforms.TransformType.FIXED);
			GlStateManager.popMatrix();
		}
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(@NotNull EntityWoodPlowCart entity) {
		return ModUtils.getID("textures/entity/wood/plow_cart.png");
	}

	public void setupRotation(float entityYaw) {
		GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(-1.0F, -1.0F, 1.0F);
	}

	public void setupTranslation(double x, double y, double z) {
		GlStateManager.translate(x, y + 1.0D, z);
	}
}
