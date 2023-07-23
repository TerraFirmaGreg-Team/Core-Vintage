/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.blocks.soil;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types2.rock.RockBlockType;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.types2.soil.SoilBlockType;
import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.util.IRockTypeBlock;
import net.dries007.tfc.api.util.ISoilTypeBlock;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.plants.BlockShortGrassTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockSoil extends Block implements ISoilTypeBlock {

    public static final Map<Triple<SoilBlockType, SoilVariant, SoilType>, ISoilTypeBlock> BLOCK_SOIL_MAP = new LinkedHashMap<>();

    public static Block getBlockSoilMap(SoilBlockType soilBlockType, SoilVariant soilVariant, SoilType soilType) {
        return (Block) BLOCK_SOIL_MAP.get(new Triple<>(soilBlockType, soilVariant, soilType));
    }

    // Used for connected textures only.
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");

    public static void spreadGrass(World world, BlockPos pos, IBlockState us, Random rand) {
        BlockPos upPos = pos.up();
        IBlockState up = world.getBlockState(upPos);
        int neighborLight;
        Block usBlock;
        if (up.getMaterial().isLiquid() || ((neighborLight = world.getLightFromNeighbors(upPos)) < 4 && up.getLightOpacity(world, upPos) > 2))
        {
            usBlock = us.getBlock();
            if (usBlock instanceof BlockPeat)
            {
                world.setBlockState(pos, BlocksTFC.PEAT.getDefaultState());
            }
            else if (usBlock instanceof BlockRockVariant)
            {
                BlockRockVariant rock = ((BlockRockVariant) usBlock);
                world.setBlockState(pos, rock.getVariant(rock.getType().getNonGrassVersion()).getDefaultState());
            }
        }
        else if (neighborLight >= 9)
        {
            for (int i = 0; i < 4; ++i)
            {
                BlockPos target = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
                if (world.isOutsideBuildHeight(target) || !world.isBlockLoaded(target))
                {
                    return;
                }
                IBlockState current = world.getBlockState(target);
                if (!BlocksTFC.isSoil(current) || BlocksTFC.isGrass(current))
                {
                    continue;
                }
                BlockPos targetUp = target.up();
                IBlockState targetUpState;
                if (world.getLightFromNeighbors(targetUp) < 4 || (targetUpState = world.getBlockState(targetUp)).getMaterial().isLiquid() || targetUpState.getLightOpacity(world, targetUp) > 3)
                {
                    continue;
                }
                Block currentBlock = current.getBlock();
                if (currentBlock instanceof BlockPeat)
                {
                    world.setBlockState(target, BlocksTFC.PEAT_GRASS.getDefaultState());
                }
                else if (currentBlock instanceof BlockRockVariant)
                {
                    Rock.Type spreader = Rock.Type.GRASS;
                    usBlock = us.getBlock();
                    if ((usBlock instanceof BlockRockVariant) && ((BlockRockVariant) usBlock).getType() == Rock.Type.DRY_GRASS)
                    {
                        spreader = Rock.Type.DRY_GRASS;
                    }
                    BlockRockVariant block = ((BlockRockVariant) current.getBlock());
                    world.setBlockState(target, block.getVariant(block.getType().getGrassVersion(spreader)).getDefaultState());
                }
            }
            for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
            {
                if (plant.getPlantType() == Plant.PlantType.SHORT_GRASS && rand.nextFloat() < 0.5f)
                {
                    float temp = ClimateTFC.getActualTemp(world, upPos);
                    BlockShortGrassTFC plantBlock = BlockShortGrassTFC.get(plant);

                    if (world.isAirBlock(upPos) &&
                        plant.isValidLocation(temp, ChunkDataTFC.getRainfall(world, upPos), Math.subtractExact(world.getLightFor(EnumSkyBlock.SKY, upPos), world.getSkylightSubtracted())) &&
                        plant.isValidGrowthTemp(temp) &&
                        rand.nextDouble() < plantBlock.getGrowthRate(world, upPos))
                    {
                        world.setBlockState(upPos, plantBlock.getDefaultState());
                    }
                }
            }
        }
    }

    private final SoilBlockType soilBlockType;
    private final SoilVariant soilVariant;
    private final SoilType soilType;
    private final ResourceLocation modelLocation;

    public BlockSoil(SoilBlockType soilBlockType, SoilVariant soilVariant, SoilType soilType) {
        super(Material.GROUND);

        if (BLOCK_SOIL_MAP.put(new Triple<>(soilBlockType, soilVariant, soilType), this) != null)
            throw new RuntimeException("Duplicate registry entry detected for block: " + soilVariant + " " + soilType);

        this.soilBlockType = soilBlockType;
        this.soilVariant = soilVariant;
        this.soilType = soilType;
        this.modelLocation = new ResourceLocation(MOD_ID, soilBlockType + "/" + soilType);

        String blockRegistryName = String.format("%s/%s/%s", soilBlockType, soilVariant, soilType);

        this.setRegistryName(MOD_ID, blockRegistryName);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        pos = pos.add(0, -1, 0);
        return state.withProperty(NORTH, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.NORTH))))
            .withProperty(EAST, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.EAST))))
            .withProperty(SOUTH, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.SOUTH))))
            .withProperty(WEST, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.WEST))));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH);
    }

    @Override
    public void onModelRegister() {

    }

    @Override
    public SoilVariant getSoilVariant() {
        return null;
    }

    @Override
    public SoilType getSoilType() {
        return null;
    }

    @Override
    public ItemBlock getItemBlock() {
        return null;
    }
}
