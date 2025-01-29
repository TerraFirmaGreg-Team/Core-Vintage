package net.dries007.tfc.objects.items.itemblock;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import net.dries007.tfc.objects.blocks.BlockPowderKeg;

import su.terrafirmagreg.api.data.enums.Mods;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class ItemBlockPowderKeg extends ItemBlockTFC implements ICapabilitySize {

  public ItemBlockPowderKeg(BlockPowderKeg block) {
    super(block);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    NBTTagCompound nbt = stack.getTagCompound();
    if (nbt != null) {
      ItemStackHandler stackHandler = new ItemStackHandler();
      stackHandler.deserializeNBT(nbt.getCompoundTag("inventory"));
      int count = 0;
      int firstSlot = -1;
      for (int i = 0; i < stackHandler.getSlots(); i++) {
        if (firstSlot < 0 && !stackHandler.getStackInSlot(i).isEmpty()) {
          firstSlot = i;
        }
        count += stackHandler.getStackInSlot(i).getCount();
      }

      if (count == 0) {
        tooltip.add(I18n.format(Mods.Names.TFC + ".tooltip.powderkeg_empty"));
      } else {
        ItemStack itemStack = stackHandler.getStackInSlot(firstSlot);
        tooltip.add(I18n.format(Mods.Names.TFC + ".tooltip.powderkeg_amount", count, itemStack.getItem().getItemStackDisplayName(itemStack)));
      }
    }
  }
}
