package su.terrafirmagreg.modules.device.object.tile;

import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.feature.ambiental.modifiers.ModifierBase;
import su.terrafirmagreg.modules.core.feature.ambiental.modifiers.ModifierTile;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalTileProvider;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.device.ConfigDevice;
import su.terrafirmagreg.modules.device.ModuleDevice;
import su.terrafirmagreg.modules.device.client.gui.GuiIceBunker;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.object.block.BlockCellarDoor;
import su.terrafirmagreg.modules.device.object.block.BlockCellarShelf;
import su.terrafirmagreg.modules.device.object.block.BlockCellarWall;
import su.terrafirmagreg.modules.device.object.container.ContainerIceBunker;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockRedstoneLight;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.climate.ClimateTFC;
import pieman.caffeineaddon.ModConfig;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Optional;

import static su.terrafirmagreg.api.data.Reference.MODID_CELLARS;
import static su.terrafirmagreg.api.data.Properties.BoolProp.LIT;

public class TileIceBunker extends TileEntityLockableLoot
  implements ITickable, IAmbientalTileProvider, IProviderContainer<ContainerIceBunker, GuiIceBunker> {

  private final int[] entrance = new int[4];    //x, z of the first door + offsetX, offsetZ of the second door
  private final int[] size = new int[4];        //internal size, +z -x -z + x
  //NBT
  //private ItemStack[] inventory = null;
  private int coolantAmount = 0;
  private int coolantRate = 0;
  private int lastUpdate = 0;
  private boolean isComplete = false;
  private boolean hasAirlock = false;

  private float iceTemp = 0;
  private boolean dryIce = false;
  private boolean seaIce = false;
  private float lossMult = -1f;

  @Getter
  private float temperature = -1;    //-1000 cellar is not complete
  private byte error = 0;

  private int updateTickCounter = 1200;
  private NonNullList<ItemStack> chestContents = NonNullList.withSize(4, ItemStack.EMPTY);

  public TileIceBunker() {
  }

  public void getCellarInfo(EntityPlayer player) {
    if (ModConfig.isDebugging) {
      player.sendMessage(new TextComponentString("Temperature: " + temperature + " Coolant: " + coolantAmount));
      player.sendMessage(new TextComponentString("Check console for more information"));
      player.sendMessage(new TextComponentString("The current error number is: " + error));
      player.sendMessage(new TextComponentString("Is the cellar complete: " + isComplete));
      //updateCellar(true);
      return;
    }

    if (isComplete) {
      if (temperature < ModConfig.frozenMaxThreshold) {
        player.sendMessage(new TextComponentString("It is icy here"));
      } else if (temperature < ModConfig.icyMaxThreshold) {
        player.sendMessage(new TextComponentString("It is freezing here"));
      } else {
        player.sendMessage(new TextComponentString("The cellar is chilly"));
      }
    } else {
      player.sendMessage(new TextComponentString("The cellar is not complete or is not chilled yet"));
    }
  }

  @Override
  public void update() {
    if (world.isRemote) {
      return;
    }

    //Check cellar compliance once per 1200 ticks, check coolant and update containers once per 100 ticks
    if (updateTickCounter % 100 == 0) {

      if (updateTickCounter >= 1200) {
        updateCellar(true);
        updateTickCounter = 0;
        if (error != 0) {
          updateContainers();
        }
      } else {
        updateCellar(false);
      }

      if (error == 0) {
        updateContainers();
      }
    }
    updateTickCounter++;
  }

  private void updateCellar(boolean checkCompliance) {
    if (ModConfig.tempMonthAvg) {
      temperature = ClimateTFC.getMonthlyTemp(this.getPos());
    } else {
      temperature = ClimateTFC.getActualTemp(this.getPos());
    }

    if (checkCompliance) {
      this.isComplete = isStructureComplete();
    }

    if (isComplete) {
      float outsideTemp = ClimateTFC.getActualTemp(this.getPos());

      if (coolantAmount <= 0) {
        for (int slot = 3; slot >= 0; slot--) {
          if (!chestContents.get(slot).isEmpty()) {
            Item item = chestContents.get(slot).getItem();
            if (item == ItemsCore.PACKED_ICE_SHARD
                || Block.getBlockFromItem(item) == Blocks.PACKED_ICE) {
              coolantAmount = coolantAmount + ModConfig.packedIceCoolant;
              seaIce = false;
              dryIce = true;
            } else if (item == ItemsCore.SEA_ICE_SHARD
                       || Block.getBlockFromItem(item) == BlocksTFC.SEA_ICE) {
              coolantAmount = coolantAmount + ModConfig.seaIceCoolant;
              seaIce = true;
              dryIce = false;
            } else if (item == ItemsCore.ICE_SHARD || Block.getBlockFromItem(item) == Blocks.ICE) {
              coolantAmount = coolantAmount + ModConfig.iceCoolant;
              seaIce = false;
              dryIce = false;
            } else if (Block.getBlockFromItem(item) == Blocks.SNOW) {
              coolantAmount = coolantAmount + ModConfig.snowCoolant;
              seaIce = false;
              dryIce = false;
            } else if (item == Items.SNOWBALL) {
              coolantAmount = coolantAmount + ModConfig.snowBallCoolant;
              seaIce = false;
              dryIce = false;
            } else {
              continue;
            }

            lastUpdate = (int) Calendar.CALENDAR_TIME.getTotalDays();
            decrStackSize(slot, 1);
            break;
          }
        }
      }

      if (coolantAmount > 0) {
        temperature = ModConfig.iceHouseTemperature;
        if (lastUpdate < (int) Calendar.CALENDAR_TIME.getTotalDays()) {
          if (outsideTemp > -10) {    //magic
            int volume = (size[1] + size[3] + 1) * (size[0] + size[2] + 1);
            coolantAmount = coolantAmount -
                            (int) (ModConfig.coolantConsumptionMultiplier * (0.05 * volume * (1 + lossMult) * (
                              outsideTemp + volume + 2)));
            coolantRate = (int) (ModConfig.coolantConsumptionMultiplier * (0.05 * volume * (1
                                                                                            + lossMult) * (outsideTemp + volume + 2)));
          }
          lastUpdate++;
        }
      }

      float doorsLossMult = doorsLossMult();
      if (lossMult == -1) {
        lossMult = doorsLossMult;
      }

      if (lossMult != doorsLossMult) {
        if (lossMult > doorsLossMult) {
          lossMult = (lossMult - 0.01f) * 0.75f;
          lossMult = Math.max(doorsLossMult, lossMult);
        } else {
          lossMult = (lossMult + 0.01f) * 1.15f;    //0.05f because lossMult cant be 0
          lossMult = Math.min(doorsLossMult, lossMult);
        }
      }

      iceTemp = 0;
      if (ModConfig.specialIceTraits) {
        if (dryIce) {
          iceTemp = -78;
        } else if (seaIce) {
          iceTemp = -21;
        }
      }
      temperature = temperature + lossMult * (outsideTemp - iceTemp);
      if (temperature > outsideTemp) {
        temperature = outsideTemp;
      }
    }

    BlockUtils.notifyBlockUpdate(world, pos, 2);
  }

  public void updateContainers() {
    for (int y = 1; y <= 2; y++) {
      for (int z = -size[2]; z <= size[0]; z++) {
        for (int x = -size[1]; x <= size[3]; x++) {
          updateContainer(x, y, z);
        }
      }
    }
  }

  private boolean isStructureComplete() {
    entrance[0] = 0;
    entrance[1] = 0;
    entrance[2] = 0;
    entrance[3] = 0;

    hasAirlock = false;
    error = 0;

    int blockType = -1;

    //get size
    for (int direction = 0; direction < 4; direction++) {
      for (int distance = 1; distance < 6; distance++) {
        //max distance between an ice bunker and a wall is 3
        if (distance == 5) {
          if (ModConfig.isDebugging) {
            System.out.println("Cellar at " + pos.getX() + " " + pos.getY() + " " + pos.getZ()
                               + " can't find a wall on " + direction + " side");
          }
          error = 1;
          return false;
        }

        if (direction == 1) {
          blockType = getBlockType(-distance, 1, 0);
        } else if (direction == 3) {
          blockType = getBlockType(distance, 1, 0);
        } else if (direction == 2) {
          blockType = getBlockType(0, 1, -distance);
        } else if (direction == 0) {
          blockType = getBlockType(0, 1, distance);
        }

        if (blockType == 0 || blockType == 1) {
          size[direction] = distance - 1;
          break;
        }

        if (blockType == -1) {
          error = 2;
          return false;
        }
      }
    }

    //check blocks and set entrance
    for (int y = 0; y < 4; y++) {
      for (int x = -size[1] - 1; x <= size[3] + 1; x++) {
        for (int z = -size[2] - 1; z <= size[0] + 1; z++) {

          //Ice bunker
          if (y == 0 && x == 0 && z == 0) {
            continue;
          }

          blockType = getBlockType(x, y, z);

          //Blocks inside the cellar
          if (y == 1 || y == 2) {
            if (x >= -size[1] && x <= size[3]) {
              if (z >= -size[2] && z <= size[0]) {
                if (blockType == 2) {
                  continue;
                }
                error = 2;
                return false;
              }
            }
          }

          //Corners
          if ((x == -size[1] - 1 || x == size[3] + 1) && (z == -size[2] - 1 || z == size[0] + 1)) {
            if (blockType == 0) {
              continue;
            }
            error = 1;
            return false;
          }

          //Doors
          if (blockType == 1) {
            //upper part of the door
            if (entrance[0] == x && entrance[1] == z) {
              continue;
            }

            //1 entrance only!
            if (entrance[0] == 0 && entrance[1] == 0) {
              entrance[0] = x;
              entrance[1] = z;
              if (x == -size[1] - 1) {
                entrance[2] = -1;
              } else if (x == size[3] + 1) {
                entrance[2] = 1;
              } else if (z == -size[2] - 1) {
                entrance[3] = -1;
              } else if (z == size[0] + 1) {
                entrance[3] = 1;
              }

              continue;
            }

            if (ModConfig.isDebugging) {
              System.out.println("Cellar at " + pos.getX() + " " + pos.getY() + " " + pos.getZ()
                                 + " has too many doors");
            }
            error = 3;
            return false;
          }

          //Walls
          if (blockType == 0) {
            continue;
          }
          error = 1;
          return false;
        }
      }
    }

    if (entrance[0] == 0 && entrance[1] == 0) {
      if (ModConfig.isDebugging) {
        System.out.println("Cellar at " + pos.getX() + " " + pos.getY() + " " + pos.getZ()
                           + " has no doors");
      }
      error = 3;
      return false;
    }

    //check the entrance
    for (int y = 0; y < 4; y++) {
      for (int x = -MathHelper.abs(entrance[3]); x <= MathHelper.abs(entrance[3]); x++) {
        for (int z = -MathHelper.abs(entrance[2]); z <= MathHelper.abs(entrance[2]); z++) {

          blockType = getBlockType(x + entrance[0] + entrance[2], y, z + entrance[1] + entrance[3]);

          if (y == 1 || y == 2) {
            if (x == 0 && z == 0) {
              if (blockType == 1) {
                hasAirlock = true;
                continue;
              }

              hasAirlock = false;
              if (ModConfig.isDebugging) {
                System.out.println("Cellar at " + pos.getX() + " " + pos.getY() + " " + pos.getZ()
                                   + " doesn't has the second door, block there is " + blockType);
              }
            }
          }
          if (blockType == 0) {
            continue;
          }

          hasAirlock = false;
          if (ModConfig.isDebugging) {
            System.out.println("Door at " + pos.getX() + " " + pos.getY() + " " + pos.getZ()
                               + " doesn't surrounded by wall, block there is " + blockType);
          }
        }
      }
    }

    if (ModConfig.isDebugging) {
      System.out.println(
        "Cellar at " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + " is complete");
    }

    return true;
  }

  private float doorsLossMult() {
    float loss = 0;

    int posX = pos.getX() + entrance[0];
    int posY = pos.getY() + 1;
    int posZ = pos.getZ() + entrance[1];

    //1st door
    Block door = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
    if (door == BlocksDevice.CELLAR_DOOR && BlockDoor.isOpen(world,
                                                             new BlockPos(posX, posY, posZ))) {

      loss = 0.05f;
    }

    //2nd door
    //Does it even exist?
    if (!hasAirlock) {
      return loss * 8 + 0.3f;
    }

    door = world.getBlockState(new BlockPos(posX + entrance[2], posY, posZ + entrance[3])).getBlock();
    if (door == BlocksDevice.CELLAR_DOOR && BlockDoor.isOpen(world,
                                                             new BlockPos(posX + entrance[2], posY, posZ + entrance[3]))) {

      return loss * 13 + 0.05f;
    }

    return loss;
  }

  private void updateContainer(int x, int y, int z) {
    Block block = world.getBlockState(new BlockPos(getPos().getX() + x, getPos().getY() + y, getPos().getZ() + z)).getBlock();
    if (block instanceof BlockCellarShelf) {
      TileUtils.getTile(world, new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z), TileCellarShelf.class)
               .ifPresent(tile -> tile.updateShelf(temperature));
    }
  }

  private int getBlockType(int x, int y, int z) {
    Block block = world.getBlockState(new BlockPos(getPos().getX() + x, getPos().getY() + y, getPos().getZ() + z)).getBlock();
    IBlockState blockState = block.getBlockState().getBaseState();
    if (block instanceof BlockCellarWall) {
      return 0;
    } else if (block instanceof BlockCellarDoor) {
      return 1;
    } else if (block instanceof BlockCellarShelf || block instanceof BlockWallSign ||
               block instanceof BlockStandingSign || block instanceof BlockTorch ||
               block instanceof BlockRedstoneTorch || blockState.getProperties().containsKey(LIT) ||
               block instanceof BlockRedstoneLight ||
               world.isAirBlock(new BlockPos(getPos().getX() + x, getPos().getY() + y, getPos().getZ() + z))) {
      return 2;
    }

    if (ConfigDevice.MISC.DEBUG.enable) {
      ModuleDevice.LOGGER.debug("Incorrect cellar block at {} {} {} {}", x, y, z, block.getRegistryName());
    }

    return -1;
  }

  @Override
  public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
    return true;
  }

  @Override
  public void openInventory(@NotNull EntityPlayer entityPlayer) {
    this.world.notifyNeighborsOfStateChange(pos, this.getBlockType(), false);
  }

  @Override
  public void closeInventory(@NotNull EntityPlayer player) {
    this.world.notifyNeighborsOfStateChange(pos, this.getBlockType(), false);
  }

  @Override
  public boolean isItemValidForSlot(int slot, ItemStack stack) {
    return true;
  }

  @Override
  protected @NotNull NonNullList<ItemStack> getItems() {
    return this.chestContents;
  }

  @Nullable
  @Override
  public SPacketUpdateTileEntity getUpdatePacket() {
    NBTTagCompound nbt = new NBTTagCompound();
    writeToNBT(nbt);
    writeSyncData(nbt);
    return new SPacketUpdateTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), 1, nbt);
  }

  private void writeSyncData(NBTTagCompound nbt) {
    float temp = (error == 0) ? temperature : (-1 * error * 1000);
    NBTUtils.setGenericNBTValue(nbt, "Temperature", temp);
  }

  @Override
  public void onDataPacket(@NotNull NetworkManager net, SPacketUpdateTileEntity packet) {
    readFromNBT(packet.getNbtCompound());
    readSyncData(packet.getNbtCompound());
  }

  @Override
  public void readFromNBT(@NotNull NBTTagCompound nbt) {
    super.readFromNBT(nbt);

    this.chestContents = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);

    if (!this.checkLootAndRead(nbt)) {
      ItemStackHelper.loadAllItems(nbt, chestContents);
    }
    if (nbt.hasKey("CustomName", 8)) {
      this.customName = nbt.getString("CustomName");
    }

    lastUpdate = nbt.getInteger("LastUpdate");
    coolantAmount = nbt.getInteger("CoolantAmount");
    coolantRate = nbt.getInteger("CoolantRate");
    error = nbt.getByte("ErrorCode");
    isComplete = nbt.getBoolean("isCompliant");
    iceTemp = nbt.getFloat("iceTemp");
    dryIce = nbt.getBoolean("dryIce");
    seaIce = nbt.getBoolean("seaIce");
  }

  private void readSyncData(NBTTagCompound nbt) {
    temperature = nbt.getFloat("Temperature");
  }

  @Override
  public int getSizeInventory() {
    return 4;
  }

  @Override
  public boolean isEmpty() {
    for (ItemStack stack : this.chestContents) {
      if (stack.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getInventoryStackLimit() {
    return 64;
  }

  @Override
  public @NotNull NBTTagCompound writeToNBT(@NotNull NBTTagCompound nbt) {
    super.writeToNBT(nbt);

    if (!this.checkLootAndRead(nbt)) {
      ItemStackHelper.saveAllItems(nbt, chestContents);
    }
    if (nbt.hasKey("CustomName", 8)) {
      NBTUtils.setGenericNBTValue(nbt, "CustomName", this.customName);
    }

    NBTUtils.setGenericNBTValue(nbt, "LastUpdate", lastUpdate);
    NBTUtils.setGenericNBTValue(nbt, "CoolantAmount", coolantAmount);
    NBTUtils.setGenericNBTValue(nbt, "CoolantRate", coolantRate);
    NBTUtils.setGenericNBTValue(nbt, "ErrorCode", error);
    NBTUtils.setGenericNBTValue(nbt, "isCompliant", isComplete);
    NBTUtils.setGenericNBTValue(nbt, "iceTemp", iceTemp);
    NBTUtils.setGenericNBTValue(nbt, "dryIce", dryIce);
    NBTUtils.setGenericNBTValue(nbt, "seaIce", seaIce);

    return nbt;
  }

  @Override
  public String getName() {
    return this.hasCustomName() ? this.customName : "container.ice_bunker";
  }

  @Override
  public @NotNull Container createContainer(@NotNull InventoryPlayer inventoryPlayer,
                                            @NotNull EntityPlayer entityPlayer) {
    return new ContainerIceBunker(inventoryPlayer, this, entityPlayer);
  }

  @Override
  public @NotNull String getGuiID() {
    return MODID_CELLARS + "ice_bunker";
  }

  public int getCoolant() {
    return coolantAmount;
  }

  public float getCoolantRate() {
    return coolantRate / 100.0F;
  }

  @Override
  public Optional<ModifierBase> getModifier(EntityPlayer player, TileEntity tile) {

    float change = 0.0f;
    float potency = 0.0f;

    if (isComplete) {
      if (temperature < 10) {
        change = -2f * (12 - temperature);
        potency = -0.5f;
      }

      if (ModifierTile.hasProtection(player)) {
        change = change * 0.3F;
      }
    }

    return ModifierBase.defined(this.getBlockType().getRegistryName().getPath(), change, potency);
  }

  @Override
  public ContainerIceBunker getContainer(InventoryPlayer inventoryPlayer, World world,
                                         IBlockState state, BlockPos pos) {
    return new ContainerIceBunker(inventoryPlayer, this, inventoryPlayer.player);
  }

  @Override
  public GuiIceBunker getGuiContainer(InventoryPlayer inventoryPlayer, World world,
                                      IBlockState state, BlockPos pos) {
    return new GuiIceBunker(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer,
                            this);
  }


}
