package net.sharkbark.cellars.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.sharkbark.cellars.Main;
import net.sharkbark.cellars.init.ModBlocks;
import net.sharkbark.cellars.init.ModItems;
import net.sharkbark.cellars.util.IHasModel;

public class BlockBase extends Block implements IHasModel {

  public BlockBase(String name, Material material) {
    //setUnlocalizedName(name);
    super(material);
    setTranslationKey(name);
    setRegistryName(name);
    setCreativeTab(CreativeTabsTFC.CT_MISC);
    setHardness(2F);
    ModBlocks.BLOCKS.add(this);
    ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
  }

  @Override
  public void registerModels() {

    Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");

  }
}
