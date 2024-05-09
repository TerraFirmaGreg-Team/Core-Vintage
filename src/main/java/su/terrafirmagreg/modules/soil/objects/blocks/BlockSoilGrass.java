package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.api.spi.block.IColorfulBlock;
import su.terrafirmagreg.api.spi.itemblock.BaseItemBlock;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariants;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariants;
import su.terrafirmagreg.modules.soil.client.GrassColorHandler;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.api.util.FallingBlockManager;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.api.data.Blockstates.*;

@Getter
@SuppressWarnings("deprecation")
public class BlockSoilGrass extends BlockGrass implements ISoilBlock, IColorfulBlock {

    private final SoilBlockVariant variant;
    private final SoilType type;

    public BlockSoilGrass(SoilBlockVariant variant, SoilType type) {

        this.variant = variant;
        this.type = type;

        FallingBlockManager.registerFallable(this, variant.getSpecification());

        setSoundType(SoundType.PLANT);
        setHardness(2.1F);
        setHarvestLevel("shovel", 0);
        setDefaultState(getBlockState().getBaseState()
                .withProperty(NORTH, Boolean.FALSE)
                .withProperty(EAST, Boolean.FALSE)
                .withProperty(SOUTH, Boolean.FALSE)
                .withProperty(WEST, Boolean.FALSE)
                .withProperty(SNOWY, Boolean.FALSE)
                .withProperty(CLAY, Boolean.FALSE));

        //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
    }

    @Override
    public @Nullable BaseItemBlock getItemBlock() {
        return new BaseItemBlock(this);
    }

