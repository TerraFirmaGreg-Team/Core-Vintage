package net.dries007.tfc.common.objects.blocks;

import net.dries007.tfc.Tags;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;


@ParametersAreNonnullByDefault
public class BlockFireClay extends Block implements IItemSize, IItemProvider {
    public BlockFireClay() {
        super(Material.CLAY);

        setSoundType(SoundType.GROUND);
        setHardness(1.0F);

        setCreativeTab(CreativeTabsTFC.ROCK);
        setRegistryName(TerraFirmaCraft.identifier("fire_clay_block"));
        setTranslationKey(Tags.MOD_ID + ".fire_clay_block");
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
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
