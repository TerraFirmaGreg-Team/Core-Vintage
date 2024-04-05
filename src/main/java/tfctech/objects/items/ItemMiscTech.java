package tfctech.objects.items;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.ItemMisc;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("WeakerAccess")
public class ItemMiscTech extends ItemMisc {
	private final String oreDictionary;

	public ItemMiscTech(Size size, Weight weight, String oreDictionary) {
		super(size, weight);
		this.oreDictionary = oreDictionary;
	}

	public ItemMiscTech(Size size, Weight weight) {
		super(size, weight);
		oreDictionary = null;
	}

	@Nullable
	public String getOreDictionary() {
		return oreDictionary;
	}
}
