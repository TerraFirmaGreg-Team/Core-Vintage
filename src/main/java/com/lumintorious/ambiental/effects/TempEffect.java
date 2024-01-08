package com.lumintorious.ambiental.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static su.terrafirmagreg.Constants.MODID_TFCAMBIENTAL;

public class TempEffect extends Potion {
	public static final TempEffect WARM = new TempEffect("warm", 0xFFFFCC00, 0);
	public static final TempEffect COOL = new TempEffect("cool", 0xFF00CCFF, 1);
	private final ResourceLocation POTION_ICONS = new ResourceLocation(MODID_TFCAMBIENTAL, "textures/gui/potions.png");

	private TempEffect(String name, int color, int index) {
		super(false, color);
		setIconIndex(index, 0);
		setRegistryName(name);
		setPotionName("effect.tfcambiental." + name + ".name");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(POTION_ICONS);
		return super.getStatusIconIndex();
	}
}
