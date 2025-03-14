package net.dries007.tfc.types;

import su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification;

import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.util.Helpers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFCF;

public class BlockTypesTFCF extends IForgeRegistryEntry.Impl<BlockTypesTFCF> {

  @GameRegistry.ObjectHolder("tfc:granite")
  public static final Rock GRANITE = Helpers.getNull();
  @GameRegistry.ObjectHolder("tfc:basalt")
  public static final Rock BASALT = Helpers.getNull();
  @GameRegistry.ObjectHolder("tfc:rhyolite")
  public static final Rock RHYOLITE = Helpers.getNull();
  @GameRegistry.ObjectHolder("tfc:limestone")
  public static final Rock LIMESTONE = Helpers.getNull();

  private final RockCategory rockCategory;
  private final ResourceLocation textureLocation;
  private final boolean isFluxStone;
  private final boolean isNaturallyGenerating;

  public BlockTypesTFCF(@Nonnull ResourceLocation name, @Nonnull RockCategory rockCategory, boolean isFluxStone, boolean isNaturallyGenerating) {
    //noinspection ConstantConditions
    if (rockCategory == null) {throw new IllegalArgumentException("Rock category is not allowed to be null (on rock " + name + ")");}

    setRegistryName(name);
    this.rockCategory = rockCategory;
    this.textureLocation = new ResourceLocation(TFCF, "textures/blocks/stonetypes/raw/" + name.getPath() + ".png");
    this.isFluxStone = isFluxStone;
    this.isNaturallyGenerating = isNaturallyGenerating;
  }

  public BlockTypesTFCF(@Nonnull ResourceLocation name, @Nonnull RockCategory rockCategory, boolean isFluxStone) {
    this(name, rockCategory, isFluxStone, true);
  }

  public BlockTypesTFCF(@Nonnull ResourceLocation name, @Nonnull ResourceLocation categoryName, boolean isFluxStone) {
    //noinspection ConstantConditions
    this(name, TFCRegistries.ROCK_CATEGORIES.getValue(categoryName), isFluxStone, true);
  }

  /**
   * Used for knapping GUI
   *
   * @return a texture resource location
   */
  public ResourceLocation getTexture() {
    return textureLocation;
  }

  public RockCategory getRockCategory() {
    return rockCategory;
  }

  public boolean isFluxStone() {
    return isFluxStone;
  }

