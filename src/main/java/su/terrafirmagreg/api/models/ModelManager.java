package su.terrafirmagreg.api.models;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;


public class ModelManager {

	// ===== StateMapper ===========================================================================================================================//

	@SideOnly(Side.CLIENT)
	public static void registerStateMapper(@Nonnull Block block, IStateMapper stateMap) {
		ModelLoader.setCustomStateMapper(block, stateMap);
	}

	// ===== ItemBlock =============================================================================================================================//

	public static void registerBlockItemModels(Block... blocks) {
		for (Block block : blocks) ModelManager.registerBlockItemModel(block);
	}

	public static void registerBlockItemModel(Block block) {

		ModelManager.registerItemModel(Item.getItemFromBlock(block), block.getRegistryName());
	}

	public static void registerBlockItemModel(Block block, String modelLocation) {
		ModelManager.registerItemModel(Item.getItemFromBlock(block), modelLocation);

	}

	public static void registerBlockItemModel(Block block, ResourceLocation modelLocation) {
		ModelManager.registerItemModel(Item.getItemFromBlock(block), modelLocation);

	}

	// ===== Item ================================================================================================================================//

	public static void registerItemModels(String subfolder, Item... items) {
		for (Item item : items) ModelManager.registerItemModels(subfolder, item);
	}

	public static void registerItemModels(String subfolder, Item item) {
		ResourceLocation registryName = item.getRegistryName();
		Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);
		String modelLocation = registryName.getNamespace() + ":" + subfolder + "/" + registryName.getPath();

		ModelManager.registerItemModel(item, modelLocation);
	}


	public static void registerItemModels(Item... items) {
		for (Item item : items) registerItemModel(item);
	}

	public static void registerItemModel(Item item) {
		ResourceLocation registryName = item.getRegistryName();
		Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);

		ModelManager.registerItemModel(item, registryName.toString());
	}

	public static void registerItemModel(Item item, String modelLocation) {
		ModelResourceLocation resourceLocation = new ModelResourceLocation(modelLocation, "inventory");

		ModelManager.registerItemModel(item, 0, resourceLocation);
	}

	public static void registerItemModel(Item item, ResourceLocation modelLocation) {
		ModelResourceLocation resourceLocation = new ModelResourceLocation(modelLocation, "inventory");

		ModelManager.registerItemModel(item, 0, resourceLocation);
	}

	public static void registerItemModel(Item item, ModelResourceLocation resourceLocation) {
		ModelManager.registerItemModel(item, 0, resourceLocation);
	}

	public static void registerItemModel(@NotNull Item item, int metadata, @NotNull String variant) {

		ResourceLocation registryName = item.getRegistryName();
		Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);

		ModelManager.registerItemModel(item, metadata, new ModelResourceLocation(registryName, variant));
	}

	public static void registerItemModel(@NotNull Item item, int meta, @NotNull ModelResourceLocation resourceLocation) {
		ModelLoader.setCustomModelResourceLocation(item, meta, resourceLocation);
	}

}
