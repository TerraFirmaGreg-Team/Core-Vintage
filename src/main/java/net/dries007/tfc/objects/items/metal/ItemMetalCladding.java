package net.dries007.tfc.objects.items.metal;

import gregtech.api.unification.material.Material;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.blocks.metal.BlockMetalCladding;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.objects.te.TEMetalSheet;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@ParametersAreNonnullByDefault
public class ItemMetalCladding extends ItemTFC {
    private static final Map<Material, ItemMetalCladding> CLADDING_STORAGE_MAP = new HashMap<>();
    private final Material material;

    public ItemMetalCladding(Material material) {
        this.material = material;

        if (CLADDING_STORAGE_MAP.put(material, this) != null) throw new IllegalStateException("There can only be one.");
    }

    public static ItemMetalCladding get(Material material) {
        return CLADDING_STORAGE_MAP.get(material);
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (worldIn.getBlockState(pos).isNormalCube() && stack.getItem() instanceof ItemMetalCladding sheet) {

            if (!ItemStack.areItemStacksEqual(new ItemStack(stack.getItem(), stack.getCount()), stack)) {
                return EnumActionResult.FAIL;
            }
            BlockPos posAt = pos.offset(facing);
            IBlockState stateAt = worldIn.getBlockState(posAt);

            if (stateAt.getBlock() instanceof BlockMetalCladding) {
                // Existing sheet block
                var metal = ((BlockMetalCladding) stateAt.getBlock()).getMaterial();
                if (metal == sheet.material) {
                    stack.shrink(1);
                    player.setHeldItem(hand, stack);
                    return placeSheet(worldIn, posAt, facing);
                }
            } else if (stateAt.getBlock().isReplaceable(worldIn, posAt)) {
                // Place a new block
                if (!worldIn.isRemote) {
                    worldIn.setBlockState(posAt, BlockMetalCladding.get(sheet.material).getDefaultState());
                    stack.shrink(1);
                    player.setHeldItem(hand, stack);
                    placeSheet(worldIn, posAt, facing);
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }

    private EnumActionResult placeSheet(World world, BlockPos pos, EnumFacing facing) {
        TEMetalSheet tile = Helpers.getTE(world, pos, TEMetalSheet.class);
        if (tile != null && !tile.getFace(facing)) {
            if (!world.isRemote) {
                tile.setFace(facing, true);
                world.playSound(null, pos.offset(facing), SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.LARGE;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.MEDIUM;
    }
}
