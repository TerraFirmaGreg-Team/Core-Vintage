package net.dries007.tfc.common.objects.blocks;

import net.dries007.tfc.api.types.rock.IRockBlock;
import net.dries007.tfc.api.types.soil.ISoilBlock;
import net.dries007.tfc.common.objects.blocks.soil.peat.BlockPeat;
import net.dries007.tfc.common.objects.blocks.soil.peat.BlockPeatGrass;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.rock.variant.RockBlockVariants.*;
import static net.dries007.tfc.api.types.soil.variant.SoilBlockVariants.*;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
@GameRegistry.ObjectHolder(MOD_ID)
public final class BlocksTFC_old {


    public static boolean isWater(IBlockState current) {
        return current.getMaterial() == Material.WATER;
    }

    public static boolean isFreshWater(IBlockState current) {
        return current == FluidRegistry.getFluid("fresh_water").getBlock().getDefaultState();
    }

    public static boolean isSaltWater(IBlockState current) {
        return current == FluidRegistry.getFluid("salt_water").getBlock().getDefaultState();
    }

    public static boolean isFreshWaterOrIce(IBlockState current) {
        return current.getBlock() == Blocks.ICE || isFreshWater(current);
    }

    public static boolean isRawStone(IBlockState current) {
        if (current.getBlock() instanceof IRockBlock rockTypeBlock)
            return rockTypeBlock.getBlockVariant() == RAW;
        return false;
    }

    public static boolean isClay(IBlockState current) {
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock) {
            var soilBlockVariant = soilTypeBlock.getBlockVariant();
            return soilBlockVariant == CLAY || soilBlockVariant == CLAY_GRASS;
        }
        return false;
    }

    public static boolean isDirt(IBlockState current) {
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock)
            return soilTypeBlock.getBlockVariant() == DIRT;
        return false;
    }

    public static boolean isSand(IBlockState current) {
        if (current.getBlock() instanceof IRockBlock rockTypeBlock) {
            return rockTypeBlock.getBlockVariant() == SAND;
        }
        return false;
    }

    public static boolean isGravel(IBlockState current) {
        if (current.getBlock() instanceof IRockBlock rockTypeBlock) {
            return rockTypeBlock.getBlockVariant() == GRAVEL;
        }
        return false;
    }

    public static boolean isSoil(IBlockState current) {
        if (current.getBlock() instanceof BlockPeat) return true;
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock) {
            var soilBlockVariant = soilTypeBlock.getBlockVariant();
            return soilBlockVariant == GRASS || soilBlockVariant == DRY_GRASS || soilBlockVariant == DIRT || soilBlockVariant == CLAY || soilBlockVariant == CLAY_GRASS;
        }
        return false;
    }

    public static boolean isGrowableSoil(IBlockState current) {
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock) {
            var soilBlockVariant = soilTypeBlock.getBlockVariant();
            return soilBlockVariant == GRASS || soilBlockVariant == DRY_GRASS || soilBlockVariant == DIRT || soilBlockVariant == CLAY || soilBlockVariant == CLAY_GRASS;
        }
        return false;
    }

    public static boolean isSoilOrGravel(IBlockState current) {
        if (current.getBlock() instanceof BlockPeat) return true;
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock) {
            var soilBlockVariant = soilTypeBlock.getBlockVariant();
            return soilBlockVariant == GRASS || soilBlockVariant == DRY_GRASS || soilBlockVariant == DIRT;
        }
        if (current.getBlock() instanceof IRockBlock rockTypeBlock)
            return rockTypeBlock.getBlockVariant() == GRAVEL;
        return false;
    }

    public static boolean isGrass(IBlockState current) {
        if (current.getBlock() instanceof BlockPeatGrass) return true;
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock) {
            var soilBlockVariant = soilTypeBlock.getBlockVariant();
            return soilBlockVariant == GRASS || soilBlockVariant == DRY_GRASS || soilBlockVariant == CLAY_GRASS;
        }
        return false;
    }

    public static boolean isDryGrass(IBlockState current) {
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock)
            return soilTypeBlock.getBlockVariant() == DRY_GRASS;
        return false;
    }

    public static boolean isGround(IBlockState current) {
        if (current.getBlock() instanceof IRockBlock rockTypeBlock) {
            var rockBlockVariant = rockTypeBlock.getBlockVariant();
            return rockBlockVariant == GRAVEL || rockBlockVariant == SAND || rockBlockVariant == RAW;
        }
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock) {
            var soilBlockVariant = soilTypeBlock.getBlockVariant();
            return soilBlockVariant == GRASS || soilBlockVariant == DRY_GRASS || soilBlockVariant == DIRT;
        }
        return false;
    }

    private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block, CreativeTabs ct) {
        block.setCreativeTab(ct);
        return register(r, name, block);
    }

    private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block) {
        block.setRegistryName(MOD_ID, name);
        block.setTranslationKey(MOD_ID + "." + name.replace('/', '.'));
        r.register(block);
        return block;
    }
}
