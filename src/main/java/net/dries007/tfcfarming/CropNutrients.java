package net.dries007.tfcfarming;

import net.dries007.firmalife.init.StemCrop;
import net.dries007.tfc.api.types.ICrop;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.agriculture.CropTFCF;

import java.util.HashMap;

import static net.dries007.tfcfarming.NutrientClass.NITROGEN;
import static net.dries007.tfcfarming.NutrientClass.PHOSPHORUS;
import static net.dries007.tfcfarming.NutrientClass.POTASSIUM;

public enum CropNutrients {

  // Grains

  AMARANTH(NITROGEN, CropTFCF.AMARANTH),
  BUCKWHEAT(NITROGEN, CropTFCF.BUCKWHEAT),
  FONIO(NITROGEN, CropTFCF.FONIO),
  MILLET(NITROGEN, CropTFCF.MILLET),
  QUINOA(NITROGEN, CropTFCF.QUINOA),
  SPELT(NITROGEN, CropTFCF.SPELT),
  BARLEY(NITROGEN, Crop.BARLEY),
  MAIZE(NITROGEN, Crop.MAIZE),
  OAT(NITROGEN, Crop.OAT),
  RICE(NITROGEN, Crop.RICE),
  RYE(NITROGEN, Crop.RYE),
  WHEAT(NITROGEN, Crop.WHEAT),
  WELD(NITROGEN, CropTFCF.WELD),
  WOAD(NITROGEN, CropTFCF.WOAD),
  TOBACCO(NITROGEN, CropTFCF.TOBACCO),
  SUGARCANE(NITROGEN, Crop.SUGARCANE),
  PURPLE_GRAPE(NITROGEN, CropTFCF.PURPLE_GRAPE),
  GREEN_GRAPE(NITROGEN, CropTFCF.GREEN_GRAPE),
  INDIGO(NITROGEN, CropTFCF.INDIGO),
  MADDER(NITROGEN, CropTFCF.MADDER),
  OPIUM_POPPY(NITROGEN, CropTFCF.OPIUM_POPPY),
  RAPE(NITROGEN, CropTFCF.RAPE),

  // Legumes      N           P           K           S

  CAYENNE_PEPPER(POTASSIUM, CropTFCF.CAYENNE_PEPPER),
  GREEN_BEAN(POTASSIUM, Crop.GREEN_BEAN),
  SOYBEAN(POTASSIUM, Crop.SOYBEAN),
  TOMATO(POTASSIUM, Crop.TOMATO),
  RED_BELL_PEPPER(POTASSIUM, Crop.RED_BELL_PEPPER),
  YELLOW_BELL_PEPPER(POTASSIUM, Crop.YELLOW_BELL_PEPPER),
  PUMPKIN(POTASSIUM, StemCrop.PUMPKIN),
  MELON(POTASSIUM, StemCrop.MELON),
  BLACK_EYED_PEAS(POTASSIUM, CropTFCF.BLACK_EYED_PEAS),
  LIQUORICE(POTASSIUM, CropTFCF.LIQUORICE_ROOT),
  COFFEA(POTASSIUM, CropTFCF.COFFEA),
  AGAVE(POTASSIUM, CropTFCF.AGAVE),
  COCA(POTASSIUM, CropTFCF.COCA),
  COTTON(POTASSIUM, CropTFCF.COTTON),
  HOP(POTASSIUM, CropTFCF.HOP),


  GINGER(PHOSPHORUS, CropTFCF.GINGER),
  GINSENG(PHOSPHORUS, CropTFCF.GINSENG),
  RUTABAGA(PHOSPHORUS, CropTFCF.RUTABAGA),
  TURNIP(PHOSPHORUS, CropTFCF.TURNIP),
  SUGAR_BEET(PHOSPHORUS, CropTFCF.SUGAR_BEET),
  BEET(PHOSPHORUS, Crop.BEET),
  CABBAGE(PHOSPHORUS, Crop.CABBAGE),
  CARROT(PHOSPHORUS, Crop.CARROT),
  GARLIC(PHOSPHORUS, Crop.GARLIC),
  ONION(PHOSPHORUS, Crop.ONION),
  POTATO(PHOSPHORUS, Crop.POTATO),
  SQUASH(PHOSPHORUS, Crop.SQUASH),
  FLAX(PHOSPHORUS, CropTFCF.FLAX),
  HEMP(PHOSPHORUS, CropTFCF.HEMP),
  JUTE(PHOSPHORUS, Crop.JUTE);

  // reverse map
  public static HashMap<ICrop, CropNutrients> MAP = new HashMap<>();
  public final int stepCost;
  public final ICrop crop;
  public final NutrientClass favouriteNutrient;
  public float maximumTemperature;


  CropNutrients(NutrientClass favouriteNutrient, ICrop crop) {
    this.stepCost = 255 / (crop.getMaxStage() + 1);
    this.favouriteNutrient = favouriteNutrient;
    this.crop = crop;
    this.maximumTemperature = crop.getTempMaxGrow();

  }

  public static CropNutrients getCropNValues(ICrop crop) {
    if (MAP.isEmpty()) {
      for (CropNutrients value : values()) {
        MAP.put(value.crop, value);
      }
    }
    return MAP.get(crop);
  }

}
