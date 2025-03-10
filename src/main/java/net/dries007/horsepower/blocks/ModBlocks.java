package net.dries007.horsepower.blocks;

import su.terrafirmagreg.modules.device.object.block.BlockQuernHorse;
import su.terrafirmagreg.modules.device.object.block.BlockQuernManual;
import su.terrafirmagreg.modules.device.object.tile.TileChopperHorse;
import su.terrafirmagreg.modules.device.object.tile.TileChopperManual;
import su.terrafirmagreg.modules.device.object.tile.TileQuernHorse;
import su.terrafirmagreg.modules.device.object.tile.TileQuernManual;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.horsepower.items.ItemBlockDouble;
import net.dries007.horsepower.lib.Reference;
import net.dries007.horsepower.tileentity.TileEntityFiller;
import net.dries007.horsepower.tileentity.TileEntityPress;

import java.util.HashSet;
import java.util.Set;

public class ModBlocks {

  public static final BlockQuernManual BLOCK_HAND_GRINDSTONE = new BlockQuernManual();
  public static final BlockQuernHorse BLOCK_GRINDSTONE = new BlockQuernHorse();
  public static final BlockChoppingBlock BLOCK_MANUAL_CHOPPER = new BlockChoppingBlock();
  public static final BlockChopper BLOCK_CHOPPER = new BlockChopper();
  public static final BlockFiller BLOCK_CHOPPER_FILLER = (BlockFiller) new BlockFiller(Material.WOOD, "chopper_", true).setHarvestLevel1("axe", 0)
    .setHardness(5F).setResistance(5F);
  public static final BlockPress BLOCK_PRESS = new BlockPress();
  public static final BlockFiller BLOCK_PRESS_FILLER = (BlockFiller) new BlockFiller(Material.WOOD, "press_", true).setHarvestLevel1("axe", 1).setHardness(5F)
    .setResistance(5F);

  public static void registerTileEntities() {
    registerTileEntity(TileQuernManual.class);
    registerTileEntity(TileQuernHorse.class);
    registerTileEntity(TileChopperManual.class);
    registerTileEntity(TileChopperHorse.class);
    registerTileEntity(TileEntityFiller.class);
    registerTileEntity(TileEntityPress.class);
  }

  private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass) {
    GameRegistry.registerTileEntity(tileEntityClass, Reference.RESOURCE_PREFIX + tileEntityClass.getSimpleName().replaceFirst("TileEntity", ""));
  }

  @Mod.EventBusSubscriber(modid = Reference.MODID)
  public static class RegistrationHandler {

    public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

    /**
     * Register this mod's {@link Block}s.
     *
     * @param event The event
     */
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
      final IForgeRegistry<Block> registry = event.getRegistry();

      BLOCK_PRESS_FILLER.setHarvestLevel("axe", 1);
      final Block[] blocks = {BLOCK_HAND_GRINDSTONE, BLOCK_GRINDSTONE,
                              BLOCK_MANUAL_CHOPPER, BLOCK_CHOPPER, BLOCK_CHOPPER_FILLER,
                              BLOCK_PRESS, BLOCK_PRESS_FILLER,
                              };

      registry.registerAll(blocks);
    }

    /**
     * Register this mod's {@link ItemBlock}s.
     *
     * @param event The event
     */
    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
      final ItemBlock[] items = {
        new ItemBlock(BLOCK_HAND_GRINDSTONE),
        new ItemBlock(BLOCK_GRINDSTONE),
        new ItemBlock(BLOCK_MANUAL_CHOPPER),
        new ItemBlockDouble(BLOCK_CHOPPER, BLOCK_CHOPPER_FILLER),
        new ItemBlockDouble(BLOCK_PRESS, BLOCK_PRESS_FILLER)
      };

      final IForgeRegistry<Item> registry = event.getRegistry();

      for (final ItemBlock item : items) {
        registry.register(item.setRegistryName(item.getBlock().getRegistryName()));
        ITEM_BLOCKS.add(item);
      }
    }
  }
}
