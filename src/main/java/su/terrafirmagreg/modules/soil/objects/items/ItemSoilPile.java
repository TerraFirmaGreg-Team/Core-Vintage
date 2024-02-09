package su.terrafirmagreg.modules.soil.objects.items;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.util.OreDictionaryHelper;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.item.ItemBase;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.item.ISoilItemVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariant;

public class ItemSoilPile extends ItemBase implements ISoilItemVariant {

    private final SoilItemVariant variant;
    private final SoilType type;

    public ItemSoilPile(SoilItemVariant variant, SoilType type) {

        this.variant = variant;
        this.type = type;

        OreDictionaryHelper.register(this, variant.toString());
        OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }

    @NotNull
    @Override
    public SoilItemVariant getItemVariant() {
        return variant;
    }

    @Override
    @NotNull
    public SoilType getType() {
        return type;
    }

    @NotNull
    @Override
    public Size getSize(@NotNull ItemStack stack) {
        return Size.SMALL;
    }

    @NotNull
    @Override
    public Weight getWeight(@NotNull ItemStack stack) {
        return Weight.VERY_LIGHT;
    }

}
