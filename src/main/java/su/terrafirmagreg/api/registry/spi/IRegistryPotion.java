package su.terrafirmagreg.api.registry.spi;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.potion.Potion;

public interface IRegistryPotion
  extends IRegistryBase {

  default Potion potion(String name, Potion potion, IAttribute attribute, String uniqueId,
                        double ammount, int operation) {

    potion.registerPotionAttributeModifier(attribute, uniqueId, ammount, operation);
    return this.potion(name, potion);
  }

  default Potion potion(String name, Potion potion) {
    potion.setRegistryName(this.getModID(), name);
    potion.setPotionName(this.getModID() + ".effect." + name.toLowerCase().replace("_", "."));

    this.getRegistry().getPotions().add(potion);

    return potion;
  }
}
