package su.terrafirmagreg.modules.core.object.item;


import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.core.capabilities.ambiental.CapabilityAmbiental;

import net.minecraft.block.Block;
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
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import com.google.common.collect.ImmutableList;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

import static su.terrafirmagreg.api.data.enums.Colors.RED;


public class ItemDebug extends BaseItem {

  private static final String TAG_MODE = "Mode";
  private static final String TAG_BLOCKSTATE = "Blockstate";
  private static final String TAG_NBT = "NBT";
  private static final String TAG_BLOCKSTATE_LIST = "BlockstateList";

  public ItemDebug() {

    getSettings()
      .registryKey("wand")
      .rarity(EnumRarity.EPIC)
      .maxCount(1);

    setNoRepair();
    setFull3D();
  }

  public static void changeMode(EntityPlayer player) {
    ItemStack stack = player.getHeldItemMainhand();
    NBTTagCompound nbt = stack.getTagCompound();
    if (nbt == null) {
      nbt = NBTUtils.resetNBT(stack);
    }
    int mode = nbt.getInteger(TAG_MODE);
    int newMode = (mode >= Mode.values().length - 1) ? 0 : mode + 1;
    NBTUtils.setGenericNBTValue(nbt, TAG_MODE, newMode);
    switch (Mode.values()[newMode]) {
      case BLOCKSTATE: {
        player.sendStatusMessage(new TextComponentString(RED + Mode.BLOCKSTATE.getText()), true);
        break;
      }
      case NBT: {
        player.sendStatusMessage(new TextComponentString(RED + Mode.NBT.getText()), true);
        break;
      }
      case BLOCKSTATE_LIST: {
        player.sendStatusMessage(new TextComponentString(RED + Mode.BLOCKSTATE_LIST.getText()), true);
        break;
      }
      case TRANSFORM: {
        player.sendStatusMessage(new TextComponentString(RED + Mode.TRANSFORM.getText()), true);
        break;
      }
      case TEMPERATURE_CAPABILITY: {
        player.sendStatusMessage(new TextComponentString(RED + Mode.TEMPERATURE_CAPABILITY.getText()), true);
        break;
      }
      case BLOCK: {
        player.sendStatusMessage(new TextComponentString(RED + Mode.BLOCK.getText()), true);
        break;
      }
    }
  }

  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (world.isRemote) {
      return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }
    ItemStack stack = player.getHeldItemMainhand();
    NBTTagCompound nbt = stack.getTagCompound();

    if (nbt == null) {
      nbt = NBTUtils.resetNBT(stack);
    }
    int mode = nbt.getInteger(TAG_MODE);
    switch (Mode.values()[mode]) {
      case BLOCKSTATE: {
        var blockstate = world.getBlockState(pos).getBlock().toString();
        NBTUtils.setGenericNBTValue(nbt, TAG_BLOCKSTATE, blockstate);
        player.sendMessage(new TextComponentString(Mode.BLOCKSTATE.getText(blockstate)));
        break;
      }
      case NBT: {
        var optionalTile = TileUtils.getTile(world, pos);
        if (optionalTile.isPresent()) {
          var tile = optionalTile.get();
          var nbtTag = tile.writeToNBT(new NBTTagCompound()).toString();
          NBTUtils.setGenericNBTValue(nbt, TAG_NBT, nbtTag);
          player.sendMessage(new TextComponentString(Mode.NBT.getText(nbtTag)));
        }
        break;
      }
      case BLOCKSTATE_LIST: {
        var blockstateList = world.getBlockState(pos).getBlock().getBlockState().getValidStates().toString();
        NBTUtils.setGenericNBTValue(nbt, TAG_BLOCKSTATE_LIST, blockstateList);
        player.sendMessage(new TextComponentString(Mode.BLOCKSTATE_LIST.getText(blockstateList)));
        break;
      }
      case TRANSFORM: {
        ImmutableList<IBlockState> list = world.getBlockState(pos).getBlock().getBlockState().getValidStates();
        int index = list.indexOf(world.getBlockState(pos));
        int newState = (index + 1 >= list.size()) ? 0 : index + 1;
        world.setBlockState(pos, list.get(newState));
        player.sendMessage(
          new TextComponentString(Mode.TRANSFORM.getText(list.get(newState).toString())));
        break;
      }
      case TEMPERATURE_CAPABILITY: {
        player.sendMessage(new TextComponentString(
          Mode.TEMPERATURE_CAPABILITY.getText() + "\n" + CapabilityAmbiental.get(player).toString()));
        break;
      }
      case BLOCK: {
        try {
          Block block = world.getBlockState(pos).getBlock();
          try {
            block.getClass().getMethod("debug").invoke(block);
          } catch (Exception t) { /* Nothing Burger */ }

          // Tile Entity
          TileEntity tile = world.getTileEntity(pos);
          if (tile != null) {
            try {
              tile.getClass().getMethod("debug").invoke(tile);
            } catch (Exception t) {
              ModuleCore.LOGGER.info("No debug method found to invoke on {}", tile);
            }

            ModuleCore.LOGGER.info("Tile Data: {}", tile.serializeNBT());

            IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (inventory != null) {
              ModuleCore.LOGGER.info("Found item handler: {}", inventory);
            }

            IFluidHandler fluids = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
            if (fluids != null) {
              ModuleCore.LOGGER.info("Found fluid handler: {}", fluids);
            }
          }
        } catch (Exception t) { /* Nothing Burger */ }
        return EnumActionResult.SUCCESS;
      }
    }
    return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    if (world.isRemote) {
      return ActionResult.newResult(EnumActionResult.FAIL, player.getHeldItem(hand));
    }
    if (player.isSneaking() &&
        world.getBlockState(Objects.requireNonNull(player.rayTrace(10, 1)).getBlockPos()).getBlock()
          .isAir(
            world.getBlockState(Objects.requireNonNull(player.rayTrace(10, 1)).getBlockPos()),
            world,
            Objects.requireNonNull(player.rayTrace(5, 1)).getBlockPos())) {
      changeMode(player);
    }

    return super.onItemRightClick(world, player, hand);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    if (worldIn == null) {
      return;
    }
    NBTTagCompound nbt = stack.getTagCompound();
    if (nbt == null) {
      return;
    }
    int mode = nbt.getInteger(TAG_MODE);
    switch (Mode.values()[mode]) {
      case BLOCKSTATE: {
        tooltip.add(Mode.BLOCKSTATE.getText(nbt.getString(TAG_BLOCKSTATE)));
        break;
      }
      case NBT: {
        tooltip.add(Mode.NBT.getText(nbt.getString(TAG_NBT)));
        break;
      }
      case BLOCKSTATE_LIST: {
        tooltip.add(Mode.BLOCKSTATE_LIST.getText(nbt.getString(TAG_BLOCKSTATE_LIST)));
        break;
      }
      case TRANSFORM: {
        tooltip.add(Mode.TRANSFORM.getText());
        break;
      }
      case TEMPERATURE_CAPABILITY: {
        tooltip.add(Mode.TEMPERATURE_CAPABILITY.getText());
        break;
      }
      case BLOCK: {
        tooltip.add(Mode.BLOCK.getText());
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
  public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
    return false;
  }

  @Getter
  enum Mode {
    BLOCKSTATE("Blockstate "),
    NBT("NBT"),
    BLOCKSTATE_LIST("Blockstate List "),
    TRANSFORM("Transform "),
    TEMPERATURE_CAPABILITY("Temperature Capability "),
    BLOCK("Block ");

    private final String text;

    Mode(String text) {
      this.text = text;

    }

    public String getText(String suffix) {
      return text + suffix;
    }
  }

}
