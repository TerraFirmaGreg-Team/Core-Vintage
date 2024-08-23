package su.terrafirmagreg.api.registry.spi;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

public interface IRegistryPotionType
        extends IRegistryBase {

    default PotionType potionType(Potion potion, String name, int duration) {

        PotionType potionType = new PotionType(new PotionEffect(potion, duration));
        return potionType(potionType, name);
    }

    default PotionType potionType(PotionType potionType, String name) {

        potionType.setRegistryName(this.getModID(), name);

        this.getRegistry().getPotionTypes().add(potionType);

        return potionType;
    }
}
