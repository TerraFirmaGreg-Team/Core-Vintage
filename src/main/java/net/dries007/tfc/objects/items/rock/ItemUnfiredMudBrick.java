package net.dries007.tfc.objects.items.rock;

import net.dries007.tfc.util.OreDictionaryHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityProviderHeat;

import net.dries007.tfc.objects.items.ItemTFCF;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
public class ItemUnfiredMudBrick extends ItemTFCF implements IRockObject {

  private static final Map<ItemMud, ItemUnfiredMudBrick> MAP = new HashMap<>();
  private final Rock rock;

  public ItemUnfiredMudBrick(ItemMud mud, Rock rock) {
    this.rock = rock;
    if (MAP.put(mud, this) != null) {throw new IllegalStateException("There can only be one.");}
    setMaxDamage(0);
    net.dries007.tfc.util.OreDictionaryHelper.register(this, "mud", "unfired_brick");
    net.dries007.tfc.util.OreDictionaryHelper.register(this, "mud", "unfired_brick", rock);
    OreDictionaryHelper.register(this, "mud", "unfired_brick", rock.getRockCategory());
  }

  public static ItemUnfiredMudBrick get(Rock rock) {
    return MAP.get(ItemMud.get(rock));
  }

  public static ItemUnfiredMudBrick get(ItemMud mud) {
    return MAP.get(mud);
  }

  public static ItemStack get(ItemMud mud, int amount) {
    return new ItemStack(MAP.get(mud), amount);
  }

  public Rock getRock() {
    return rock;
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return Size.SMALL; // Stored everywhere
  }

  @Nonnull
  @Override
  public Weight getWeight(ItemStack stack) {
    return Weight.LIGHT; // Stacksize = 32
  }

  @Nonnull
  @Override
  public Rock getRock(ItemStack stack) {
    return rock;
  }

  @Nonnull
  @Override
  public RockCategory getRockCategory(ItemStack stack) {
    return rock.getRockCategory();
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    // Heat capability, as pottery needs to be able to be fired, or survive despite not having a heat capability
    return new CapabilityProviderHeat(nbt, 1.0f, 1599f);
  }
}
