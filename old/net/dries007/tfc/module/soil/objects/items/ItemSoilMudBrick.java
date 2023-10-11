package net.dries007.tfc.module.soil.objects.items;

import net.dries007.tfc.module.core.api.capability.size.Size;
import net.dries007.tfc.module.core.api.capability.size.Weight;
import net.dries007.tfc.module.core.api.objects.item.ItemBase;
import net.dries007.tfc.module.soil.api.types.type.SoilType;
import net.dries007.tfc.module.soil.api.types.variant.item.ISoilItem;
import net.dries007.tfc.module.soil.api.types.variant.item.SoilItemVariant;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemSoilMudBrick extends ItemBase implements ISoilItem {
    private final SoilItemVariant variant;
    private final SoilType type;

    public ItemSoilMudBrick(SoilItemVariant variant, SoilType type) {
        this.variant = variant;
        this.type = type;

        OreDictionaryHelper.register(this, variant.toString());
        OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }

    @Nonnull
    @Override
    public SoilItemVariant getItemVariant() {
        return variant;
    }

    @Nonnull
    @Override
    public SoilType getType() {
        return type;
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack) {
        return Size.SMALL; // Stored everywhere
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack) {
        return Weight.LIGHT; // Stacksize = 32
    }

    @Override
    public void onModelRegister() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
                new ModelResourceLocation(getResourceLocation(), "normal"));
    }
}
