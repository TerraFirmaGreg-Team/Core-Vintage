package net.dries007.tfc.objects.items.metal;

import su.terrafirmagreg.modules.core.capabilities.forge.ForgeableHeatableHandler;
import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.ItemTFC;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Since TFC has Metal.ItemType we can't reuse {@link net.dries007.tfc.objects.items.metal.ItemMetal} directly
 */
public class ItemTechMetal extends ItemTFC implements ICapabilityMetal {

  private static final Map<Metal, EnumMap<ItemType, ItemTechMetal>> TABLE = new HashMap<>();
  protected final Metal metal;
  protected final ItemType type;

  public ItemTechMetal(Metal metal, ItemType type) {
    this.metal = metal;
    this.type = type;
    if (!TABLE.containsKey(metal)) {
      TABLE.put(metal, new EnumMap<>(ItemType.class));
    }
    TABLE.get(metal).put(type, this);
    setNoRepair();

  }

  @Nullable
  public static ItemTechMetal get(Metal metal, ItemTechMetal.ItemType type) {
    return TABLE.get(metal).get(type);
  }

  public ItemType getType() {
    return type;
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack itemStack) {
    switch (type) {
      case WIRE:
      case BOWL_MOUNT:
        return Size.LARGE;
      case GEAR:
      case RACKWHEEL:
        return Size.NORMAL;
      default:
        return Size.SMALL;
    }
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack itemStack) {
    switch (type) {
      case RACKWHEEL:
      case GEAR:
        return Weight.HEAVY;
      case GROOVE:
      case WIRE:
      case SLEEVE:
      case STRIP:
        return Weight.LIGHT;
      default:
        return Weight.MEDIUM;
    }
  }

  @Override
  @Nonnull
  public String getItemStackDisplayName(@Nonnull ItemStack stack) {
    //noinspection ConstantConditions
    String metalName = (new TextComponentTranslation("tfc.types.metal." + metal.getRegistryName().getPath().toLowerCase())).getFormattedText();
    return (new TextComponentTranslation("item.tfctech.metalitem." + type.name().toLowerCase() + ".name", metalName)).getFormattedText();
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new ForgeableHeatableHandler(nbt, metal.getSpecificHeat(), metal.getMeltTemp());
  }

  @Nonnull
  @Override
  public Metal getMetal(ItemStack itemStack) {
    return metal;
  }

  @Override
  public int getSmeltAmount(ItemStack itemStack) {
    return type.getSmeltAmount();
  }

  public enum ItemType {
    BOWL_MOUNT(144),
    DRAW_PLATE(144),
    GROOVE(72, false, ItemGroove::new),
    INDUCTOR(288),
    TONGS(144),
    STRIP(72),
    LONG_ROD(144),
    ROD(72),
    BOLT(36),
    SCREW(36),
    SLEEVE(144, true),
    RACKWHEEL_PIECE(144, true),
    RACKWHEEL(576),
    GEAR(576, false, ItemGear::new),
    WIRE(72, false, ItemWire::new);

    private final int smeltAmount;
    private final boolean hasMold;
    private final BiFunction<Metal, ItemType, Item> supplier;

    ItemType(int smeltAmount) {
      this(smeltAmount, false);
    }

    ItemType(int smeltAmount, boolean hasMold) {
      this(smeltAmount, hasMold, ItemTechMetal::new);
    }

    ItemType(int smeltAmount, boolean hasMold, @Nonnull BiFunction<Metal, ItemType, Item> supplier) {
      this.smeltAmount = smeltAmount;
      this.hasMold = hasMold;
      this.supplier = supplier;
    }

    public static Item create(Metal metal, ItemType type) {
      return type.supplier.apply(metal, type);
    }

    public int getSmeltAmount() {
      return smeltAmount;
    }

    public boolean hasMold() {
      return hasMold;
    }
  }
}
