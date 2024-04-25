package tfcflorae.objects.blocks.groundcover;

import su.terrafirmagreg.api.util.BlockUtils;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BlockCoralBlock extends Block implements IItemSize {

    public static final Map<EnumDyeColor, BlockCoralBlock> TUBE_CORAL_BLOCK = new HashMap<>();
    public static final Map<EnumDyeColor, BlockCoralBlock> BRAIN_CORAL_BLOCK = new HashMap<>();
    public static final Map<EnumDyeColor, BlockCoralBlock> BUBBLE_CORAL_BLOCK = new HashMap<>();
    public static final Map<EnumDyeColor, BlockCoralBlock> FIRE_CORAL_BLOCK = new HashMap<>();
    public static final Map<EnumDyeColor, BlockCoralBlock> HORN_CORAL_BLOCK = new HashMap<>();

    public BlockCoralBlock(MapColor blockMapColorIn) {
        super(Material.CORAL, blockMapColorIn);
        setSoundType(SoundType.PLANT);
        setHardness(0.5F);
        BlockUtils.setFireInfo(this, 5, 20);
    }

    @Override
    public @NotNull Size getSize(ItemStack stack) {
        return Size.SMALL;
    }

    @Override
    public @NotNull Weight getWeight(ItemStack stack) {
        return Weight.LIGHT;
    }
}
