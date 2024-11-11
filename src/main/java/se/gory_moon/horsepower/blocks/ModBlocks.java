package se.gory_moon.horsepower.blocks;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings.Settings;
import su.terrafirmagreg.api.data.Reference;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.modules.device.object.tile.TileChopperHorse;
import su.terrafirmagreg.modules.device.object.tile.TileChopperManual;
import su.terrafirmagreg.modules.device.object.tile.TilePressHorse;

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

import net.dries007.tfc.objects.items.ItemBlockDouble;
import se.gory_moon.horsepower.tileentity.TileFiller;

import java.util.HashSet;
import java.util.Set;

import static se.gory_moon.horsepower.lib.Reference.RESOURCE_PREFIX;

public class ModBlocks {

  public static final BlockChopperManual BLOCK_MANUAL_CHOPPER = new BlockChopperManual();
  public static final BlockChopperHorse BLOCK_CHOPPER = new BlockChopperHorse();
  public static final BlockFiller BLOCK_CHOPPER_FILLER = new BlockFiller(Settings.of(Material.WOOD).harvestLevel(ToolClasses.AXE, 0), "chopper_");
  public static final BlockPress BLOCK_PRESS = new BlockPress();
  public static final BlockFiller BLOCK_PRESS_FILLER = new BlockFiller(Settings.of(Material.WOOD).harvestLevel(ToolClasses.AXE, 1), "press_");

  public static void registerTileEntities() {
    registerTileEntity(TileChopperManual.class);
    registerTileEntity(TileChopperHorse.class);
    registerTileEntity(TileFiller.class);
    registerTileEntity(TilePressHorse.class);
  }

  private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass) {
    GameRegistry.registerTileEntity(tileEntityClass, RESOURCE_PREFIX + tileEntityClass.getSimpleName());
  }

  @Mod.EventBusSubscriber(modid = Reference.MODID_HORSEPOWER)
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
      final Block[] blocks = {
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