  public boolean isNaturallyGenerating() {
    return isNaturallyGenerating;
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public String toString() {
    return getRegistryName().getPath();
  }

  public enum RockTFCF {
    MOSSY_RAW(Material.ROCK, false, Specification.COLLAPSABLE),
    MUD_BRICKS(Material.ROCK, false, null),
    MUD(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    BOG_IRON(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    BOG_IRON_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    DRY_BOG_IRON_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    SPARSE_BOG_IRON_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    BOG_IRON_PODZOL(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    COARSE_DIRT(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    PODZOL(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    LOAMY_SAND(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    COARSE_LOAMY_SAND(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    LOAMY_SAND_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    LOAMY_SAND_PODZOL(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    SANDY_LOAM(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    COARSE_SANDY_LOAM(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    SANDY_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    SANDY_LOAM_PODZOL(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    SANDY_CLAY_LOAM(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    COARSE_SANDY_CLAY_LOAM(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    SANDY_CLAY_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    SANDY_CLAY_LOAM_PODZOL(Material.GRASS, true, Specification.VERTICAL_ONLY),
    SANDY_CLAY(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    COARSE_SANDY_CLAY(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    SANDY_CLAY_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    SANDY_CLAY_PODZOL(Material.GRASS, true, Specification.VERTICAL_ONLY),
    LOAM(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    COARSE_LOAM(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    LOAM_PODZOL(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    CLAY_LOAM(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    COARSE_CLAY_LOAM(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    CLAY_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    CLAY_LOAM_PODZOL(Material.GRASS, true, Specification.VERTICAL_ONLY),
    //CLAY(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    COARSE_CLAY(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    //CLAY_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    CLAY_PODZOL(Material.GRASS, true, Specification.VERTICAL_ONLY),
    SILTY_CLAY(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    COARSE_SILTY_CLAY(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    SILTY_CLAY_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    SILTY_CLAY_PODZOL(Material.GRASS, true, Specification.VERTICAL_ONLY),
    SILTY_CLAY_LOAM(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    COARSE_SILTY_CLAY_LOAM(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    SILTY_CLAY_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    SILTY_CLAY_LOAM_PODZOL(Material.GRASS, true, Specification.VERTICAL_ONLY),
    SILT_LOAM(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    COARSE_SILT_LOAM(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    SILT_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    SILT_LOAM_PODZOL(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    SILT(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    COARSE_SILT(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    SILT_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    SILT_PODZOL(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    DRY_LOAMY_SAND_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    DRY_SANDY_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    DRY_SANDY_CLAY_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    DRY_SANDY_CLAY_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    DRY_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    DRY_CLAY_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    DRY_CLAY_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    DRY_SILTY_CLAY_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    DRY_SILTY_CLAY_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    DRY_SILT_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    DRY_SILT_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    HUMUS(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    COARSE_HUMUS(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    HUMUS_GRASS(Material.GROUND, true, Specification.VERTICAL_AND_HORIZONTAL),
    DRY_HUMUS_GRASS(Material.GROUND, true, Specification.VERTICAL_AND_HORIZONTAL),
    CLAY_HUMUS(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    CLAY_HUMUS_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    DRY_CLAY_HUMUS_GRASS(Material.GROUND, true, Specification.VERTICAL_ONLY),
    COARSE_CLAY_HUMUS(Material.GROUND, false, Specification.VERTICAL_AND_HORIZONTAL),
    SPARSE_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    SPARSE_CLAY_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    SPARSE_LOAMY_SAND_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    SPARSE_SANDY_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    SPARSE_SANDY_CLAY_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    SPARSE_SANDY_CLAY_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    SPARSE_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    SPARSE_CLAY_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    SPARSE_SILTY_CLAY_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    SPARSE_SILTY_CLAY_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_ONLY),
    SPARSE_SILT_LOAM_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    SPARSE_SILT_GRASS(Material.GRASS, true, Specification.VERTICAL_AND_HORIZONTAL),
    SPARSE_HUMUS_GRASS(Material.GROUND, true, Specification.VERTICAL_AND_HORIZONTAL),
    SPARSE_CLAY_HUMUS_GRASS(Material.GROUND, true, Specification.VERTICAL_ONLY),

    // Farmland
    LOAMY_SAND_FARMLAND(Material.GROUND, false, Specification.VERTICAL_ONLY),
    SANDY_LOAM_FARMLAND(Material.GROUND, false, Specification.VERTICAL_ONLY),
    LOAM_FARMLAND(Material.GROUND, false, Specification.VERTICAL_ONLY),
    SILT_LOAM_FARMLAND(Material.GROUND, false, Specification.VERTICAL_ONLY),
    SILT_FARMLAND(Material.GROUND, false, Specification.VERTICAL_ONLY),
    HUMUS_FARMLAND(Material.GROUND, false, Specification.VERTICAL_ONLY);

    public final Material material;
    public final boolean isGrass;

        /*
        private final FallingBlockType gravType;

        RockTFCF(Material material, FallingBlockType gravType, boolean isGrass)
        {
            this.material = material;
            this.gravType = gravType;
            this.isGrass = isGrass;
        }
        */

    @Nullable
    private final Specification fallingSpecification;

    RockTFCF(Material material, boolean isGrass, @Nullable Specification fallingSpecification) {
      this.material = material;
      this.isGrass = isGrass;
      this.fallingSpecification = fallingSpecification;
    }

    public static RockTFCF getNonGrassVersionStatic(RockTFCF rock) {
      if (!rock.isGrass) {return rock;}
      switch (rock) {
        case BOG_IRON_GRASS:
        case DRY_BOG_IRON_GRASS:
        case SPARSE_BOG_IRON_GRASS:
        case BOG_IRON_PODZOL:
          return BOG_IRON;
        case SPARSE_LOAMY_SAND_GRASS:
        case DRY_LOAMY_SAND_GRASS:
        case LOAMY_SAND_GRASS:
        case LOAMY_SAND_PODZOL:
          return LOAMY_SAND;
        case SPARSE_SANDY_LOAM_GRASS:
        case DRY_SANDY_LOAM_GRASS:
        case SANDY_LOAM_GRASS:
        case SANDY_LOAM_PODZOL:
          return SANDY_LOAM;
        case SPARSE_SANDY_CLAY_LOAM_GRASS:
        case DRY_SANDY_CLAY_LOAM_GRASS:
        case SANDY_CLAY_LOAM_GRASS:
        case SANDY_CLAY_LOAM_PODZOL:
          return SANDY_CLAY_LOAM;
        case SPARSE_SANDY_CLAY_GRASS:
        case DRY_SANDY_CLAY_GRASS:
        case SANDY_CLAY_GRASS:
        case SANDY_CLAY_PODZOL:
          return SANDY_CLAY;
        case SPARSE_LOAM_GRASS:
        case DRY_LOAM_GRASS:
        case LOAM_GRASS:
        case LOAM_PODZOL:
          return LOAM;
        case SPARSE_CLAY_LOAM_GRASS:
        case DRY_CLAY_LOAM_GRASS:
        case CLAY_LOAM_GRASS:
        case CLAY_LOAM_PODZOL:
          return CLAY_LOAM;
        case SPARSE_SILTY_CLAY_GRASS:
        case DRY_SILTY_CLAY_GRASS:
        case SILTY_CLAY_GRASS:
        case SILTY_CLAY_PODZOL:
          return SILTY_CLAY;
        case SPARSE_SILTY_CLAY_LOAM_GRASS:
        case DRY_SILTY_CLAY_LOAM_GRASS:
        case SILTY_CLAY_LOAM_GRASS:
        case SILTY_CLAY_LOAM_PODZOL:
          return SILTY_CLAY_LOAM;
        case SPARSE_SILT_LOAM_GRASS:
        case DRY_SILT_LOAM_GRASS:
        case SILT_LOAM_GRASS:
        case SILT_LOAM_PODZOL:
          return SILT_LOAM;
        case SPARSE_SILT_GRASS:
        case DRY_SILT_GRASS:
        case SILT_GRASS:
        case SILT_PODZOL:
          return SILT;
        case HUMUS_GRASS:
        case SPARSE_HUMUS_GRASS:
        case DRY_HUMUS_GRASS:
          return HUMUS;
        case CLAY_HUMUS_GRASS:
        case SPARSE_CLAY_HUMUS_GRASS:
        case DRY_CLAY_HUMUS_GRASS:
          return CLAY_HUMUS;
        case SPARSE_GRASS:
        case SPARSE_CLAY_GRASS:
        case DRY_CLAY_GRASS:
        case CLAY_PODZOL:
        case PODZOL:
          return null; //This is supposed to happen, because it is expected to run getNonGrassVersionTFC after this one
      }
      throw new IllegalStateException("Someone forgot to add enum constants to this switch case...");
    }

    public static Rock.Type getNonGrassVersionTFCStatic(RockTFCF rock) {
      if (!rock.isGrass) {return null;}
      switch (rock) {
        case SPARSE_CLAY_GRASS:
        case DRY_CLAY_GRASS:
        case CLAY_PODZOL:
          return Rock.Type.CLAY;
        case PODZOL:
        case SPARSE_GRASS:
          return Rock.Type.DIRT;
      }
      throw new IllegalStateException("Someone forgot to add enum constants to this switch case...");
    }

    public static boolean isDryGrass(RockTFCF grass) {
      if (!grass.isGrass) {throw new IllegalArgumentException("Non-grass can't spread.");} else
        return grass == DRY_BOG_IRON_GRASS ||
               grass == DRY_LOAMY_SAND_GRASS ||
               grass == DRY_SANDY_LOAM_GRASS ||
               grass == DRY_SANDY_CLAY_LOAM_GRASS ||
               grass == DRY_SANDY_CLAY_GRASS ||
               grass == DRY_LOAM_GRASS ||
               grass == DRY_CLAY_LOAM_GRASS ||
               grass == DRY_SILTY_CLAY_GRASS ||
               grass == DRY_SILTY_CLAY_LOAM_GRASS ||
               grass == DRY_SILT_LOAM_GRASS ||
               grass == DRY_SILT_GRASS ||
               grass == DRY_HUMUS_GRASS ||
               grass == DRY_CLAY_HUMUS_GRASS;
    }

    public static boolean isSparseGrass(RockTFCF grass) {
      if (!grass.isGrass) {throw new IllegalArgumentException("Non-grass can't spread.");} else
        return grass == SPARSE_BOG_IRON_GRASS ||
               grass == SPARSE_GRASS ||
               grass == SPARSE_CLAY_GRASS ||
               grass == SPARSE_LOAMY_SAND_GRASS ||
               grass == SPARSE_SANDY_LOAM_GRASS ||
               grass == SPARSE_SANDY_CLAY_LOAM_GRASS ||
               grass == SPARSE_SANDY_CLAY_GRASS ||
               grass == SPARSE_LOAM_GRASS ||
               grass == SPARSE_CLAY_LOAM_GRASS ||
               grass == SPARSE_SILTY_CLAY_GRASS ||
               grass == SPARSE_SILTY_CLAY_LOAM_GRASS ||
               grass == SPARSE_SILT_LOAM_GRASS ||
               grass == SPARSE_SILT_GRASS ||
               grass == SPARSE_HUMUS_GRASS ||
               grass == SPARSE_CLAY_HUMUS_GRASS;
    }

    public boolean canFall() {
      return fallingSpecification != null;
    }

    public boolean canFallHorizontal() {
      return fallingSpecification != null && fallingSpecification.canFallHorizontally();
    }

    public boolean canFallHorizontally() {
      return fallingSpecification != null && fallingSpecification.canFallHorizontally();
    }

    public boolean shouldRockify() {
      switch (this) {
        case MOSSY_RAW:
        case MUD_BRICKS:
        case MUD:
        case PODZOL:
        case SPARSE_GRASS:
        case COARSE_DIRT:
        case CLAY_PODZOL:
        case DRY_CLAY_GRASS:
        case SPARSE_CLAY_GRASS:
        case COARSE_CLAY:
          return true;
        default:
          return false;
      }
    }

    public RockTFCF getNonGrassVersion() {
      if (!isGrass) {return this;}
      switch (this) {
        case BOG_IRON_GRASS:
        case DRY_BOG_IRON_GRASS:
        case SPARSE_BOG_IRON_GRASS:
        case BOG_IRON_PODZOL:
          return BOG_IRON;
        case SPARSE_LOAMY_SAND_GRASS:
        case DRY_LOAMY_SAND_GRASS:
        case LOAMY_SAND_GRASS:
        case LOAMY_SAND_PODZOL:
          return LOAMY_SAND;
        case SPARSE_SANDY_LOAM_GRASS:
        case DRY_SANDY_LOAM_GRASS:
        case SANDY_LOAM_GRASS:
        case SANDY_LOAM_PODZOL:
          return SANDY_LOAM;
        case SPARSE_SANDY_CLAY_LOAM_GRASS:
        case DRY_SANDY_CLAY_LOAM_GRASS:
        case SANDY_CLAY_LOAM_GRASS:
        case SANDY_CLAY_LOAM_PODZOL:
          return SANDY_CLAY_LOAM;
        case SPARSE_SANDY_CLAY_GRASS:
        case DRY_SANDY_CLAY_GRASS:
        case SANDY_CLAY_GRASS:
        case SANDY_CLAY_PODZOL:
          return SANDY_CLAY;
        case SPARSE_LOAM_GRASS:
        case DRY_LOAM_GRASS:
        case LOAM_GRASS:
        case LOAM_PODZOL:
          return LOAM;
        case SPARSE_CLAY_LOAM_GRASS:
        case DRY_CLAY_LOAM_GRASS:
        case CLAY_LOAM_GRASS:
        case CLAY_LOAM_PODZOL:
          return CLAY_LOAM;
        case SPARSE_SILTY_CLAY_GRASS:
        case DRY_SILTY_CLAY_GRASS:
        case SILTY_CLAY_GRASS:
        case SILTY_CLAY_PODZOL:
          return SILTY_CLAY;
        case SPARSE_SILTY_CLAY_LOAM_GRASS:
        case DRY_SILTY_CLAY_LOAM_GRASS:
        case SILTY_CLAY_LOAM_GRASS:
        case SILTY_CLAY_LOAM_PODZOL:
          return SILTY_CLAY_LOAM;
        case SPARSE_SILT_LOAM_GRASS:
        case DRY_SILT_LOAM_GRASS:
        case SILT_LOAM_GRASS:
        case SILT_LOAM_PODZOL:
          return SILT_LOAM;
        case SPARSE_SILT_GRASS:
        case DRY_SILT_GRASS:
        case SILT_GRASS:
        case SILT_PODZOL:
          return SILT;
        case HUMUS_GRASS:
        case SPARSE_HUMUS_GRASS:
        case DRY_HUMUS_GRASS:
          return HUMUS;
        case CLAY_HUMUS_GRASS:
        case SPARSE_CLAY_HUMUS_GRASS:
        case DRY_CLAY_HUMUS_GRASS:
          return CLAY_HUMUS;
        case SPARSE_GRASS:
        case SPARSE_CLAY_GRASS:
        case DRY_CLAY_GRASS:
        case CLAY_PODZOL:
        case PODZOL:
          return null; //This is supposed to happen, because it is expected to run getNonGrassVersionTFC after this one
      }
      throw new IllegalStateException("Someone forgot to add enum constants to this switch case...");
    }

    public Rock.Type getNonGrassVersionTFC() {
      if (!isGrass) {return null;}
      switch (this) {
        case SPARSE_CLAY_GRASS:
        case DRY_CLAY_GRASS:
        case CLAY_PODZOL:
          return Rock.Type.CLAY;
        case SPARSE_GRASS:
        case PODZOL:
          return Rock.Type.DIRT;
      }
      throw new IllegalStateException("Someone forgot to add enum constants to this switch case...");
    }

    public RockTFCF getGrassVersion(RockTFCF spreader) {
      if (!spreader.isGrass) {throw new IllegalArgumentException("Non-grass can't spread.");}
      switch (this) {
        case BOG_IRON:
          if (isDryGrass(spreader)) {return DRY_BOG_IRON_GRASS;} else if (isSparseGrass(spreader)) {return SPARSE_BOG_IRON_GRASS;} else {return BOG_IRON_GRASS;}
        case LOAMY_SAND:
          if (isDryGrass(spreader)) {return DRY_LOAMY_SAND_GRASS;} else if (isSparseGrass(spreader)) {return SPARSE_LOAMY_SAND_GRASS;} else {
            return LOAMY_SAND_GRASS;
          }
        case SANDY_LOAM:
          if (isDryGrass(spreader)) {return DRY_SANDY_LOAM_GRASS;} else if (isSparseGrass(spreader)) {return SPARSE_SANDY_LOAM_GRASS;} else {
            return SANDY_LOAM_GRASS;
          }
        case SANDY_CLAY_LOAM:
          if (isDryGrass(spreader)) {return DRY_SANDY_CLAY_LOAM_GRASS;} else if (isSparseGrass(spreader)) {return SPARSE_SANDY_CLAY_LOAM_GRASS;} else {
            return SANDY_CLAY_LOAM_GRASS;
          }
        case SANDY_CLAY:
          if (isDryGrass(spreader)) {return DRY_SANDY_CLAY_GRASS;} else if (isSparseGrass(spreader)) {return SPARSE_SANDY_CLAY_GRASS;} else {
            return SANDY_CLAY_GRASS;
          }
        case LOAM:
          if (isDryGrass(spreader)) {return DRY_LOAM_GRASS;} else if (isSparseGrass(spreader)) {return SPARSE_LOAM_GRASS;} else {return LOAM_GRASS;}
        case CLAY_LOAM:
          if (isDryGrass(spreader)) {return DRY_CLAY_LOAM_GRASS;} else if (isSparseGrass(spreader)) {return SPARSE_CLAY_LOAM_GRASS;} else {
            return CLAY_LOAM_GRASS;
          }
        case SILTY_CLAY:
          if (isDryGrass(spreader)) {return DRY_SILTY_CLAY_GRASS;} else if (isSparseGrass(spreader)) {return SPARSE_SILTY_CLAY_GRASS;} else {
            return SILTY_CLAY_GRASS;
          }
        case SILTY_CLAY_LOAM:
          if (isDryGrass(spreader)) {return DRY_SILTY_CLAY_LOAM_GRASS;} else if (isSparseGrass(spreader)) {return SPARSE_SILTY_CLAY_LOAM_GRASS;} else {
            return SILTY_CLAY_LOAM_GRASS;
          }
        case SILT_LOAM:
          if (isDryGrass(spreader)) {return DRY_SILT_LOAM_GRASS;} else if (isSparseGrass(spreader)) {return SPARSE_SILT_LOAM_GRASS;} else {
            return SILT_LOAM_GRASS;
          }
        case SILT:
          if (isDryGrass(spreader)) {return DRY_SILT_GRASS;} else if (isSparseGrass(spreader)) {return SPARSE_SILT_GRASS;} else {return SILT_GRASS;}
        case HUMUS:
          if (isDryGrass(spreader)) {return DRY_HUMUS_GRASS;} else if (isSparseGrass(spreader)) {return SPARSE_HUMUS_GRASS;} else {return HUMUS_GRASS;}
        case CLAY_HUMUS:
          if (isDryGrass(spreader)) {return DRY_CLAY_HUMUS_GRASS;} else if (isSparseGrass(spreader)) {return SPARSE_CLAY_HUMUS_GRASS;} else {
            return CLAY_HUMUS_GRASS;
          }
      }
      throw new IllegalArgumentException("You cannot get grass from rock types.");
    }

        /*
        public RockTFCF getGrassVersion(RockTFCF spreader)
        {
            if (!spreader.isGrass) throw new IllegalArgumentException("Non-grass can't spread.");
            switch (this)
            {
                case LOAMY_SAND:
                    return spreader == DRY_LOAMY_SAND_GRASS ? DRY_LOAMY_SAND_GRASS : LOAMY_SAND_GRASS;
                case SANDY_LOAM:
                    return spreader == DRY_SANDY_LOAM_GRASS ? DRY_SANDY_LOAM_GRASS : SANDY_LOAM_GRASS;
                case SANDY_CLAY_LOAM:
                    return spreader == DRY_SANDY_CLAY_LOAM_GRASS ? DRY_SANDY_CLAY_LOAM_GRASS : SANDY_CLAY_LOAM_GRASS;
                case SANDY_CLAY:
                    return spreader == DRY_SANDY_CLAY_GRASS ? DRY_SANDY_CLAY_GRASS : SANDY_CLAY_GRASS;
                case LOAM:
                    return spreader == DRY_LOAM_GRASS ? DRY_LOAM_GRASS : LOAM_GRASS;
                case CLAY_LOAM:
                    return spreader == DRY_CLAY_LOAM_GRASS ? DRY_CLAY_LOAM_GRASS : CLAY_LOAM_GRASS;
                case SILTY_CLAY:
                    return spreader == DRY_SILTY_CLAY_GRASS ? DRY_SILTY_CLAY_GRASS : SILTY_CLAY_GRASS;
                case SILTY_CLAY_LOAM:
                    return spreader == DRY_SILTY_CLAY_LOAM_GRASS ? DRY_SILTY_CLAY_LOAM_GRASS : SILTY_CLAY_LOAM_GRASS;
                case SILT_LOAM:
                    return spreader == DRY_SILT_LOAM_GRASS ? DRY_SILT_LOAM_GRASS : SILT_LOAM_GRASS;
                case SILT:
                    return spreader == DRY_SILT_GRASS ? DRY_SILT_GRASS : SILT_GRASS;
                case HUMUS:
                    return spreader == DRY_HUMUS_GRASS ? DRY_HUMUS_GRASS : HUMUS_GRASS;
                case CLAY_HUMUS:
                    return spreader == DRY_CLAY_HUMUS_GRASS ? DRY_CLAY_HUMUS_GRASS : CLAY_HUMUS_GRASS;

                // Kaolinite Clay
                case KAOLINITE_CLAY:
                    return spreader == DRY_KAOLINITE_CLAY_GRASS ? DRY_KAOLINITE_CLAY_GRASS : KAOLINITE_CLAY_GRASS;
                case SANDY_KAOLINITE_CLAY_LOAM:
                    return spreader == DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS ? DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS : SANDY_KAOLINITE_CLAY_LOAM_GRASS;
                case SANDY_KAOLINITE_CLAY:
                    return spreader == DRY_SANDY_KAOLINITE_CLAY_GRASS ? DRY_SANDY_KAOLINITE_CLAY_GRASS : SANDY_KAOLINITE_CLAY_GRASS;
                case KAOLINITE_CLAY_LOAM:
                    return spreader == DRY_KAOLINITE_CLAY_LOAM_GRASS ? DRY_KAOLINITE_CLAY_LOAM_GRASS : KAOLINITE_CLAY_LOAM_GRASS;
                case SILTY_KAOLINITE_CLAY:
                    return spreader == DRY_SILTY_KAOLINITE_CLAY_GRASS ? DRY_SILTY_KAOLINITE_CLAY_GRASS : SILTY_KAOLINITE_CLAY_GRASS;
                case SILTY_KAOLINITE_CLAY_LOAM:
                    return spreader == DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS ? DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS : SILTY_KAOLINITE_CLAY_LOAM_GRASS;
                case KAOLINITE_CLAY_HUMUS:
                    return spreader == DRY_KAOLINITE_CLAY_HUMUS_GRASS ? DRY_KAOLINITE_CLAY_HUMUS_GRASS : KAOLINITE_CLAY_HUMUS_GRASS;
            }
            throw new IllegalArgumentException("You cannot get grass from rock types.");
        }
        */

    @Nullable
    public Specification getFallingSpecification() {
      return fallingSpecification;
    }
  }
}
