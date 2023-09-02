package net.dries007.tfc.common.objects.items.wood;

import com.ferreusveritas.dynamictrees.items.Seed;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.wood.IWoodItem;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.item.WoodItemVariant;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nonnull;

public class ItemWoodSeed extends Seed implements IWoodItem {

    private final WoodType type;
    private final WoodItemVariant variant;

    public ItemWoodSeed(WoodItemVariant variant, WoodType type) {
        super(String.format("wood/%s/%s", variant, type));
        this.type = type;
        this.variant = variant;

        setTranslationKey(getTranslationName());

        OreDictionaryHelper.register(this, variant.toString());
        OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }


    @Nonnull
    @Override
    public WoodItemVariant getItemVariant() {
        return variant;
    }

    @Nonnull
    @Override
    public WoodType getType() {
        return type;
    }

    @Nonnull
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.SMALL;
    }

    @Nonnull
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.VERY_LIGHT;
    }

    @Override
    public void onModelRegister() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
                new ModelResourceLocation(getResourceLocation() + "/" + getType()));
    }
}
