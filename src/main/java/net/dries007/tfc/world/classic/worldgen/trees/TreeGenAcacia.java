package net.dries007.tfc.world.classic.worldgen.trees;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.trees.ITreeGenerator;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariants;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.common.objects.blocks.wood.BlockWoodLeaves;
import net.dries007.tfc.common.objects.blocks.wood.BlockWoodSapling;
import net.dries007.tfc.world.classic.StructureHelper;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static net.dries007.tfc.common.objects.blocks.wood.BlockWoodLog.PLACED;
import static net.minecraft.block.BlockLog.LOG_AXIS;

public class TreeGenAcacia implements ITreeGenerator {
    private static final PlacementSettings settings = StructureHelper.getDefaultSettings();
    private IBlockState trunk;
    private IBlockState bark;

    @Override
    public void generateTree(TemplateManager manager, World world, BlockPos pos, WoodType woodType, Random rand, boolean isWorldGen) {
        trunk = TFCBlocks.getWoodBlock(WoodBlockVariants.LOG, woodType).getDefaultState().withProperty(PLACED, false);
        bark = TFCBlocks.getWoodBlock(WoodBlockVariants.LOG, woodType).getDefaultState().withProperty(PLACED, false).withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);

        final boolean smallBranch = rand.nextBoolean();
        final int branches = 2 + rand.nextInt(2);
        final int height = 5 + rand.nextInt(4);
        List<EnumFacing> sides = Arrays.stream(EnumFacing.HORIZONTALS).collect(Collectors.toList());
        EnumFacing face;

        int x1, y1, y2 = 0, y3 = 0;
        EnumFacing side = EnumFacing.UP;
        if (smallBranch) {
            y3 = rand.nextInt(3) + 2;
            side = sides.get(rand.nextInt(sides.size()));
            placeBranch(manager, world, pos.offset(side).add(0, y3, 0), woodType + "/branch3");
        }
        for (int i = 0; i < branches; i++) {
            x1 = 2 + rand.nextInt(3);
            y1 = 4 + rand.nextInt(height - 2);
            if (y1 > y2)
                y2 = y1;
            face = sides.remove(rand.nextInt(sides.size()));
            for (int j = 1; j < x1; j++)
                placeLog(world, pos.add(0, y1 - j, 0).offset(face, x1 - j), true);
            int branch = 1 + rand.nextInt(2);
            placeBranch(manager, world, pos.add(0, y1, 0).offset(face, x1), woodType + "/branch" + branch);
        }
        for (int i = 0; i < height; i++) {
            if (smallBranch && i == y3) {
                placeLog(world, pos.add(0, i - 1, 0), true);
                pos = pos.offset(side.getOpposite());
                placeLog(world, pos.add(0, i, 0), true);
                continue;
            }
            placeLog(world, pos.add(0, i, 0), false);
        }
        placeBranch(manager, world, pos.add(0, height, 0), woodType + "/branch3");
    }

    private void placeBranch(TemplateManager manager, World world, BlockPos pos, String name) {
        ResourceLocation base = TerraFirmaCraft.identifier(name);
        Template structureBase = manager.get(world.getMinecraftServer(), base);

        if (structureBase == null) {
            TerraFirmaCraft.LOGGER.warn("Unable to find a template for " + base);
            return;
        }
        BlockPos size = structureBase.getSize();
        pos = pos.add(-size.getX() / 2, 0, -size.getZ() / 2);

        StructureHelper.addStructureToWorld(world, pos, structureBase, settings);
    }

    private void placeLog(World world, BlockPos pos, boolean useBark) {
        if (world.getBlockState(pos).getMaterial().isReplaceable() ||
                world.getBlockState(pos).getBlock() instanceof BlockWoodSapling ||
                world.getBlockState(pos).getBlock() instanceof BlockWoodLeaves)
            world.setBlockState(pos, useBark ? bark : trunk);
    }
}
