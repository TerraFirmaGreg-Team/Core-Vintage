package su.terrafirmagreg.modules.wood.client.gui;

import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.client.FluidSpriteCache;
import net.dries007.tfc.client.button.IButtonTooltip;
import net.dries007.tfc.client.gui.GuiContainerTE;
import net.dries007.tfc.util.Helpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.opengl.GL11;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.network.SCPacketGuiButton;
import su.terrafirmagreg.modules.wood.ModuleWood;
import su.terrafirmagreg.modules.wood.client.button.GuiButtonBarrelSeal;
import su.terrafirmagreg.modules.wood.objects.container.ContainerWoodBarrel;
import su.terrafirmagreg.modules.wood.objects.tiles.TEWoodBarrel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GuiWoodBarrel extends GuiContainerTE<TEWoodBarrel> {
	public static final ResourceLocation BARREL_BACKGROUND = ModUtils.getID("textures/gui/container/barrel.png");
	private final String translationKey;

	public GuiWoodBarrel(Container container, InventoryPlayer playerInv, TEWoodBarrel tile, String translationKey) {
		super(container, playerInv, tile, BARREL_BACKGROUND);

		this.translationKey = translationKey;
	}

	@Override
	public void initGui() {
		super.initGui();
		addButton(new GuiButtonBarrelSeal(tile, 0, guiTop, guiLeft));
	}

	@Override
	protected void renderHoveredToolTip(int mouseX, int mouseY) {
		super.renderHoveredToolTip(mouseX, mouseY);

		int relX = mouseX - guiLeft;
		int relY = mouseY - guiTop;

		if (relX >= 7 && relY >= 19 && relX < 25 && relY < 71) {
			IFluidHandler tank = ((ContainerWoodBarrel) inventorySlots).getBarrelTank();

			if (tank != null) {
				FluidStack fluid = tank.getTankProperties()[0].getContents();
				List<String> tooltip = new ArrayList<>();

				if (fluid == null || fluid.amount == 0) {
					tooltip.add(I18n.format(ModUtils.getIDName(".tooltip.barrel_empty")));
				} else {
					tooltip.add(fluid.getLocalizedName());
					tooltip.add(TextFormatting.GRAY + I18n.format(ModUtils.getIDName(".tooltip.barrel_fluid_amount"), fluid.amount));
				}

				this.drawHoveringText(tooltip, mouseX, mouseY, fontRenderer);
			}
		}

		// Button Tooltips
		for (GuiButton button : buttonList) {
			if (button instanceof IButtonTooltip tooltip && button.isMouseOver()) {
				if (tooltip.hasTooltip()) {
					drawHoveringText(I18n.format(tooltip.getTooltip()), mouseX, mouseY);
				}
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n.format(translationKey + ".name");
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);

		if (tile.isSealed()) {
			// Draw over the input items, making them look unavailable
			IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			if (handler != null) {
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				for (int slotId = 0; slotId < handler.getSlots(); slotId++) {
					drawSlotOverlay(inventorySlots.getSlot(slotId));
				}
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			}

			// Draw the text displaying both the seal date, and the recipe name
			boolean isLong = false;
			BarrelRecipe recipe = tile.getRecipe();
			if (recipe != null) {
				String resultName = recipe.getResultName();
				int recipeWidth = fontRenderer.getStringWidth(resultName);
				if (recipeWidth > 80)
					isLong = true;
				fontRenderer.drawString(resultName, xSize / 2 - (isLong ? recipeWidth / 2 - 6 : 28), isLong ? 73 : 61, 0x404040);
			}
			fontRenderer.drawString(tile.getSealedDate(), xSize / 2 - (isLong ? 28 : fontRenderer.getStringWidth(tile.getSealedDate()) / 2), isLong ? 19 : 73, 0x404040);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		if (Helpers.isJEIEnabled()) {
			drawTexturedModalRect(guiLeft + 92, guiTop + 21, 227, 0, 9, 14);
		}

		ContainerWoodBarrel container = (ContainerWoodBarrel) inventorySlots;
		IFluidHandler tank = container.getBarrelTank();

		if (tank != null) {
			IFluidTankProperties t = tank.getTankProperties()[0];
			FluidStack fs = t.getContents();

			if (fs != null) {
				int fillHeightPixels = (int) (50 * fs.amount / (float) t.getCapacity());

				if (fillHeightPixels > 0) {
					Fluid fluid = fs.getFluid();
					TextureAtlasSprite sprite = FluidSpriteCache.getStillSprite(fluid);

					int positionX = guiLeft + 8;
					int positionY = guiTop + 54;

					Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
					BufferBuilder buffer = Tessellator.getInstance().getBuffer();

					GlStateManager.enableAlpha();
					GlStateManager.enableBlend();
					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

					int color = fluid.getColor();

					float r = ((color >> 16) & 0xFF) / 255f;
					float g = ((color >> 8) & 0xFF) / 255f;
					float b = (color & 0xFF) / 255f;
					float a = ((color >> 24) & 0xFF) / 255f;

					GlStateManager.color(r, g, b, a);

					buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

					while (fillHeightPixels > 15) {
						buffer.pos(positionX, positionY, 0).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
						buffer.pos(positionX, positionY + 16, 0).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
						buffer.pos(positionX + 16, positionY + 16, 0)
								.tex(sprite.getMaxU(), sprite.getMaxV())
								.endVertex();
						buffer.pos(positionX + 16, positionY, 0).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();

						fillHeightPixels -= 16;
						positionY -= 16;
					}

					if (fillHeightPixels > 0) {
						int blank = 16 - fillHeightPixels;
						positionY += blank;
						buffer.pos(positionX, positionY, 0)
								.tex(sprite.getMinU(), sprite.getInterpolatedV(blank))
								.endVertex();
						buffer.pos(positionX, positionY + fillHeightPixels, 0)
								.tex(sprite.getMinU(), sprite.getMaxV())
								.endVertex();
						buffer.pos(positionX + 16, positionY + fillHeightPixels, 0)
								.tex(sprite.getMaxU(), sprite.getMaxV())
								.endVertex();
						buffer.pos(positionX + 16, positionY, 0)
								.tex(sprite.getMaxU(), sprite.getInterpolatedV(blank))
								.endVertex();
					}

					Tessellator.getInstance().draw();

					Minecraft.getMinecraft().renderEngine.bindTexture(BARREL_BACKGROUND);
					GlStateManager.color(1, 1, 1, 1);
				}
			}
		}

		drawTexturedModalRect(guiLeft + 7, guiTop + 19, 176, 0, 18, 52);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		ModuleWood.PACKET_SERVICE.sendToServer(new SCPacketGuiButton(button.id));
		super.actionPerformed(button);
	}
}
