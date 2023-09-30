package net.dries007.tfc.module.soil.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import net.dries007.tfc.module.core.api.util.IHasModel;
import net.dries007.tfc.client.util.GrassColorHandler;
import net.dries007.tfc.module.soil.StorageSoil;
import net.dries007.tfc.module.soil.objects.blocks.BlockSoilFarmland;
import net.dries007.tfc.module.soil.objects.blocks.peat.BlockPeat;
import net.dries007.tfc.module.soil.objects.blocks.peat.BlockPeatGrass;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.soil.api.types.variant.block.SoilBlockVariants.FARMLAND;

public class BlocksSoil {

    public static BlockPeatGrass PEAT_GRASS;
    public static BlockPeat PEAT;

    public static void onRegister(Registry registry) {
        for (var block : StorageSoil.SOIL_BLOCKS.values()) {
            var itemBlock = block.getItemBlock();
            if (itemBlock != null) registry.registerBlock((Block) block, block.getItemBlock(), block.getName());
            else registry.registerBlock((Block) block, block.getName());
        }

        registry.registerBlock(PEAT_GRASS = new BlockPeatGrass(), PEAT_GRASS.getItemBlock(), BlockPeatGrass.NAME);
        registry.registerBlock(PEAT = new BlockPeat(), PEAT.getItemBlock(), BlockPeat.NAME);

    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {
        registry.registerClientModelRegistrationStrategy(() -> {

            StorageSoil.SOIL_BLOCKS.values().forEach(IHasModel::onModelRegister);

            ModelRegistrationHelper.registerBlockItemModels(
                    PEAT_GRASS,
                    PEAT
            );
        });
    }

    @SideOnly(Side.CLIENT)
    public static void onClientInitialization() {
        var itemColors = Minecraft.getMinecraft().getItemColors();
        var blockColors = Minecraft.getMinecraft().getBlockColors();

        IBlockColor grassColor = GrassColorHandler::computeGrassColor;


        blockColors.registerBlockColorHandler(grassColor,
                StorageSoil.SOIL_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getBlockVariant().isGrass())
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        blockColors.registerBlockColorHandler((s, w, p, i) ->
                        BlockSoilFarmland.TINT[s.getValue(BlockSoilFarmland.MOISTURE)],
                StorageSoil.SOIL_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getBlockVariant() == FARMLAND)
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        blockColors.registerBlockColorHandler(grassColor, PEAT_GRASS);

        itemColors.registerItemColorHandler((s, i) ->
                        blockColors.colorMultiplier(((ItemBlock) s.getItem()).getBlock().getDefaultState(), null, null, i),
                StorageSoil.SOIL_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getBlockVariant().isGrass())
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        itemColors.registerItemColorHandler((s, i) ->
                        blockColors.colorMultiplier(((ItemBlock) s.getItem()).getBlock().getDefaultState(), null, null, i),
                PEAT_GRASS);
    }

}
