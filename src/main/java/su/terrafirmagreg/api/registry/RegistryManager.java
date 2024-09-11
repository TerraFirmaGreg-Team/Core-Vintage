package su.terrafirmagreg.api.registry;

import su.terrafirmagreg.api.network.NetworkEntityIdSupplier;
import su.terrafirmagreg.api.registry.spi.IRegistryBiome;
import su.terrafirmagreg.api.registry.spi.IRegistryBlock;
import su.terrafirmagreg.api.registry.spi.IRegistryCommand;
import su.terrafirmagreg.api.registry.spi.IRegistryDataSerializer;
import su.terrafirmagreg.api.registry.spi.IRegistryEnchantment;
import su.terrafirmagreg.api.registry.spi.IRegistryEntity;
import su.terrafirmagreg.api.registry.spi.IRegistryItem;
import su.terrafirmagreg.api.registry.spi.IRegistryKeyBinding;
import su.terrafirmagreg.api.registry.spi.IRegistryLootTable;
import su.terrafirmagreg.api.registry.spi.IRegistryPotion;
import su.terrafirmagreg.api.registry.spi.IRegistryPotionType;
import su.terrafirmagreg.api.registry.spi.IRegistrySound;
import su.terrafirmagreg.api.registry.spi.IRegistryWorldGenerator;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public class RegistryManager
        implements IRegistryBlock, IRegistryBiome, IRegistryKeyBinding, IRegistryDataSerializer, IRegistryEnchantment, IRegistryEntity, IRegistryItem,
        IRegistryLootTable, IRegistryPotion, IRegistryPotionType, IRegistrySound, IRegistryWorldGenerator, IRegistryCommand {

  /**
   * The id of the mod the registry helper instance belongs to.
   */
  private final String modID;

  /**
   * The creative tab used by the mod. This can be null.
   */
  @Nullable
  private final CreativeTabs tab;

  /**
   * The auto registry for the helper.
   */
  private final Registry registry;

  private NetworkEntityIdSupplier networkEntityIdSupplier;

  /**
   * Constructs a new Registry. The modid for the helper is equal to that of the active mod container, and auto model registration is enabled.
   *
   * @param tab The tab for the registry helper.
   */
  public RegistryManager(@Nullable CreativeTabs tab, String modID) {

    this.modID = modID;
    this.tab = tab;

    this.registry = new Registry(this);
    MinecraftForge.EVENT_BUS.register(this.registry);
  }

  public void setNetworkEntityIdSupplier(NetworkEntityIdSupplier supplier) {

    if (this.networkEntityIdSupplier != null) {
      throw new IllegalStateException("Network entity id supplier has already been set");
    }

    this.networkEntityIdSupplier = supplier;
  }
}
