/*This structure generator is based upon Additional-Structures by XxRexRaptorxX*/
/*Link to original mod source: https://github.com/XxRexRaptorxX/Additional-Structures*/

package su.terrafirmagreg.modules.worldgen.classic.objects.generator.structures;

import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.flora.api.types.type.FloraType;
import su.terrafirmagreg.modules.flora.init.BlocksFlora;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.worldgen.classic.ChunkGenClassic;
import su.terrafirmagreg.modules.worldgen.classic.init.BiomesWorld;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfcflorae.TFCFlorae;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import static su.terrafirmagreg.api.data.Reference.MODID_TFCF;
import static su.terrafirmagreg.api.util.MathUtils.RNG;
import static su.terrafirmagreg.modules.rock.init.BlocksRock.SAND;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.DIRT;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.DRY_GRASS;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.GRASS;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.SPARSE_GRASS;

public class StructureGenerator extends WorldGenerator {

  private final String structureName;

  public StructureGenerator(String structureName) {
    this.structureName = structureName;
  }

  public static boolean canSpawnHere(Template template, World world, BlockPos pos, int variation) {
    return isCornerValid(world, pos, variation) && isCornerValid(world, pos.add(template.getSize()
                                                                                        .getX(), 0, 0), variation)
           && isCornerValid(world, pos.add(template.getSize().getX(), 0, template.getSize().getZ()),
                            variation)
           && isCornerValid(world, pos.add(0, 0, template.getSize().getZ()), variation);
  }

  public static boolean checkBiome(Template template, World world, BlockPos pos) {
    BlockPos x1 = new BlockPos(pos.getX() + template.getSize().getX(), pos.getY(), pos.getZ());
    BlockPos x2 = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + template.getSize().getZ());
    BlockPos x3 = new BlockPos(pos.getX() + template.getSize().getX(), pos.getY(),
                               pos.getZ() + template.getSize()
                                                    .getZ());

    final Biome b1 = world.getBiome(x1);
    final Biome b2 = world.getBiome(x2);
    final Biome b3 = world.getBiome(x3);

