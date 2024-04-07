package su.terrafirmagreg.modules.rock.objects.items;

import su.terrafirmagreg.api.spi.item.ItemBase;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.item.IRockItem;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;

import net.minecraft.item.ItemStack;

import lombok.Getter;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;

@Getter
public class ItemRockGravel extends ItemBase implements IRockItem {

    private final RockItemVariant itemVariant;
    private final RockType type;

    public ItemRockGravel(RockItemVariant itemVariant, RockType type) {
        this.itemVariant = itemVariant;
        this.type = type;
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, itemVariant);
        OreDictUtils.register(this, itemVariant, getCategory());
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
