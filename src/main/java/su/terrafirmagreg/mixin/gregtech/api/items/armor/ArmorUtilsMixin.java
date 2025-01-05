package su.terrafirmagreg.mixin.gregtech.api.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;

import gregtech.api.items.armor.ArmorUtils;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ArmorUtils.class, remap = false)
public class ArmorUtilsMixin {

  @Inject(method = "canEat", at = @At(value = "HEAD"), cancellable = true)
  private static void canEat(EntityPlayer player, ItemStack food, CallbackInfoReturnable<ActionResult<ItemStack>> cir) {
    if (!(food.getItem() instanceof ItemFood)) {
      cir.setReturnValue(new ActionResult<>(EnumActionResult.FAIL, food));
    }

    IFood cap = food.getCapability(CapabilityFood.CAPABILITY, null);
    ItemFood foodItem = (ItemFood) food.getItem();

    if (player.getFoodStats().needFood()) {

      if (!player.isCreative()) {
        food.setCount(food.getCount() - 1);
      }

      // Find the saturation of the food
      float saturation = foodItem.getSaturationModifier(food);

      // The amount of empty food haunches of the player
      int hunger = 20 - player.getFoodStats().getFoodLevel();

      // Increase the saturation of the food if the food replenishes more than the amount of missing haunches
      saturation += (hunger - foodItem.getHealAmount(food)) < 0 ? foodItem.getHealAmount(food) - hunger : 1.0F;

      if (cap != null && !cap.isRotten()) {
        // Use this method to add stats for compat with TFC, who overrides addStats(int amount, float saturation) for their food and does nothing
        player.getFoodStats()
          .addStats(new ItemFood(foodItem.getHealAmount(food), saturation, foodItem.isWolfsFavoriteMeat()), food);
      }
      cir.setReturnValue(new ActionResult<>(EnumActionResult.SUCCESS, food));
    } else {
      cir.setReturnValue(new ActionResult<>(EnumActionResult.FAIL, food));
    }

  }
}
