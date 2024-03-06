package su.terrafirmagreg.api.models;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;


public class ModelManager {

	// ===== StateMapper ===========================================================================================================================//

	/**
	 * A {@link StateMapperBase} used to create property strings.
	 */
	public static StateMapperBase PROPERTY_STRING_MAPPER = new StateMapperBase() {
		@Override
		protected @NotNull ModelResourceLocation getModelResourceLocation(@NotNull IBlockState state) {
			return new ModelResourceLocation("minecraft:air");
		}
	};


	@SideOnly(Side.CLIENT)
	public static void registerStateMapper(@Nonnull Block block, IStateMapper stateMap) {
		ModelLoader.setCustomStateMapper(block, stateMap);
	}


	// ===== ItemBlock =============================================================================================================================//

	public static void registerBlockInventoryModes(String subfolder, Block... blocks) {
		for (Block block : blocks) ModelManager.registerBlockInventoryModel(subfolder, block);
	}

	public static void registerBlockInventoryModel(String subfolder, Block block) {
		ResourceLocation registryName = block.getRegistryName();
		Preconditions.checkNotNull(registryName, "Item %s has null registry name", block);
		String modelLocation = registryName.getNamespace() + ":" + subfolder + "/" + registryName.getPath();

		ModelManager.registerBlockInventoryModel(block, modelLocation);
	}

	public static void registerBlockInventoryModes(Block... blocks) {
		for (Block block : blocks) ModelManager.registerBlockInventoryModel(block);
	}

	public static void registerBlockInventoryModel(Block block) {
		ResourceLocation registryName = block.getRegistryName();
		Preconditions.checkNotNull(registryName, "block %s has null registry name", block);
		ModelManager.registerInventoryModel(Item.getItemFromBlock(block), registryName);
	}

	public static void registerBlockInventoryModel(Block block, String modelLocation) {
		ModelManager.registerInventoryModel(Item.getItemFromBlock(block), modelLocation);

	}

	public static void registerBlockInventoryModel(Block block, ResourceLocation modelLocation) {
		ModelManager.registerInventoryModel(Item.getItemFromBlock(block), modelLocation);

	}

	// ===== Item ================================================================================================================================//

	public static void registerInventoryModel(String subfolder, Item... items) {
		for (Item item : items) ModelManager.registerInventoryModel(subfolder, item);
	}

	public static void registerInventoryModel(String subfolder, Item item) {
		ResourceLocation registryName = item.getRegistryName();
		Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);
		String modelLocation = registryName.getNamespace() + ":" + subfolder + "/" + registryName.getPath();

		ModelManager.registerInventoryModel(item, modelLocation);
	}


	public static void registerInventoryModel(Item... items) {
		for (Item item : items) registerInventoryModel(item);
	}

	public static void registerInventoryModel(Item item) {
		ResourceLocation registryName = item.getRegistryName();
		Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);

		ModelManager.registerInventoryModel(item, registryName);
	}

	public static void registerInventoryModel(Item item, String modelLocation) {
		ModelManager.registerInventoryModel(item, new ModelResourceLocation(modelLocation, "inventory"));
	}

	public static void registerInventoryModel(Item item, ResourceLocation modelLocation) {
		ModelManager.registerInventoryModel(item, new ModelResourceLocation(modelLocation, "inventory"));
	}

	public static void registerInventoryModel(Item item, ModelResourceLocation resourceLocation) {
		ModelManager.registerInventoryModel(item, 0, resourceLocation);
	}

	public static void registerInventoryModel(@NotNull Item item, int metadata, @NotNull String variant) {
		ResourceLocation registryName = item.getRegistryName();
		Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);

		ModelManager.registerInventoryModel(item, metadata, new ModelResourceLocation(registryName, variant));
	}

	public static void registerInventoryModel(@NotNull Item item, int meta, @NotNull ModelResourceLocation resourceLocation) {
		ModelLoader.setCustomModelResourceLocation(item, meta, resourceLocation);
	}

}
