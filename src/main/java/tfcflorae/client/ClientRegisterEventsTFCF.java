package tfcflorae.client;

import su.terrafirmagreg.data.Properties;
import su.terrafirmagreg.modules.soil.client.GrassColorHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import com.google.common.base.Strings;
import net.dries007.tfc.objects.GemTFCF;
import net.dries007.tfc.objects.blocks.BlocksTFCF;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropDead;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeLeaves;
import net.dries007.tfc.objects.blocks.groundcover.BlockCoral;
import net.dries007.tfc.objects.blocks.plants.BlockCreepingPlantTFCF;
import net.dries007.tfc.objects.blocks.plants.BlockHangingCreepingPlantTFCF;
import net.dries007.tfc.objects.blocks.plants.BlockHangingPlantTFCF;
import net.dries007.tfc.objects.blocks.plants.BlockPlant.BlockPlantDummy1;
import net.dries007.tfc.objects.blocks.plants.BlockShortGrassTFCF;
import net.dries007.tfc.objects.blocks.plants.BlockTallGrassWater;
import net.dries007.tfc.objects.blocks.plants.BlockWaterGlowPlant;
import net.dries007.tfc.objects.blocks.plants.BlockWaterPlantTFCF;
import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFCF;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFCF;
import net.dries007.tfc.objects.blocks.wood.BlockSaplingTFC;
import net.dries007.tfc.objects.items.ItemArmorTFCF;
import net.dries007.tfc.objects.items.ItemGemTFCF;
import net.dries007.tfc.objects.items.ItemsTFCF;
import tfcflorae.ConfigTFCF;

import static net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC.WILD;
import static su.terrafirmagreg.data.Constants.MODID_TFCF;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = {Side.CLIENT}, modid = MODID_TFCF)
public class ClientRegisterEventsTFCF {

  private final java.util.Map<net.minecraftforge.registries.IRegistryDelegate<Item>, IItemColor> itemColorMap = com.google.common.collect.Maps.newHashMap();

  public ClientRegisterEventsTFCF() {
  }

