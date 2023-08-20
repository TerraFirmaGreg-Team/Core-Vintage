package net.dries007.tfc.api.types.trees;

import net.dries007.tfc.world.classic.worldgen.trees.*;

public class TreeGeneratorHandler {

    public static void init() {
        TreeGenerators.GEN_NORMAL = new TreeGenNormal(1, 3);
        TreeGenerators.GEN_MEDIUM = new TreeGenNormal(2, 2);
        TreeGenerators.GEN_TALL = new TreeGenNormal(3, 3);
        TreeGenerators.GEN_CONIFER = new TreeGenVariants(false, 7);
        TreeGenerators.GEN_TROPICAL = new TreeGenVariants(true, 7);
        TreeGenerators.GEN_WILLOW = new TreeGenWillow();
        TreeGenerators.GEN_ACACIA = new TreeGenAcacia();
        TreeGenerators.GEN_KAPOK = new TreeGenKapok();
        TreeGenerators.GEN_SEQUOIA = new TreeGenSequoia();
        TreeGenerators.GEN_KAPOK_COMPOSITE = new TreeGenComposite().add(0.4f, TreeGenerators.GEN_TALL).add(0.6f, TreeGenerators.GEN_KAPOK);
        TreeGenerators.GEN_BUSHES = new TreeGenBushes();
        TreeGenerators.GEN_FRUIT = new TreeGenFruit();
    }

}
