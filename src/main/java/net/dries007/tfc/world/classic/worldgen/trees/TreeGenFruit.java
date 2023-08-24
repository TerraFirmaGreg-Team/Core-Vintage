package net.dries007.tfc.world.classic.worldgen.trees;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.trees.ITreeGenerator;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.world.classic.StructureHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class TreeGenFruit implements ITreeGenerator {
    private static final PlacementSettings SETTINGS = StructureHelper.getDefaultSettings();

    @Override
    public void generateTree(TemplateManager manager, World world, BlockPos pos, WoodType woodType, Random rand, boolean isWorldGen) {
        ResourceLocation base = TerraFirmaCraft.identifier("fruit_trees/" + woodType.toString());
        Template structureBase = manager.get(world.getMinecraftServer(), base);

        if (structureBase == null) {
            TerraFirmaCraft.LOGGER.warn("Unable to find a template for " + base);
            return;
        }

        BlockPos size = structureBase.getSize();
        pos = pos.add(-size.getX() / 2, 0, -size.getZ() / 2);

        StructureHelper.addStructureToWorld(world, pos, structureBase, SETTINGS);
    }
}
