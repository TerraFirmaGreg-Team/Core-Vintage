package net.dries007.tfc.module.soil.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.module.core.client.util.GrassColorHandler;
import net.dries007.tfc.module.soil.common.blocks.BlockSoilFarmland;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.core.common.objects.blocks.TFCBlocks.PEAT_GRASS;
import static net.dries007.tfc.module.core.common.objects.blocks.TFCBlocks.ROOTY_DIRT_MIMIC;
import static net.dries007.tfc.module.soil.api.variant.block.SoilBlockVariants.FARMLAND;
import static net.dries007.tfc.module.soil.common.SoilStorage.SOIL_BLOCKS;

public class BlockInitializer {

    public static void onRegister(Registry registry) {
        for (var block : SOIL_BLOCKS.values()) {
            var itemBlock = block.getItemBlock();
            if (itemBlock != null) registry.registerBlock((Block) block, block.getItemBlock(), block.getName());
            else registry.registerBlock((Block) block, block.getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {
        registry.registerClientModelRegistrationStrategy(() -> {
            SOIL_BLOCKS.values().forEach(IHasModel::onModelRegister);
        });
    }

    @SideOnly(Side.CLIENT)
    public static void onClientInitialization() {
        var minecraft = Minecraft.getMinecraft();
        var itemColors = minecraft.getItemColors();
        var blockColors = minecraft.getBlockColors();

        IBlockColor grassColor = GrassColorHandler::computeGrassColor;


        blockColors.registerBlockColorHandler(grassColor,
                SOIL_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getBlockVariant().isGrass())
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        blockColors.registerBlockColorHandler((s, w, p, i) ->
                        BlockSoilFarmland.TINT[s.getValue(BlockSoilFarmland.MOISTURE)],
                SOIL_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getBlockVariant() == FARMLAND)
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        blockColors.registerBlockColorHandler(grassColor, PEAT_GRASS);
        blockColors.registerBlockColorHandler(grassColor, ROOTY_DIRT_MIMIC);

        itemColors.registerItemColorHandler((s, i) ->
                        blockColors.colorMultiplier(((ItemBlock) s.getItem()).getBlock().getDefaultState(), null, null, i),
                SOIL_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getBlockVariant().isGrass())
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        itemColors.registerItemColorHandler((s, i) ->
                        blockColors.colorMultiplier(((ItemBlock) s.getItem()).getBlock().getDefaultState(), null, null, i),
                PEAT_GRASS);
    }

}
