/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package pieman.caffeineaddon.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pieman.caffeineaddon.Reference;
import net.dries007.tfc.objects.potioneffects.PotionTFC;

@SuppressWarnings("WeakerAccess")
public abstract class PotionCA extends Potion{
    private static ResourceLocation POTION_ICON = new ResourceLocation(Reference.MOD_ID, "textures/gui/potion.png");

    protected PotionCA(boolean isBadEffectIn, int liquidColorIn){
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex(){
        Minecraft.getMinecraft().renderEngine.bindTexture(POTION_ICON);
        return super.getStatusIconIndex();
    }
}
