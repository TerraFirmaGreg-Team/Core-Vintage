package su.terrafirmagreg.mixin.minecraft.item;

import lombok.Getter;


import su.terrafirmagreg.api.capabilities.size.spi.Size;

import su.terrafirmagreg.api.capabilities.size.spi.Weight;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.registries.IForgeRegistryEntry;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;


import su.terrafirmagreg.api.spi.item.IItemSettings;

@Getter
@Mixin(value = Item.class, remap = false)
public abstract class MixinItem extends IForgeRegistryEntry.Impl<Item> implements IItemSettings {

    @Unique
    protected final Settings settings = Settings.of();

    /**
     * @author Xikaro
     * @reason Адаптация под ISettingsItem
     */
    @Override
    @Overwrite
    public IRarity getForgeRarity(ItemStack stack) {
        return getSettings().getRarity();
    }

    /**
     * @author Xikaro
     * @reason Адаптация под ISettingsItem
     */
    @Override
    @Overwrite
    public int getMaxDamage() {
        return getSettings().getMaxDamage();
    }

    /**
     * @author Xikaro
     * @reason Адаптация под ISettingsItem
     */
    @Override
    @Overwrite
    public int getItemStackLimit() {
        return getSettings().getMaxCount();
    }

    /**
     * @author Xikaro
     * @reason Адаптация под ISettingsItem
     */
    @Override
    @Overwrite
    public int getItemStackLimit(ItemStack stack) {
        return getStackSize(stack);
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
