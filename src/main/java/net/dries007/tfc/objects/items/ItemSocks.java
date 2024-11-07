package net.dries007.tfc.objects.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSocks extends ItemArmor {

  private final boolean isWet;

  public ItemSocks(boolean isWet) {
    super(ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.FEET);
    this.setMaxDamage(-1);

    this.isWet = isWet;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    if (this.isWet) {
      tooltip.add("It's wet!!! ARGH");
    }
  }
}
