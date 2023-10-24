package pieman.caffeineaddon.potion;

import javax.annotation.ParametersAreNonnullByDefault;

import net.dries007.tfc.objects.potioneffects.PotionTFC;
import net.minecraft.entity.EntityLivingBase;

@SuppressWarnings("WeakerAccess")
@ParametersAreNonnullByDefault
public class PotionCaffeine extends PotionCA
{
    public PotionCaffeine()
    {
        super(true, 0x0028120b);
        setPotionName("effectsTFC.caffeine");
        setIconIndex(0, 0);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier)
    {
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

    @Override
    public boolean isInstant()
    {
        return true;
    }
}
