package net.dries007.horsepower.client;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.horsepower.blocks.ModBlocks;
import net.dries007.horsepower.client.model.BakedChopperModel;

import java.util.HashSet;
import java.util.Set;

import static su.terrafirmagreg.api.data.Reference.MODID_HORSEPOWER;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MODID_HORSEPOWER)
public class ModModelManager {

  public static final ModModelManager INSTANCE = new ModModelManager();
  private static final ResourceLocation MODEL_ChoppingBlock = new ResourceLocation(MODID_HORSEPOWER, "block/chopper");
  private static final ResourceLocation MODEL_ManualChoppingBlock = new ResourceLocation(MODID_HORSEPOWER, "block/chopping_block");
  private final Set<Item> itemsRegistered = new HashSet<>();
  /**
   * A {@link StateMapperBase} used to create property strings.
   */
  private final StateMapperBase propertyStringMapper = new StateMapperBase() {

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
      return new ModelResourceLocation("minecraft:air");
    }
  };

  public ModModelManager() {
  }

  @SubscribeEvent
  public static void onModelBake(ModelBakeEvent event) {
    replaceChoppingModel(new ModelResourceLocation("horsepower:chopper", "facing=north,part=base"), MODEL_ChoppingBlock, event);
    replaceChoppingModel(new ModelResourceLocation("horsepower:chopper", "facing=south,part=base"), MODEL_ChoppingBlock, event);
    replaceChoppingModel(new ModelResourceLocation("horsepower:chopper", "facing=west,part=base"), MODEL_ChoppingBlock, event);
    replaceChoppingModel(new ModelResourceLocation("horsepower:chopper", "facing=east,part=base"), MODEL_ChoppingBlock, event);
    replaceChoppingModel(new ModelResourceLocation("horsepower:chopping_block"), MODEL_ManualChoppingBlock, event);

    event.getModelRegistry()
         .putObject(getModel("chopper"), event.getModelRegistry()
                                              .getObject(new ModelResourceLocation("horsepower:chopper", "facing=north,part=base")));
    event.getModelRegistry()
         .putObject(getModel("chopper"), event.getModelRegistry()
                                              .getObject(new ModelResourceLocation("horsepower:chopper", "facing=south,part=base")));
    event.getModelRegistry()
         .putObject(getModel("chopper"), event.getModelRegistry()
                                              .getObject(new ModelResourceLocation("horsepower:chopper", "facing=west,part=base")));
    event.getModelRegistry()
         .putObject(getModel("chopper"), event.getModelRegistry()
                                              .getObject(new ModelResourceLocation("horsepower:chopper", "facing=east,part=base")));
    event.getModelRegistry()
         .putObject(getModel("chopping_block"), event.getModelRegistry()
                                                     .getObject(new ModelResourceLocation("horsepower:chopping_block")));
  }

  public static void replaceChoppingModel(ModelResourceLocation modelVariantLocation, ResourceLocation modelLocation, ModelBakeEvent event) {
    try {
      IModel model = ModelLoaderRegistry.getModel(modelLocation);
      IBakedModel standard = event.getModelRegistry().getObject(modelVariantLocation);
      if (standard != null) {
        IBakedModel finalModel = new BakedChopperModel(standard, model, DefaultVertexFormats.BLOCK);

        event.getModelRegistry().putObject(modelVariantLocation, finalModel);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static ModelResourceLocation getModel(String resource) {
    return new ModelResourceLocation(MODID_HORSEPOWER + ":" + resource, "inventory");
  }

  @SubscribeEvent
  public static void registerAllModels(ModelRegistryEvent event) {
    INSTANCE.registerBlockModels();
  }

  private void registerBlockModels() {
    ModBlocks.RegistrationHandler.ITEM_BLOCKS.stream()
                                             .filter(item -> !itemsRegistered.contains(item))
                                             .forEach(this::registerItemModel);
  }


  /*
   * Register a model for each metadata value of the {@link Block}'s {@link Item} corresponding to the values of an {@link IProperty}.
   * <p>
   * For each value:
   * <li>The domain/path is the registry name</li>
   * <li>The variant is {@code baseState} with the {@link IProperty} set to the value</li>
   * <p>
   * The {@code getMeta} function is used to get the metadata of each value.
   *
   * @param baseState The base state to use for the variant
   * @param property  The property whose values should be used
   * @param getMeta   A function to get the metadata of each value
   * @param <T>       The value type
   */
    /*private <T extends Comparable<T>> void registerVariantBlockItemModels(IBlockState baseState, IProperty<T> property, ToIntFunction<T> getMeta) {
        property.getAllowedValues().forEach(value -> registerBlockItemModelForMeta(baseState.withProperty(property, value), getMeta.applyAsInt(value)));
    }

    private <T extends VariantItem> void registerVariantItems(T variant, String variantName) {
        variant.getMetas().forEach(value -> registerItemModelForMeta(variant, value, variantName + "=" + variant.getVariant(value)));
    }
*/

  /**
   * Register a single model for an {@link Item}.
   * <p>
   * Uses the registry name as the domain/path and {@code "inventory"} as the variant.
   *
   * @param item The Item
   */
  private void registerItemModel(Item item) {
    registerItemModel(item, item.getRegistryName().toString());
  }

  /**
   * Register a single model for an {@link Item}.
   * <p>
   * Uses {@code modelLocation} as the domain/path and {@link "inventory"} as the variant.
   *
   * @param item          The Item
   * @param modelLocation The model location
   */
  private void registerItemModel(Item item, String modelLocation) {
    final ModelResourceLocation fullModelLocation = new ModelResourceLocation(modelLocation, "inventory");
    registerItemModel(item, fullModelLocation);
  }

  /**
   * Register a single model for an {@link Item}.
   * <p>
   * Uses {@code fullModelLocation} as the domain, path and variant.
   *
   * @param item              The Item
   * @param fullModelLocation The full model location
   */
  private void registerItemModel(Item item, ModelResourceLocation fullModelLocation) {
    ModelBakery.registerItemVariants(item,
                                     fullModelLocation); // Ensure the custom model is loaded and prevent the default model from being loaded
    registerItemModel(item, MeshDefinitionFix.create(stack -> fullModelLocation));
  }

  /**
   * Register an {@link ItemMeshDefinition} for an {@link Item}.
   *
   * @param item           The Item
   * @param meshDefinition The ItemMeshDefinition
   */
  private void registerItemModel(Item item, ItemMeshDefinition meshDefinition) {
    itemsRegistered.add(item);
    ModelLoader.setCustomMeshDefinition(item, meshDefinition);
  }

  /**
   * Register a single model for the {@link Block}'s {@link Item}.
   * <p>
   * Uses the registry name as the domain/path and the {@link IBlockState} as the variant.
   *
   * @param state The state to use as the variant
   */
  private void registerBlockInventoryModel(IBlockState state) {
    final Block block = state.getBlock();
    final Item item = Item.getItemFromBlock(block);

    if (item != null) {
      registerItemModel(item,
                        new ModelResourceLocation(block.getRegistryName(), propertyStringMapper.getPropertyString(state.getProperties())));
    }
  }

  /**
   * Register a model for a metadata value of the {@link Block}'s {@link Item}.
   * <p>
   * Uses the registry name as the domain/path and the {@link IBlockState} as the variant.
   *
   * @param state    The state to use as the variant
   * @param metadata The items metadata to register the model for
   */
  private void registerBlockItemModelForMeta(IBlockState state, int metadata) {
    final Item item = Item.getItemFromBlock(state.getBlock());

    if (item != null) {
      registerItemModelForMeta(item, metadata, propertyStringMapper.getPropertyString(state.getProperties()));
    }
  }

  /**
   * Register a model for a metadata value an {@link Item}.
   * <p>
   * Uses the registry name as the domain/path and {@code variant} as the variant.
   *
   * @param item     The Item
   * @param metadata The metadata
   * @param variant  The variant
   */
  private void registerItemModelForMeta(Item item, int metadata, String variant) {
    registerItemModelForMeta(item, metadata, new ModelResourceLocation(item.getRegistryName(), variant));
  }

  /**
   * Register a model for a metadata value of an {@link Item}.
   * <p>
   * Uses {@code modelResourceLocation} as the domain, path and variant.
   *
   * @param item                  The Item
   * @param metadata              The metadata
   * @param modelResourceLocation The full model location
   */
  private void registerItemModelForMeta(Item item, int metadata, ModelResourceLocation modelResourceLocation) {
    itemsRegistered.add(item);
    ModelLoader.setCustomModelResourceLocation(item, metadata, modelResourceLocation);
  }
}
