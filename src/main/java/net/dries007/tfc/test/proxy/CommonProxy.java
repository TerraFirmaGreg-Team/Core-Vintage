package net.dries007.tfc.test.proxy;

import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.util.IItemProvider;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Function;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class CommonProxy {


	@SubscribeEvent
	@SuppressWarnings("ConstantConditions")
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> r = event.getRegistry();

		//=== Rock ===================================================================================================//

		for (var stoneTypeBlock : TFCStorage.ROCK_BLOCKS.values()) {
			r.register((Block) stoneTypeBlock);
		}

		//=== Soil ===================================================================================================//

		for (var soilTypeBlock : TFCStorage.SOIL_BLOCKS.values()) {
			r.register((Block) soilTypeBlock);
		}

		//=== Plant ==================================================================================================//

		for (var plantTypeBlock : TFCStorage.PLANT_BLOCKS.values()) {
			r.register((Block) plantTypeBlock);
		}

		//=== Wood ===================================================================================================//

		for (var woodTypeBlock : TFCStorage.WOOD_BLOCKS.values()) {
			r.register((Block) woodTypeBlock);
		}

		//=== Alabaster ==============================================================================================//

		for (var alabasterBlock : TFCStorage.ALABASTER_BLOCK.values()) {
			r.register(alabasterBlock);
		}

		//=== Groundcover ==============================================================================================//

		for (var groundcoverBlock : TFCStorage.GROUNDCOVER_BLOCK.values()) {
			r.register(groundcoverBlock);
		}

		//=== Other ==================================================================================================//

		TFCStorage.ITEM_BLOCKS.forEach(x -> r.register(x.getBlock()));

	}


	@SubscribeEvent
	@SuppressWarnings("ConstantConditions")
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> r = event.getRegistry();

		//=== Rock ===================================================================================================//

		for (var stoneTypeBlock : TFCStorage.ROCK_BLOCKS.values()) {
			var itemBlock = stoneTypeBlock.getItemBlock();
			if (itemBlock != null) registerItemBlock(r, itemBlock);
		}

		//=== Soil ===================================================================================================//

		for (var soilTypeBlock : TFCStorage.SOIL_BLOCKS.values()) {
			var itemBlock = soilTypeBlock.getItemBlock();
			if (itemBlock != null) registerItemBlock(r, itemBlock);
		}

		//=== Plant ==================================================================================================//

		for (var plantTypeBlock : TFCStorage.PLANT_BLOCKS.values()) {
			var itemBlock = plantTypeBlock.getItemBlock();
			if (itemBlock != null) registerItemBlock(r, itemBlock);
		}

		//=== Wood ===================================================================================================//

		for (var woodTypeBlock : TFCStorage.WOOD_BLOCKS.values()) {
			var itemBlock = woodTypeBlock.getItemBlock();
			if (itemBlock != null) registerItemBlock(r, itemBlock);
		}

		//=== Alabaster ==============================================================================================//

		for (var alabasterBlock : TFCStorage.ALABASTER_BLOCK.values()) {
			r.register(createItemBlock(alabasterBlock));
		}

		//=== Groundcover ==============================================================================================//

//		for (var groundcoverBlock : TFCStorage.GROUNDCOVER_BLOCK.values()) {
//			r.register(createItemBlock(groundcoverBlock, ItemBlock::new));
//		}

		//=== Other ==================================================================================================//

		TFCStorage.ITEM_BLOCKS.forEach(x -> registerItemBlock(r, x));


	}

	@SuppressWarnings("ConstantConditions")
	private static <T extends Block> ItemBlock createItemBlock(T block) {
		var itemBlock = ((IItemProvider) block).getItemBlock();
		var registryName = block.getRegistryName();
		if (registryName == null)
			throw new IllegalArgumentException("Block " + block.getTranslationKey() + " has no registry name.");

		itemBlock.setRegistryName(registryName);
		return itemBlock;
	}


	private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
		var itemBlock = producer.apply(block);
		var registryName = block.getRegistryName();
		if (registryName == null)
			throw new IllegalArgumentException("Block " + block.getTranslationKey() + " has no registry name.");

		itemBlock.setRegistryName(registryName);
		return itemBlock;
	}

	@SuppressWarnings("ConstantConditions")
	private static void registerItemBlock(IForgeRegistry<Item> r, ItemBlock item) {
		item.setRegistryName(item.getBlock().getRegistryName());
		item.setCreativeTab(item.getBlock().getCreativeTab());
		r.register(item);
	}

	// Для регистрации одиночных блоков
	private static <T extends Block> T registerBlock(String name, T block, CreativeTabs ct) {
		block.setRegistryName(MOD_ID, name);
		block.setTranslationKey(MOD_ID + "." + name.replace('/', '.'));
		block.setCreativeTab(ct);
		return block;
	}

	// Для регистрации одиночных предметов
	private static <T extends Item> T registerItem(String name, T item, CreativeTabs ct) {
		item.setRegistryName(MOD_ID, name);
		item.setTranslationKey(MOD_ID + "." + name.replace('/', '.'));
		item.setCreativeTab(ct);
		return item;
	}

}
