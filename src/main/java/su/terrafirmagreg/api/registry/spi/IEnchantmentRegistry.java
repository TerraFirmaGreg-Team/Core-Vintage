package su.terrafirmagreg.api.registry.spi;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;

public interface IEnchantmentRegistry
        extends IBaseRegistry {

    /**
     * Registers an enchantment.
     *
     * @param enchant The enchantment to register.
     * @param name    The ID of the enchantment.
     * @return The enchantment that was registered.
     */
    default Enchantment enchantment(Enchantment enchant, String name) {

        enchant.setRegistryName(new ResourceLocation(this.getModID(), name));

        this.getRegistry().getEnchantments().add(enchant);

        return enchant;
    }
}
