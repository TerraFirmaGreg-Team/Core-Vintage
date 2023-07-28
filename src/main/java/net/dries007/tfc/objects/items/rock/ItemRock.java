/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.items.rock;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types2.rock.RockCategory;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemRock extends ItemTFC implements IRockObject {
	private static final Map<RockType, ItemRock> MAP = new HashMap<>();

	public static ItemRock get(RockType rock) {
		return MAP.get(rock);
	}

	public static ItemStack get(RockType rock, int amount) {
		return new ItemStack(MAP.get(rock), amount);
	}

	private final RockType rock;

	public ItemRock(RockType rock) {
		this.rock = rock;
		if (MAP.put(rock, this) != null) throw new IllegalStateException("There can only be one.");
		setMaxDamage(0);
		OreDictionaryHelper.register(this, "rock");
		OreDictionaryHelper.register(this, "rock", rock);
		OreDictionaryHelper.register(this, "rock", rock.getRockCategory());

//        if (rock.isFluxStone())
//        {
//            OreDictionaryHelper.register(this, "rock", "flux");
//        }
	}

	@Override
	@Nonnull
	public RockType getRock(ItemStack stack) {
		return rock;
	}

	@Override
	@Nonnull
	public RockCategory getRockCategory(ItemStack stack) {
		return rock.getRockCategory();
	}

	@Nonnull
	@Override
	public Size getSize(ItemStack stack) {
		return Size.SMALL; // Stored everywhere
	}

	@Nonnull
	@Override
	public Weight getWeight(ItemStack stack) {
		return Weight.VERY_LIGHT; // Stacksize = 64
	}

	@Override
	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote && !player.isSneaking() && stack.getCount() > 1) {
			TFCGuiHandler.openGui(world, player.getPosition(), player, TFCGuiHandler.Type.KNAPPING_STONE);
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}
}
