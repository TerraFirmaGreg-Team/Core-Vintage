package net.dries007.tfc.module.wood.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import net.dries007.tfc.Tags;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.module.core.client.util.GrassColorHandler;
import net.dries007.tfc.module.wood.api.variant.block.IWoodBlock;
import net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariantHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.wood.common.WoodStorage.WOOD_BLOCKS;

public class BlockInitializer {

    public static void onRegister(Registry registry) {
        WoodBlockVariantHandler.init();

        for (var wood : WOOD_BLOCKS.values()) {
            var itemBlock = wood.getItemBlock();
            if (itemBlock != null) registry.registerBlock((Block) wood, wood.getItemBlock(), wood.getName());
            else registry.registerBlock((Block) wood, wood.getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {
        registry.registerClientModelRegistrationStrategy(() -> {
            WOOD_BLOCKS.values().forEach(IHasModel::onModelRegister);
        });
    }

    @SideOnly(Side.CLIENT)
    public static void onClientInitialization() {
        var minecraft = Minecraft.getMinecraft();
        var itemColors = minecraft.getItemColors();
        var blockColors = minecraft.getBlockColors();

        IBlockColor foliageColor = GrassColorHandler::computeGrassColor;


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
