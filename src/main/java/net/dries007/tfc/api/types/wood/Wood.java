package net.dries007.tfc.api.types.wood;

import net.dries007.tfc.api.types.tree.Tree;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.TemplateManager;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.dries007.tfc.types.DefaultTrees.*;

public enum Wood implements IStringSerializable {
    ACACIA(0x8B3929, new Tree.Builder(30f, 210f, 19f, 31f)
            .setGenerator(GEN_ACACIA)
            .setHeight(12)
            .setGrowthTime(11)
            .setDensity(0.1f, 0.6f)
            .setBurnInfo(650f, 1000)
            .build()),
    ASH(0xAE604E, new Tree.Builder(60f, 140f, -6f, 12f)
            .setGenerator(GEN_NORMAL)
            .setBurnInfo(696f, 1250)
            .build()),
    ASPEN(0x373727, new Tree.Builder(10f, 80f, -10f, 16f)
            .setGenerator(GEN_MEDIUM)
            .setRadius(1)
            .setGrowthTime(8)
            .setBurnInfo(611f, 1000)
            .build()),
    BIRCH(0x897658, new Tree.Builder(20f, 180f, -15f, 7f)
            .setGenerator(GEN_TALL)
            .setRadius(1)
            .setTannin()
            .setBurnInfo(652f, 1750)
            .build()),
    BLACKWOOD(0x1A1A1A, new Tree.Builder(0f, 120f, 4f, 33f)
            .setGenerator(GEN_MEDIUM)
            .setHeight(12)
            .setGrowthTime(8)
            .setBurnInfo(720f, 1750)
            .build()),
    CHESTNUT(0x642C1E, new Tree.Builder(160f, 320f, 11f, 35f)
            .setGenerator(GEN_NORMAL)
            .setTannin()
            .setBurnInfo(651f, 1500)
            .build()),
    DOUGLAS_FIR(0xD7BC8D, new Tree.Builder(280f, 480f, -2f, 14f)
            .setGenerator(GEN_TALL)
            .setDominance(5.2f)
            .setHeight(16)
            .setBushes()
            .setTannin()
            .setDensity(0.25f, 2f)
            .setBurnInfo(707f, 1500)
            .build()),
    HICKORY(0x4E3418, new Tree.Builder(80f, 250f, 7f, 29f)
            .setGenerator(GEN_TALL)
            .setGrowthTime(10)
            .setTannin()
            .setBurnInfo(762f, 2000)
            .build()),
    MAPLE(0xD8BFAA, new Tree.Builder(140f, 360f, 3f, 20f)
            .setGenerator(GEN_MEDIUM)
            .setDominance(6.3f)
            .setRadius(1)
            .setTannin()
            .setBurnInfo(745f, 2000)
            .build()),
    OAK(0x8B4513, new Tree.Builder(180f, 430f, -8f, 12f)
            .setGenerator(GEN_TALL)
            .setHeight(16)
            .setGrowthTime(10)
            .setTannin()
            .setBurnInfo(728f, 2250)
            .build()),
    PALM(0x8B4513, new Tree.Builder(280f, 500f, 16f, 35f)
            .setGenerator(GEN_TROPICAL)
            .setDecayDist(6)
            .setBurnInfo(730f, 1250)
            .build()),
    PINE(0x8B4513, new Tree.Builder(60f, 250f, -15f, 7f)
            .setGenerator(GEN_CONIFER)
            .setRadius(1)
            .setConifer()
            .setDensity(0.1f, 0.8f)
            .setBurnInfo(627f, 1250)
            .build()),
    ROSEWOOD(0x8B4513, new Tree.Builder(10f, 190f, 8f, 18f)
            .setGenerator(GEN_MEDIUM)
            .setHeight(12)
            .setGrowthTime(8)
            .setBurnInfo(640f, 1500)
            .build()),
    SEQUOIA(0x965B3B, new Tree.Builder(250f, 420f, -5f, 12f)
            .setGenerator(GEN_SEQUOIA)
            .setRadius(3)
            .setHeight(24)
            .setDecayDist(6)
            .setGrowthTime(18)
            .setConifer()
            .setBushes()
            .setTannin()
            .setDensity(0.4f, 0.9f)
            .setBurnInfo(612f, 1750)
            .build()),
    SPRUCE(0x8B4513, new Tree.Builder(120f, 380f, -11f, 6f)
            .setGenerator(GEN_CONIFER)
            .setRadius(1)
            .setConifer()
            .setDensity(0.1f, 0.8f)
            .setBurnInfo(608f, 1500)
            .build()),
    SYCAMORE(0x8B4513, new Tree.Builder(120f, 290f, 17f, 33f)
            .setGenerator(GEN_MEDIUM)
            .setGrowthTime(8)
            .setBushes()
            .setDensity(0.25f, 2f)
            .setBurnInfo(653f, 1750)
            .build()),
    WHITE_CEDAR(0xD4D4D4, new Tree.Builder(10f, 240f, -8f, 17f)
            .setGenerator(GEN_TALL)
            .setHeight(16)
            .setBurnInfo(625f, 1500)
            .build()),
    WILLOW(0x3A430B, new Tree.Builder(230f, 400f, 15f, 32f)
            .setGenerator(GEN_WILLOW)
            .setRadius(1)
            .setGrowthTime(11)
            .setBushes()
            .setDensity(0.7f, 2f)
            .setBurnInfo(603f, 1000)
            .build()),
    KAPOK(0xAD879F, new Tree.Builder(210f, 500f, 15f, 35f)
            .setGenerator(GEN_ACACIA)
            .setDominance(8.5f)
            .setRadius(3)
            .setHeight(24)
            .setDecayDist(6)
            .setGrowthTime(18)
            .setBushes()
            .setDensity(0.6f, 2f)
            .setBurnInfo(645f, 1000)
            .build());

    private static final Wood[] VALUES = values();
    private final int color;
    private final Tree tree;

    /**
     * Создает новый тип дерева с указанной температурой горения, количеством тиков горения и цветом.
     *
     * @param color цвет типа дерева
     */
    Wood(int color, Tree tree) {
        this.color = color;
        this.tree = tree;
    }

    public static Wood valueOf(int i) {
        return i >= 0 && i < VALUES.length ? VALUES[i] : VALUES[i % VALUES.length];
    }

    /**
     * Возвращает цвет данного типа дерева.
     *
     * @return цвет
     */
    public int getColor() {
        return color;
    }


    public Tree getTree() {
        return tree;
    }

    public boolean makeTree(TemplateManager manager, World world, BlockPos pos, Random rand, boolean isWorldGen) {
        if (this.getTree().getGenerator().canGenerateTree(world, pos, this)) {
            this.getTree().getGenerator().generateTree(manager, world, pos, this, rand, isWorldGen);
            return true;
        }
        return false;
    }

    public boolean makeTree(World world, BlockPos pos, Random rand, boolean isWorldGen) {
        if (!world.isRemote) {
            return makeTree(((WorldServer) world).getStructureTemplateManager(), world, pos, rand, isWorldGen);
        }
        return false;
    }


    /**
     * Возвращает имя перечисления в нижнем регистре.
     *
     * @return имя перечисления
     */
    @Nonnull
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
