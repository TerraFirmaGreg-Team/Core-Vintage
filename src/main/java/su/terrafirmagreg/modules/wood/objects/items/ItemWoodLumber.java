package su.terrafirmagreg.modules.wood.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.IWoodItem;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public class ItemWoodLumber extends BaseItem implements IWoodItem {

    private final WoodItemVariant variant;
    private final WoodType type;

    public ItemWoodLumber(WoodItemVariant variant, WoodType type) {
        this.type = type;
        this.variant = variant;

        setMaxDamage(0);
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, variant);
        OreDictUtils.register(this, variant, type);
    }

    @NotNull
    @Override
    public Size getSize(ItemStack stack) {
        return Size.SMALL;
    }

    @NotNull
    @Override
    public Weight getWeight(ItemStack stack) {
        return Weight.VERY_LIGHT;
    }

    @Override
    public void onModelRegister() {
        ModelUtils.registerInventoryModel(this, getResourceLocation());

    }

    @Override
    public IItemColor getItemColor() {
        return (s, i) -> this.getType().getColor();
    }
}
