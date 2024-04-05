package tfctech.client;

import net.dries007.tfc.api.capability.heat.Heat;
import net.dries007.tfc.client.FluidSpriteCache;
import net.dries007.tfc.util.Helpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import su.terrafirmagreg.modules.device.objects.container.ContainerCrucible;
import tfctech.TFCTech;
import tfctech.client.gui.*;
import tfctech.objects.container.ContainerElectricForge;
import tfctech.objects.container.ContainerGlassworking;
import tfctech.objects.container.ContainerSmelteryCauldron;
import tfctech.objects.container.ContainerSmelteryFirebox;
import tfctech.objects.items.glassworking.ItemBlowpipe;
import tfctech.objects.tileentities.TEElectricForge;
import tfctech.objects.tileentities.TEInductionCrucible;
import tfctech.objects.tileentities.TESmelteryCauldron;
import tfctech.objects.tileentities.TESmelteryFirebox;

import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFCTECH;

public class TechGuiHandler implements IGuiHandler {
	public static final ResourceLocation GUI_ELEMENTS = new ResourceLocation(MODID_TFCTECH, "textures/gui/elements.png");

	public static void openGui(World world, BlockPos pos, EntityPlayer player, Type type) {
		player.openGui(TFCTech.getInstance(), type.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Nullable
	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		ItemStack stack = player.getHeldItemMainhand();
		Type type = Type.valueOf(ID);
		switch (type) {
			case ELECTRIC_FORGE:
				TEElectricForge teElectricForge = Helpers.getTE(world, pos, TEElectricForge.class);
				return teElectricForge == null ? null : new ContainerElectricForge(player.inventory, teElectricForge);
			case INDUCTION_CRUCIBLE:
				TEInductionCrucible teInductionCrucible = Helpers.getTE(world, pos, TEInductionCrucible.class);
				return teInductionCrucible == null ? null : new ContainerCrucible(player.inventory, teInductionCrucible);
			case SMELTERY_CAULDRON:
				TESmelteryCauldron teSmelteryCauldron = Helpers.getTE(world, pos, TESmelteryCauldron.class);
				return teSmelteryCauldron == null ? null : new ContainerSmelteryCauldron(player.inventory, teSmelteryCauldron);
			case SMELTERY_FIREBOX:
				TESmelteryFirebox teSmelteryFirebox = Helpers.getTE(world, pos, TESmelteryFirebox.class);
				return teSmelteryFirebox == null ? null : new ContainerSmelteryFirebox(player.inventory, teSmelteryFirebox);
			case GLASSWORKING:
				return new ContainerGlassworking(player.inventory, stack.getItem() instanceof ItemBlowpipe ? stack : player.getHeldItemOffhand());
			default:
				return null;
		}

	}

	@Override
	@Nullable
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		Container container = getServerGuiElement(ID, player, world, x, y, z);
		Type type = Type.valueOf(ID);
		BlockPos pos = new BlockPos(x, y, z);
		switch (type) {
			case ELECTRIC_FORGE:
				return new GuiElectricForge(container, player.inventory, Helpers.getTE(world, pos, TEElectricForge.class));
			case INDUCTION_CRUCIBLE:
				return new GuiInductionCrucible(container, player.inventory, Helpers.getTE(world, pos, TEInductionCrucible.class));
			case SMELTERY_CAULDRON:
				return new GuiSmelteryCauldron(container, player.inventory, Helpers.getTE(world, pos, TESmelteryCauldron.class));
			case SMELTERY_FIREBOX:
				return new GuiSmelteryFirebox(container, player.inventory, Helpers.getTE(world, pos, TESmelteryFirebox.class));
			case GLASSWORKING:
				return new GuiGlassworking(container, player);
			default:
				return null;
		}
	}

	public enum Type {
		ELECTRIC_FORGE,
		INDUCTION_CRUCIBLE,
		SMELTERY_CAULDRON,
		SMELTERY_FIREBOX,
		GLASSWORKING;

		private static final Type[] values = values();

		@NotNull
		public static Type valueOf(int id) {
			while (id >= values.length) id -= values.length;
			while (id < 0) id += values.length;
			return values[id];
		}
	}

