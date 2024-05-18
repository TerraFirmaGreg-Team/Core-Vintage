package su.terrafirmagreg.modules.core.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.modules.core.api.capabilities.temperature.CapabilityTemperature;

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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import com.google.common.collect.ImmutableList;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import static net.minecraft.util.text.TextFormatting.GOLD;

public class ItemDebug extends BaseItem {

    public ItemDebug() {

        setNoRepair();
        setFull3D();
        getSettings()
                .registryKey("core/wand")
                .maxCount(1)
                .rarity(EnumRarity.EPIC);
    }

    public static void changeMode(EntityPlayer player) {
        ItemStack stack = player.getHeldItemMainhand();
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) NBTUtils.resetNBT(stack);
        assert nbt != null;
        int mode = nbt.getInteger("mode");
        int newMode = (mode > 4) ? 0 : mode + 1;
        NBTUtils.setGenericNBTValue(nbt, "mode", newMode);
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
            case 4: {
                player.sendStatusMessage(new TextComponentString(GOLD + "Temperature Capability"), true);
                break;
            }
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        ItemStack stack = player.getHeldItemMainhand();
        NBTTagCompound nbt = stack.getTagCompound();

        if (nbt == null) NBTUtils.resetNBT(stack);
        int mode = stack.getTagCompound().getInteger("mode");
        switch (mode) {
            case 0: {
                var blockstate = world.getBlockState(pos).getBlock();
                NBTUtils.setGenericNBTValue(nbt, "blockstate", blockstate);
                player.sendMessage(new TextComponentString("Blockstate: " + blockstate));
                break;
            }
            case 1: {
                TileEntity tile = world.getTileEntity(pos);
                if (tile == null) break;
                var nbtTag = tile.writeToNBT(new NBTTagCompound());
                NBTUtils.setGenericNBTValue(nbt, "nbtTag", nbtTag);
                player.sendMessage(new TextComponentString("NBTTagCompound: " + nbtTag));
                break;
            }
            case 2: {
                var blockstateList = world.getBlockState(pos).getBlock().getBlockState().getValidStates();
                NBTUtils.setGenericNBTValue(nbt, "blockstateList", blockstateList);
                player.sendMessage(new TextComponentString("Blockstate List: " + blockstateList));
                break;
            }
            case 3: {
                ImmutableList<IBlockState> list = world.getBlockState(pos).getBlock().getBlockState().getValidStates();
                int index = list.indexOf(world.getBlockState(pos));
                int newState = (index + 1 >= list.size()) ? 0 : index + 1;
                world.setBlockState(pos, list.get(newState));
                player.sendMessage(new TextComponentString("New Blockstate: " + list.get(newState).toString()));
                break;
            }
            case 4: {
                player.sendMessage(new TextComponentString("Temperature Capability: \n" + CapabilityTemperature.get(player).toString()));
                break;
            }
        }
        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (world.isRemote) return ActionResult.newResult(EnumActionResult.FAIL, player.getHeldItem(hand));
        if (player.isSneaking() &&
                world.getBlockState(Objects.requireNonNull(player.rayTrace(10, 1)).getBlockPos()).getBlock()
                        .isAir(
                                world.getBlockState(Objects.requireNonNull(player.rayTrace(10, 1)).getBlockPos()),
                                world,
                                Objects.requireNonNull(player.rayTrace(5, 1)).getBlockPos()))
            changeMode(player);

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
                tooltip.add("Blockstate: " + nbt.getString("blockstate"));
                break;
            }
            case 1: {
                tooltip.add("NBTTagCompound: " + nbt.getString("nbtTag"));
                break;
            }
            case 2: {
                tooltip.add("Blockstate List: " + nbt.getString("blockstateList"));
                break;
            }
            case 3: {
                tooltip.add("Transform");
                break;
            }
            case 4: {
                tooltip.add("TemperatureCapability");
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

}
