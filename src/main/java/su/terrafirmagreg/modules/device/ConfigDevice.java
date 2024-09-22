package su.terrafirmagreg.modules.device;

import su.terrafirmagreg.modules.device.config.ConfigDeviceBlock;
import su.terrafirmagreg.modules.device.config.ConfigDeviceEntity;
import su.terrafirmagreg.modules.device.config.ConfigDeviceItem;
import su.terrafirmagreg.modules.device.config.ConfigDeviceMisc;

import net.minecraftforge.common.config.Config;

import com.cleanroommc.configanytime.ConfigAnytime;


import static su.terrafirmagreg.data.Constants.MOD_ID;
import static su.terrafirmagreg.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/module/Device")
public class ConfigDevice {


  @Config.Name("Block")
  @Config.Comment("Block setting")
  public static final ConfigDeviceBlock BLOCK = new ConfigDeviceBlock();

  @Config.Name("Item")
  @Config.Comment("Item setting")
  public static final ConfigDeviceItem ITEM = new ConfigDeviceItem();

  @Config.Name("Entity")
  @Config.Comment("Entity setting")
  public static final ConfigDeviceEntity ENTITY = new ConfigDeviceEntity();

  @Config.Name("Misc")
  @Config.Comment("Misc setting")
  public static final ConfigDeviceMisc MISC = new ConfigDeviceMisc();

  static {
    ConfigAnytime.register(ConfigDevice.class);
  }


}
