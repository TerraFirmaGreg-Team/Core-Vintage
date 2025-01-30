package su.terrafirmagreg.api.base.object.item.spi;


import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.base.object.item.api.IItemSettings;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import static com.feed_the_beast.ftbquests.FTBQuests.TAB;

@Getter
public class BaseItemBlock extends ItemBlock implements IItemSettings {

  protected final Settings settings;

  public BaseItemBlock(Block block) {
    super(block);

    this.settings = Settings.of().group(TAB, TAB);

    if (block instanceof IBlockSettings settingsBlock) {
      getSettings()
        .rarity(settingsBlock.getSettings().getRarity())
        .group(settingsBlock.getSettings().getGroups())
        .oreDict(settingsBlock.getSettings().getOreDict())
        .capability(settingsBlock.getSettings().getCapability());
    }
  }

  @Override
  public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
    return definition$initCapabilities(stack, nbt);
  }

}
