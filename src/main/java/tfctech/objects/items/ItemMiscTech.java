package tfctech.objects.items;

import javax.annotation.Nullable;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import net.dries007.tfc.objects.items.ItemMisc;

@SuppressWarnings("WeakerAccess")
public class ItemMiscTech extends ItemMisc
{
    private final String oreDictionary;

    public ItemMiscTech(Size size, Weight weight, String oreDictionary)
    {
        super(size, weight);
        this.oreDictionary = oreDictionary;
    }

    public ItemMiscTech(Size size, Weight weight)
    {
        super(size, weight);
        oreDictionary = null;
    }

    @Nullable
    public String getOreDictionary()
    {
        return oreDictionary;
    }
}
