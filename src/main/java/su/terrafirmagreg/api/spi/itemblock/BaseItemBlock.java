package su.terrafirmagreg.api.spi.itemblock;

import su.terrafirmagreg.api.spi.block.BaseBlock;
import su.terrafirmagreg.api.spi.block.IMultiItemBlock;
import su.terrafirmagreg.api.spi.item.BaseItem;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.capability.size.IItemSize;

import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
public class BaseItemBlock extends ItemBlock implements IItemSize {

    private final EnumRarity rarity;

    public BaseItemBlock(Block block) {
        super(block);

        this.rarity = EnumRarity.COMMON;

        if (block instanceof IMultiItemBlock multiItemBlock) {
            setMaxDamage(multiItemBlock.getMaxItemDamage());
            setHasSubtypes(multiItemBlock.getHasItemSubtypes());
        } else {
            setMaxDamage(0);
        }
    }

    public BaseItemBlock(BaseBlock block) {
        super(block);

        this.rarity = block.getSettings().getRarity();

        if (block instanceof IMultiItemBlock multiItemBlock) {
            setMaxDamage(multiItemBlock.getMaxItemDamage());
            setHasSubtypes(multiItemBlock.getHasItemSubtypes());
        } else {
            setMaxDamage(0);
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return rarity;
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

    /**
     * @see BaseItem#getItemStackLimit(ItemStack)
     */
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return getWeight(stack).getStackSize();
    }

}
