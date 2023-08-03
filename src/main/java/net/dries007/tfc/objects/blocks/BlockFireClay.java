package net.dries007.tfc.objects.blocks;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@ParametersAreNonnullByDefault
public class BlockFireClay extends Block implements IItemSize {
    public BlockFireClay() {
        super(Material.CLAY);

        setCreativeTab(CreativeTabsTFC.ROCK_STUFFS);
        setSoundType(SoundType.GROUND);
        setHardness(1.0F);
        setRegistryName(MOD_ID, "fire_clay_block");
        setTranslationKey(MOD_ID + ".fire_clay_block");
    }

    @Override
    public int quantityDropped(Random random) {
        return 4;
    }

    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ItemsTFC.FIRE_CLAY;
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack) {
        return Size.VERY_SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack) {
        return Weight.HEAVY;
    }
}
