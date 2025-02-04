package su.terrafirmagreg.api.util;

import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.base.object.item.api.IItemSettings;
import su.terrafirmagreg.api.library.model.CustomStateMap;
import su.terrafirmagreg.framework.registry.api.provider.IProviderBlockColor;
import su.terrafirmagreg.framework.registry.api.provider.IProviderBlockState;
import su.terrafirmagreg.framework.registry.api.provider.IProviderItemColor;
import su.terrafirmagreg.framework.registry.api.provider.IProviderItemMesh;
import su.terrafirmagreg.framework.registry.api.provider.IProviderTile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.base.Preconditions;

import org.jetbrains.annotations.NotNull;

import lombok.experimental.UtilityClass;

@SideOnly(Side.CLIENT)
@UtilityClass
@SuppressWarnings("unused")
public final class ModelUtils {

  //region ===== StateMapper

  /**
   * A {@link StateMapperBase} used to create property strings.
   */
  public static final StateMapperBase PROPERTY_STRING_MAPPER = new StateMapperBase() {

    @Override
    protected @NotNull ModelResourceLocation getModelResourceLocation(@NotNull IBlockState state) {
      return new ModelResourceLocation("minecraft:air");
    }
  };

  public static void stateMapper(Block block) {
    if (block instanceof IProviderBlockState provider) {
      ModelUtils.stateMapper(block, provider.getStateMapper());
      return;
    }
    if (block instanceof IBlockSettings provider) {
      final var settings = provider.getSettings();
      final var ignored = settings.getIgnoredProperties();
      final var resource = settings.getResource();

      ModelUtils.stateMapper(block, CustomStateMap.builder().ignore(ignored).customResource(resource).build());
    }
  }

  public static void stateMapper(Block block, IStateMapper stateMap) {
    if (stateMap != null) {
      ModelLoader.setCustomStateMapper(block, stateMap);
    }
  }

  //endregion

  //region ===== ItemBlock


  public static void model(Block block) {
    if (block instanceof IBlockSettings provider) {
      if (provider.getSettings().getResource() != null) {
        ModelUtils.model(block, provider.getSettings().getResource());
        return;
      }
    }

    ResourceLocation registryName = block.getRegistryName();
    Preconditions.checkNotNull(registryName, "block %s has null registry name", block);

    ModelUtils.model(Item.getItemFromBlock(block), registryName);

  }

  public static void model(IBlockState blockState) {

    Block block = blockState.getBlock();
    Item item = Item.getItemFromBlock(block);

    ModelUtils.model(item, new ModelResourceLocation(
        Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block),
        PROPERTY_STRING_MAPPER.getPropertyString(blockState.getProperties())
      )
    );
  }

  public static void model(Block block, String modelLocation) {

    ModelUtils.model(Item.getItemFromBlock(block), modelLocation);
  }

  public static void model(Block block, ResourceLocation modelLocation) {

    ModelUtils.model(Item.getItemFromBlock(block), modelLocation);
  }

  public static void model(String subfolder, Block block) {
    ResourceLocation registryName = block.getRegistryName();
    Preconditions.checkNotNull(registryName, "Item %s has null registry name", block);
    String modelLocation = registryName.getNamespace() + ":" + subfolder + "/" + registryName.getPath();

    ModelUtils.model(block, modelLocation);
  }

  //endregion

  //region ===== Item

  public static void model(Item item) {
    if (item instanceof IItemSettings provider) {
      if (provider.getSettings().getResource() != null) {
        ModelUtils.model(item, provider.getSettings().getResource());
        return;
      }

    }
    if (item instanceof ItemBlock itemBlock) {
      var block = itemBlock.getBlock();
      if (block instanceof IBlockSettings provider) {
        if (provider.getSettings().getResource() != null) {
          ModelUtils.model(itemBlock, provider.getSettings().getResource());
          return;
        }
      }
      ModelUtils.model(itemBlock, block.getRegistryName());
      return;
    }

    ResourceLocation registryName = item.getRegistryName();
    Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);

    ModelUtils.model(item, registryName);
  }

  public static void model(Item item, String modelLocation) {

    ModelUtils.model(item, new ModelResourceLocation(modelLocation, "inventory"));
  }

  public static void model(Item item, ModelResourceLocation resourceLocation) {

    ModelUtils.model(item, 0, resourceLocation);
  }

  public static void model(Item item, int metadata, ModelResourceLocation resourceLocation) {

    ModelLoader.setCustomModelResourceLocation(item, metadata, resourceLocation);
  }

  public static void model(Item item, ResourceLocation modelLocation) {

    ModelUtils.model(item, new ModelResourceLocation(modelLocation, "inventory"));
  }


  public static void model(@NotNull Item item, int metadata, @NotNull String variant) {
    ResourceLocation registryName = item.getRegistryName();
    Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);

    ModelUtils.model(item, metadata, new ModelResourceLocation(registryName, variant));
  }

  public static void model(String subfolder, Item item) {
    ResourceLocation registryName = item.getRegistryName();
    Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);
    String modelLocation = registryName.getNamespace() + ":" + subfolder + "/" + registryName.getPath();

    ModelUtils.model(item, modelLocation);
  }

  public static void customMeshDefinition(Item item) {

    if (item instanceof IProviderItemMesh provider) {
      ModelUtils.customMeshDefinition(item, provider.getItemMesh());
    }
  }

  public static void customMeshDefinition(Item item, ItemMeshDefinition meshDefinition) {

    ModelLoader.setCustomMeshDefinition(item, meshDefinition);
  }

  //endregion

  //region ===== TileEntitySpecialRenderer

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T extends TileEntity> void tesr(Block block) {
    if (block instanceof IProviderTile provider) {
      final TileEntitySpecialRenderer tesr = provider.getTileRenderer();

      ModelUtils.tesr(provider.getTileClass(), tesr);
    }
  }

  public static <T extends TileEntity> void tesr(Class<T> tileEntityClass, TileEntitySpecialRenderer<? super T> specialRenderer) {
    if (specialRenderer != null) {
      ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, specialRenderer);
    }
  }

  //endregion

  //region ===== TileEntitySpecialRenderer

//  @SuppressWarnings({"unchecked"})
//  public static <T extends EntityEntry> void entity(T entity) {
//    var entityClass = entity.getEntityClass();
//    var clazz = IProviderEntityRenderer.class.isAssignableFrom(entityClass);
//    if (clazz) {
//      var classSubclass = entityClass.asSubclass(IProviderEntityRenderer.class);
//      var provider = ClassUtils.createObjectInstance(classSubclass);
//
//      ModelUtils.entity(entityClass, provider.getRenderFactory());
//    }
//  }

  public static <T extends Entity> void entity(Class<T> entityClass, IRenderFactory<? super T> renderFactory) {
    if (renderFactory != null) {
      RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
    }
  }

  //endregion

  //region ===== Color

  public void color(final BlockColors blockColors, Block block) {
    if (block instanceof IProviderBlockColor provider) {
      if (provider.getBlockColor() != null) {
        blockColors.registerBlockColorHandler(provider.getBlockColor(), block);
      }
    }
  }

  public void color(final ItemColors itemColors, Block block) {
    if (block instanceof IProviderItemColor provider) {
      if (provider.getItemColor() != null) {
        itemColors.registerItemColorHandler(provider.getItemColor(), block);
      }
    }
  }

  public void color(final ItemColors itemColors, Item item) {
    if (item instanceof IProviderItemColor provider) {
      if (provider.getItemColor() != null) {
        itemColors.registerItemColorHandler(provider.getItemColor(), item);
      }
    }
  }

  //endregion
}
