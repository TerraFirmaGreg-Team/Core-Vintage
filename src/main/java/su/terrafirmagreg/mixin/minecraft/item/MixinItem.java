package su.terrafirmagreg.mixin.minecraft.item;

import su.terrafirmagreg.api.spi.item.ISettingsItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
@Mixin(value = Item.class, remap = false)
public abstract class MixinItem implements ISettingsItem {

    @Unique
    protected final Settings settings = Settings.of();

    /**
     * @author Xikaro
     * @reason Адаптация под ISettingsItem
     */
    @Override
    @Overwrite
    public @NotNull IRarity getForgeRarity(ItemStack stack) {
        return getSettings().getRarity();
    }

    /**
     * @author Xikaro
     * @reason Адаптация под ISettingsItem
     */
    @Override
    @Overwrite
    public int getItemStackLimit(@NotNull ItemStack stack) {
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
