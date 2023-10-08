package net.dries007.tfc.module.animal.api.type;

import net.dries007.tfc.Tags;
import net.dries007.tfc.module.animal.api.type.IAnimal.Age;
import net.dries007.tfc.module.core.ModuleCore;
import net.dries007.tfc.module.core.network.SCPacketSimpleMessage;
import net.dries007.tfc.module.core.network.SCPacketSimpleMessage.MessageCategory;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public interface IRidable {
    default <A extends EntityAnimal & IAnimal> boolean attemptApplyHalter(A animal, World world, EntityPlayer player, ItemStack stack) {
        if (animal.getAge() != Age.CHILD && animal.getFamiliarity() > 0.15f) {
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
                if (animal.getAge() == Age.CHILD) {
                    ModuleCore.PACKET_SERVICE.sendTo(SCPacketSimpleMessage.translateMessage(MessageCategory.ANIMAL, Tags.MOD_ID + ".tooltip.animal.product.young", animal.getAnimalName()), (EntityPlayerMP) player);
                } else {
                    ModuleCore.PACKET_SERVICE.sendTo(SCPacketSimpleMessage.translateMessage(MessageCategory.ANIMAL, Tags.MOD_ID + ".tooltip.animal.product.low_familiarity", animal.getAnimalName()), (EntityPlayerMP) player);
                }
            }
            return false;
        }
    }

    /**
     * @return true if itemstack is in 'halter' oredict and the animal does not have a halter
     */
    default boolean canAcceptHalter(ItemStack stack) {
        return !isHalter() && OreDictionaryHelper.doesStackMatchOre(stack, "halter");
    }

    boolean isHalter();

    void setHalter(boolean state);
}
