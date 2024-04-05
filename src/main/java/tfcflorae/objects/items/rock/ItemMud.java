package tfcflorae.objects.items.rock;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import tfcflorae.client.GuiHandler;
import tfcflorae.objects.items.ItemTFCF;
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFCF;

@MethodsReturnNonnullByDefault

public class ItemMud extends ItemTFCF implements IRockObject {
	private static final Map<Rock, ItemMud> MAP = new HashMap<>();
	private final Rock rock;
	private final ResourceLocation textureForegroundLocation;
	private final ResourceLocation textureBackgroundLocation;

	public ItemMud(Rock rock) {
		this.rock = rock;
		this.textureForegroundLocation = new ResourceLocation(MODID_TFCF, "textures/gui/knapping/mud_button/" + rock + ".png");
		this.textureBackgroundLocation = new ResourceLocation(MODID_TFCF, "textures/gui/knapping/mud_button_disabled/" + rock + ".png");
		if (MAP.put(rock, this) != null) throw new IllegalStateException("There can only be one.");
		setMaxDamage(0);
		OreDictionaryHelper.register(this, "mud");
		OreDictionaryHelper.register(this, "mud", rock);
		OreDictionaryHelper.register(this, "mud", rock.getRockCategory());

		if (rock.isFluxStone()) {
			OreDictionaryHelper.register(this, "mud", "flux");
		}
	}

	public static ItemMud get(Rock rock) {
		return MAP.get(rock);
	}

	public static ItemStack get(Rock rock, int amount) {
		return new ItemStack(MAP.get(rock), amount);
	}

	public Rock getRock() {
		return rock;
	}

	@Override
	@NotNull
	public Rock getRock(ItemStack stack) {
		return rock;
	}

	@Override
	@NotNull
	public RockCategory getRockCategory(ItemStack stack) {
		return rock.getRockCategory();
	}


	@Override
	public @NotNull Size getSize(ItemStack stack) {
		return Size.SMALL; // Stored everywhere
	}


	@Override
	public @NotNull Weight getWeight(ItemStack stack) {
		return Weight.VERY_LIGHT; // Stacksize = 64
	}

	@Override
	@NotNull
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @NotNull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote && !player.isSneaking() && stack.getCount() > 2) {
			GuiHandler.openGui(world, player.getPosition(), player, GuiHandler.Type.MUD);
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	@NotNull
	public void onRightClick(PlayerInteractEvent.RightClickItem event) {
		EnumHand hand = event.getHand();
		if (OreDictionaryHelper.doesStackMatchOre(event.getItemStack(), "mud") && hand == EnumHand.MAIN_HAND) {
			EntityPlayer player = event.getEntityPlayer();
			World world = event.getWorld();
			ItemStack stack = player.getHeldItem(hand);
			if (!world.isRemote && !player.isSneaking() && stack.getCount() > 2) {
				GuiHandler.openGui(world, player.getPosition(), player, GuiHandler.Type.MUD);
			}
		}
	}

	public ResourceLocation getForegroundTexture() {
		return textureForegroundLocation;
	}

	public ResourceLocation getBackgroundTexture() {
		return textureBackgroundLocation;
	}
}
