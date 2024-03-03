package su.terrafirmagreg.modules.core.objects.items;

import com.google.common.collect.ImmutableList;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.item.ItemBase;
import su.terrafirmagreg.api.util.NBTUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

import static net.minecraft.util.text.TextFormatting.GOLD;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemDebug extends ItemBase {

	public ItemDebug() {
		setNoRepair();
		setMaxStackSize(1);
		setFull3D();
	}

	public static void changeMode(EntityPlayer player) {
		ItemStack stack = player.getHeldItemMainhand();
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) NBTUtils.resetNBT(stack);
		assert nbt != null;
		int mode = nbt.getInteger("mode");
		int newMode = (mode > 3) ? 0 : mode + 1;
		nbt.setInteger("mode", newMode);
		switch (newMode) {
			case 0: {
				player.sendStatusMessage(new TextComponentString(GOLD + "Blockstate"), true);
				break;
			}
			case 1: {
				player.sendStatusMessage(new TextComponentString(GOLD + "NBT"), true);
				break;
			}
			case 2: {
				player.sendStatusMessage(new TextComponentString(GOLD + "Blockstate list"), true);
				break;
			}
			case 3: {
				player.sendStatusMessage(new TextComponentString(GOLD + "Transform"), true);
				break;
			}
		}
	}

	@Override
	@Nonnull
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
		ItemStack stack = player.getHeldItemMainhand();
		NBTTagCompound nbt = stack.getTagCompound();

		if (nbt == null) NBTUtils.resetNBT(stack);
		assert nbt != null;
		int mode = nbt.getInteger("mode");
		switch (mode) {
			case 0: {
				nbt.setString("Blockstate", world.getBlockState(pos).toString());
				break;
			}
			case 1: {
				TileEntity tile = world.getTileEntity(pos);
				if (tile == null) break;
				nbt.setString("NBTTagCompound", tile.writeToNBT(new NBTTagCompound()).toString());
				break;
			}
			case 2: {
				nbt.setString("BlockstateList", world.getBlockState(pos).getBlock().getBlockState().getValidStates().toString());
				break;
			}
			case 3: {
				ImmutableList<IBlockState> list = world.getBlockState(pos).getBlock().getBlockState().getValidStates();
				int index = list.indexOf(world.getBlockState(pos));
				int newState = (index + 1 >= list.size()) ? 0 : index + 1;
				world.setBlockState(pos, list.get(newState));
				break;
			}
		}
		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		if (world.isRemote) return ActionResult.newResult(EnumActionResult.FAIL, player.getHeldItem(hand));
		if (player.isSneaking() && world.getBlockState(Objects.requireNonNull(player.rayTrace(10, 1)).getBlockPos()).getBlock()
				.isAir(world.getBlockState(
								Objects.requireNonNull(player.rayTrace(10, 1)).getBlockPos()), world,
						Objects.requireNonNull(player.rayTrace(5, 1)).getBlockPos())) changeMode(player);
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (worldIn == null) return;
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) return;
		int mode = nbt.getInteger("mode");
		switch (mode) {
			case 0: {
				tooltip.add("Blockstate: " + nbt.getString("Blockstate"));
				break;
			}
			case 1: {
				tooltip.add("NBTTagCompound: " + nbt.getString("NBTTagCompound"));
				break;
			}
			case 2: {
				tooltip.add("Blockstate List: " + nbt.getString("BlockstateList"));
				break;
			}
			case 3: {
				tooltip.add("Transform");
				break;
			}
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public int getEntityLifespan(ItemStack itemStack, World world) {
		return 60;
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack) {
		return EnumRarity.EPIC;
	}

	@Override
	public @NotNull String getName() {
		return "wand";
	}
}
