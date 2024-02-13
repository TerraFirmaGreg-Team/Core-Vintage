package su.terrafirmagreg.modules.rock.objects.items;

import net.minecraft.item.ItemStack;

import lombok.Getter;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.util.OreDictionaryHelper;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.item.ItemBase;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.item.IRockItem;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;


@Getter
public class ItemRockGravel extends ItemBase implements IRockItem {

    private final RockItemVariant itemVariant;
    private final RockType type;

    public ItemRockGravel(RockItemVariant itemVariant, RockType type) {
        this.itemVariant = itemVariant;
        this.type = type;

        OreDictionaryHelper.register(this, itemVariant.toString());
        OreDictionaryHelper.register(this, itemVariant.toString(), type.toString());
        OreDictionaryHelper.register(this, itemVariant.toString(), type.getRockCategory().toString());
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
