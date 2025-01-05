package su.terrafirmagreg.api.base.object.itemblock.spi;


import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.framework.registry.api.provider.IProviderMultiItemBlock;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;

import lombok.Getter;

@Getter
public class BaseItemBlock extends ItemBlock {

  private final IRarity rarity;

  public BaseItemBlock(Block block) {
    super(block);

    if (block instanceof IBlockSettings settingsBlock) {
      this.rarity = settingsBlock.getSettings().getRarity();
    } else {
      this.rarity = EnumRarity.COMMON;
    }

    if (block instanceof IProviderMultiItemBlock multiItemBlock) {
      setMaxDamage(multiItemBlock.getMaxItemDamage());
      setHasSubtypes(multiItemBlock.getHasItemSubtypes());
    } else {
      setMaxDamage(0);
    }
  }

  @Override
  public int getMetadata(int damage) {
    return damage;
  }


  @Override
  public IRarity getForgeRarity(ItemStack stack) {
    return rarity;
  }

  @Override
  public String getTranslationKey(ItemStack stack) {
    if (block instanceof IProviderMultiItemBlock multiItemBlock && multiItemBlock.getTranslationKey(stack.getMetadata()) != null) {
      return super.getTranslationKey(stack) + "." + multiItemBlock.getTranslationKey(stack.getMetadata());
    } else {
      return super.getTranslationKey(stack);
    }
  }

}
