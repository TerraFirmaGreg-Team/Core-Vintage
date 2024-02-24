package net.sharkbark.cellars.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.sharkbark.cellars.Main;
import net.sharkbark.cellars.init.ModBlocks;
import net.sharkbark.cellars.init.ModItems;
import su.terrafirmagreg.api.models.IModelRegister;

public class BlockBase extends Block implements IModelRegister {

	public BlockBase(String name, Material material) {
		super(material);
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(Main.creativeTab);
		setHardness(2F);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void onModelRegister() {

		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");

	}
}