	/*
	 * Some helper functions to alleviate the design of new GUIs
	 */
	@SideOnly(Side.CLIENT)
	public static abstract class Drawing {
		/**
		 * Draw the temperature bar onscreen
		 *
		 * @param minecraft   the minecraft obj
		 * @param guiElement  the gui obj
		 * @param posX        the x coords to draw (don't forget guiLeft!)
		 * @param posY        the y coords to draw (don't forget guiTop!)
		 * @param temperature the temperature to draw the indicator
		 */
		public static void drawTemperatureBar(Minecraft minecraft, Gui guiElement, int posX, int posY, float temperature) {
			// The bar
			minecraft.getTextureManager().bindTexture(TechGuiHandler.GUI_ELEMENTS);
			guiElement.drawTexturedModalRect(posX, posY, 39, 1, 9, 52);

			// the temperature indicator <->
			int temperaturePixels = (int) (51 * Math.min(Heat.maxVisibleTemperature(), temperature) / Heat.maxVisibleTemperature()); //Max temperature is brilliant white in tfc
			guiElement.drawTexturedModalRect(posX - 3, posY + 49 - temperaturePixels, 36, 54, 15, 5);
		}

		/**
		 * Draw a tank with contents onscreen
		 *
		 * @param minecraft  the minecraft obj
		 * @param guiElement the gui obj
		 * @param posX       the x coords to draw (don't forget guiLeft!)
		 * @param posY       the y coords to draw (don't forget guiTop!)
		 * @param capacity   the capacity of this tank
		 * @param fluid      the fluid if you want to draw it, null otherwise
		 */
		public static void drawTank(Minecraft minecraft, Gui guiElement, int posX, int posY, int capacity, @Nullable FluidStack fluid) {
			minecraft.getTextureManager().bindTexture(TechGuiHandler.GUI_ELEMENTS);

			// Draw the background
			guiElement.drawTexturedModalRect(posX, posY, 0, 102, 18, 49);

			// Draw fluid
			if (fluid != null) {
				// Fluid
				int fillPixels = (int) Math.min(Math.ceil((fluid.amount / (float) capacity) * 47), 47);
				TextureAtlasSprite sprite = FluidSpriteCache.getStillSprite(fluid.getFluid());
				minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				BufferBuilder buffer = Tessellator.getInstance().getBuffer();

				GlStateManager.enableAlpha();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

				int color = fluid.getFluid().getColor();

				float r = ((color >> 16) & 0xFF) / 255f;
				float g = ((color >> 8) & 0xFF) / 255f;
				float b = (color & 0xFF) / 255f;
				float a = ((color >> 24) & 0xFF) / 255f;

				GlStateManager.color(r, g, b, a);

				buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

				int startX = posX + 1;
				int startY = posY + 48 - fillPixels;
				int endX = posX + 17;
				int endY = posY + 48;

				buffer.pos(startX, startY, 0).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
				buffer.pos(startX, endY, 0).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
				buffer.pos(endX, endY, 0).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
				buffer.pos(endX, startY, 0).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();

				Tessellator.getInstance().draw();

				minecraft.renderEngine.bindTexture(GUI_ELEMENTS);
				GlStateManager.color(1, 1, 1, 1);

				// Overlay
				guiElement.drawTexturedModalRect(posX, posY, 18, 102, 18, 49);
			}
		}

		/**
		 * Draws tooltip if not null
		 *
		 * @param fluid  the fluid
		 * @param mouseX the mouseX relation to GUI (mouseX - guiLeft)
		 * @param mouseY the mouseY relation to GUI (mouseY - guiTop)
		 * @param posX   the tank's x coords (without guiLeft!)
		 * @param posY   the tank's y coords (without guiTop!)
		 */
		@Nullable
		public static List<String> getFluidTooltip(@Nullable FluidStack fluid, int mouseX, int mouseY, int posX, int posY) {
			if (fluid != null && mouseX >= posX && mouseX <= posX + 18 && mouseY >= posY && mouseY <= posY + 49) {
				List<String> tooltip = new ArrayList<>();
				tooltip.add(fluid.getLocalizedName());
				tooltip.add(fluid.amount + " / " + TESmelteryCauldron.FLUID_CAPACITY);
				return tooltip;
			}
			return null;
		}
	}
}
