package su.terrafirmagreg.mixin.minecraft.item;


import su.terrafirmagreg.api.base.object.item.api.IItemSettings;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.IForgeRegistryEntry;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Item.class, remap = false)
public abstract class MixinItem extends IForgeRegistryEntry.Impl<Item> implements IItemSettings {

  @Unique
  @Mutable
  @Final
  protected Settings terraFirmaGreg$settings;

  @Inject(method = "<init>", at = @At(value = "TAIL"), remap = false)
  public void onConstruct(CallbackInfo ci) {
    this.terraFirmaGreg$settings = Settings.of();
  }

  @Override
  public Settings getSettings() {
    return terraFirmaGreg$settings;
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
    return getSettings().getMaxStackSize();
  }

  // Fix #12 by actually implementing the MC function that limits stack sizes

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsItem
   */
  @Overwrite
  public int getItemStackLimit(ItemStack stack) {
    return getItemStackLimit();
  }

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsItem
   */
  @Overwrite
  public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
    if (getSettings().getCapability().isEmpty()) {
      return null;
    }
    return def$initCapabilities(stack, nbt);
  }


}
