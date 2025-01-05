/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package su.terrafirmagreg.api.data;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

import lombok.Getter;

/**
 * This is an extension enum for the vanilla's ArmorMaterials. We register a new material in vanilla and bind crushing, slashing and piercing resistances.
 */
@Getter
public class ArmorMaterials {
  //todo tweak all these values
  //currently, modifiers = classic / 40. Should give about 45% resistance(damage = 55%) to red/blue steel before letting vanilla mechanic do the rest.
  //red/blue steel has the same base resistance(eg: the damage you take on generic/the "base" reduction using vanilla mechanics) as vanilla's diamond armor
  //black and normal steel has the same as vanilla's iron armor
  //wrought iron is equivalent to chain mail
  //copper is a little better than vanilla's leather and bronzes are in between wrought iron and copper.

  //LEATHER?
  public static final ArmorMaterials QUIVER;
  public static final ArmorMaterials COPPER;
  public static final ArmorMaterials BISMUTH_BRONZE;
  public static final ArmorMaterials BLACK_BRONZE;
  public static final ArmorMaterials BRONZE;
  public static final ArmorMaterials WROUGHT_IRON;
  public static final ArmorMaterials STEEL;
  public static final ArmorMaterials BLACK_STEEL;
  public static final ArmorMaterials BLUE_STEEL;
  public static final ArmorMaterials RED_STEEL;
  public static final ArmorMaterials PINEAPPLE_LEATHER;
  public static final ArmorMaterials BURLAP_CLOTH;
  public static final ArmorMaterials WOOL_CLOTH;
  public static final ArmorMaterials SILK_CLOTH;
  public static final ArmorMaterials SISAL_CLOTH;
  public static final ArmorMaterials COTTON_CLOTH;
  public static final ArmorMaterials LINEN_CLOTH;
  public static final ArmorMaterials HEMP_CLOTH;
  public static final ArmorMaterials YUCCA_CANVAS;
  public static final ArmorMaterials SNOW_SHOES;
  public static final ArmorMaterials HIKING_BOOTS;

