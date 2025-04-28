package su.terrafirmagreg.modules.device.object.tile;

import su.terrafirmagreg.api.base.object.tile.spi.BaseTileTickableInventory;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.registry.api.provider.IProviderContainer;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierTile;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderTile;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.climate.Climate;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.core.object.item.ItemIceShard;
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
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import net.dries007.tfc.objects.blocks.BlocksTFC;

import javax.annotation.Nonnull;
import java.util.Optional;

import static su.terrafirmagreg.api.data.Properties.BoolProp.LIT;

public class TileIceBunker extends BaseTileTickableInventory
  implements IAmbientalProviderTile, IProviderContainer<ContainerIceBunker, GuiIceBunker> {

  private int coolantAmount = 0;
  private int coolantRate = 0;
  private int lastUpdate = 0;

  private int[] entrance = new int[4];  //x, z of the first door + offsetX, offsetZ of the second door
  private int[] size = new int[4];    //internal size, +z -x -z + x
  private boolean isComplete = false;
  private boolean hasAirlock = false;


  private float iceTemp = 0;
  private boolean dryIce = false;
  private boolean seaIce = false;
  private float lossMult = -1f;

  private float temperature = -1;  //-1000 cellar is not complete
  private byte error = 0;

  private int updateTickCounter = 1200;

  public TileIceBunker() {
    super(4);
  }


  @Override
  public void update() {
    super.update();
    if (world.isRemote) {
      return;
    }

    //Check cellar compliance once per 1200 ticks, check coolant and update containers once per 100 ticks
    if (updateTickCounter % 100 == 0) {

      if (updateTickCounter >= 100) {
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
    if (ConfigDevice.BLOCK.ICE_BUNKER.tempMonthAvg) {
      temperature = Climate.getMonthlyTemp(this.getPos());
    } else {
      temperature = Climate.getActualTemp(this.getPos());
    }

    if (checkCompliance) {
      this.isComplete = isStructureComplete();
    }

    if (isComplete) {
      float outsideTemp = Climate.getActualTemp(this.getPos());

      if (coolantAmount <= 0) {
        for (int slot = 3; slot >= 0; slot--) {
          var stackInSlot = inventory.getStackInSlot(slot);
          if (!stackInSlot.isEmpty()) {
            var item = stackInSlot.getItem();

            if (Block.getBlockFromItem(item) == Blocks.PACKED_ICE) {
              coolantAmount = coolantAmount + ConfigDevice.BLOCK.ICE_BUNKER.packedIceCoolant;
              seaIce = false;
              dryIce = true;
            } else if (Block.getBlockFromItem(item) == BlocksTFC.SEA_ICE) {
              coolantAmount = coolantAmount + ConfigDevice.BLOCK.ICE_BUNKER.seaIceCoolant;
              seaIce = true;
              dryIce = false;
            } else if (item == ItemsCore.ICE_SHARD.get() || Block.getBlockFromItem(item) == Blocks.ICE) {
              coolantAmount = coolantAmount + ConfigDevice.BLOCK.ICE_BUNKER.iceCoolant;
              seaIce = false;
              dryIce = false;
            } else if (Block.getBlockFromItem(item) == Blocks.SNOW) {
              coolantAmount = coolantAmount + ConfigDevice.BLOCK.ICE_BUNKER.snowCoolant;
              seaIce = false;
              dryIce = false;
            } else if (item == Items.SNOWBALL) {
              coolantAmount = coolantAmount + ConfigDevice.BLOCK.ICE_BUNKER.snowBallCoolant;
              seaIce = false;
              dryIce = false;
            } else {
              continue;
            }

            lastUpdate = (int) Calendar.CALENDAR_TIME.getTotalDays();
            this.inventory.extractItem(slot, 1, false);
            break;
          }
        }
      }

      if (coolantAmount > 0) {
        temperature = ConfigDevice.BLOCK.ICE_BUNKER.iceHouseTemperature;
        if (lastUpdate < (int) Calendar.CALENDAR_TIME.getTotalDays()) {
          if (outsideTemp > -10) {    //magic
            int volume = (size[1] + size[3] + 1) * (size[0] + size[2] + 1);
            coolantAmount = coolantAmount - (int) (ConfigDevice.BLOCK.ICE_BUNKER.coolantConsumptionMultiplier * (0.05 * volume * (1 + lossMult) * (outsideTemp + volume + 2)));
            coolantRate = (int) (ConfigDevice.BLOCK.ICE_BUNKER.coolantConsumptionMultiplier * (0.05 * volume * (1 + lossMult) * (outsideTemp + volume + 2)));
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
      if (ConfigDevice.BLOCK.ICE_BUNKER.specialIceTraits) {
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

  private float doorsLossMult() {
    float loss = 0;

    int posX = pos.getX() + entrance[0];
    int posY = pos.getY() + 1;
    int posZ = pos.getZ() + entrance[1];

    //1st door
    var doorPos1 = new BlockPos(posX, posY, posZ);
    Block door = world.getBlockState(doorPos1).getBlock();
    if (door == BlocksDevice.CELLAR_DOOR.get() && BlockDoor.isOpen(world, doorPos1)) {

      loss = 0.05f;
    }

    //2nd door
    //Does it even exist?
    if (!hasAirlock) {
      return loss * 8 + 0.3f;
    }

    var doorPos2 = new BlockPos(posX + entrance[2], posY, posZ + entrance[3]);
    door = world.getBlockState(doorPos2).getBlock();
    if (door == BlocksDevice.CELLAR_DOOR.get() && BlockDoor.isOpen(world, doorPos2)) {

      return loss * 13 + 0.05f;
    }

    return loss;
  }

  private boolean isStructureComplete() {
    if (getPos().getY() > world.getSeaLevel()) {
      return false;
    }

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
          if (ConfigDevice.BLOCK.ICE_BUNKER.debug) {
            ModuleDevice.LOGGER.info("Cellar at {} {} {} can't find a wall on {} side", pos.getX(), pos.getY(), pos.getZ(), direction);
          }
          error = 1;
          return false;
        }

        blockType = switch (direction) {
          case 0 -> getBlockType(0, 1, distance);
          case 1 -> getBlockType(-distance, 1, 0);
          case 2 -> getBlockType(0, 1, -distance);
          case 3 -> getBlockType(distance, 1, 0);
          default -> blockType;
        };

        switch (blockType) {
          case 0:
          case 1:
            size[direction] = distance - 1;
            break;
          case -1:
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
            if (ConfigDevice.BLOCK.ICE_BUNKER.debug) {
              ModuleDevice.LOGGER.info("Cellar at {} {} {} has too many doors.", pos.getX(), pos.getY(), pos.getZ());
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
      if (ConfigDevice.BLOCK.ICE_BUNKER.debug) {
        ModuleDevice.LOGGER.info("Cellar at {} {} {}  has no doors.", pos.getX(), pos.getY(), pos.getZ());
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
              if (ConfigDevice.BLOCK.ICE_BUNKER.debug) {
                ModuleDevice.LOGGER.info("Cellar at {} {} {} doesn't has the second door, block there is {}", pos.getX(), pos.getY(), pos.getZ(), blockType);
              }
            }
          }
          if (blockType == 0) {
            continue;
          }

          hasAirlock = false;
          if (ConfigDevice.BLOCK.ICE_BUNKER.debug) {
            ModuleDevice.LOGGER.info("Door at {} {} {} doesn't surrounded by wall, block there is {}", pos.getX(), pos.getY(), pos.getZ(), blockType);
          }
        }
      }
    }

    if (ConfigDevice.BLOCK.ICE_BUNKER.debug) {
      ModuleDevice.LOGGER.info("Cellar at {} {} {} is complete", pos.getX(), pos.getY(), pos.getZ());
    }
    return true;
  }

  private int getBlockType(int x, int y, int z) {
    var pos = new BlockPos(getPos().getX() + x, getPos().getY() + y, getPos().getZ() + z);
    Block block = world.getBlockState(pos).getBlock();
    IBlockState blockState = block.getBlockState().getBaseState();
    if (block instanceof BlockCellarWall) {
      return 0;
    } else if (block instanceof BlockCellarDoor) {
      return 1;
    } else if (block instanceof BlockCellarShelf || block instanceof BlockWallSign || block instanceof BlockStandingSign
               ||
               block instanceof BlockTorch || block instanceof BlockRedstoneTorch || blockState.getProperties()
                 .containsKey(LIT) ||
               block instanceof BlockRedstoneLight || world.isAirBlock(pos)) {
      return 2;
    }

    if (ConfigDevice.BLOCK.ICE_BUNKER.debug) {
      ModuleDevice.LOGGER.debug("Incorrect cellar block at {} {} {} {}", x, y, z, block.getRegistryName());
    }

    return -1;
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

  public void onBreakBlock(World world, BlockPos pos, IBlockState state) {
    super.onBreakBlock(world, pos, state);
    temperature = -1000;
    updateContainers();
  }

  private void updateContainer(int x, int y, int z) {
    BlockPos pos = new BlockPos(getPos().getX() + x, getPos().getY() + y, getPos().getZ() + z);
    Block block = world.getBlockState(pos).getBlock();
    if (block instanceof BlockCellarShelf) {
      TileUtils.getTile(world, pos, TileCellarShelf.class)
        .ifPresent(tile -> tile.updateShelf(temperature));
    }
  }

  public float getTemperature() {
    return temperature;
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    super.readFromNBT(nbt);

    lastUpdate = nbt.getInteger("LastUpdate");
    coolantAmount = nbt.getInteger("CoolantAmount");
    coolantRate = nbt.getInteger("CoolantRate");
    error = nbt.getByte("ErrorCode");
    isComplete = nbt.getBoolean("IsCompliant");
    iceTemp = nbt.getFloat("IceTemp");
    dryIce = nbt.getBoolean("DryIce");
    seaIce = nbt.getBoolean("SeaIce");
    temperature = nbt.getFloat("Temperature");
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    super.writeToNBT(nbt);

    NBTUtils.setGenericNBTValue(nbt, "LastUpdate", lastUpdate);
    NBTUtils.setGenericNBTValue(nbt, "CoolantAmount", coolantAmount);
    NBTUtils.setGenericNBTValue(nbt, "CoolantRate", coolantRate);
    NBTUtils.setGenericNBTValue(nbt, "ErrorCode", error);
    NBTUtils.setGenericNBTValue(nbt, "IsCompliant", isComplete);
    NBTUtils.setGenericNBTValue(nbt, "IceTemp", iceTemp);
    NBTUtils.setGenericNBTValue(nbt, "DryIce", dryIce);
    NBTUtils.setGenericNBTValue(nbt, "SeaIce", seaIce);
    NBTUtils.setGenericNBTValue(nbt, "Temperature", temperature);

    return nbt;
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
    var item = stack.getItem();

    return item instanceof ItemIceShard || item instanceof ItemSnowball;
  }

  public int getCoolant() {
    return coolantAmount;
  }

  public float getCoolantRate() {
    return coolantRate / 100.0F;
  }

  @Override
  public Optional<ModifierTile> getModifier(EntityPlayer player, TileEntity tile) {

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

    return ModifierTile.defined(this.getBlockType().getRegistryName().getPath(), change, potency);
  }

  @Override
  public ContainerIceBunker getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new ContainerIceBunker(inventoryPlayer, this);
  }

  @Override
  public GuiIceBunker getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new GuiIceBunker(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, this);
  }


}