    public static void spreadGrass(World world, BlockPos pos, IBlockState us, Random rand) {
        // Получаем позицию верхнего блока
        BlockPos upPos = pos.up();
        // Получаем состояние верхнего блока
        IBlockState up = world.getBlockState(upPos);
        // Получаем значение освещенности от соседних блоков
        int neighborLight = world.getLightFromNeighbors(upPos);
        // Получаем тип блока, с которого распространяется трава
        Block usBlock = us.getBlock();

        // Проверяем условие для генерации торфа
        if (up.getMaterial().isLiquid() || (neighborLight < 4 && up.getLightOpacity(world, upPos) > 2)) {
            // Генерируем торф в зависимости от типа блока
            if (usBlock instanceof BlockSoilPeat) {
                world.setBlockState(pos, BlocksSoil.PEAT.getDefaultState());
            } else if (usBlock instanceof ISoilBlock soil) {
                world.setBlockState(pos, soil.getVariant().getNonGrassVersion().get(soil.getType()).getDefaultState());
            }
        } else if (neighborLight >= 9) {
            for (int i = 0; i < 4; ++i) {
                // Генерируем случайную позицию вокруг исходной позиции
                BlockPos target = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                // Проверяем, находится ли целевая позиция в пределах допустимой области
                if (world.isOutsideBuildHeight(target) || !world.isBlockLoaded(target)) {
                    return;
                }

                // Получаем состояние текущего блока
                IBlockState current = world.getBlockState(target);

                // Пропускаем итерацию, если текущий блок не является почвой или уже имеет траву
                if (!BlockUtils.isSoil(current) || BlockUtils.isGrass(current)) {
                    continue;
                }

                // Получаем позицию верхнего блока целевой позиции
                BlockPos targetUp = target.up();
                // Получаем состояние верхнего блока целевой позиции
                IBlockState targetUpState = world.getBlockState(targetUp);

                // Пропускаем итерацию, если верхний блок жидкость или имеет высокую прозрачность
                if (world.getLightFromNeighbors(targetUp) < 4 || targetUpState.getMaterial()
                        .isLiquid() || targetUpState.getLightOpacity(world, targetUp) > 3) {
                    continue;
                }

                // Получаем тип текущего блока
                Block currentBlock = current.getBlock();

                // Генерируем траву в зависимости от типа текущего блока
                if (currentBlock instanceof BlockSoilPeat) {
                    world.setBlockState(target, BlocksSoil.PEAT_GRASS.getDefaultState());
                } else if (currentBlock instanceof ISoilBlock soilBlock) {
                    SoilBlockVariant spreader = SoilBlockVariants.GRASS;

                    // Проверяем тип блока, с которого распространяется трава
                    if (usBlock instanceof ISoilBlock) {
                        if (BlockUtils.isDryGrass(usBlock.getDefaultState())) {
                            spreader = SoilBlockVariants.DRY_GRASS;
                        } else {
                            spreader = SoilBlockVariants.GRASS;
                        }

                    }

                    var s = soilBlock.getVariant().getGrassVersion(spreader);
                    world.setBlockState(target, s.get(soilBlock.getType()).getDefaultState());
                }
            }
            // Генерируем короткую траву на верхнем блоке с определенной вероятностью
            //            for (PlantType plant : PlantType.getPlantTypes()) {
            //                if (plant.getPlantVariant() == PlantEnumVariant.SHORT_GRASS && rand.nextFloat() < 0.5f) {
            //                    float temp = ClimateTFC.getActualTemp(world, upPos);
            //                    var plantBlock = (BlockPlantShortGrass) StoragePlant.getPlantBlock(plant.getPlantVariant(), plant);
            //
            //                    // Проверяем условия для генерации короткой травы
            //                    if (world.isAirBlock(upPos) &&
            //                            plant.isValidLocation(temp, ChunkDataTFC.getRainfall(world, upPos), Math.subtractExact(world.getLightFor(EnumSkyBlock.SKY, upPos), world.getSkylightSubtracted())) &&
            //                            plant.isValidGrowthTemp(temp) &&
            //                            rand.nextDouble() < plantBlock.getGrowthRate(world, upPos)) {
            //                        world.setBlockState(upPos, plantBlock.getDefaultState());
            //                    }
            //                }
            //            }
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            if (!worldIn.isAreaLoaded(pos, 3))
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            Block block = worldIn.getBlockState(pos).getBlock();
            if (block instanceof ISoilBlock soilBlockVariant) {
                var soil = soilBlockVariant.getType();

                if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2) {
                    worldIn.setBlockState(pos, SoilBlockVariants.DIRT.get(soil).getDefaultState());
                } else {
                    if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
                        for (int i = 0; i < 4; ++i) {
                            BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                            if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
                                return;
                            }

                            IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
                            IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

                            if (iblockstate1.getBlock() == SoilBlockVariants.DIRT.get(soil) && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 &&
                                    iblockstate.getLightOpacity(worldIn, pos.up()) <= 2) {
                                worldIn.setBlockState(blockpos, SoilBlockVariants.GRASS.get(soil)
                                        .getDefaultState());
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        pos = pos.add(0, -1, 0);
        Block blockUp = world.getBlockState(pos.up()).getBlock();
        return state
                .withProperty(NORTH, world.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() instanceof BlockSoilGrass)
                .withProperty(EAST, world.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() instanceof BlockSoilGrass)
                .withProperty(SOUTH, world.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() instanceof BlockSoilGrass)
                .withProperty(WEST, world.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() instanceof BlockSoilGrass)
                .withProperty(SNOWY, blockUp == Blocks.SNOW || blockUp == Blocks.SNOW_LAYER);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH, SNOWY, CLAY);
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return state.getValue(CLAY) ? random.nextInt(4) : super.quantityDropped(state, fortune, random);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(CLAY) ? Items.CLAY_BALL : SoilItemVariants.PILE.get(getType());
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.isRemote) return;
        spreadGrass(world, pos, state, rand);
        super.randomTick(world, pos, state, rand);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (this.variant.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
            double d0 = (float) pos.getX() + rand.nextFloat();
            double d1 = (double) pos.getY() - 0.05D;
            double d2 = (float) pos.getZ() + rand.nextFloat();
            world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return super.shouldSideBeRendered(blockState, world, pos, side);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    // TODO
    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        int beachDistance = 2;

        //        if (plantable instanceof BlockPlant) {
        //            switch (((BlockPlant) plantable).getEnumType()) {
        //                case EnumType.CLAY -> {
        //                    return variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                            variant == SoilBlockVariants.DRY_GRASS || variant == SoilBlockVariants.CLAY || variant == SoilBlockVariants.CLAY_GRASS;
        //                }
        //                case EnumType.DESERT_CLAY -> {
        //                    return variant == SoilBlockVariants.CLAY || variant == SoilBlockVariants.CLAY_GRASS || Helpers.isSand(state);
        //                }
        //                case EnumType.DRY_CLAY -> {
        //                    return variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.DRY_GRASS ||
        //                            variant == SoilBlockVariants.CLAY || variant == SoilBlockVariants.CLAY_GRASS || Helpers.isSand(state);
        //                }
        //                case EnumType.DRY -> {
        //                    return variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.DRY_GRASS || Helpers.isSand(state);
        //                }
        //                case EnumType.FRESH_WATER -> {
        //                    return variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                            variant == SoilBlockVariants.DRY_GRASS || Helpers.isGravel(state);
        //                }
        //                case EnumType.SALT_WATER -> {
        //                    return variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                            variant == SoilBlockVariants.DRY_GRASS || Helpers.isSand(state) || Helpers.isGravel(state);
        //                }
        //                case EnumType.FRESH_BEACH -> {
        //                    boolean flag = false;
        //                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
        //                        for (int i = 1; i <= beachDistance; i++) {
        //                            if (Helpers.isFreshWaterOrIce(world.getBlockState(pos.offset(facing, i)))) {
        //                                flag = true;
        //                                break;
        //                            }
        //                        }
        //                    }
        //                    return (variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                            variant == SoilBlockVariants.DRY_GRASS || Helpers.isSand(state)) && flag;
        //                }
        //                case EnumType.SALT_BEACH -> {
        //                    boolean flag = false;
        //                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
        //                        for (int i = 1; i <= beachDistance; i++)
        //                            if (Helpers.isSaltWater(world.getBlockState(pos.offset(facing, i)))) {
        //                                flag = true;
        //                            }
        //                    }
        //                    return (variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                            variant == SoilBlockVariants.DRY_GRASS || Helpers.isSand(state)) && flag;
        //                }
        //            }
        //        } else if (plantable instanceof BlockCropGrowing) {
        //            IBlockState cropState = world.getBlockState(pos.up());
        //            if (cropState.get() instanceof BlockCropGrowing) {
        //                boolean isWild = cropState.getValue(BlockCropGrowing.WILD);
        //                if (isWild) {
        //                    if (variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                            variant == SoilBlockVariants.DRY_GRASS || variant == SoilBlockVariants.CLAY_GRASS) {
        //                        return true;
        //                    }
        //                }
        //                return variant == SoilBlockVariants.FARMLAND;
        //            }
        //        }
        //
        //        switch (plantable.getPlantType(world, pos.offset(direction))) {
        //            case EnumPlantType.Plains -> {
        //                return variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                        variant == SoilBlockVariants.FARMLAND || variant == SoilBlockVariants.DRY_GRASS ||
        //                        variant == SoilBlockVariants.CLAY || variant == SoilBlockVariants.CLAY_GRASS;
        //            }
        //            case EnumPlantType.Crop -> {
        //                return variant == SoilBlockVariants.FARMLAND;
        //            }
        //            case EnumPlantType.Desert -> {
        //                return Helpers.isSand(state);
        //            }
        //            case EnumPlantType.Cave -> {
        //                return true;
        //            }
        //            case EnumPlantType.Water, EnumPlantType.Nether -> {
        //                return false;
        //            }
        //            case EnumPlantType.Beach -> {
        //                boolean flag = false;
        //                for (EnumFacing facing : EnumFacing.HORIZONTALS) {
        //                    for (int i = 1; i <= beachDistance; i++)
        //                        if (Helpers.isWater(world.getBlockState(pos.offset(facing, i)))) {
        //                            flag = true;
        //                        }
        //                }
        //                return (variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                        variant == SoilBlockVariants.DRY_GRASS || Helpers.isSand(state)) && flag;
        //            }
        //        }

        return false;
    }

    @Override
    public IBlockColor getColorHandler() {
        return GrassColorHandler::computeGrassColor;
    }

    @Override
    public IItemColor getItemColorHandler() {
        return (s, i) -> this.getColorHandler().colorMultiplier(((ItemBlock) s.getItem()).getBlock().getDefaultState(), null, null, i);
    }
}
