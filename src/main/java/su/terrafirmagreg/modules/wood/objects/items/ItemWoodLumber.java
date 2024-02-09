package su.terrafirmagreg.modules.wood.objects.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.item.ItemBase;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.IWoodItem;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;

public class ItemWoodLumber extends ItemBase implements IWoodItem {

    private final WoodType type;
    private final WoodItemVariant variant;

    public ItemWoodLumber(WoodItemVariant variant, WoodType type) {
        this.type = type;
        this.variant = variant;

        setMaxDamage(0);

//        OreDictionaryHelper.register(this, variant.toString());
//        OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }


    @NotNull
    @Override
    public WoodItemVariant getItemVariant() {
        return variant;
    }

    @NotNull
    @Override
    public WoodType getType() {
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

    @Override
    public void onModelRegister() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
                new ModelResourceLocation(getResourceLocation(), "inventory"));
    }
}
