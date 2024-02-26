package su.terrafirmagreg.api.spi.effects;


import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.util.ModUtils;

@SuppressWarnings("WeakerAccess")
public abstract class PotionBase extends Potion {

	private static final ResourceLocation POTION_ICONS = ModUtils.getID("textures/gui/icons/potion.png");

	protected PotionBase(boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(POTION_ICONS);

		return super.getStatusIconIndex();
	}
}
