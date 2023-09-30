package net.dries007.tfc.module.rock.common.blocks;

import gregtech.common.items.ToolItems;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.rock.StorageRock;
import net.dries007.tfc.module.rock.api.types.type.RockType;
import net.dries007.tfc.module.rock.api.types.variant.block.RockBlockVariant;
import net.dries007.tfc.util.GemsFromRawRocks;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.dries007.tfc.module.rock.api.types.variant.block.RockBlockVariants.ANVIL;
import static net.dries007.tfc.module.rock.api.types.variant.block.RockBlockVariants.COBBLE;

public class BlockRockRaw extends BlockRock {
    /* This is for the not-surrounded-on-all-sides-pop-off mechanic. It's a dirty fix to the stack overflow caused by placement during water / lava collisions in world gen */
    public static final PropertyBool CAN_FALL = PropertyBool.create("can_fall");

    public BlockRockRaw(RockBlockVariant variant, RockType type) {
        super(variant, type);

        setDefaultState(getBlockState().getBaseState()
                .withProperty(CAN_FALL, true));

        // Copy as each raw stone has an unique resultingState
        var spec = new FallingBlockManager.Specification(FallingBlockManager.Specification.COLLAPSABLE_ROCK);
        spec.setResultingState(StorageRock.getRockBlock(COBBLE, type).getDefaultState());

        FallingBlockManager.registerFallable(this, spec);
        OreDictionaryHelper.register(this, "stone");
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(CAN_FALL, meta == 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if (state.getBlock() != this) {
            return 0;
        } else {
            return state.getValue(CAN_FALL) ? 0 : 1;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        // Raw blocks that can't fall also can't pop off
        if (state.getValue(CAN_FALL)) {
            for (EnumFacing face : EnumFacing.VALUES) {
                BlockPos offsetPos = pos.offset(face);
                IBlockState faceState = worldIn.getBlockState(offsetPos);
                if (faceState.getBlock().isSideSolid(faceState, worldIn, offsetPos, face.getOpposite())) {
                    return;
                }
            }

            // No supporting solid blocks, so pop off as an item
            worldIn.setBlockToAir(pos);
            Helpers.spawnItemStack(worldIn, pos, new ItemStack(state.getBlock(), 1));
        }
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItemMainhand();
        if (ConfigTFC.General.OVERRIDES.enableStoneAnvil && stack.getItem() == ToolItems.HARD_HAMMER.get() && !worldIn.isBlockNormalCube(pos.up(), true)) {
            if (!worldIn.isRemote) {
                // Create a stone anvil
                var anvil = StorageRock.getRockBlock(ANVIL, getType());
                if (anvil instanceof BlockRockAnvil) {
                    worldIn.setBlockState(pos, anvil.getDefaultState());
                }
            }
            return true;
        }
        return false;
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CAN_FALL);
    }

    @Override
    public void getDrops(@Nonnull NonNullList<ItemStack> drops, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);

        if (RANDOM.nextDouble() < ConfigTFC.General.MISC.stoneGemDropChance) {
            drops.add(GemsFromRawRocks.getRandomGem());
        }
    }

    @Override
    public int quantityDropped(@Nonnull IBlockState state, int fortune, Random random) {
        return 1 + random.nextInt(3);
    }
}