    return (world.getBlockState(x1).getBlock() != ChunkGenClassic.FRESH_WATER.getBlock()
            && world.getBlockState(x1)
                    .getBlock() != ChunkGenClassic.SALT_WATER.getBlock() && world.getBlockState(x1)
                                                                                 .getBlock() != ChunkGenClassic.HOT_WATER.getBlock() && b1 != BiomesWorld.OCEAN
            && b1 != BiomesWorld.DEEP_OCEAN &&
            b1 != BiomesWorld.LAKE &&
            b1 != BiomesWorld.RIVER && b1 != BiomesWorld.BEACH && b1 != BiomesWorld.GRAVEL_BEACH) &&
           (world.getBlockState(x2).getBlock() != ChunkGenClassic.FRESH_WATER.getBlock()
            && world.getBlockState(x2)
                    .getBlock() != ChunkGenClassic.SALT_WATER.getBlock() && world.getBlockState(x2)
                                                                                 .getBlock() != ChunkGenClassic.HOT_WATER.getBlock() && b2 != BiomesWorld.OCEAN
            && b2 != BiomesWorld.DEEP_OCEAN &&
            b2 != BiomesWorld.LAKE && b2 != BiomesWorld.RIVER && b2 != BiomesWorld.BEACH
            && b2 != BiomesWorld.GRAVEL_BEACH) &&
           (world.getBlockState(x3).getBlock() != ChunkGenClassic.FRESH_WATER.getBlock()
            && world.getBlockState(x3)
                    .getBlock() != ChunkGenClassic.SALT_WATER.getBlock() && world.getBlockState(x3)
                                                                                 .getBlock() != ChunkGenClassic.HOT_WATER.getBlock() && b3 != BiomesWorld.OCEAN
            && b3 != BiomesWorld.DEEP_OCEAN &&
            b3 != BiomesWorld.LAKE && b3 != BiomesWorld.RIVER && b3 != BiomesWorld.BEACH
            && b3 != BiomesWorld.GRAVEL_BEACH);
  }

  /**
   * Get a rotation. This is a provisionally method to avoid a bug in the foundation generator ;(
   *
   * @return A specific random rotation
   */
  public static Rotation getRotation() {
    if (RNG.nextInt(2) == 1) {
      return Rotation.CLOCKWISE_180;
    } else {
      return Rotation.NONE;
    }
  }

  public static int getLowestCorner(Template template, World world, BlockPos pos) {
    int groundX1 = getGroundFromAbove(world, pos.getX(), pos.getZ());
    int groundX2 = getGroundFromAbove(world, pos.getX() + template.getSize().getX(), pos.getZ());
    int groundX3 = getGroundFromAbove(world, pos.getX(), pos.getZ() + template.getSize().getZ());
    int groundX4 = getGroundFromAbove(world, pos.getX() + template.getSize().getX(),
                                      pos.getZ() + template.getSize()
                                                           .getZ());

    if (groundX1 <= groundX2 && groundX1 <= groundX3 && groundX1 <= groundX4) {
      return groundX1;
    } else if (groundX2 <= groundX1 && groundX2 <= groundX3 && groundX2 <= groundX4) {
      return groundX2;
    } else if (groundX3 <= groundX1 && groundX3 <= groundX2 && groundX3 <= groundX4) {
      return groundX3;
    } else {
      return groundX4;
    }
  }

  public static boolean isCornerValid(World world, BlockPos pos, int variation) {
    int groundY = getGroundFromAbove(world, pos.getX(), pos.getZ());
    return groundY > pos.getY() - variation && groundY < pos.getY() + variation;
  }

  public static int getGroundFromAbove(World world, int x, int z) {
    int y = -99;
    y = world.getActualHeight();
    boolean foundGround = false;

    while (!foundGround && y-- > 0) {
      IBlockState current = world.getBlockState(new BlockPos(x, y, z));
      foundGround = BlockHelper.isGround(current);
    }
    BlockPos pos = new BlockPos(x, y - 1, z);

    //Not accepted positions
    if (world.getBlockState(pos).getBlock() == ChunkGenClassic.FRESH_WATER.getBlock()
        || world.getBlockState(pos)
                .getBlock() == ChunkGenClassic.SALT_WATER.getBlock() || world.getBlockState(pos)
                                                                             .getBlock() == ChunkGenClassic.HOT_WATER.getBlock()) {
      y = -99;
    }
    if (world.getBlockState(pos).getBlock() == Blocks.LAVA ||
        world.getBlockState(pos).getBlock() == Blocks.AIR ||
        world.getBlockState(pos).getBlock() == Blocks.ICE ||
        world.getBlockState(pos).getBlock() == Blocks.PACKED_ICE ||
        world.getBlockState(pos).getBlock() == BlocksTFC.SEA_ICE) {
      y = -99;
    }
    return y;
  }

  @Override
  public boolean generate(World world, Random rand, BlockPos position) {
    WorldServer worldServer = (WorldServer) world;
    MinecraftServer minecraftServer = world.getMinecraftServer();
    TemplateManager templateManager = worldServer.getStructureTemplateManager();
    Template template = templateManager.get(minecraftServer,
                                            new ResourceLocation(MODID_TFCF, structureName));
    int variation = 3;

    if (template == null) {
      TFCFlorae.getLog().info("TFCFlorae: Structure '" + structureName + "' does not exist ");
      return false;
    }
    if (structureName.endsWith("_bury")) {
      // The template is a bury structure
      variation = 8;
    } else if (structureName.startsWith("underground_") || structureName.startsWith("flying_")
               || world.provider.getDimension() == -1) {
      // The template is a underground or flying structure
      variation = 200;
    } else {
      // The template is a "normal" structure
      variation = 3;
    }

    //TFCFlorae.getLog().info("TFCFlorae: Structure '" + structureName + "' Is trying to spawn at location: " + position);

    if (canSpawnHere(template, worldServer, position, variation) && checkBiome(template, world,
                                                                               position)) {
      //TFCFlorae.getLog().info("TFCFlorae: Structure '" + structureName + "' can spawn here");
      // The structure can spawn here
      Rotation rotation = Rotation.values()[rand.nextInt(3)];
      PlacementSettings settings = new PlacementSettings().setMirror(Mirror.NONE)
                                                          .setRotation(getRotation())
                                                          .setIgnoreStructureBlock(false);

      int newY = getLowestCorner(template, world, position);
      position = position.add(0, (newY - position.getY()), 0);

      template.addBlocksToWorld(world, position, settings);

      Map<BlockPos, String> dataBlocks = template.getDataBlocks(position, settings);
      // Structure blocks with loottables become replaced with chests
      for (Entry<BlockPos, String> entry : dataBlocks.entrySet()) {
        try {
          String[] data = entry.getValue().split(" ");
          if (data.length < 2) {
            continue;
          }

          Block block = Block.getBlockFromName(data[0]);
          IBlockState state = null;

          if (data.length == 3) {
            state = block.getStateFromMeta(Integer.parseInt(data[2]));
          } else {
            state = block.getDefaultState();
          }

          for (Entry<IProperty<?>, Comparable<?>> entry2 : block.getDefaultState().getProperties()
                                                                .entrySet()) {
            if (entry2.getKey().getValueClass().equals(EnumFacing.class) && entry2.getKey()
                                                                                  .getName()
                                                                                  .equals("facing")) {
              state = state.withRotation(rotation.add(Rotation.CLOCKWISE_180));
              break;
            }
          }

          world.setBlockState(entry.getKey(), state, 3);

          var tile = TileUtils.getTile(world, entry.getKey(), TileEntityLockableLoot.class);
          tile.ifPresent(tileEntityLockableLoot -> tileEntityLockableLoot.setLootTable(new ResourceLocation(data[1]), rand.nextLong()));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      // Places foundations under flying structures
      if (!structureName.startsWith("underground_")
          || world.provider.getDimension() != DimensionType.NETHER.getId()) {
        final int searchRange = 10;
        int posX = position.getX();
        int posY = position.getY() - 1;
        int posZ = position.getZ();

        for (int x = 0; x < template.getSize().getX(); x++) {
          for (int z = 0; z < template.getSize().getZ(); z++) {
            for (int y = 0; y < searchRange; y++) {
              for (FloraType plant : FloraType.getTypes()) {
                if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR ||
                    world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == BlocksFlora.PLANT.get(plant)) {
                  final IBlockState current = world.getBlockState(position);

                  if (Variant.isVariant(current, SAND)) {
                    world.setBlockState(new BlockPos(posX, posY, posZ),
                                        BlocksRock.SAND.get(ProviderChunkData.getRockHeight(world, position)).getDefaultState(), 2);

                  } else if (BlockHelper.isDirt(current)) {
                    world.setBlockState(new BlockPos(posX, posY, posZ), DIRT.get(ProviderChunkData.getSoilHeight(world, position)).getDefaultState(), 2);

                  } else if (Variant.isVariant(current, DRY_GRASS)) {
                    world.setBlockState(new BlockPos(posX, posY, posZ), DRY_GRASS.get(ProviderChunkData.getSoilHeight(world, position)).getDefaultState(), 2);

                  } else if (BlockHelper.isGrass(current)) {
                    world.setBlockState(new BlockPos(posX, posY, posZ), GRASS.get(ProviderChunkData.getSoilHeight(world, position)).getDefaultState(), 2);

                  } else if (Variant.isVariant(current, SPARSE_GRASS)) {
                    world.setBlockState(new BlockPos(posX, posY, posZ), SPARSE_GRASS.get(ProviderChunkData.getSoilHeight(world, position))
                                                                                    .getDefaultState(), 2);

                  } else {
                    world.setBlockState(new BlockPos(posX, posY, posZ), DIRT.get(ProviderChunkData.getSoilHeight(world, position)).getDefaultState(), 2);
                  }
                  posY = posY - 1;
                }
              }
            }
            posY = position.getY() - 1;
            if (settings.getRotation() == Rotation.NONE) {
              posZ = posZ + 1;
            } else if (settings.getRotation() == Rotation.CLOCKWISE_180) {
              posZ = posZ - 1;
            } else if (settings.getRotation() == Rotation.CLOCKWISE_90) {
              posZ = posZ + 1;
            } else if (settings.getRotation() == Rotation.COUNTERCLOCKWISE_90) {
              posZ = posZ - 1;
            }
          }
          posZ = position.getZ();
          if (settings.getRotation() == Rotation.NONE) {
            posX = posX + 1;
          } else if (settings.getRotation() == Rotation.CLOCKWISE_180) {
            posX = posX - 1;
          } else if (settings.getRotation() == Rotation.CLOCKWISE_90) {
            posX = posX - 1;
          } else if (settings.getRotation() == Rotation.COUNTERCLOCKWISE_90) {
            posX = posX + 1;
          }
        }
        posX = position.getX();
      }
      return true;
    }
    return false;
  }
}
