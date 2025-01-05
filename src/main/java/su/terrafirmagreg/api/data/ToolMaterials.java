package su.terrafirmagreg.api.data;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class ToolMaterials {

  public static final Item.ToolMaterial FLINT = EnumHelper.addToolMaterial(ModUtils.id("flint"), 1, 50, 7.3f, 2.0f, 5); //Tier 0
  public static final Item.ToolMaterial IGNEOUS_INTRUSIVE = EnumHelper.addToolMaterial(ModUtils.id("igneous_intrusive"), 1, 60, 7, 2.0f, 5); //Tier 0
  public static final Item.ToolMaterial SEDIMENTARY = EnumHelper.addToolMaterial(ModUtils.id("sedimentary"), 1, 50, 7, 2.0f, 5);
  public static final Item.ToolMaterial IGNEOUS_EXTRUSIVE = EnumHelper.addToolMaterial(ModUtils.id("igneous_extrusive"), 1, 70, 6, 2.0f, 5);
  public static final Item.ToolMaterial METAMORPHIC = EnumHelper.addToolMaterial(ModUtils.id("metamorphic"), 1, 55, 6.5f, 2.0f, 5);
  public static final Item.ToolMaterial COPPER = EnumHelper.addToolMaterial(ModUtils.id("copper"), 2, 600, 8, 3.5f, 8); //Tier 1
  public static final Item.ToolMaterial BRONZE = EnumHelper.addToolMaterial(ModUtils.id("bronze"), 2, 1300, 11, 4.0f, 13); //Tier 2
  public static final Item.ToolMaterial BISMUTH_BRONZE = EnumHelper.addToolMaterial(ModUtils.id("bismuth_bronze"), 2, 1200, 10, 4.0f, 10);
  public static final Item.ToolMaterial BLACK_BRONZE = EnumHelper.addToolMaterial(ModUtils.id("black_bronze"), 2, 1460, 9, 4.25f, 10);
  public static final Item.ToolMaterial WROUGHT_IRON = EnumHelper.addToolMaterial(ModUtils.id("iron"), 2, 2200, 12, 4.75f, 12); //Tier 3
  public static final Item.ToolMaterial STEEL = EnumHelper.addToolMaterial(ModUtils.id("steel"), 2, 3300, 14, 5.75f, 12); //Tier 4
  public static final Item.ToolMaterial BLACK_STEEL = EnumHelper.addToolMaterial(ModUtils.id("black_steel"), 3, 4200, 16, 7.0f, 17); //Tier 5
  public static final Item.ToolMaterial BLUE_STEEL = EnumHelper.addToolMaterial(ModUtils.id("blue_steel"), 3, 6500, 18, 9.0f, 22); //Tier 6
  public static final Item.ToolMaterial RED_STEEL = EnumHelper.addToolMaterial(ModUtils.id("red_steel"), 3, 6500, 18, 9.0f, 22);

  private ToolMaterials() {}
}
