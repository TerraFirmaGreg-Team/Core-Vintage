package su.terrafirmagreg.api.data.enums;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public enum Mods {
  TFC(Names.TFC),
  TFCF(Names.TFCF),
  TFCTECH(Names.TFCTECH),
  TFCTHINGS(Names.TFCTHINGS),
  HORSEPOWER(Names.HORSEPOWER),
  CA(Names.CAFFEINEADDON),
  TIME4TFC(Names.TIME4TFC),
  OSA(Names.OSA),
  CELLARS(Names.CELLARS),
  HOTORNOT(Names.HOTORNOT),
  FL(Names.FL),
  AGEDDRINKS(Names.AGEDDRINKS),
  DDD(Names.DDD),
  FF(Names.FF),
  TFCFARMING(Names.TFCFARMING),
  TFCPASSINGDAYS(Names.TFCPASSINGDAYS),
  FLUIDLOGGED(Names.FLUIDLOGGED),
  GREGTECH(Names.GREGTECH);


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
    return new ResourceLocation(modid, path);
  }

  public static class Names {

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
