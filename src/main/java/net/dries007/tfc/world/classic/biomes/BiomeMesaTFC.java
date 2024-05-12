package net.dries007.tfc.world.classic.biomes;

import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;


import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import java.util.Arrays;
import java.util.Random;

public class BiomeMesaTFC extends BiomeTFC {

    protected static final IBlockState HARDENED_CLAY = Blocks.HARDENED_CLAY.getDefaultState();
    protected static final IBlockState STAINED_HARDENED_CLAY = Blocks.STAINED_HARDENED_CLAY.getDefaultState();
    protected static final IBlockState ORANGE_STAINED_HARDENED_CLAY = STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE);
    protected static final IBlockState SAND = Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.SAND);

    private final boolean brycePillars;
    private final boolean hasForest;
    private IBlockState[] clayBands;
    private long worldSeed;
    private NoiseGeneratorPerlin pillarNoise;
    private NoiseGeneratorPerlin pillarRoofNoise;
    private NoiseGeneratorPerlin clayBandsOffsetNoise;

    public BiomeMesaTFC(boolean hasBrycePillars, boolean hasForest, int debugColor, Biome.BiomeProperties properties) {
        this(hasBrycePillars, hasForest, debugColor, properties, 0, 0);
    }

    public BiomeMesaTFC(boolean hasBrycePillars, boolean hasForest, int debugColor, Biome.BiomeProperties properties, int lilyPadPerChunk,
                        int waterPlantsPerChunk) {
        super(debugColor, properties, 0, 0);
        this.brycePillars = hasBrycePillars;
        this.hasForest = hasForest;
        this.topBlock = SAND;
        this.fillerBlock = STAINED_HARDENED_CLAY;
    }

    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
        BlockPos chunkBlockPos = new BlockPos(x << 4, 0, z << 4);
        if (this.clayBands == null || this.worldSeed != worldIn.getSeed()) {
            this.generateBands(worldIn.getSeed());
        }

        if (this.pillarNoise == null || this.pillarRoofNoise == null || this.worldSeed != worldIn.getSeed()) {
            Random random = new Random(this.worldSeed);
            this.pillarNoise = new NoiseGeneratorPerlin(random, 4);
            this.pillarRoofNoise = new NoiseGeneratorPerlin(random, 1);
        }

        this.worldSeed = worldIn.getSeed();
        double d4 = 0.0;
        int k1;
        int l1;
        if (this.brycePillars) {
            k1 = (x & -16) + (z & 15);
            l1 = (z & -16) + (x & 15);
            double d0 = Math.min(Math.abs(noiseVal), this.pillarNoise.getValue((double) k1 * 0.25, (double) l1 * 0.25));
            if (d0 > 0.0) {
                double d1 = 0.001953125;
                double d2 = Math.abs(this.pillarRoofNoise.getValue((double) k1 * 0.001953125, (double) l1 * 0.001953125));
                d4 = d0 * d0 * 2.5;
                double d3 = Math.ceil(d2 * 50.0) + 14.0;
                if (d4 > d3) {
                    d4 = d3;
                }

                d4 += 144.0;
            }
        }

        k1 = x & 15;
        l1 = z & 15;
        int i2 = worldIn.getSeaLevel();
        IBlockState iblockstate = STAINED_HARDENED_CLAY;
        IBlockState iblockstate3 = this.fillerBlock;
        int k = (int) (noiseVal / 3.0 + 3.0 + rand.nextDouble() * 0.25);
        boolean flag = Math.cos(noiseVal / 3.0 * Math.PI) > 0.0;
        int l = -1;
        boolean flag1 = false;
        int i1 = 0;

        for (int j1 = 255; j1 >= 0; --j1) {
            if (chunkPrimerIn.getBlockState(l1, j1, k1).getMaterial() == Material.AIR && j1 < (int) d4) {
                chunkPrimerIn.setBlockState(l1, j1, k1, BlockRockVariant.get(ChunkDataTFC.getRockHeight(worldIn, chunkBlockPos), Rock.Type.RAW)
                        .getDefaultState());
            }

            if (j1 <= rand.nextInt(5)) {
                chunkPrimerIn.setBlockState(l1, j1, k1, BEDROCK);
            } else if (i1 < 15 || this.brycePillars) {
                IBlockState iblockstate1 = chunkPrimerIn.getBlockState(l1, j1, k1);
                if (iblockstate1.getMaterial() == Material.AIR) {
                    l = -1;
                } else if (iblockstate1.getBlock() == BlockRockVariant.get(ChunkDataTFC.getRockHeight(worldIn, chunkBlockPos), Rock.Type.RAW)
                        .getDefaultState()) {
                    if (l == -1) {
                        flag1 = false;
                        if (k <= 0) {
                            iblockstate = AIR;
                            iblockstate3 = BlockRockVariant.get(ChunkDataTFC.getRockHeight(worldIn, chunkBlockPos), Rock.Type.RAW)
                                    .getDefaultState();
                        } else if (j1 >= i2 - 4 && j1 <= i2 + 1) {
                            iblockstate = STAINED_HARDENED_CLAY;
                            iblockstate3 = this.fillerBlock;
                        }

                        if (j1 < i2 && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
                            iblockstate = ChunkGenTFC.FRESH_WATER;
                        }

                        l = k + Math.max(0, j1 - i2);
                        if (j1 >= i2 - 1) {
                            if (this.hasForest && j1 > 86 + k * 2) {
                                if (flag) {
                                    chunkPrimerIn.setBlockState(l1, j1, k1,
                                            BlockRockVariant.get(ChunkDataTFC.getRockHeight(worldIn, chunkBlockPos), Rock.Type.DIRT)
                                                    .getDefaultState());
                                } else {
                                    chunkPrimerIn.setBlockState(l1, j1, k1,
                                            BlockRockVariant.get(ChunkDataTFC.getRockHeight(worldIn, chunkBlockPos), Rock.Type.GRASS)
                                                    .getDefaultState());
                                }
                            } else if (j1 > i2 + 3 + k) {
                                IBlockState iblockstate2;
                                if (j1 >= 144 && j1 <= 127) {
                                    if (flag) {
                                        iblockstate2 = HARDENED_CLAY;
                                    } else {
                                        iblockstate2 = this.getBand(x, j1, z);
                                    }
                                } else {
                                    iblockstate2 = ORANGE_STAINED_HARDENED_CLAY;
                                }

                                chunkPrimerIn.setBlockState(l1, j1, k1, iblockstate2);
                            } else {
                                chunkPrimerIn.setBlockState(l1, j1, k1, this.topBlock);
                                flag1 = true;
                            }
                        } else {
                            chunkPrimerIn.setBlockState(l1, j1, k1, iblockstate3);
                            if (iblockstate3.getBlock() == Blocks.STAINED_HARDENED_CLAY) {
                                chunkPrimerIn.setBlockState(l1, j1, k1, ORANGE_STAINED_HARDENED_CLAY);
                            }
                        }
                    } else if (l > 0) {
                        --l;
                        if (flag1) {
                            chunkPrimerIn.setBlockState(l1, j1, k1, ORANGE_STAINED_HARDENED_CLAY);
                        } else {
                            chunkPrimerIn.setBlockState(l1, j1, k1, this.getBand(x, j1, z));
                        }
                    }

                    ++i1;
                }
            }
        }

    }

    public void generateBands(long seed) {
        this.clayBands = new IBlockState[144];
        Arrays.fill(this.clayBands, HARDENED_CLAY);
        Random random = new Random(seed);
        this.clayBandsOffsetNoise = new NoiseGeneratorPerlin(random, 1);

        int i2;
        for (i2 = 0; i2 < 144; ++i2) {
            i2 += random.nextInt(5) + 1;
            if (i2 < 144) {
                this.clayBands[i2] = ORANGE_STAINED_HARDENED_CLAY;
            }
        }

        i2 = random.nextInt(4) + 2;

        int j2;
        int l2;
        int k3;
        int j4;
        for (j2 = 0; j2 < i2; ++j2) {
            l2 = random.nextInt(3) + 1;
            k3 = random.nextInt(144);

            for (j4 = 0; k3 + j4 < 144 && j4 < l2; ++j4) {
                this.clayBands[k3 + j4] = STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.YELLOW);
            }
        }

        j2 = random.nextInt(4) + 2;

        int k4;
        for (l2 = 0; l2 < j2; ++l2) {
            k3 = random.nextInt(3) + 2;
            j4 = random.nextInt(144);

            for (k4 = 0; j4 + k4 < 144 && k4 < k3; ++k4) {
                this.clayBands[j4 + k4] = STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.BROWN);
            }
        }

        l2 = random.nextInt(4) + 2;

        for (k3 = 0; k3 < l2; ++k3) {
            j4 = random.nextInt(3) + 1;
            k4 = random.nextInt(144);

            for (int j1 = 0; k4 + j1 < 144 && j1 < j4; ++j1) {
                this.clayBands[k4 + j1] = STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.RED);
            }
        }

        k3 = random.nextInt(3) + 3;
        j4 = 0;

        for (k4 = 0; k4 < k3; ++k4) {
            boolean i5 = true;
            j4 += random.nextInt(16) + 4;

            for (int k1 = 0; j4 + k1 < 144 && k1 < 1; ++k1) {
                this.clayBands[j4 + k1] = STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.WHITE);
                if (j4 + k1 > 1 && random.nextBoolean()) {
                    this.clayBands[j4 + k1 - 1] = STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
                }

                if (j4 + k1 < 63 && random.nextBoolean()) {
                    this.clayBands[j4 + k1 + 1] = STAINED_HARDENED_CLAY.withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
                }
            }
        }

    }

    public IBlockState getBand(int p_180629_1_, int p_180629_2_, int p_180629_3_) {
        int i = (int) Math.round(this.clayBandsOffsetNoise.getValue((double) p_180629_1_ / 512.0, (double) p_180629_1_ / 512.0) * 2.0);
        return this.clayBands[(p_180629_2_ + i + 144) % 144];
    }
}
