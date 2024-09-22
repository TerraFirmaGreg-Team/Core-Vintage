package se.gory_moon.horsepower.blocks;

import su.terrafirmagreg.data.ToolClasses;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class BlockHPChoppingBase extends BlockHPBase {


  public BlockHPChoppingBase() {
    super(Settings.of(Material.WOOD));

    getSettings()
            .sound(SoundType.WOOD)
            .harvestLevel(ToolClasses.AXE, 0);
  }

  public static ItemStack createItemStack(BlockHPChoppingBase table, int amount, ItemStack blockItem) {
    ItemStack stack = new ItemStack(table, amount);
    Block block = Block.getBlockFromItem(blockItem.getItem());

    if (block != Blocks.AIR) {
      ItemStack blockStack = new ItemStack(block, 1, blockItem.getItemDamage());
      NBTTagCompound tag = new NBTTagCompound();
      NBTTagCompound subTag = new NBTTagCompound();
      if (block instanceof BlockHPChoppingBase) {
        subTag = blockItem.getSubCompound("textureBlock");
        subTag = subTag != null ? subTag : new NBTTagCompound();
      } else {
        blockStack.writeToNBT(subTag);
      }
      tag.setTag("textureBlock", subTag);
      stack.setTagCompound(tag);
    }

    return stack;
  }


}
