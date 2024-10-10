package su.terrafirmagreg.core.mixin.tfc;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import gregtech.api.items.toolitem.ToolHelper;
import gregtech.common.items.ToolItems;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.objects.container.ContainerAnvilTFC;
import net.dries007.tfc.objects.container.ContainerTE;
import net.dries007.tfc.objects.container.IButtonHandler;
import net.dries007.tfc.objects.te.TEAnvilTFC;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.te.TEAnvilTFC.SLOT_HAMMER;
import static net.dries007.tfc.objects.te.TEAnvilTFC.SLOT_INPUT_1;

@Mixin(value = ContainerAnvilTFC.class, remap = false)
@ParametersAreNonnullByDefault
public abstract class ContainerAnvilTFCMixin extends ContainerTE<TEAnvilTFC> implements IButtonHandler {

  protected ContainerAnvilTFCMixin(InventoryPlayer playerInv, TEAnvilTFC tile) {
    super(playerInv, tile);
  }

  /**
   * @author SpeeeDCraft
   * @reason Allow to use GT hammer in TFC anvil
   */
  @Overwrite
  private boolean attemptWork() {
    // This only runs on server

    // Get the slot for input
    Slot slotInput = inventorySlots.get(SLOT_INPUT_1);
    if (slotInput == null) {return false;}

    ItemStack stack = slotInput.getStack();
    IForgeable cap = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);

    // The input must have the forge item capability
    if (cap == null) {return false;}

    // A recipe must exist
    AnvilRecipe recipe = TFCRegistries.ANVIL.getValue(cap.getRecipeName());
    if (recipe == null) {
      return false;
    }
    if (!tile.getTier().isAtLeast(recipe.getTier())) {
      TerraFirmaCraft.getLog().info("Anvil Tier: {} + Recipe Tier: {}", tile.getTier(), recipe.getTier());
      player.sendMessage(new TextComponentTranslation(MOD_ID + ".tooltip.anvil_tier_too_low"));
      return false;
    }

    if (!cap.isWorkable()) {
      player.sendMessage(new TextComponentTranslation(MOD_ID + ".tooltip.anvil_too_cold"));
      return false;
    }

    Slot slot = inventorySlots.get(SLOT_HAMMER);
    if (slot == null) {return false;}

    stack = slot.getStack();
    if (!stack.isEmpty()) {
      ToolHelper.damageItem(stack, null);
      if (stack.getCount() <= 0) {
        slot.putStack(ItemStack.EMPTY);
      } else {
        slot.putStack(stack);
      }
      return true;
    } else {
      // Fallback to the held item if it is a hammer
      stack = player.inventory.mainInventory.get(player.inventory.currentItem);
      if (stack.getItem() == ToolItems.HARD_HAMMER) {
        ToolHelper.damageItem(stack, null);
        return true;
      } else {
        player.sendMessage(new TextComponentString("" + TextFormatting.RED).appendSibling(new TextComponentTranslation(MOD_ID + ".tooltip.anvil_no_hammer")));
        return false;
      }
    }
  }
}
