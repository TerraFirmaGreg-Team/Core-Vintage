package tfcflorae.objects.items;

import su.terrafirmagreg.api.data.ArmorMaterials;
import su.terrafirmagreg.modules.core.capabilities.damage.ICapabilityDamageResistance;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import javax.annotation.Nonnull;

public class ItemArmorTFCF extends ItemArmor implements IItemSize, ICapabilityDamageResistance {

  public static final int DEFAULT_COLOR = 14277081; // Light Gray
  public static final int BURLAP_COLOR = 12497798;
  public static final int COTTON_COLOR = 13027525;
  public static final int HEMP_COLOR = 8164702;
  public static final int LINEN_COLOR = 10200219;
  public static final int PINEAPPLE_LEATHER_COLOR = 13818026;
  public static final int SILK_COLOR = 15658734;
  public static final int SISAL_COLOR = 13942943;
  public static final int WOOL_COLOR = 14079702;
  public static final int YUCCA_COLOR = 10585698;

  public final ArmorMaterials armorMaterial;

  public ItemArmorTFCF(ArmorMaterials armorMaterial, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
    super(armorMaterial.getArmorMaterial(), renderIndexIn, equipmentSlotIn);
    this.armorMaterial = armorMaterial;
    setNoRepair();
  }

  @Override
  public float getCrushingModifier() {
    return armorMaterial.getCrushingModifier();
  }

  @Override
  public float getPiercingModifier() {
    return armorMaterial.getPiercingModifier();
  }

  @Override
  public float getSlashingModifier() {
    return armorMaterial.getSlashingModifier();
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack stack) {
    return Size.LARGE; // Stored in chests
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack stack) {
    return Weight.HEAVY; // Stacksize is already restricted to 1
  }

  @Override
  public boolean canStack(@Nonnull ItemStack stack) {
    return false;
  }

  /**
   * Determines if this armor will be rendered with the secondary 'overlay' texture. If this is true, the first texture will be rendered using a tint of the
   * color specified by getColor(ItemStack)
   *
   * @param stack The stack
   * @return true/false
   */
  @Override
  public boolean hasOverlay(ItemStack stack) {
    return true;
  }

  /**
   * Return whether the specified armor ItemStack has a color.
   */
  @Override
  public boolean hasColor(ItemStack stack) {
    if (this.armorMaterial != ArmorMaterials.PINEAPPLE_LEATHER ||
        this.armorMaterial != ArmorMaterials.BURLAP_CLOTH ||
        this.armorMaterial != ArmorMaterials.WOOL_CLOTH ||
        this.armorMaterial != ArmorMaterials.SILK_CLOTH ||
        this.armorMaterial != ArmorMaterials.SISAL_CLOTH ||
        this.armorMaterial != ArmorMaterials.COTTON_CLOTH ||
        this.armorMaterial != ArmorMaterials.LINEN_CLOTH ||
        this.armorMaterial != ArmorMaterials.HEMP_CLOTH ||
        this.armorMaterial != ArmorMaterials.YUCCA_CANVAS) {
      return false;
    } else {
      NBTTagCompound nbttagcompound = stack.getTagCompound();
      return nbttagcompound != null && nbttagcompound.hasKey("display", 10) && nbttagcompound.getCompoundTag("display").hasKey("color", 3);
    }
    	/*return !stack.hasTagCompound() ? 
            false : (!stack.getTagCompound().hasKey("display", 10) ? 
                false : stack.getTagCompound().getCompoundTag("display").hasKey("color", 3));*/
  }

  /**
   * Return the color for the specified armor ItemStack.
   */
  @Override
  public int getColor(ItemStack stack) {
    if (this.armorMaterial != ArmorMaterials.PINEAPPLE_LEATHER ||
        this.armorMaterial != ArmorMaterials.BURLAP_CLOTH ||
        this.armorMaterial != ArmorMaterials.WOOL_CLOTH ||
        this.armorMaterial != ArmorMaterials.SILK_CLOTH ||
        this.armorMaterial != ArmorMaterials.SISAL_CLOTH ||
        this.armorMaterial != ArmorMaterials.COTTON_CLOTH ||
        this.armorMaterial != ArmorMaterials.LINEN_CLOTH ||
        this.armorMaterial != ArmorMaterials.HEMP_CLOTH ||
        this.armorMaterial != ArmorMaterials.YUCCA_CANVAS) {
      return DEFAULT_COLOR;
    } else {
      NBTTagCompound nbttagcompound = stack.getTagCompound();
      if (nbttagcompound != null) {
        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
        if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3)) {
          return nbttagcompound1.getInteger("color");
        }
      }
      if (this.armorMaterial == ArmorMaterials.BURLAP_CLOTH) {
        return BURLAP_COLOR;
      }
      if (this.armorMaterial == ArmorMaterials.COTTON_CLOTH) {
        return COTTON_COLOR;
      }
      if (this.armorMaterial == ArmorMaterials.HEMP_CLOTH) {
        return HEMP_COLOR;
      }
      if (this.armorMaterial == ArmorMaterials.LINEN_CLOTH) {
        return LINEN_COLOR;
      }
      if (this.armorMaterial == ArmorMaterials.PINEAPPLE_LEATHER) {
        return PINEAPPLE_LEATHER_COLOR;
      }
      if (this.armorMaterial == ArmorMaterials.SILK_CLOTH) {
        return SILK_COLOR;
      }
      if (this.armorMaterial == ArmorMaterials.SISAL_CLOTH) {
        return SISAL_COLOR;
      }
      if (this.armorMaterial == ArmorMaterials.WOOL_CLOTH) {
        return WOOL_COLOR;
      }
      if (this.armorMaterial == ArmorMaterials.YUCCA_CANVAS) {
        return YUCCA_COLOR;
      }
    }
    return DEFAULT_COLOR;
  }

  /**
   * Remove the color from the specified armor ItemStack.
   */
  @Override
  public void removeColor(ItemStack stack) {
    if (this.armorMaterial == ArmorMaterials.PINEAPPLE_LEATHER ||
        this.armorMaterial == ArmorMaterials.BURLAP_CLOTH ||
        this.armorMaterial == ArmorMaterials.WOOL_CLOTH ||
        this.armorMaterial == ArmorMaterials.SILK_CLOTH ||
        this.armorMaterial == ArmorMaterials.SISAL_CLOTH ||
        this.armorMaterial == ArmorMaterials.COTTON_CLOTH ||
        this.armorMaterial == ArmorMaterials.LINEN_CLOTH ||
        this.armorMaterial == ArmorMaterials.HEMP_CLOTH ||
        this.armorMaterial == ArmorMaterials.YUCCA_CANVAS) {
      NBTTagCompound nbttagcompound = stack.getTagCompound();
      if (nbttagcompound != null) {
        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
        if (nbttagcompound1.hasKey("color")) {
          nbttagcompound1.removeTag("color");
        }
      }
    }
  }

  @Override
  public void setColor(ItemStack stack, int color) {
    NBTTagCompound nbttagcompound = stack.getTagCompound();
    if (nbttagcompound == null) {
      nbttagcompound = new NBTTagCompound();
      stack.setTagCompound(nbttagcompound);
    }
    NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
    if (!nbttagcompound.hasKey("display", 10)) {
      nbttagcompound.setTag("display", nbttagcompound1);
    }
    nbttagcompound1.setInteger("color", color);
  }
}
