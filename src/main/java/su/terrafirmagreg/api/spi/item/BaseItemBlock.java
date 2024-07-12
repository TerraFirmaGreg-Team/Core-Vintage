package su.terrafirmagreg.api.spi.item;

import su.terrafirmagreg.api.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.api.registry.provider.IMultiItemBlockProvider;
import su.terrafirmagreg.api.spi.block.IBlockSettings;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;


import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
public class BaseItemBlock extends ItemBlock implements ICapabilitySize {

    private final EnumRarity rarity;

    public BaseItemBlock(Block block) {
        super(block);

        if (block instanceof IBlockSettings settingsBlock) {
            this.rarity = settingsBlock.getSettings().getRarity();
        } else {
            this.rarity = EnumRarity.COMMON;
        }

        if (block instanceof IMultiItemBlockProvider multiItemBlock) {
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
        if (block instanceof IMultiItemBlockProvider multiItemBlock && multiItemBlock.getTranslationKey(stack.getMetadata()) != null) {
            return super.getTranslationKey(stack) + "." + multiItemBlock.getTranslationKey(stack.getMetadata());
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
