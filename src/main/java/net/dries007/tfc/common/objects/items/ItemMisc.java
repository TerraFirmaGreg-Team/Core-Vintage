package net.dries007.tfc.common.objects.items;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class ItemMisc extends ItemTFC implements IItemSize {
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

        setRegistryName(MOD_ID, name);
        setTranslationKey(MOD_ID + "." + name.toLowerCase().replace("/", "."));
        setCreativeTab(CreativeTabsTFC.MISC);
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
