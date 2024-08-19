package net.dries007.tfc.objects.blocks.stone;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGrassPath;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC.WILD;

@MethodsReturnNonnullByDefault
public class BlockRockVariant extends Block implements ICapabilitySize {

    private static final Map<Rock, EnumMap<Rock.Type, BlockRockVariant>> TABLE = new HashMap<>();
    protected final Rock.Type type;
    protected final Rock rock;

    public BlockRockVariant(Rock.Type type, Rock rock) {
        super(type.material);

        if (!TABLE.containsKey(rock)) {
            TABLE.put(rock, new EnumMap<>(Rock.Type.class));
        }
        TABLE.get(rock).put(type, this);

        this.type = type;
        this.rock = rock;
        if (type.isGrass) setTickRandomly(true);
        switch (type) {
            case BRICKS:
            case RAW:
            case ANVIL:
            case SPIKE:
            case SMOOTH:
                setSoundType(SoundType.STONE);
                setHardness(rock.getRockCategory().getHardness()).setResistance(rock.getRockCategory().getResistance());
                setHarvestLevel("pickaxe", 0);
                break;
            case COBBLE:
                setSoundType(SoundType.STONE);
                setHardness(rock.getRockCategory().getHardness() * 0.75F).setResistance(rock.getRockCategory()
                        .getResistance());
                setHarvestLevel("pickaxe", 0);
                break;
            case SAND:
                setSoundType(SoundType.SAND);
                setHardness(rock.getRockCategory().getHardness() * 0.05F);
                setHarvestLevel("shovel", 0);
                break;
            case DIRT:
            case PATH:
            case FARMLAND:
                setSoundType(SoundType.GROUND);
                setHardness(rock.getRockCategory().getHardness() * 0.15F);
                setHarvestLevel("shovel", 0);
                break;
            case GRAVEL:
            case CLAY:
                setSoundType(SoundType.GROUND);
                setHardness(rock.getRockCategory().getHardness() * 0.2F);
                setHarvestLevel("shovel", 0);
                break;
            case CLAY_GRASS:
            case GRASS:
            case DRY_GRASS:
                setSoundType(SoundType.PLANT);
                setHardness(rock.getRockCategory().getHardness() * 0.2F);
                setHarvestLevel("shovel", 0);
                break;
        }
        if (type != Rock.Type.SPIKE && type != Rock.Type.ANVIL) //since spikes and anvils don't generate ItemBlocks
        {
            OreDictionaryHelper.registerRockType(this, type);
        }
    }

    public static BlockRockVariant get(Rock rock, Rock.Type type) {
        //noinspection ConstantConditions
        if (rock == null) {
            return TABLE.get(Rock.GRANITE).get(type);
        }
        return TABLE.get(rock).get(type);
    }

    public static BlockRockVariant create(Rock rock, Rock.Type type) {
        return switch (type) {
            case RAW -> new BlockRockRaw(type, rock);
            case SMOOTH -> new BlockRockSmooth(type, rock);
            case ANVIL -> new BlockStoneAnvil(type, rock);
            case SPIKE -> new BlockRockSpike(type, rock);
            case FARMLAND -> new BlockFarmlandTFC(type, rock);
            case PATH -> new BlockPathTFC(type, rock);
            case GRASS, DRY_GRASS, CLAY_GRASS -> new BlockRockVariantConnected(type, rock);
            case SAND, DIRT, CLAY, GRAVEL, COBBLE -> new BlockRockVariantFallable(type, rock);
            default -> new BlockRockVariant(type, rock);
        };
    }

    public BlockRockVariant getVariant(Rock.Type t) {
        return TABLE.get(rock).get(t);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        switch (this.type) {
            case PATH:
            case FARMLAND:
                switch (side) {
                    case UP:
                        return true;
                    case NORTH:
                    case SOUTH:
                    case WEST:
                    case EAST:
                        IBlockState state = world.getBlockState(pos.offset(side));
                        Block block = state.getBlock();
                        if (state.isOpaqueCube()) return false;
                        if (block instanceof BlockFarmland || block instanceof BlockGrassPath) return false;
                        if (block instanceof BlockRockVariant blockRockVariant) {
                            return switch (blockRockVariant.type) {
                                case FARMLAND, PATH -> false;
                                default -> true;
                            };
                        }
                        return true;
                    case DOWN:
                        return super.shouldSideBeRendered(blockState, world, pos, side);
                }
            default:
                return super.shouldSideBeRendered(blockState, world, pos, side);
        }
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.isRemote) return;
        if (type.isGrass) BlockRockVariantConnected.spreadGrass(world, pos, state, rand);
        super.randomTick(world, pos, state, rand);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return switch (type) {
            case RAW, SPIKE -> ItemRock.get(rock);
            case CLAY, CLAY_GRASS -> Items.CLAY_BALL;
            default -> super.getItemDropped(state, rand, fortune);
            case GRASS, DRY_GRASS, PATH -> Item.getItemFromBlock(get(rock, Rock.Type.DIRT));
        };
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return type.isGrass ? BlockRenderLayer.CUTOUT : BlockRenderLayer.SOLID;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return switch (type) {
            case CLAY, CLAY_GRASS -> 4;
            case RAW, SPIKE -> 1 + random.nextInt(3);
            default -> super.quantityDropped(state, fortune, random);
        };
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        int beachDistance = 2;

