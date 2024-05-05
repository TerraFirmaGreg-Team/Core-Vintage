package pieman.caffeineaddon.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import static su.terrafirmagreg.api.data.Constants.MODID_CAFFEINEADDON;

@SuppressWarnings("WeakerAccess")
public abstract class PotionCA extends Potion {

    private static ResourceLocation POTION_ICON = new ResourceLocation(MODID_CAFFEINEADDON, "textures/gui/potion.png");

    protected PotionCA(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(POTION_ICON);
        return super.getStatusIconIndex();
    }
}
