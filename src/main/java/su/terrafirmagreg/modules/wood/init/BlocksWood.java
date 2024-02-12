package su.terrafirmagreg.modules.wood.init;

import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.api.util.Pair;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import java.util.Map;

public final class BlocksWood {

    public static final Map<Pair<WoodBlockVariant, WoodType>, Block> WOOD_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();


    public static void onRegister(Registry registry) {

        for (var block : WOOD_BLOCKS.values()) registry.registerAuto(block);
        //for (var block : TREE_BLOCKS) registry.registerBlock(block);

        //registry.registerBlocks(LeavesPaging.getLeavesMapForModId(Tags.MOD_ID).values().toArray(new Block[0]));


//        registry.registerTileEntities(
//                TEWoodLoom.class,
//                TEWoodToolRack.class,
//                TEWoodChest.class,
//                TEWoodBarrel.class
//        );
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {
//        registry.registerClientModelRegistrationStrategy(() -> {
//            //for (var tree : WoodTreeVariant.getTreeTypes()) ModelHelperTFC.regModel(tree);
//        });

        //==== TESRs =================================================================================================//
//        ClientRegistry.bindTileEntitySpecialRenderer(TEWoodBarrel.class, new TESRWoodBarrel());
//        ClientRegistry.bindTileEntitySpecialRenderer(TEWoodLoom.class, new TESRWoodLoom());
//        ClientRegistry.bindTileEntitySpecialRenderer(TEWoodChest.class, new TESRWoodChest());
//        ClientRegistry.bindTileEntitySpecialRenderer(TEWoodToolRack.class, new TESRWoodToolRack());

    }

    public static void onPostInitialization() {
//        for (var type : WoodType.getTypes()) {
//            FuelManager.addFuel(new Fuel(IIngredient.of(new ItemStack(StorageWood.getBlock(LOG, type))), type.getBurnTicks(), type.getBurnTemp()));
//        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientInitialization() {
//        var itemColors = Minecraft.getMinecraft().getItemColors();
//        var blockColors = Minecraft.getMinecraft().getBlockColors();
//
//        IBlockColor foliageColor = GrassColorHandler::computeGrassColor;
//        IBlockColor grassColor = GrassColorHandler::computeGrassColor;
//
//
//        blockColors.registerBlockColorHandler((s, w, p, i) -> {
//                    // цвет листвы
//                    if (i == 0) return foliageColor.colorMultiplier(s, w, p, i);
//                    // цвет дерева
//                    if (i == 1) return ((IWoodBlock) s.getBlock()).getType().getColor();
//                    // Если не указан индекс
//                    return 0xFFFFFF;
//                },
//                WOOD_BLOCKS.values()
//                        .stream()
//                        .map(s -> (Block) s)
//                        .toArray(Block[]::new));
//
//        itemColors.registerItemColorHandler((s, i) -> {
//                    // цвет листвы
//                    if (i == 0)
//                        return blockColors.colorMultiplier(((ItemBlock) s.getItem()).getBlock()
//                                .getDefaultState(), null, null, i);
//                    // цвет дерева
//                    if (i == 1)
//                        return ((IWoodBlock) ((ItemBlock) s.getItem()).getBlock()).getType().getColor();
//                    // Если не указан индекс
//                    return 0xFFFFFF;
//                },
//                WOOD_BLOCKS.values()
//                        .stream()
//                        .map(s -> (Block) s)
//                        .toArray(Block[]::new));

//        blockColors.registerBlockColorHandler(foliageColor,
//                LeavesPaging.getLeavesMapForModId(Tags.MOD_ID).values()
//                        .toArray(new Block[0]));
    }



    @NotNull
    public static Block getBlock(@NotNull WoodBlockVariant variant, @NotNull WoodType type) {
        var block = (Block) WOOD_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block wood is null: %s, %s", variant, type));
    }
}