        if (plantable instanceof BlockPlantTFC) {
            switch (((BlockPlantTFC) plantable).getPlantTypeTFC()) {
                case CLAY:
                    return type == Rock.Type.DIRT || type == Rock.Type.GRASS || type == Rock.Type.DRY_GRASS || type == Rock.Type.CLAY ||
                            type == Rock.Type.CLAY_GRASS;
                case DESERT_CLAY:
                    return type == Rock.Type.SAND || type == Rock.Type.CLAY || type == Rock.Type.CLAY_GRASS;
                case DRY_CLAY:
                    return type == Rock.Type.DIRT || type == Rock.Type.DRY_GRASS || type == Rock.Type.SAND || type == Rock.Type.CLAY ||
                            type == Rock.Type.CLAY_GRASS;
                case DRY:
                    return type == Rock.Type.DIRT || type == Rock.Type.DRY_GRASS || type == Rock.Type.SAND;
                case FRESH_WATER:
                    return type == Rock.Type.DIRT || type == Rock.Type.GRASS || type == Rock.Type.DRY_GRASS || type == Rock.Type.GRAVEL;
                case SALT_WATER:
                    return type == Rock.Type.DIRT || type == Rock.Type.GRASS || type == Rock.Type.DRY_GRASS || type == Rock.Type.SAND ||
                            type == Rock.Type.GRAVEL;
                case FRESH_BEACH: {
                    boolean flag = false;
                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                        for (int i = 1; i <= beachDistance; i++) {
                            if (BlocksTFC.isFreshWaterOrIce(world.getBlockState(pos.offset(facing, i)))) {
                                flag = true;
                                break;
                            }
                        }
                    }
                    return (type == Rock.Type.DIRT || type == Rock.Type.GRASS || type == Rock.Type.SAND || type == Rock.Type.DRY_GRASS) && flag;
                }
                case SALT_BEACH: {
                    boolean flag = false;
                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                        for (int i = 1; i <= beachDistance; i++)
                            if (BlocksTFC.isSaltWater(world.getBlockState(pos.offset(facing, i)))) {
                                flag = true;
                            }
                    }
                    return (type == Rock.Type.DIRT || type == Rock.Type.GRASS || type == Rock.Type.SAND || type == Rock.Type.DRY_GRASS) && flag;
                }
            }
        } else if (plantable instanceof BlockCropTFC) {
            IBlockState cropState = world.getBlockState(pos.up());
            if (cropState.getBlock() instanceof BlockCropTFC) {
                boolean isWild = cropState.getValue(WILD);
                if (isWild) {
                    if (type == Rock.Type.DIRT || type == Rock.Type.GRASS || type == Rock.Type.DRY_GRASS || type == Rock.Type.CLAY_GRASS) {
                        return true;
                    }
                }
                return type == Rock.Type.FARMLAND;
            }
        }

        switch (plantable.getPlantType(world, pos.offset(direction))) {
            case Plains:
                return type == Rock.Type.DIRT || type == Rock.Type.GRASS || type == Rock.Type.FARMLAND || type == Rock.Type.DRY_GRASS ||
                        type == Rock.Type.CLAY || type == Rock.Type.CLAY_GRASS;
            case Crop:
                return type == Rock.Type.FARMLAND;
            case Desert:
                return type == Rock.Type.SAND;
            case Cave:
                return true;
            case Water:
                return false;
            case Beach: {
                boolean flag = false;
                for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                    for (int i = 1; i <= beachDistance; i++)
                        if (BlocksTFC.isWater(world.getBlockState(pos.offset(facing, i)))) {
                            flag = true;
                        }
                }
                return (type == Rock.Type.DIRT || type == Rock.Type.GRASS || type == Rock.Type.SAND || type == Rock.Type.DRY_GRASS) && flag;
            }
            case Nether:
                return false;
        }

        return false;
    }

    public Rock.Type getType() {
        return type;
    }

    public Rock getRock() {
        return rock;
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack stack) {
        return Size.SMALL; // Store anywhere
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack stack) {
        return Weight.LIGHT; // Stacksize = 32
    }

}
