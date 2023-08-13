package net.dries007.tfc.common.objects.items.wood;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.wood.IWoodItem;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nonnull;

public class ItemWoodLumber extends ItemTFC implements IHasModel, IWoodItem {

    private final WoodType woodType;

    public ItemWoodLumber(WoodType woodType) {
        this.woodType = woodType;

        setRegistryName(getRegistryLocation("lumber"));
        setTranslationKey(getTranslationName("lumber"));
        setCreativeTab(CreativeTabsTFC.WOOD);

        OreDictionaryHelper.register(this, "lumber");
        OreDictionaryHelper.register(this, "lumber", woodType.toString());
    }


    @Nonnull
    @Override
    public WoodType getWoodType() {
        return woodType;
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.VERY_LIGHT;
    }

    @Override
    public void onModelRegister() {
        ModelLoader.setCustomModelResourceLocation(
                this,
                0,
                new ModelResourceLocation(getResourceLocation("lumber"), "inventory"));
    }
}
