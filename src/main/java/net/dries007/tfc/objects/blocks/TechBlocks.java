package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.device.object.block.BlockElectricForge;
import su.terrafirmagreg.modules.device.object.block.BlockFridge;
import su.terrafirmagreg.modules.device.object.block.BlockInductionCrucible;
import su.terrafirmagreg.modules.device.object.block.BlockLatexExtractor;
import su.terrafirmagreg.modules.device.object.block.BlockSmelteryCauldron;
import su.terrafirmagreg.modules.device.object.block.BlockSmelteryFirebox;
import su.terrafirmagreg.modules.device.object.block.BlockWireDrawBench;
import su.terrafirmagreg.modules.device.object.tile.TileElectricForge;
import su.terrafirmagreg.modules.device.object.tile.TileFridge;
import su.terrafirmagreg.modules.device.object.tile.TileInductionCrucible;
import su.terrafirmagreg.modules.device.object.tile.TileLatexExtractor;
import su.terrafirmagreg.modules.device.object.tile.TileSmelteryCauldron;
import su.terrafirmagreg.modules.device.object.tile.TileSmelteryFirebox;
import su.terrafirmagreg.modules.device.object.tile.TileWireDrawBench;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import com.google.common.collect.ImmutableList;
import net.dries007.tfc.objects.fluids.TechFluids;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.objects.items.itemblocks.ItemBlockFridge;
import net.dries007.tfc.objects.items.itemblocks.ItemBlockWireDrawBench;

import static net.dries007.tfc.objects.CreativeTabsTFC.CT_MISC;
import static net.dries007.tfc.util.Helpers.getNull;
import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFCTECH;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = TFCTECH)
@GameRegistry.ObjectHolder(TFCTECH)
public final class TechBlocks {

  public static final BlockElectricForge ELECTRIC_FORGE = getNull();
  public static final BlockInductionCrucible INDUCTION_CRUCIBLE = getNull();
  public static final BlockLatexExtractor LATEX_EXTRACTOR = getNull();
  public static final BlockWireDrawBench WIRE_DRAW_BENCH = getNull();
  public static final BlockFridge FRIDGE = getNull();
  public static final BlockSmelteryCauldron SMELTERY_CAULDRON = getNull();
  public static final BlockSmelteryFirebox SMELTERY_FIREBOX = getNull();

  private static ImmutableList<ItemBlock> allInventoryItemBlocks, allTEISRBlocks;
  private static ImmutableList<BlockFluidBase> allFluidBlocks;

  public static ImmutableList<ItemBlock> getAllInventoryItemBlocks() {
    return allInventoryItemBlocks;
  }

  public static ImmutableList<ItemBlock> getAllTEISRBlocks() {
    return allTEISRBlocks;
  }

  public static ImmutableList<BlockFluidBase> getAllFluidBlocks() {
    return allFluidBlocks;
  }

  @SubscribeEvent
  public static void registerBlocks(RegistryEvent.Register<Block> event) {

    IForgeRegistry<Block> r = event.getRegistry();
    TechFluids.registerFluids();

    ImmutableList.Builder<BlockFluidBase> b = ImmutableList.builder();
    if (FluidsCore.LATEX.isDefault()) {
      b.add(register(r, "fluid/" + FluidsCore.LATEX.get().getName(), new BlockFluidTFC(FluidsCore.LATEX.get(), Material.WATER)));
    }
    allFluidBlocks = b.build();

    //Normal inventory Blocks
    ImmutableList.Builder<ItemBlock> inventoryItemBlocks = ImmutableList.builder();

    inventoryItemBlocks.add(new ItemBlockTFC(register(r, "electric_forge", new BlockElectricForge(), CT_MISC)));
    inventoryItemBlocks.add(new ItemBlockTFC(register(r, "induction_crucible", new BlockInductionCrucible(), CT_MISC)));
    inventoryItemBlocks.add(new ItemBlockTFC(register(r, "smeltery_cauldron", new BlockSmelteryCauldron(), CT_MISC)));
    inventoryItemBlocks.add(new ItemBlockTFC(register(r, "smeltery_firebox", new BlockSmelteryFirebox(), CT_MISC)));

    allInventoryItemBlocks = inventoryItemBlocks.build();

    //TEISR Blocks
    ImmutableList.Builder<ItemBlock> teisrItemBlocks = ImmutableList.builder();

    teisrItemBlocks.add(new ItemBlockWireDrawBench(register(r, "wire_draw_bench", new BlockWireDrawBench(), CT_MISC)));
    teisrItemBlocks.add(new ItemBlockFridge(register(r, "fridge", new BlockFridge(), CT_MISC)));

    allTEISRBlocks = teisrItemBlocks.build();

    //No itemblocks
    register(r, "latex_extractor", new BlockLatexExtractor());

    //Register TEs
    register(TileElectricForge.class, "electric_forge");
    register(TileInductionCrucible.class, "induction_crucible");
    register(TileLatexExtractor.class, "latex_extractor");
    register(TileWireDrawBench.class, "wire_draw_bench");
    register(TileFridge.class, "fridge");
    register(TileSmelteryCauldron.class, "smeltery_cauldron");
    register(TileSmelteryFirebox.class, "smeltery_firebox");
  }

  private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block, CreativeTabs ct) {
    block.setCreativeTab(ct);
    return register(r, name, block);
  }

  private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block) {
    block.setRegistryName(TFCTECH, name);
    block.setTranslationKey(TFCTECH + "." + name.replace('/', '.'));
    r.register(block);
    return block;
  }

  private static <T extends TileEntity> void register(Class<T> te, String name) {
    TileEntity.register(TFCTECH + ":" + name, te);
  }
}
