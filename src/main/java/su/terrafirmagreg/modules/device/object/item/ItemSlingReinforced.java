package su.terrafirmagreg.modules.device.object.item;

import net.minecraft.item.ItemStack;

public class ItemSlingReinforced extends ItemSling {

  public ItemSlingReinforced(String name) {
    super(name);

    getSettings()
            .maxDamage(256);
  }

  @Override
  protected boolean isStone(ItemStack stack) {
    if (stack.getItem() instanceof ItemSlingAmmo) {
      return true;
    } else {
      return super.isStone(stack);
    }
  }

}
