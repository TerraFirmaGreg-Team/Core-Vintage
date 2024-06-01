package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.device.init.BlocksDevice;

import net.minecraft.block.BlockBed;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import net.dries007.tfc.ConfigTFC;


import su.terrafirmagreg.api.capabilities.size.spi.Size;

import su.terrafirmagreg.api.capabilities.size.spi.Weight;


import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static net.dries007.tfc.objects.blocks.BlockPlacedHide.SIZE;

public class ItemAnimalHide extends ItemTFC {

    private static final Map<HideType, Map<HideSize, ItemAnimalHide>> TABLE = new HashMap<>();
    protected final HideSize size;
    private final HideType type;

    public ItemAnimalHide(HideType type, HideSize size) {
        this.type = type;
        this.size = size;

        if (!TABLE.containsKey(type)) {
            TABLE.put(type, new HashMap<>());
        }
        TABLE.get(type).put(size, this);
    }

    @NotNull
    public static ItemAnimalHide get(HideType type, HideSize size) {
        return TABLE.get(type).get(size);
    }

    @NotNull
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY,
                                      float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (ConfigTFC.General.OVERRIDES.enableThatchBed && type == HideType.RAW && size == HideSize.LARGE && facing == EnumFacing.UP && worldIn
                .getBlockState(pos)
                .getBlock() == BlocksCore.THATCH && worldIn.getBlockState(pos.offset(player.getHorizontalFacing()))
                .getBlock() == BlocksCore.THATCH) {
            // Try and create a thatch bed
            BlockPos headPos = pos.offset(player.getHorizontalFacing());
            //Creating a thatch bed
            if (player.canPlayerEdit(pos, facing, stack) && player.canPlayerEdit(headPos, facing, stack)) {
                if (!worldIn.isRemote) {
                    IBlockState footState = BlocksDevice.THATCH_BED.getDefaultState()
                            .withProperty(BlockBed.OCCUPIED, false)
                            .withProperty(BlockBed.FACING, player.getHorizontalFacing())
                            .withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);
                    IBlockState headState = BlocksDevice.THATCH_BED.getDefaultState()
                            .withProperty(BlockBed.OCCUPIED, false)
                            .withProperty(BlockBed.FACING, player.getHorizontalFacing()
                                    .getOpposite())
                            .withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD);
                    worldIn.setBlockState(pos, footState, 10);
                    worldIn.setBlockState(headPos, headState, 10);
                    SoundType soundtype = BlocksDevice.THATCH_BED.getSoundType(footState, worldIn, pos, player);
                    worldIn.playSound(null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F,
                            soundtype.getPitch() * 0.8F);

                    stack.shrink(1);
                    player.setHeldItem(hand, stack);
                }
                return EnumActionResult.SUCCESS;
            }
        } else if (type == HideType.SOAKED) {
            IBlockState stateAt = worldIn.getBlockState(pos);
            BlockPos posAbove = pos.up();
            IBlockState stateAbove = worldIn.getBlockState(posAbove);
            ItemStack stackAt = stateAt.getBlock().getPickBlock(stateAt, null, worldIn, pos, player);
            if (facing == EnumFacing.UP && OreDictionaryHelper.doesStackMatchOre(stackAt, "logWood") && stateAbove.getBlock()
                    .isAir(stateAbove, worldIn, posAbove)) {
                if (!worldIn.isRemote) {
                    worldIn.setBlockState(posAbove, BlocksTFC.PLACED_HIDE.getDefaultState().withProperty(SIZE, size));
                }
                stack.shrink(1);
                player.setHeldItem(hand, stack);
                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.FAIL;
        }
        return EnumActionResult.PASS;
    }

    @Override
    @NotNull
    public ItemStack getContainerItem(ItemStack itemStack) {
        switch (size) {
            case SMALL:
                return new ItemStack(ItemAnimalHide.get(HideType.RAW, HideSize.SMALL));
            case MEDIUM:
                return new ItemStack(ItemAnimalHide.get(HideType.RAW, HideSize.MEDIUM));
            case LARGE:
                return new ItemStack(ItemAnimalHide.get(HideType.RAW, HideSize.LARGE));
        }
        return new ItemStack(ItemAnimalHide.get(HideType.RAW, HideSize.SMALL));
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return type == HideType.SHEEPSKIN;
    }

    @Override
    public @NotNull Size getSize(ItemStack stack) {
        return Size.NORMAL; // Stored in chests and Large Vessels
    }

    @Override
    public @NotNull Weight getWeight(ItemStack stack) {
        switch (size) {
            case LARGE:
                return Weight.MEDIUM; // Stacksize = 16
            case MEDIUM:
                return Weight.LIGHT; // Stacksize = 32
            case SMALL:
            default:
                return Weight.VERY_LIGHT; // Stacksize = 64
        }
    }

    public enum HideSize implements IStringSerializable {
        SMALL,
        MEDIUM,
        LARGE;

        private static final HideSize[] VALUES = values();

        @NotNull
        public static HideSize valueOf(int index) {
            return index < 0 || index > VALUES.length ? MEDIUM : VALUES[index];
        }

        @Override
        public String getName() {
            return this.name().toLowerCase();
        }
    }

    public enum HideType {
        RAW,
        SOAKED,
        SCRAPED,
        PREPARED,
        SHEEPSKIN
    }
}
