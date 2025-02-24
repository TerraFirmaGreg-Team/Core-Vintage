package su.terrafirmagreg.api.data.enums;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public enum Mods {
  TFC(ModIDs.TFC),
  TFCF(ModIDs.TFCF),
  TFCTECH(ModIDs.TFCTECH),
  TFCTHINGS(ModIDs.TFCTHINGS),
  HORSEPOWER(ModIDs.HORSEPOWER),
  CA(ModIDs.CAFFEINEADDON),
  TIME4TFC(ModIDs.TIME4TFC),
  OSA(ModIDs.OSA),
  CELLARS(ModIDs.CELLARS),
  HOTORNOT(ModIDs.HOTORNOT),
  FL(ModIDs.FL),
  AGEDDRINKS(ModIDs.AGEDDRINKS),
  DDD(ModIDs.DDD),
  FF(ModIDs.FF),
  TFCFARMING(ModIDs.TFCFARMING),
  TFCPASSINGDAYS(ModIDs.TFCPASSINGDAYS),
  FLUIDLOGGED(ModIDs.FLUIDLOGGED),
  GREGTECH(ModIDs.GREGTECH);


  private final String modid;
  private Boolean modLoaded;

  Mods(String modid) {
    this.modid = modid;
  }

  public boolean isModLoaded() {
    if (this.modLoaded == null) {
      this.modLoaded = Loader.isModLoaded(this.modid);
    }
    return this.modLoaded;
  }

  public ItemStack getItem(@NotNull String name) {
    return getItem(name, 1, 0, null);
  }

  @NotNull
  public ItemStack getItem(@NotNull String name, int count) {
    return getItem(name, count, 0, null);
  }

  @NotNull
  public ItemStack getItem(@NotNull String name, int count, int meta) {
    return getItem(name, count, meta, null);
  }

  @NotNull
  public ItemStack getItem(@NotNull String name, int count, int meta, @Nullable String nbt) {
    // The following statement is intentional.
    return GameRegistry.makeItemStack(modid + ":" + name, meta, count, nbt);
  }

  @NotNull
  public ResourceLocation getResource(@NotNull String path) {
    return ModUtils.resource(modid, path);
  }

  public static class ModIDs {

    public static final String TFC = "tfc";
    public static final String TFCF = "tfcflorae";
    public static final String TFCTECH = "tfctech";
    public static final String TFCTHINGS = "tfcthings";
    public static final String HORSEPOWER = "horsepower";
    public static final String CAFFEINEADDON = "ca";
    public static final String TIME4TFC = "time4tfc";
    public static final String OSA = "oversizediteminstoragearea";
    public static final String CELLARS = "cellars";
    public static final String HOTORNOT = "hotornot";
    public static final String FL = "firmalife";
    public static final String AGEDDRINKS = "aged_drinks";
    public static final String DDD = "deathdairydespair";
    public static final String FF = "floraefixes";
    public static final String TFCFARMING = "tfcfarming";
    public static final String TFCPASSINGDAYS = "tfcpassingdays";
    public static final String FLUIDLOGGED = "fluidlogged_api";
    public static final String GREGTECH = "gregtech";
  }

}