  static {
    QUIVER = new ArmorMaterials(EnumHelper.addArmorMaterial("quiver", ModUtils.id("quiver"), 10, new int[]{0, 0, 0, 0}, 9,
      SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 0, 0, 0f
    );

    COPPER = new ArmorMaterials(EnumHelper.addArmorMaterial("copper", ModUtils.id("copper"), 14, new int[]{1, 3, 4, 1}, 9,
      SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 10, 10, 6.25f
    );

    BISMUTH_BRONZE = new ArmorMaterials(EnumHelper.addArmorMaterial("bismuth_bronze", ModUtils.id("bismuth_bronze"), 21, new int[]{1, 4, 4, 1}, 9,
      SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 15, 10, 8.25f
    );

    BLACK_BRONZE = new ArmorMaterials(EnumHelper.addArmorMaterial("black_bronze", ModUtils.id("black_bronze"), 21, new int[]{1, 4, 4, 1}, 9,
      SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 10, 15, 8.25f
    );

    BRONZE = new ArmorMaterials(EnumHelper.addArmorMaterial("bronze", ModUtils.id("bronze"), 21, new int[]{1, 4, 4, 1}, 9,
      SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 12.5f, 12.5f, 8.25f
    );

    WROUGHT_IRON = new ArmorMaterials(EnumHelper.addArmorMaterial("wrought_iron", ModUtils.id("wrought_iron"), 33, new int[]{1, 4, 5, 2}, 12,
      SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 20, 20, 13.2f
    );

    STEEL = new ArmorMaterials(EnumHelper.addArmorMaterial("steel", ModUtils.id("steel"), 40, new int[]{2, 5, 6, 2}, 12,
      SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 25, 30, 16.5f
    );

    BLACK_STEEL = new ArmorMaterials(EnumHelper.addArmorMaterial("black_steel", ModUtils.id("black_steel"), 50, new int[]{2, 5, 6, 2}, 17,
      SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 50, 45, 33
    );

    BLUE_STEEL = new ArmorMaterials(EnumHelper.addArmorMaterial("blue_steel", ModUtils.id("blue_steel"), 68, new int[]{3, 6, 8, 3}, 23,
      SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F), 62.5f, 50, 50
    );

    RED_STEEL = new ArmorMaterials(EnumHelper.addArmorMaterial("red_steel", ModUtils.id("red_steel"), 68, new int[]{3, 6, 8, 3}, 23,
      SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F), 50, 62.5f, 50
    );

    PINEAPPLE_LEATHER = new ArmorMaterials(EnumHelper.addArmorMaterial("pineapple_leather", ModUtils.id("pineapple_leather"), 5, new int[]{1, 2, 3, 1}, 15,
      SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 2, 2, 5
    );

    BURLAP_CLOTH = new ArmorMaterials(EnumHelper.addArmorMaterial("burlap_cloth", ModUtils.id("burlap_cloth"), 5, new int[]{1, 2, 3, 1}, 15,
      SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4
    );

    WOOL_CLOTH = new ArmorMaterials(EnumHelper.addArmorMaterial("wool_cloth", ModUtils.id("wool_cloth"), 5, new int[]{1, 2, 3, 1}, 15,
      SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4
    );

    SILK_CLOTH = new ArmorMaterials(EnumHelper.addArmorMaterial("silk_cloth", ModUtils.id("silk_cloth"), 5, new int[]{1, 2, 3, 1}, 15,
      SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4
    );

    SISAL_CLOTH = new ArmorMaterials(EnumHelper.addArmorMaterial("sisal_cloth", ModUtils.id("sisal_cloth"), 5, new int[]{1, 2, 3, 1}, 15,
      SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4
    );

    COTTON_CLOTH = new ArmorMaterials(EnumHelper.addArmorMaterial("cotton_cloth", ModUtils.id("cotton_cloth"), 5, new int[]{1, 2, 3, 1}, 15,
      SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4
    );

    LINEN_CLOTH = new ArmorMaterials(EnumHelper.addArmorMaterial("linen_cloth", ModUtils.id("linen_cloth"), 5, new int[]{1, 2, 3, 1}, 15,
      SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4
    );

    HEMP_CLOTH = new ArmorMaterials(EnumHelper.addArmorMaterial("hemp_cloth", ModUtils.id("hemp_cloth"), 5, new int[]{1, 2, 3, 1}, 15,
      SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4
    );

    YUCCA_CANVAS = new ArmorMaterials(EnumHelper.addArmorMaterial("yucca_canvas", ModUtils.id("yucca_canvas"), 5, new int[]{1, 2, 3, 1}, 15,
      SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4
    );

    SNOW_SHOES = new ArmorMaterials(EnumHelper.addArmorMaterial("snow_shoes", ModUtils.id("snow_shoes"), 14, new int[]{1, 0, 0, 0}, 9,
      SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 5.0F, 5.0F, 10.0F
    );

    HIKING_BOOTS = new ArmorMaterials(EnumHelper.addArmorMaterial("hiking_boots", ModUtils.id("hiking_boots"), 28, new int[]{1, 0, 0, 0}, 9,
      SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 5.0F, 5.0F, 10.0F
    );
  }

  private final float crushingModifier;
  private final float piercingModifier;
  private final float slashingModifier;

  private final ArmorMaterial armorMaterial;

  @SuppressWarnings("WeakerAccess")
  public ArmorMaterials(ArmorMaterial armorMaterial, float piercingModifier, float slashingModifier, float crushingModifier) {
    this.armorMaterial = armorMaterial;
    this.crushingModifier = crushingModifier;
    this.piercingModifier = piercingModifier;
    this.slashingModifier = slashingModifier;
  }
}
