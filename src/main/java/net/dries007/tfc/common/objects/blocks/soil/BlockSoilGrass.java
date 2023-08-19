/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package net.dries007.tfc.common.objects.blocks.soil;

import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.plant.type.PlantType;
import net.dries007.tfc.api.types.soil.ISoilBlock;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.SoilBlockVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.blocks.BlocksTFC_old;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.common.objects.blocks.crop.BlockCropGrowing;
import net.dries007.tfc.common.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.common.objects.blocks.plants.BlockShortGrassTFC;
import net.dries007.tfc.common.objects.blocks.soil.peat.BlockPeat;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.dries007.tfc.api.types.plant.variant.PlantBlockVariant.SHORT_GRASS;
import static net.dries007.tfc.api.types.soil.variant.SoilBlockVariants.*;
import static net.dries007.tfc.common.objects.blocks.crop.BlockCropGrowing.WILD;

public class BlockSoilGrass extends BlockGrass implements ISoilBlock {

    // Used for connected textures only.
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");

    private final SoilBlockVariant variant;
    private final SoilType type;

    public BlockSoilGrass(SoilBlockVariant variant, SoilType type) {

        if (variant.canFall())
            FallingBlockManager.registerFallable(this, variant.getFallingSpecification());

        this.variant = variant;
        this.type = type;


        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.EARTH);
        setSoundType(SoundType.PLANT);
        setHardness(2.1F);
        setHarvestLevel("shovel", 0);
        setTickRandomly(true);
        setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, Boolean.FALSE)
                .withProperty(EAST, Boolean.FALSE)
                .withProperty(SOUTH, Boolean.FALSE)
                .withProperty(WEST, Boolean.FALSE));

        OreDictionaryHelper.register(this, variant.toString());
        OreDictionaryHelper.register(this, variant.toString(), type.toString());
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
            if (usBlock instanceof BlockPeat) {
                world.setBlockState(pos, TFCBlocks.PEAT.getDefaultState());
            } else if (usBlock instanceof ISoilBlock soil) {
                world.setBlockState(pos, TFCStorage.getSoilBlock(soil.getBlockVariant().getNonGrassVersion(), soil.getType()).getDefaultState());
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
                if (!BlocksTFC_old.isSoil(current) || BlocksTFC_old.isGrass(current)) {
                    continue;
                }

                // Получаем позицию верхнего блока целевой позиции
                BlockPos targetUp = target.up();
                // Получаем состояние верхнего блока целевой позиции
                IBlockState targetUpState = world.getBlockState(targetUp);

                // Пропускаем итерацию, если верхний блок жидкость или имеет высокую прозрачность
                if (world.getLightFromNeighbors(targetUp) < 4 || targetUpState.getMaterial().isLiquid() || targetUpState.getLightOpacity(world, targetUp) > 3) {
                    continue;
                }

                // Получаем тип текущего блока
                Block currentBlock = current.getBlock();

                // Генерируем траву в зависимости от типа текущего блока
                if (currentBlock instanceof BlockPeat) {
                    world.setBlockState(target, TFCBlocks.PEAT_GRASS.getDefaultState());
                } else if (currentBlock instanceof ISoilBlock block) {
                    SoilBlockVariant spreader = GRASS;

                    // Проверяем тип блока, с которого распространяется трава
                    if (usBlock instanceof ISoilBlock && ((ISoilBlock) usBlock).getBlockVariant() == DRY_GRASS) {
                        spreader = DRY_GRASS;
                    }

                    world.setBlockState(pos, TFCStorage.getSoilBlock(block.getBlockVariant().getGrassVersion(spreader), block.getType()).getDefaultState());
                }
            }
            // Генерируем короткую траву на верхнем блоке с определенной вероятностью
            for (PlantType plant : PlantType.getPlantTypes()) {
                if (plant.getPlantVariant() == SHORT_GRASS && rand.nextFloat() < 0.5f) {
                    float temp = ClimateTFC.getActualTemp(world, upPos);
                    var plantBlock = (BlockShortGrassTFC) TFCStorage.getPlantBlock(plant.getPlantVariant(), plant);

                    // Проверяем условия для генерации короткой травы
                    if (world.isAirBlock(upPos) &&
                            plant.isValidLocation(temp, ChunkDataTFC.getRainfall(world, upPos), Math.subtractExact(world.getLightFor(EnumSkyBlock.SKY, upPos), world.getSkylightSubtracted())) &&
                            plant.isValidGrowthTemp(temp) &&
                            rand.nextDouble() < plantBlock.getGrowthRate(world, upPos)) {
                        world.setBlockState(upPos, plantBlock.getDefaultState());
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    public SoilBlockVariant getBlockVariant() {
        return variant;
    }

    @Nonnull
    @Override
    public SoilType getType() {
        return type;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    @Override
    public void updateTick(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        if (!worldIn.isRemote) {
            if (!worldIn.isAreaLoaded(pos, 3))
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            Block block = worldIn.getBlockState(pos).getBlock();
            if (block instanceof ISoilBlock) {
                var soil = ((ISoilBlock) block).getType();

                if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2) {
                    worldIn.setBlockState(pos, TFCStorage.getSoilBlock(DIRT, soil).getDefaultState());
                } else {
                    if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
                        for (int i = 0; i < 4; ++i) {
                            BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                            if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
                                return;
                            }

                            IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
                            IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

                            if (iblockstate1.getBlock() == TFCStorage.getSoilBlock(DIRT, soil) && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2) {
                                worldIn.setBlockState(blockpos, TFCStorage.getSoilBlock(GRASS, soil).getDefaultState());
                            }
                        }
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, @Nonnull BlockPos pos) {
        pos = pos.add(0, -1, 0);
        Block blockUp = world.getBlockState(pos.up()).getBlock();
        return state
                .withProperty(NORTH, world.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() instanceof BlockSoilGrass)
                .withProperty(EAST, world.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() instanceof BlockSoilGrass)
                .withProperty(SOUTH, world.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() instanceof BlockSoilGrass)
                .withProperty(WEST, world.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() instanceof BlockSoilGrass);
        // TODO: 15.08.2023
        //.withProperty(SNOWY, Boolean.valueOf(blockUp == Blocks.SNOW || blockUp == Blocks.SNOW_LAYER));
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, EAST, NORTH, WEST, SOUTH, SNOWY);
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return Item.getItemFromBlock(TFCStorage.getSoilBlock(DIRT, type));
    }

    @Override
    public void randomTick(World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        if (world.isRemote) return;
        spreadGrass(world, pos, state, rand);
        super.randomTick(world, pos, state, rand);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (this.variant.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
            double d0 = (float) pos.getX() + rand.nextFloat();
            double d1 = (double) pos.getY() - 0.05D;
            double d2 = (float) pos.getZ() + rand.nextFloat();
            world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean shouldSideBeRendered(@Nonnull IBlockState blockState, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
        return super.shouldSideBeRendered(blockState, world, pos, side);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(getResourceLocation(),
                        "east=" + state.getValue(EAST) + "," +
                                "north=" + state.getValue(NORTH) + "," +
                                "soiltype=" + type.toString() + "," +
                                "south=" + state.getValue(SOUTH) + "," +
                                "west=" + state.getValue(WEST));
            }
        });

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                getMetaFromState(getBlockState().getBaseState()),
                new ModelResourceLocation(getResourceLocation(),
                        "east=false,north=false," +
                                "soiltype=" + type.toString() + "," +
                                "south=false,west=false"));
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canSustainPlant(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing direction, @Nonnull IPlantable plantable) {
        int beachDistance = 2;

        if (plantable instanceof BlockPlantTFC) {
            switch (((BlockPlantTFC) plantable).getEnumType()) {
                case CLAY -> {
                    return variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || variant == CLAY || variant == CLAY_GRASS;
                }
                case DESERT_CLAY -> {
                    return variant == CLAY || variant == CLAY_GRASS || BlocksTFC_old.isSand(state);
                }
                case DRY_CLAY -> {
                    return variant == DIRT || variant == DRY_GRASS ||
                            variant == CLAY || variant == CLAY_GRASS || BlocksTFC_old.isSand(state);
                }
                case DRY -> {
                    return variant == DIRT || variant == DRY_GRASS || BlocksTFC_old.isSand(state);
                }
                case FRESH_WATER -> {
                    return variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || BlocksTFC_old.isGravel(state);
                }
                case SALT_WATER -> {
                    return variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || BlocksTFC_old.isSand(state) || BlocksTFC_old.isGravel(state);
                }
                case FRESH_BEACH -> {
                    boolean flag = false;
                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                        for (int i = 1; i <= beachDistance; i++) {
                            if (BlocksTFC_old.isFreshWaterOrIce(world.getBlockState(pos.offset(facing, i)))) {
                                flag = true;
                                break;
                            }
                        }
                    }
                    return (variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || BlocksTFC_old.isSand(state)) && flag;
                }
                case SALT_BEACH -> {
                    boolean flag = false;
                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                        for (int i = 1; i <= beachDistance; i++)
                            if (BlocksTFC_old.isSaltWater(world.getBlockState(pos.offset(facing, i)))) {
                                flag = true;
                            }
                    }
                    return (variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || BlocksTFC_old.isSand(state)) && flag;
                }
            }
        }
        else if (plantable instanceof BlockCropGrowing) {
            IBlockState cropState = world.getBlockState(pos.up());
            if (cropState.getBlock() instanceof BlockCropGrowing) {
                boolean isWild = cropState.getValue(WILD);
                if (isWild) {
                    if (variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || variant == CLAY_GRASS) {
                        return true;
                    }
                }
                return variant == FARMLAND;
            }
        }

        switch (plantable.getPlantType(world, pos.offset(direction))) {
            case Plains -> {
                return variant == DIRT || variant == GRASS ||
                        variant == FARMLAND || variant == DRY_GRASS ||
                        variant == CLAY || variant == CLAY_GRASS;
            }
            case Crop -> {
                return variant == FARMLAND;
            }
            case Desert -> {
                return BlocksTFC_old.isSand(state);
            }
            case Cave -> {
                return true;
            }
            case Water, Nether -> {
                return false;
            }
            case Beach -> {
                boolean flag = false;
                for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                    for (int i = 1; i <= beachDistance; i++)
                        if (BlocksTFC_old.isWater(world.getBlockState(pos.offset(facing, i)))) {
                            flag = true;
                        }
                }
                return (variant == DIRT || variant == GRASS ||
                        variant == DRY_GRASS || BlocksTFC_old.isSand(state)) && flag;
            }
        }

        return false;
    }
}
