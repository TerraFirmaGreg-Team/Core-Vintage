package net.dries007.tfc.module.plant.init;

import su.terrafirmagreg.util.registry.Registry;
import net.dries007.tfc.module.core.api.util.IHasModel;
import net.dries007.tfc.client.util.GrassColorHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.plant.StoragePlant.PLANT_BLOCKS;
import static net.dries007.tfc.module.plant.api.types.variant.block.PlantEnumVariant.SHORT_GRASS;
import static net.dries007.tfc.module.plant.api.types.variant.block.PlantEnumVariant.TALL_GRASS;

public class BlocksPlant {

    public static void onRegister(Registry registry) {
        for (var block : PLANT_BLOCKS.values()) {
            var itemBlock = block.getItemBlock();
            if (itemBlock != null) registry.registerBlock((Block) block, block.getItemBlock(), block.getName());
            else registry.registerBlock((Block) block, block.getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {
        registry.registerClientModelRegistrationStrategy(() -> {
            PLANT_BLOCKS.values().forEach(IHasModel::onModelRegister);
        });
    }

    @SideOnly(Side.CLIENT)
    public static void onClientInitialization() {
        var minecraft = Minecraft.getMinecraft();
        var itemColors = minecraft.getItemColors();
        var blockColors = minecraft.getBlockColors();

        IBlockColor grassColor = GrassColorHandler::computeGrassColor;


        blockColors.registerBlockColorHandler(grassColor,
                PLANT_BLOCKS.values()
                        .stream()
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        itemColors.registerItemColorHandler((s, i) -> blockColors.colorMultiplier(((ItemBlock) s.getItem()).getBlock().getDefaultState(), null, null, i),
                PLANT_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getBlockVariant() == SHORT_GRASS || x.getBlockVariant() == TALL_GRASS)
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));
    }
}
