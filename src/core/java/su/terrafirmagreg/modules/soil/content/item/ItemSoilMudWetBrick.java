package su.terrafirmagreg.modules.soil.content.item;

import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.heat.capability.CapabilityProviderHeat;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.ISoilEntry;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public class ItemSoilMudWetBrick extends BaseItem implements ISoilEntry {

  protected final SoilType type;

  public ItemSoilMudWetBrick(SoilType type) {
    super(ItemSettings.of()
      .registryKey(type.getRegistryKey("mud_brick_wet"))
      .maxDamage(0)
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
    );

    this.type = type;
  }


  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    // Heat capability, as pottery needs to be able to be fired, or survive despite not having a heat capability
    return new CapabilityProviderHeat(nbt, 1.0f, 1599f);
  }
}
