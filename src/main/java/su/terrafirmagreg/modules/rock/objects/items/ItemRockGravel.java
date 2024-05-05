package su.terrafirmagreg.modules.rock.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.item.IRockItem;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;

import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public class ItemRockGravel extends BaseItem implements IRockItem {

    private final RockItemVariant variant;
    private final RockType type;

    public ItemRockGravel(RockItemVariant variant, RockType type) {
        this.variant = variant;
        this.type = type;
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, variant);
        OreDictUtils.register(this, variant, this.getType().getRockCategory());
    }

    @NotNull
    @Override
    public Size getSize(ItemStack stack) {
        return Size.SMALL; // Stored everywhere
    }

    @NotNull
    @Override
    public Weight getWeight(ItemStack stack) {
        return Weight.LIGHT; // Stacksize = 32
    }

}
