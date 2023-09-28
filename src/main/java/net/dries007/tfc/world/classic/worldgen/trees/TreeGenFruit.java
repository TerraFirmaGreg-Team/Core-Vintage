//package net.dries007.tfc.world.classic.worldgen.trees;
//
//import net.dries007.tfc.TerraFirmaGreg;
//import net.dries007.tfc.world.classic.worldgen.trees.ITreeGenerator;
//import net.dries007.tfc.module.core.submodule.wood.api.type.WoodType;
//import net.dries007.tfc.world.classic.StructureHelper;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.gen.structure.template.PlacementSettings;
//import net.minecraft.world.gen.structure.template.Template;
//import net.minecraft.world.gen.structure.template.TemplateManager;
//
//import java.util.Random;
//
//public class TreeGenFruit implements ITreeGenerator {
//    private static final PlacementSettings SETTINGS = StructureHelper.getDefaultSettings();
//
//    @Override
//    public void generateTree(TemplateManager manager, World world, BlockPos pos, WoodType woodType, Random rand, boolean isWorldGen) {
//        ResourceLocation base = TerraFirmaGreg.identifier("fruit_trees/" + woodType.toString());
//        Template structureBase = manager.get(world.getMinecraftServer(), base);
//
//        if (structureBase == null) {
//            TerraFirmaGreg.LOGGER.warn("Unable to find a template for " + base);
//            return;
//        }
//
//        BlockPos size = structureBase.getSize();
//        pos = pos.add(-size.getX() / 2, 0, -size.getZ() / 2);
//
//        StructureHelper.addStructureToWorld(world, pos, structureBase, SETTINGS);
//    }
//}
