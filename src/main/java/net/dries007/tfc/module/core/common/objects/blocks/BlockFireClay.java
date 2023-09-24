package net.dries007.tfc.module.core.common.objects.blocks;

import net.dries007.tfc.Tags;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.module.core.common.objects.items.TFCItems;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import static net.dries007.tfc.module.core.common.objects.CreativeTabsTFC.MISC_TAB;


@ParametersAreNonnullByDefault
public class BlockFireClay extends TFCBlock implements IItemSize {
    public BlockFireClay() {
        super(Material.CLAY);

        setSoundType(SoundType.GROUND);
        setHardness(1.0F);

        setCreativeTab(MISC_TAB);
        setRegistryName(TerraFirmaCraft.getID("fire_clay_block"));
        setTranslationKey(Tags.MOD_ID + ".fire_clay_block");
    }

    @Override
    public int quantityDropped(Random random) {
        return 4;
    }

    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return TFCItems.FIRE_CLAY;
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
