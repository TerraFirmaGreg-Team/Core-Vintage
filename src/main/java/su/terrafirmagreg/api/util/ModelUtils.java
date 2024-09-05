package su.terrafirmagreg.api.util;

import su.terrafirmagreg.data.lib.model.CustomModelLoader;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import com.google.common.base.Preconditions;

import org.jetbrains.annotations.NotNull;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public final class ModelUtils {

  //region ===== StateMapper

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
  public static void registerStateMapper(@NotNull Block block, IStateMapper stateMap) {
    if (stateMap != null) {
      ModelLoader.setCustomStateMapper(block, stateMap);
    }
  }

  //endregion

  //region ===== ItemBlock

  public static void registerBlockInventoryModes(String subfolder, Block... blocks) {
    for (Block block : blocks) {
      ResourceLocation registryName = block.getRegistryName();
      Preconditions.checkNotNull(registryName, "Item %s has null registry name", block);
      String modelLocation =
          registryName.getNamespace() + ":" + subfolder + "/" + registryName.getPath();

      ModelUtils.registerBlockInventoryModel(block, modelLocation);
    }
  }

  public static void registerBlockInventoryModes(Block... blocks) {
    for (Block block : blocks) {
      ModelUtils.registerBlockInventoryModel(block);
    }
  }

  public static void registerBlockInventoryModel(Block block) {
    ResourceLocation registryName = block.getRegistryName();
    Preconditions.checkNotNull(registryName, "block %s has null registry name", block);

    ModelUtils.registerInventoryModel(Item.getItemFromBlock(block), registryName);
  }

  public static void registerBlockInventoryModel(Block block, String modelLocation) {

    ModelUtils.registerInventoryModel(Item.getItemFromBlock(block), modelLocation);
  }

  public static void registerBlockInventoryModel(Block block, ResourceLocation modelLocation) {

    ModelUtils.registerInventoryModel(Item.getItemFromBlock(block), modelLocation);
  }

  public static void registerCustomMeshDefinition(Block block, ItemMeshDefinition meshDefinition) {

    ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(block), meshDefinition);
  }

  //endregion

  //region ===== Item

  public static void registerInventoryModel(String subfolder, Item... items) {
    for (Item item : items) {
      ModelUtils.registerInventoryModel(subfolder, item);
    }
  }

  public static void registerInventoryModel(String subfolder, Item item) {
    ResourceLocation registryName = item.getRegistryName();
    Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);
    String modelLocation =
        registryName.getNamespace() + ":" + subfolder + "/" + registryName.getPath();

    ModelUtils.registerInventoryModel(item, modelLocation);
  }

  public static void registerInventoryModel(Item... items) {

    for (Item item : items) {
      ModelUtils.registerInventoryModel(item);
    }
  }

  public static void registerInventoryModel(Item item) {
    ResourceLocation registryName = item.getRegistryName();
    Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);

    ModelUtils.registerInventoryModel(item, registryName);
  }

  public static void registerInventoryModel(Item item, String modelLocation) {

    ModelUtils.registerInventoryModel(item, new ModelResourceLocation(modelLocation, "inventory"));
  }

  public static void registerInventoryModel(Item item, ResourceLocation modelLocation) {

    ModelUtils.registerInventoryModel(item, new ModelResourceLocation(modelLocation, "inventory"));
  }

  public static void registerInventoryModel(Item item, ModelResourceLocation resourceLocation) {

    ModelUtils.registerInventoryModel(item, 0, resourceLocation);
  }

  public static void registerInventoryModel(@NotNull Item item, int metadata,
      @NotNull String variant) {
    ResourceLocation registryName = item.getRegistryName();
    Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);

    ModelUtils.registerInventoryModel(item, metadata,
        new ModelResourceLocation(registryName, variant));
  }

  public static void registerInventoryModel(@NotNull Item item, int metadata,
      @NotNull ModelResourceLocation resourceLocation) {

    ModelLoader.setCustomModelResourceLocation(item, metadata, resourceLocation);
  }

  public static void registerCustomMeshDefinition(Item item, ItemMeshDefinition meshDefinition) {

    ModelLoader.setCustomMeshDefinition(item, meshDefinition);
  }

  //endregion

  //region ===== Backed

  public static void registerBackedModel(ResourceLocation resourceLocation, @NotNull IModel model) {

    CustomModelLoader.registerModel(resourceLocation, model);
  }

  //endregion

  //endregion

  //region ===== TileEntitySpecialRenderer

  public static <T extends TileEntity> void registerTileEntitySpecialRenderer(
      Class<T> tileEntityClass, TileEntitySpecialRenderer<? super T> specialRenderer) {

    if (specialRenderer != null) {
      ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, specialRenderer);
    }
  }

  //endregion
}
