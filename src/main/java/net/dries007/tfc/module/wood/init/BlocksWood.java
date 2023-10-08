package net.dries007.tfc.module.wood.init;

import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import net.dries007.tfc.Tags;
import net.dries007.tfc.client.util.GrassColorHandler;
import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.module.core.api.util.IHasModel;
import net.dries007.tfc.module.core.api.util.fuel.Fuel;
import net.dries007.tfc.module.core.api.util.fuel.FuelManager;
import net.dries007.tfc.module.wood.StorageWood;
import net.dries007.tfc.module.wood.api.types.type.WoodType;
import net.dries007.tfc.module.wood.api.types.variant.block.IWoodBlock;
import net.dries007.tfc.module.wood.client.render.TESRWoodBarrel;
import net.dries007.tfc.module.wood.client.render.TESRWoodChest;
import net.dries007.tfc.module.wood.client.render.TESRWoodLoom;
import net.dries007.tfc.module.wood.client.render.TESRWoodToolRack;
import net.dries007.tfc.module.wood.objects.tiles.TEWoodBarrel;
import net.dries007.tfc.module.wood.objects.tiles.TEWoodChest;
import net.dries007.tfc.module.wood.objects.tiles.TEWoodLoom;
import net.dries007.tfc.module.wood.objects.tiles.TEWoodToolRack;
import net.dries007.tfc.module.wood.plugin.dynamictrees.client.ModelHelperTFC;
import net.dries007.tfc.module.wood.plugin.dynamictrees.type.TreeType;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.util.registry.Registry;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.module.wood.StorageWood.TREE_BLOCKS;
import static net.dries007.tfc.module.wood.StorageWood.WOOD_BLOCKS;
import static net.dries007.tfc.module.wood.api.types.variant.block.WoodBlockVariants.LOG;

public class BlocksWood {


    public static void onRegister(Registry registry) {

        for (var block : WOOD_BLOCKS.values()) registry.registerBlockWithItem((Block) block, block.getName());
        for (var block : TREE_BLOCKS) registry.registerBlock(block);

        registry.registerBlocks(LeavesPaging.getLeavesMapForModId(MOD_ID).values().toArray(new Block[0]));


        registry.registerTileEntities(
                TEWoodLoom.class,
                TEWoodToolRack.class,
                TEWoodChest.class,
                TEWoodBarrel.class
        );
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {
        registry.registerClientModelRegistrationStrategy(() -> {
            WOOD_BLOCKS.values().forEach(IHasModel::onModelRegister);
            for (var tree : TreeType.getTreeTypes()) ModelHelperTFC.regModel(tree);
        });

        //==== TESRs =================================================================================================//
        ClientRegistry.bindTileEntitySpecialRenderer(TEWoodBarrel.class, new TESRWoodBarrel());
        ClientRegistry.bindTileEntitySpecialRenderer(TEWoodLoom.class, new TESRWoodLoom());
        ClientRegistry.bindTileEntitySpecialRenderer(TEWoodChest.class, new TESRWoodChest());
        ClientRegistry.bindTileEntitySpecialRenderer(TEWoodToolRack.class, new TESRWoodToolRack());
    }

    public static void onPostInitialization() {
        for (var type : WoodType.getWoodTypes()) {
            FuelManager.addFuel(new Fuel(IIngredient.of(new ItemStack(StorageWood.getWoodBlock(LOG, type))), type.getBurnTicks(), type.getBurnTemp()));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientInitialization() {
        var itemColors = Minecraft.getMinecraft().getItemColors();
        var blockColors = Minecraft.getMinecraft().getBlockColors();

        IBlockColor foliageColor = GrassColorHandler::computeGrassColor;
        IBlockColor grassColor = GrassColorHandler::computeGrassColor;


        blockColors.registerBlockColorHandler((s, w, p, i) -> {
                    // цвет листвы
                    if (i == 0) return foliageColor.colorMultiplier(s, w, p, i);
                    // цвет дерева
                    if (i == 1) return ((IWoodBlock) s.getBlock()).getType().getColor();
                    // Если не указан индекс
                    return 0xFFFFFF;
                },
                WOOD_BLOCKS.values()
                        .stream()
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        itemColors.registerItemColorHandler((s, i) -> {
                    // цвет листвы
                    if (i == 0)
                        return blockColors.colorMultiplier(((ItemBlock) s.getItem()).getBlock().getDefaultState(), null, null, i);
                    // цвет дерева
                    if (i == 1)
                        return ((IWoodBlock) ((ItemBlock) s.getItem()).getBlock()).getType().getColor();
                    // Если не указан индекс
                    return 0xFFFFFF;
                },
                WOOD_BLOCKS.values()
                        .stream()
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        blockColors.registerBlockColorHandler(foliageColor,
                LeavesPaging.getLeavesMapForModId(Tags.MOD_ID).values()
                        .toArray(new Block[0]));
    }
}
