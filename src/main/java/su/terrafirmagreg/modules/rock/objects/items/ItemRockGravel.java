package su.terrafirmagreg.modules.rock.objects.items;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.util.OreDictionaryHelper;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.objects.item.ItemBase;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.item.IRockItem;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;


public class ItemRockGravel extends ItemBase implements IRockItem {

    private final RockItemVariant variant;
    private final RockType type;

    public ItemRockGravel(RockItemVariant variant, RockType type) {
        this.variant = variant;
        this.type = type;

        OreDictionaryHelper.register(this, variant.toString());
        OreDictionaryHelper.register(this, variant.toString(), type.toString());
        OreDictionaryHelper.register(this, variant.toString(), type.getRockCategory().toString());
    }

    @NotNull
    @Override
    public RockItemVariant getItemVariant() {
        return variant;
    }

    @NotNull
    @Override
    public RockType getType() {
        return type;
    }

    @NotNull
    @Override
    public Size getSize(@NotNull ItemStack stack) {
        return Size.SMALL; // Stored everywhere
    }

    @NotNull
    @Override
    public Weight getWeight(@NotNull ItemStack stack) {
        return Weight.LIGHT; // Stacksize = 32
    }

}
