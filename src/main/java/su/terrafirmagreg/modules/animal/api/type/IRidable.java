package su.terrafirmagreg.modules.animal.api.type;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.animal.ModuleAnimal;
import su.terrafirmagreg.modules.core.network.SCPacketSimpleMessage;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IRidable {

  default <A extends EntityAnimal & IAnimal> boolean attemptApplyHalter(A animal, World world, EntityPlayer player, ItemStack stack) {
    if (animal.getAge() != IAnimal.Age.CHILD && animal.getFamiliarity() > 0.15f) {
      if (!world.isRemote) {
        // Can't use EntityAnimal#consumeItemFromStack since thats protected
        if (!player.capabilities.isCreativeMode) {
          stack.shrink(1);
        }
        setHalter(true);
      }
      return true;
    } else {
      // Show tooltips
      if (!world.isRemote) {
        if (animal.getAge() == IAnimal.Age.CHILD) {
          ModuleAnimal.PACKET_SERVICE.sendTo(
            SCPacketSimpleMessage.translateMessage(SCPacketSimpleMessage.MessageCategory.ANIMAL,
                                                   ModUtils.localize("tooltip", "animal.product.young"), animal.getAnimalName()),
            (EntityPlayerMP) player);
        } else {
          ModuleAnimal.PACKET_SERVICE.sendTo(
            SCPacketSimpleMessage.translateMessage(SCPacketSimpleMessage.MessageCategory.ANIMAL,
                                                   ModUtils.localize("tooltip", "animal.product.low_familiarity"),
                                                   animal.getAnimalName()), (EntityPlayerMP) player);
        }
      }
      return false;
    }
  }

  /**
   * @return true if itemstack is in 'halter' oredict and the animal does not have a halter
   */
  default boolean canAcceptHalter(ItemStack stack) {
    return !isHalter() && OreDictUtils.contains(stack, "halter");
  }

  boolean isHalter();

  void setHalter(boolean state);
}
