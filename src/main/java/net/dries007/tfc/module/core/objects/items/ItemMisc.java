package net.dries007.tfc.module.core.objects.items;

import net.dries007.tfc.Tags;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.module.core.api.item.ItemBase;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

import static net.dries007.tfc.module.core.ModuleCore.MISC_TAB;


public class ItemMisc extends ItemBase implements IItemSize {
    private final String name;
    private final Size size;
    private final Weight weight;

    public ItemMisc(String name, Size size, Weight weight, CreativeTabs ct, String... oreNameParts) {
        this(name, size, weight, ct);

        for (String oreName : oreNameParts) {
            OreDictionaryHelper.register(this, oreName);
        }
    }

    public ItemMisc(String name, Size size, Weight weight, String... oreNameParts) {
        this(name, size, weight);

        for (String oreName : oreNameParts) {
            OreDictionaryHelper.register(this, oreName);
        }
    }

    public ItemMisc(String name, Size size, Weight weight, CreativeTabs ct) {
        this(name, size, weight);

        setCreativeTab(ct);
    }

    public ItemMisc(String name, Size size, Weight weight) {

        this.name = name;
        this.size = size;
        this.weight = weight;

        setRegistryName(Tags.MOD_ID, name);
        setTranslationKey(Tags.MOD_ID + "." + name.toLowerCase().replace("/", "."));
        setCreativeTab(MISC_TAB);
    }


    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return size;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return weight;
    }
}
