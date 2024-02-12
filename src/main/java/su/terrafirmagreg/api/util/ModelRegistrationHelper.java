package su.terrafirmagreg.api.util;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import com.google.common.base.Preconditions;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.IHasModel;

import javax.annotation.Nonnull;
import java.util.function.ToIntFunction;

/**
 * Based on:
 * <a href="https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1.12.2/src/main/java/choonster/testmod3/client/model/ModModelManager.java">...</a>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ModelRegistrationHelper {

    /**
     * A {@link StateMapperBase} used to create property strings.
     */
    public static final StateMapperBase PROPERTY_STRING_MAPPER = new StateMapperBase() {

        @Override
        protected @NotNull ModelResourceLocation getModelResourceLocation(@NotNull IBlockState state) {

            return new ModelResourceLocation("minecraft:air");
        }
    };

    // --------------------------------------------------------------------------
    // - ItemBlock
    // --------------------------------------------------------------------------

    /**
     * Registers given blocks either as variant block item models or single block item models.
     *
     * @param blocks the blocks
     */
    public static void registerBlockItemModels(Block... blocks) {
        for (Block block : blocks) {
            ModelRegistrationHelper.registerBlockItemModel(block);
        }
    }

    public static void registerBlockItemModel(Block block) {

        if (block instanceof IHasModel model) {
            model.onModelRegister();

        } else {
            ModelRegistrationHelper.registerBlockItemModel(block.getDefaultState());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockModel(@Nonnull Block block, IStateMapper stateMap) {
        ModelLoader.setCustomStateMapper(block, stateMap);
    }

    /**
     * Registers a single item model for the block of the given blockState.
     * <p>
     * Uses the block registry name as the resource path / domain.
     * Uses the property string mapper for the variant.
     *
     * @param blockState the blockState
     */
    public static void registerBlockItemModel(IBlockState blockState) {

        Block block = blockState.getBlock();
        Item item = Item.getItemFromBlock(block);

        ResourceLocation registryName = block.getRegistryName();
        Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);

        ModelRegistrationHelper.registerItemModel(

                item,
                new ModelResourceLocation(
                        registryName,
                        PROPERTY_STRING_MAPPER.getPropertyString(blockState.getProperties())
                )
        );
    }


    /**
     * Register a model for each property given.
     * <p>
     * Uses the registry name as the domain / path and the property string mapper for the variant.
     * <p>
     * Note: Throws NPE if the block's item doesn't have a registry name.
     *
     * @param baseState the blockState
     * @param property  the property
     * @param getMeta   the meta function
     * @param <T>       the property type
     */
    public static <T extends Comparable<T>> void registerVariantBlockItemModels(
            IBlockState baseState,
            IProperty<T> property,
            ToIntFunction<T> getMeta
    ) {

        property.getAllowedValues()
                .forEach(value -> ModelRegistrationHelper.registerBlockItemModelForMeta(
                        baseState.withProperty(property, value),
                        getMeta.applyAsInt(value)
                ));
    }

    /**
     * Register a model for the blockState and meta given.
     * <p>
     * Uses the registry name as the domain / path and the property string mapper for the variant.
     * <p>
     * Note: Throws NPE if the block's item doesn't have a registry name.
     *
     * @param state    the blockState
     * @param metadata the meta
     */
    public static void registerBlockItemModelForMeta(final IBlockState state, final int metadata) {

        Item item = Item.getItemFromBlock(state.getBlock());

        if (item == Items.AIR) {
            return;
        }

        ModelRegistrationHelper.registerItemModel(item, metadata, PROPERTY_STRING_MAPPER.getPropertyString(state.getProperties())
        );
    }

    // --------------------------------------------------------------------------
    // - Item
    // --------------------------------------------------------------------------

    /**
     * Register a single model for the given items.
     * <p>
     * Uses a meta of 0.
     * <p>
     * Uses the registry name as the domain / path and {@code "inventory"} as the variant.
     * <p>
     * Note: This method will check that each item has a registry name and throw a NPE if it doesn't.
     *
     * @param items the items
     */
    public static void registerItemModels(Item... items) {

        for (Item item : items) {
            ModelRegistrationHelper.registerItemModel(item);
        }
    }

    public static void registerItemModel(Item item) {
        if (item instanceof IHasModel model) {
            model.onModelRegister();

        } else {
            ResourceLocation registryName = item.getRegistryName();
            Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);
            ModelRegistrationHelper.registerItemModel(item, registryName.toString());
        }
    }

    /**
     * The same as {@link #registerItemModels(Item...)}, but allows providing
     * a model subfolder. The given subfolder string must not have a preceding or
     * trailing slash.
     *
     * @param subfolder subfolder model location
     * @param items     the items
     * @see ModelRegistrationHelper#registerItemModels(Item...)
     */
    public static void registerItemModels(String subfolder, Item... items) {

        for (Item item : items) {
            ResourceLocation registryName = item.getRegistryName();
            Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);
            String modelLocation = registryName.getNamespace() + ":" + subfolder + "/" + registryName.getPath();
            ModelRegistrationHelper.registerItemModel(item, modelLocation);
        }
    }

    /**
     * Register a single model for the given item.
     * <p>
     * Uses a meta of 0.
     * <p>
     * Uses {@code modelLocation} as the domain/path and {@code "inventory"} as the variant.
     *
     * @param item          the item
     * @param modelLocation the model location
     */
    public static void registerItemModel(Item item, String modelLocation) {

        ModelResourceLocation resourceLocation = new ModelResourceLocation(modelLocation, "inventory");
        ModelRegistrationHelper.registerItemModel(item, 0, resourceLocation);
    }

    public static void registerItemModel(Item item, ResourceLocation modelLocation) {

        ModelResourceLocation resourceLocation = new ModelResourceLocation(modelLocation, "inventory");
        ModelRegistrationHelper.registerItemModel(item, 0, resourceLocation);
    }



    /**
     * Register a single model for the item given.
     * <p>
     * Uses a meta of 0.
     *
     * @param item             the item
     * @param resourceLocation the model resource location
     */
    public static void registerItemModel(Item item, ModelResourceLocation resourceLocation) {

        ModelRegistrationHelper.registerItemModel(item, 0, resourceLocation);
    }

    /**
     * Register a model for the item and meta given.
     * <p>
     * Uses the registry name as the domain / path and the given variant.
     * <p>
     * Note: This method will check that the item has a registry name and throw a NPE if it doesn't.
     *
     * @param item     the item
     * @param metadata the item's meta
     * @param variant  the variant
     */
    public static void registerItemModel(@NotNull final Item item, final int metadata, @NotNull final String variant) {

        ResourceLocation registryName = item.getRegistryName();
        Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);

        ModelRegistrationHelper.registerItemModel(item, metadata, new ModelResourceLocation(registryName, variant)
        );
    }


    /**
     * Sets a custom model resource location for the item and meta given.
     *
     * @param item             the item
     * @param meta             the item's meta
     * @param resourceLocation the custom model resource location
     */
    public static void registerItemModel(@NotNull Item item, int meta, @NotNull ModelResourceLocation resourceLocation) {

        ModelLoader.setCustomModelResourceLocation(item, meta, resourceLocation);
    }

}
