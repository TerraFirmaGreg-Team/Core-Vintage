package tfctech.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;


import com.google.common.collect.ImmutableList;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import tfctech.objects.blocks.devices.BlockElectricForge;
import tfctech.objects.blocks.devices.BlockFridge;
import tfctech.objects.blocks.devices.BlockInductionCrucible;
import tfctech.objects.blocks.devices.BlockLatexExtractor;
import tfctech.objects.blocks.devices.BlockSmelteryCauldron;
import tfctech.objects.blocks.devices.BlockSmelteryFirebox;
import tfctech.objects.blocks.devices.BlockWireDrawBench;
import tfctech.objects.items.itemblocks.ItemBlockFridge;
import tfctech.objects.items.itemblocks.ItemBlockWireDrawBench;
import tfctech.objects.tileentities.TEElectricForge;
import tfctech.objects.tileentities.TEFridge;
import tfctech.objects.tileentities.TEInductionCrucible;
import tfctech.objects.tileentities.TELatexExtractor;
import tfctech.objects.tileentities.TESmelteryCauldron;
import tfctech.objects.tileentities.TESmelteryFirebox;
import tfctech.objects.tileentities.TEWireDrawBench;

import lombok.Getter;

import static net.dries007.tfc.objects.CreativeTabsTFC.CT_MISC;
import static net.dries007.tfc.util.Helpers.getNull;
import static su.terrafirmagreg.api.lib.Constants.MODID_TFCTECH;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID_TFCTECH)
@GameRegistry.ObjectHolder(MODID_TFCTECH)
public final class TechBlocks {

    public static final BlockElectricForge ELECTRIC_FORGE = getNull();
    public static final BlockInductionCrucible INDUCTION_CRUCIBLE = getNull();
    public static final BlockLatexExtractor LATEX_EXTRACTOR = getNull();
    public static final BlockWireDrawBench WIRE_DRAW_BENCH = getNull();
    public static final BlockFridge FRIDGE = getNull();
    public static final BlockSmelteryCauldron SMELTERY_CAULDRON = getNull();
    public static final BlockSmelteryFirebox SMELTERY_FIREBOX = getNull();

    @Getter
    private static ImmutableList<ItemBlock> allInventoryItemBlocks;
    @Getter
    private static ImmutableList<ItemBlock> allTEISRBlocks;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        IForgeRegistry<Block> r = event.getRegistry();

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
        register(TEElectricForge.class, "electric_forge");
        register(TEInductionCrucible.class, "induction_crucible");
        register(TELatexExtractor.class, "latex_extractor");
        register(TEWireDrawBench.class, "wire_draw_bench");
        register(TEFridge.class, "fridge");
        register(TESmelteryCauldron.class, "smeltery_cauldron");
        register(TESmelteryFirebox.class, "smeltery_firebox");
    }

    private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block, CreativeTabs ct) {
        block.setCreativeTab(ct);
        return register(r, name, block);
    }

    private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block) {
        block.setRegistryName(MODID_TFCTECH, name);
        block.setTranslationKey(MODID_TFCTECH + "." + name.replace('/', '.'));
        r.register(block);
        return block;
    }

    private static <T extends TileEntity> void register(Class<T> te, String name) {
        TileEntity.register(MODID_TFCTECH + ":" + name, te);
    }
}
