/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

import net.dries007.tfc.api.types.IArmorMaterialTFC;

import javax.annotation.Nonnull;

import static su.terrafirmagreg.old.api.data.Reference.TFC;
import static su.terrafirmagreg.old.api.data.Reference.TFCF;

/**
 * This is an extension enum for the vanilla's ArmorMaterials. We register a new material in vanilla and bind crushing, slashing and piercing resistances.
 */
public class ArmorMaterialTFC implements IArmorMaterialTFC {
  //todo tweak all these values
  //currently, modifiers = classic / 40. Should give about 45% resistance(damage = 55%) to red/blue steel before letting vanilla mechanic do the rest.
  //red/blue steel has the same base resistance(eg: the damage you take on generic/the "base" reduction using vanilla mechanics) as vanilla's diamond armor
  //black and normal steel has the same as vanilla's iron armor
  //wrought iron is equivalent to chain mail
  //copper is a little better than vanilla's leather and bronzes are in between wrought iron and copper.

  //LEATHER?
  public static final IArmorMaterialTFC QUIVER = new ArmorMaterialTFC(
    EnumHelper.addArmorMaterial("quiver", TFC + ":quiver", 10, new int[]{0, 0, 0, 0}, 9,
                                SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 0, 0, 0f
  );

  public static final IArmorMaterialTFC COPPER = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("copper", TFC + ":copper", 14, new int[]{1, 3, 4,
                                                                                                                                           1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 10, 10, 6.25f);
  public static final IArmorMaterialTFC BISMUTH_BRONZE = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("bismuth_bronze",
                                                                                                          TFC + ":bismuth_bronze", 21, new int[]{1, 4, 4,
                                                                                                                                                 1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 15, 10, 8.25f);
  public static final IArmorMaterialTFC BLACK_BRONZE = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("black_bronze",
                                                                                                        TFC + ":black_bronze", 21, new int[]{1, 4, 4,
                                                                                                                                             1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 10, 15, 8.25f);
  public static final IArmorMaterialTFC BRONZE = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("bronze", TFC + ":bronze", 21, new int[]{1, 4, 4,
                                                                                                                                           1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 12.5f, 12.5f, 8.25f);
  public static final IArmorMaterialTFC WROUGHT_IRON = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("wrought_iron",
                                                                                                        TFC + ":wrought_iron", 33, new int[]{1, 4, 5,
                                                                                                                                             2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 20, 20, 13.2f);
  public static final IArmorMaterialTFC STEEL = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("steel", TFC + ":steel", 40, new int[]{2, 5, 6,
                                                                                                                                        2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 25, 30, 16.5f);
  public static final IArmorMaterialTFC BLACK_STEEL = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("black_steel",
                                                                                                       TFC + ":black_steel", 50, new int[]{2, 5, 6,
                                                                                                                                           2}, 17, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 50, 45, 33);
  public static final IArmorMaterialTFC BLUE_STEEL = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("blue_steel",
                                                                                                      TFC + ":blue_steel", 68, new int[]{3, 6, 8,
                                                                                                                                         3}, 23, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F), 62.5f, 50, 50);
  public static final IArmorMaterialTFC RED_STEEL = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("red_steel", TFC + ":red_steel", 68, new int[]{3, 6, 8,
                                                                                                                                                    3}, 23, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F), 50, 62.5f, 50);
  public static final IArmorMaterialTFC PINEAPPLE_LEATHER = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("pineapple_leather",
                                                                                                             TFCF + ":pineapple_leather", 5, new int[]{1, 2, 3,
                                                                                                                                                       1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 2, 2, 5);
  public static final IArmorMaterialTFC BURLAP_CLOTH = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("burlap_cloth",
                                                                                                        TFCF + ":burlap_cloth", 5, new int[]{1, 2, 3,
                                                                                                                                             1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);
  public static final IArmorMaterialTFC WOOL_CLOTH = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("wool_cloth", TFCF + ":wool_cloth", 5, new int[]{1, 2, 3,
                                                                                                                                                       1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);
  public static final IArmorMaterialTFC SILK_CLOTH = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("silk_cloth", TFCF + ":silk_cloth", 5, new int[]{1, 2, 3,
                                                                                                                                                       1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);
  public static final IArmorMaterialTFC SISAL_CLOTH = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("sisal_cloth",
                                                                                                       TFCF + ":sisal_cloth", 5, new int[]{1, 2, 3,
                                                                                                                                           1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);
  public static final IArmorMaterialTFC COTTON_CLOTH = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("cotton_cloth",
                                                                                                        TFCF + ":cotton_cloth", 5, new int[]{1, 2, 3,
                                                                                                                                             1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);
  public static final IArmorMaterialTFC LINEN_CLOTH = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("linen_cloth",
                                                                                                       TFCF + ":linen_cloth", 5, new int[]{1, 2, 3,
                                                                                                                                           1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);
  public static final IArmorMaterialTFC HEMP_CLOTH = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("hemp_cloth", TFCF + ":hemp_cloth", 5, new int[]{1, 2, 3,
                                                                                                                                                       1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);
  public static final IArmorMaterialTFC YUCCA_CANVAS = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("yucca_canvas",
                                                                                                        TFCF + ":yucca_canvas", 5, new int[]{1, 2, 3,
                                                                                                                                             1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);

  private final float piercingRes, slashingRes, crushingRes;
  private final ArmorMaterial material;

  @SuppressWarnings("WeakerAccess")
  public ArmorMaterialTFC(ArmorMaterial material, float piercingRes, float slashingRes, float crushingRes) {
    this.material = material;
    this.crushingRes = crushingRes;
    this.piercingRes = piercingRes;
    this.slashingRes = slashingRes;
  }

  @Override
  public float getCrushingModifier() {
    return crushingRes;
  }

  @Override
  public float getPiercingModifier() {
    return piercingRes;
  }

  @Override
  public float getSlashingModifier() {
    return slashingRes;
  }

  @Override
  @Nonnull
  public ArmorMaterial getMaterial() {
    return material;
  }
}
