package net.dries007.tfc.test.proxy;

import net.dries007.tfc.api.registries.TFCStorage;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
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
		IForgeRegistry<Block> register = event.getRegistry();

		//=== Rock ===================================================================================================//

		for (var stoneTypeBlock : TFCStorage.ROCK_BLOCKS.values()) {register.register((Block) stoneTypeBlock);}

		//=== Soil ===================================================================================================//

		for (var soilTypeBlock : TFCStorage.SOIL_BLOCKS.values()) {register.register((Block) soilTypeBlock);}

		//=== Plant ==================================================================================================//

		for (var plantTypeBlock : TFCStorage.PLANT_BLOCKS.values()) {register.register((Block) plantTypeBlock);}

	}


	@SubscribeEvent
	@SuppressWarnings("ConstantConditions")
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> register = event.getRegistry();

		//=== Rock ===================================================================================================//

		for (var stoneTypeBlock : TFCStorage.ROCK_BLOCKS.values()) {register.register(createItemBlock((Block) stoneTypeBlock, ItemBlock::new));}

		//=== Soil ===================================================================================================//

		for (var soilTypeBlock : TFCStorage.SOIL_BLOCKS.values()) {register.register(createItemBlock((Block) soilTypeBlock, ItemBlock::new));}

		//=== Plant ==================================================================================================//

		for (var plantTypeBlock : TFCStorage.PLANT_BLOCKS.values()) {register.register(createItemBlock((Block) plantTypeBlock, ItemBlock::new));}

	}


	private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
		ItemBlock itemBlock = producer.apply(block);
		ResourceLocation registryName = block.getRegistryName();
		if (registryName == null) {
			throw new IllegalArgumentException("Block " + block.getTranslationKey() + " has no registry name.");
		}
		itemBlock.setRegistryName(registryName);
		return itemBlock;
	}
}
