package su.terrafirmagreg.modules.world.classic.objects.generator.vein;

import su.terrafirmagreg.data.enums.EnumGradeOre;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.type.RockTypes;
import su.terrafirmagreg.modules.rock.init.BlocksRock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import com.google.common.collect.ImmutableSet;
import gregtech.api.worldgen.config.OreConfigUtils;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.objects.blocks.stone.BlockOreTFC;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Collection;
import java.util.Random;
import java.util.Set;

public class VeinType {

  @Getter
  private final Ore ore;
  private final ItemStack looseRock;
  private final Set<RockType> baseRocks;

  @Getter
  private final int width;
  @Getter
  private final int height;
  @Getter
  private final int minY;
  @Getter
  private final int maxY;

  @Getter
  private final double weight;
  @Getter
  private final double density;

  @Getter
  private final int rarity;
  private final Shape shape;
  private String name;

  public VeinType(@Nullable Ore ore, ItemStack looseRock, Collection<RockType> baseRocks, Shape shape,
                  int width, int height, int rarity, int minY,
                  int maxY, int density) {
    this.ore = ore;
    this.looseRock = looseRock;
    this.baseRocks = ImmutableSet.copyOf(baseRocks);
    this.shape = shape;

    this.width = width;
    this.height = height;
    this.rarity = rarity;
    this.weight = 1.0D / (double) rarity;
    this.minY = minY;
    this.maxY = maxY;
    this.density = 0.006
                   * density; // For debug purposes, removing the factor here will lead to ore veins being full size, easy to see shapes
  }

  /**
   * Creates a new instance of a vein at a given chunk. Used during world gen for various purposes.
   */
  public Vein createVein(Random rand, int chunkX, int chunkZ) {
    BlockPos startPos = new BlockPos(chunkX * 16 + 8 + rand.nextInt(16),
                                     minY + rand.nextInt(maxY - minY), chunkZ * 16 + 8 + rand.nextInt(16));
    EnumGradeOre grade = EnumGradeOre.NORMAL;
    if (ore != null && ore.isGraded()) {
      float randomGrade = rand.nextFloat();
      if (randomGrade < 0.2) {
        grade = EnumGradeOre.RICH;
      } else if (randomGrade < 0.5) {
        grade = EnumGradeOre.POOR;
      }
    }
    return switch (shape) {
      case SPHERE -> new VeinSphere(startPos, this, grade, rand);
      case CLUSTER -> new VeinCluster(startPos, this, grade, rand);
    };
  }

  /**
   * Intended to be used by propick via {@code VeinRegistry.INSTANCE.getVeins().values().forEach(x -> x.ore.isOreBlock(state)}
   *
   * @return true if the given block state is part of this ore vein
   */
  public boolean isOreBlock(IBlockState state) {
    return state.getBlock() instanceof BlockOreTFC blockOre && blockOre.ore == this.getOre();
  }

  public boolean hasLooseRocks() {
    return !looseRock.isEmpty();
  }

  @NotNull
  public ItemStack getLooseRockItem() {
    return looseRock.copy();
  }

  public String getRegistryName() {
    return name;
  }

  public void setRegistryName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    String ore = "special";
    if (getOre() != null) {
      ore = getOre().toString();
    } else if (getOreState(RockTypes.GRANITE) != null) {
      // print the registry name
      //noinspection ConstantConditions
      ore = getOreState(RockTypes.GRANITE).getBlock().getRegistryName().toString();
    }
    return String.format(
      "%s: {ore=%s, shape=%s, size=%s, rarity=%d, baseRocks=%s, minY=%d, maxY=%d, density=%.2f}",
      name, ore, shape, getWidth(),
      getRarity(), baseRocks, getMinY(), getMaxY(), getDensity());
  }

  public IBlockState getOreState(RockType rock) {

    return OreConfigUtils.getOreForMaterial(rock.getMaterial()).get(BlocksRock.RAW.getStoneType());
  }

  /**
   * Can the vein spawn in the specified rock type
   */
  public boolean canSpawnIn(RockType rock) {
    return baseRocks.contains(rock);
  }

  public enum Shape {
    SPHERE,
    CLUSTER
  }

}