  @SubscribeEvent
  @SuppressWarnings("ConstantConditions")
  public static void registerModels(ModelRegistryEvent event) {
    // ITEMS

    for (Item item : ItemsTFCF.getAllSimpleItems()) {
      ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName()
              .toString()));
    }

    for (ItemGemTFCF item : ItemsTFCF.getAllGemTFCFItems()) {
      for (GemTFCF.Grade grade : GemTFCF.Grade.values()) {
        registerEnumBasedMetaItems("gem", grade, item);
      }
    }

    for (Item item : ItemsTFCF.getAllItemBows()) {
      ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName()
              .toString()));
    }

    for (ItemArmorTFCF item : ItemsTFCF.getAllArmorItems()) {
      ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName()
              .toString()));
    }

    // BLOCKS

    for (ItemBlock itemBlock : BlocksTFCF.getAllNormalItemBlocks()) {
      ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(itemBlock.getRegistryName()
              .toString()));
    }

    for (Block block : BlocksTFCF.getAllCoralPlants()) {
      ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockCoral.LEVEL).build());
    }

    for (Block block : BlocksTFCF.getAllGlowWaterPlants()) {
      ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockWaterGlowPlant.LEVEL).build());
    }

    if (ConfigTFCF.General.WORLD.enableGroundcoverBones) {
      for (Block block : BlocksTFCF.getAllSurfaceBones()) {
        ModelLoader.setCustomStateMapper(block, new StateMap.Builder().build());
      }
    }

    if (ConfigTFCF.General.WORLD.enableGroundcoverDriftwood) {
      for (Block block : BlocksTFCF.getAllSurfaceDriftwood()) {
        ModelLoader.setCustomStateMapper(block, new StateMap.Builder().build());
      }
    }

    if (ConfigTFCF.General.WORLD.enableGroundcoverFlint) {
      for (Block block : BlocksTFCF.getAllSurfaceFlint()) {
        ModelLoader.setCustomStateMapper(block, new StateMap.Builder().build());
      }
    }

    if (ConfigTFCF.General.WORLD.enableGroundcoverPinecone) {
      for (Block block : BlocksTFCF.getAllSurfacePinecone()) {
        ModelLoader.setCustomStateMapper(block, new StateMap.Builder().build());
      }
    }

    if (ConfigTFCF.General.WORLD.enableGroundcoverSeashell) {
      for (Block block : BlocksTFCF.getAllSurfaceSeashells()) {
        ModelLoader.setCustomStateMapper(block, new StateMap.Builder().build());
      }
    }

    if (ConfigTFCF.General.WORLD.enableGroundcoverTwig) {
      for (Block block : BlocksTFCF.getAllSurfaceTwig()) {
        ModelLoader.setCustomStateMapper(block, new StateMap.Builder().build());
      }
    }

    //        for (Block block : BlocksTFCF.getAllJoshuaTreeSaplingBlocks())
    //            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockJoshuaTreeSapling.STAGE).build());

    for (BlockFruitTreeLeaves leaves : BlocksTFCF.getAllFruitLeaves()) {
      ModelLoader.setCustomStateMapper(leaves, new StateMap.Builder().ignore(BlockFruitTreeLeaves.DECAYABLE)
              .ignore(BlockFruitTreeLeaves.HARVESTABLE)
              .build());
    }

    for (BlockLeavesTFCF leaves : BlocksTFCF.getAllNormalTreeLeaves()) {
      ModelLoader.setCustomStateMapper(leaves, new StateMap.Builder().ignore(BlockLeavesTFCF.DECAYABLE)
              .ignore(BlockLeavesTFCF.HARVESTABLE)
              .build());
    }

    for (BlockLogTFCF Logs : BlocksTFCF.getAllNormalTreeLog()) {
      ModelLoader.setCustomStateMapper(Logs, new StateMap.Builder().ignore(BlockLogTFCF.PLACED).build());
    }

    for (Block block : BlocksTFCF.getAllCropBlocks()) {
      ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(WILD).build());
    }

    for (Block block : BlocksTFCF.getAllFluidBlocks()) {
      ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
    }

    for (Block block : BlocksTFCF.getAllBambooLog()) {
      ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(Properties.CAN_GROW)
              .build());
    }

    for (Block block : BlocksTFCF.getAllBambooLeaves()) {
      ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockLeaves.DECAYABLE).build());
    }

    for (Block block : BlocksTFCF.getAllBambooSapling()) {
      ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockSaplingTFC.STAGE).build());
    }

    ModelLoader.setCustomStateMapper(BlocksTFCF.CASSIA_CINNAMON_LOG, new StateMap.Builder().ignore(Properties.CAN_GROW)
            .build());
    ModelLoader.setCustomStateMapper(BlocksTFCF.CASSIA_CINNAMON_LEAVES, new StateMap.Builder().ignore(BlockLeaves.DECAYABLE)
            .build());
    ModelLoader.setCustomStateMapper(BlocksTFCF.CASSIA_CINNAMON_SAPLING, new StateMap.Builder().ignore(BlockSaplingTFC.STAGE)
            .build());

    ModelLoader.setCustomStateMapper(BlocksTFCF.CEYLON_CINNAMON_LOG, new StateMap.Builder().ignore(Properties.CAN_GROW)
            .build());
    ModelLoader.setCustomStateMapper(BlocksTFCF.CEYLON_CINNAMON_LEAVES, new StateMap.Builder().ignore(BlockLeaves.DECAYABLE)
            .build());
    ModelLoader.setCustomStateMapper(BlocksTFCF.CEYLON_CINNAMON_SAPLING, new StateMap.Builder().ignore(BlockSaplingTFC.STAGE)
            .build());
  }

  @SideOnly(Side.CLIENT)
  private static void registerEnumBasedMetaItems(String prefix, Enum e, Item item) {
    //noinspection ConstantConditions
    String registryName = item.getRegistryName().getPath();
    StringBuilder path = new StringBuilder(MODID_TFCF).append(':');
    if (!Strings.isNullOrEmpty(prefix)) {
      path.append(prefix).append('/');
    }
    path.append(e.name());
    if (!Strings.isNullOrEmpty(prefix)) {
      path.append(registryName.replace(prefix,
              "")); // There well be a '/' at the start of registryName due to the prefix, so don't add an extra one.
    } else {
      path.append('/').append(registryName);
    }
    ModelLoader.setCustomModelResourceLocation(item, e.ordinal(), new ModelResourceLocation(path.toString()
            .toLowerCase()));
  }

  @SuppressWarnings("deprecation")
  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public static void registerColorHandlerItems(ColorHandlerEvent.Item event) {
    ItemColors itemColors = event.getItemColors();

        /*itemColors.registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex)
            {
                return tintIndex > 0 ? -1 : ((ItemArmorTFCF)stack.get()).getColor(stack);
            }
        }, ItemsTFCF.getAllArmorItems().toArray(new ItemArmorTFCF[0]));*/

    itemColors.registerItemColorHandler((stack, tintIndex) ->
                    tintIndex > 0 ? -1 : ((ItemArmorTFCF) stack.getItem()).getColor(stack),
            ItemsTFCF.getAllArmorItems().toArray(new ItemArmorTFCF[0]));

    itemColors.registerItemColorHandler((stack, tintIndex) ->
                    event.getBlockColors()
                            .colorMultiplier(((ItemBlock) stack.getItem()).getBlock()
                                    .getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllFruitLeaves().toArray(new BlockFruitTreeLeaves[0])
    );

    itemColors.registerItemColorHandler((stack, tintIndex) ->
                    event.getBlockColors()
                            .colorMultiplier(((ItemBlock) stack.getItem()).getBlock()
                                    .getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllNormalTreeLeaves().toArray(new BlockLeavesTFCF[0])
    );

    itemColors.registerItemColorHandler((stack, tintIndex) ->
                    event.getBlockColors()
                            .colorMultiplier(((ItemBlock) stack.getItem()).getBlock()
                                    .getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.CASSIA_CINNAMON_LEAVES);

    itemColors.registerItemColorHandler((stack, tintIndex) ->
                    event.getBlockColors()
                            .colorMultiplier(((ItemBlock) stack.getItem()).getBlock()
                                    .getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.CEYLON_CINNAMON_LEAVES);

    itemColors.registerItemColorHandler((stack, tintIndex) ->
                    event.getBlockColors()
                            .colorMultiplier(((ItemBlock) stack.getItem()).getBlock()
                                    .getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllTallGrassWaterBlocks().toArray(new BlockTallGrassWater[0]));

        /*itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.get()).get().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllShortGrassBlocks().toArray(new BlockShortGrassTFCF[0]));

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.get()).get().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllTallGrassBlocks().toArray(new BlockTallGrassTFCF[0]));*/

        /*itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.get()).get().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllStandardBlocks().toArray(new BlockPlantTFCF[0]));

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.get()).get().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllStandardBlocks().toArray(new BlockPlantDummy1[0]));

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.get()).get().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllStandardBlocks().toArray(new BlockPlantDummy2[0]));*/

        /*itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.get()).get().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllHangingCreepingPlantBlocks().toArray(new BlockHangingCreepingPlantTFCF[0]));

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                event.getBlockColors().colorMultiplier(((ItemBlock) stack.get()).get().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
            BlocksTFCF.getAllCreepingPlantBlocks().toArray(new BlockCreepingPlantTFCF[0]));*/
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public static void registerColorHandlerBlocks(ColorHandlerEvent.Block event) {
    BlockColors blockColors = event.getBlockColors();
    IBlockColor grassColor = GrassColorHandler::computeGrassColor;
    IBlockColor foliageColor = GrassColorHandler::computeGrassColor;

    blockColors.registerBlockColorHandler(grassColor, BlocksTFCF.getAllShortGrassBlocks()
            .toArray(new BlockShortGrassTFCF[0]));
    //blockColors.registerBlockColorHandler(grassColor, BlocksTFCF.getAllTallGrassBlocks().toArray(new BlockTallGrassTFCF[0]));

    blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllFruitLeaves().toArray(new Block[0]));
    blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllNormalTreeLeaves().toArray(new Block[0]));

    for (BlockCropDead block : BlocksTFCF.getAllDeadCrops()) {
      blockColors.registerBlockColorHandler((state, world, os, tintIndex) -> 0xCC7400, block);
    }

    for (Block block : BlocksTFCF.getAllBambooLeaves()) {
      blockColors.registerBlockColorHandler(foliageColor, block);
    }

    blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.CASSIA_CINNAMON_LEAVES);
    blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.CEYLON_CINNAMON_LEAVES);
    blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllWaterPlantBlocks()
            .toArray(new BlockWaterPlantTFCF[0]));
    blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllHangingPlantBlocks()
            .toArray(new BlockHangingPlantTFCF[0]));
    blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllHangingCreepingPlantBlocks()
            .toArray(new BlockHangingCreepingPlantTFCF[0]));
    blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllCreepingPlantBlocks()
            .toArray(new BlockCreepingPlantTFCF[0]));
    blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllTallGrassWaterBlocks()
            .toArray(new BlockTallGrassWater[0]));
    //blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllStandardBlocks().toArray(new BlockPlantTFCF[0]));
    blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllStandardBlocks()
            .toArray(new BlockPlantDummy1[0]));
    //blockColors.registerBlockColorHandler(foliageColor, BlocksTFCF.getAllStandardBlocks().toArray(new BlockPlantDummy2[0]));

  }
}
