package su.terrafirmagreg.mixin.minecraft.item;

import su.terrafirmagreg.api.base.item.spi.IItemSettings;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.registries.IForgeRegistryEntry;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.jetbrains.annotations.NotNull;

@Mixin(value = Item.class, remap = false)
public abstract class MixinItem extends IForgeRegistryEntry.Impl<Item> implements IItemSettings {

  @Mutable
  @Final
  protected Settings settings;

  @Inject(method = "<init>", at = @At(value = "TAIL"), remap = false)
  public void onConstruct(CallbackInfo ci) {
    this.settings = Settings.of();
  }

  @Override
  public Settings getSettings() {
    return settings;
  }

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsItem
   */
  @Overwrite
  public IRarity getForgeRarity(ItemStack stack) {
    return getSettings().getRarity();
  }

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsItem
   */
  @Overwrite
  public int getMaxDamage() {
    return getSettings().getMaxDamage();
  }

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsItem
   */
  @Overwrite
  public int getItemStackLimit() {
    return getSettings().getMaxCount();
  }


  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsItem
   */
  @Overwrite
  public int getItemStackLimit(@NotNull ItemStack stack) {
    return this.getStackSize(stack);
  }

  @Override
  public Size getSize(ItemStack stack) {
    return getSettings().getSize();
  }

  @Override
  public Weight getWeight(ItemStack stack) {
    return getSettings().getWeight();
  }

  @Override
  public boolean canStack(ItemStack stack) {
    return getSettings().isCanStack();
  }


}
