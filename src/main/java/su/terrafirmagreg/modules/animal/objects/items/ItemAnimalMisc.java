package su.terrafirmagreg.modules.animal.objects.items;

import su.terrafirmagreg.api.spi.item.ItemBase;
import su.terrafirmagreg.api.util.OreDictUtils;

import lombok.Getter;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

public class ItemAnimalMisc extends ItemBase {

    @Getter
    private final Size size;
    @Getter
    private final Weight weight;

    @Getter
    private final String name;
    private Object[] oreNameParts = null;

    public ItemAnimalMisc(String name, Size size, Weight weight, Object... oreNameParts) {
        this(name, size, weight);
        this.oreNameParts = oreNameParts;
    }

    public ItemAnimalMisc(String name, Size size, Weight weight) {
        this.name = "animal/" + name;
        this.size = size;
        this.weight = weight;
    }

    @Override
    public void onRegisterOreDict() {
        if (oreNameParts != null) OreDictUtils.register(this, oreNameParts);
    }

}
