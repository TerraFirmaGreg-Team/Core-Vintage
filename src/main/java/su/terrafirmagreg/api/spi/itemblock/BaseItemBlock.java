package su.terrafirmagreg.api.spi.itemblock;

import su.terrafirmagreg.api.spi.block.IMultiItemBlock;
import su.terrafirmagreg.api.spi.block.ISettingsBlock;
import su.terrafirmagreg.api.spi.item.BaseItem;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
public class BaseItemBlock extends ItemBlock implements IItemSize {

    protected final ISettingsBlock.Settings settings;

    public BaseItemBlock(Block block) {
        super(block);

        this.settings = ISettingsBlock.Settings.copy(block);

        if (block instanceof IMultiItemBlock multiItemBlock) {
            setMaxDamage(multiItemBlock.getMaxItemDamage());
            setHasSubtypes(multiItemBlock.getHasItemSubtypes());
        } else {
            setMaxDamage(0);
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return settings.getRarity();
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        if (block instanceof IMultiItemBlock multiItemBlock) {
            return multiItemBlock.getTranslationKey(stack.getMetadata());
        } else {
            return super.getTranslationKey(stack);
        }
    }

    @Override
    public Size getSize(ItemStack stack) {
        return settings.getSize();
    }

    @Override
    public Weight getWeight(ItemStack stack) {
        return settings.getWeight();
    }

    @Override
    public boolean canStack(ItemStack stack) {
        return settings.isCanStack();
    }

    /**
     * @see BaseItem#getItemStackLimit(ItemStack)
     */
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return getWeight(stack).getStackSize();
    }

}
