package su.terrafirmagreg.api.registry;

import su.terrafirmagreg.api.module.IModule;
import su.terrafirmagreg.api.network.NetworkEntityIdSupplier;
import su.terrafirmagreg.api.registry.spi.IRegistryBiome;
import su.terrafirmagreg.api.registry.spi.IRegistryBlock;
import su.terrafirmagreg.api.registry.spi.IRegistryCommand;
import su.terrafirmagreg.api.registry.spi.IRegistryDataSerializer;
import su.terrafirmagreg.api.registry.spi.IRegistryEnchantment;
import su.terrafirmagreg.api.registry.spi.IRegistryEntity;
import su.terrafirmagreg.api.registry.spi.IRegistryFluid;
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
             IRegistryLootTable, IRegistryPotion, IRegistryPotionType, IRegistrySound, IRegistryWorldGenerator, IRegistryCommand, IRegistryFluid {

  private final IModule module;
  private final String moduleName;
  private final String modID;
  
  @Nullable
  private final CreativeTabs tab;
  private final Registry registry;
  private NetworkEntityIdSupplier networkEntityIdSupplier;

  public RegistryManager(@Nullable CreativeTabs tab, IModule module) {

    this.module = module;
    this.modID = module.getModID();
    this.moduleName = module.getName();
    this.tab = tab;

    this.registry = new Registry();
    MinecraftForge.EVENT_BUS.register(this.registry);
  }

  public void setNetworkEntityIdSupplier(NetworkEntityIdSupplier supplier) {
    if (this.networkEntityIdSupplier != null) {
      throw new IllegalStateException("Network entity id supplier has already been set");
    }

    this.networkEntityIdSupplier = supplier;
  }
}
