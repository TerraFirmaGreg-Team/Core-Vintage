package su.terrafirmagreg.modules.rock.objects.items;

import lombok.Getter;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.item.ItemBase;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.item.IRockItem;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;


@Getter
public class ItemRockBrick extends ItemBase implements IRockItem {

	private final RockItemVariant itemVariant;
	private final RockType type;

	public ItemRockBrick(RockItemVariant variant, RockType type) {
		this.itemVariant = variant;
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
