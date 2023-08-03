package net.dries007.tfc.objects.items.rock;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.util.IRockItem;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.item.ItemStack;

@ParametersAreNonnullByDefault
public class ItemBrickTFC extends ItemTFC implements IRockItem {
    private final RockType rock;

    public ItemBrickTFC(RockType rock) {
        this.rock = rock;

        TFCStorage.addBrickItem(rock, this);

        OreDictionaryHelper.register(this, "brick");
        OreDictionaryHelper.register(this, "brick", rock.getRockCategory());
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

    @Nonnull
    @Override
    public RockType getRockType() {
        return rock;
    }
}
