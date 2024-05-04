package su.terrafirmagreg.modules.rock.objects.blocks;

import com.github.bsideup.jabel.Desugar;

import java.util.function.Supplier;

@Desugar
public record DecorationBlockRegistryObject(Supplier<? extends BlockRockSlab> slab, Supplier<? extends BlockRockSlab> double_slab, Supplier<? extends BlockRockStairs> stair,
                                            Supplier<? extends BlockRockWall> wall) {}

