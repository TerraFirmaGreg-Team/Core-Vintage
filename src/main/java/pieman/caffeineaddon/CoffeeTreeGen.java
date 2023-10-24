package pieman.caffeineaddon;

import java.util.Random;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.api.util.IFruitTreeGenerator;
import net.dries007.tfc.world.classic.StructureHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class CoffeeTreeGen implements IFruitTreeGenerator {
	
    private static final PlacementSettings SETTINGS = StructureHelper.getDefaultSettings();

    @Override
    public void generateTree(TemplateManager manager, World world, BlockPos pos, IFruitTree tree, Random rand)
    {
        ResourceLocation base = new ResourceLocation("ca:fruit_trees/" + tree.getName());
        Template structureBase = manager.get(world.getMinecraftServer(), base);

        if (structureBase == null)
        {
            TerraFirmaCraft.getLog().warn("Unable to find a template for " + base.toString());
            return;
        }

        BlockPos size = structureBase.getSize();
        pos = pos.add(-size.getX() / 2, 0, -size.getZ() / 2);

        StructureHelper.addStructureToWorld(world, pos, structureBase, SETTINGS);
    }

}
