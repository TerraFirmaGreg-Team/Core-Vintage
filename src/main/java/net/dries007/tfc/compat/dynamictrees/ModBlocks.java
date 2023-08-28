package net.dries007.tfc.compat.dynamictrees;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.cells.ICellKit;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.compat.dynamictrees.blocks.BlockRootyDirtTFC;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;
import java.util.Map;

import static com.ferreusveritas.dynamictrees.ModConstants.MODID;
import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.wood.type.WoodTypes.*;
import static net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariants.LEAVES;

public class ModBlocks {
    public static LeavesProperties[] tfcLeavesProperties;
    public static Map<WoodType, LeavesProperties> leafMap;
    public static Map<WoodType, ICellKit> kitMap;
    public static BlockRootyDirtTFC blockRootyDirt;

    public static void preInit() {
        blockRootyDirt = new BlockRootyDirtTFC();

        kitMap = new HashMap<>();
        fillMaps(kitMap);
    }

    public static void register(IForgeRegistry<Block> registry) {
        //For this mod it is vital that these are never reordered.  If a leaves properties is removed from the
        //mod then there should be a LeavesProperties.NULLPROPERTIES used as a placeholder.
        tfcLeavesProperties = new LeavesProperties[WoodType.getWoodTypes().size()];
        leafMap = new HashMap<>();
        int i = 0; // DT wants an array of leafprops for some reason
        for (var type : WoodType.getWoodTypes()) {
            var leaf = TFCBlocks.getWoodBlock(LEAVES, type);
            var prop = new LeavesProperties(leaf.getDefaultState(), kitMap.get(type));
            leafMap.put(type, prop);
            tfcLeavesProperties[i++] = prop;
        }

        for (LeavesProperties lp : tfcLeavesProperties) {
            LeavesPaging.getNextLeavesBlock(MOD_ID, lp);
        }
        registry.register(blockRootyDirt);
    }

    private static void fillMaps(Map<WoodType, ICellKit> kitMap) {
        kitMap.put(ACACIA, TreeRegistry.findCellKit(new ResourceLocation(MODID, "acacia")));
        kitMap.put(ASH, TreeRegistry.findCellKit(new ResourceLocation(MODID, "deciduous")));
        kitMap.put(ASPEN, TreeRegistry.findCellKit(new ResourceLocation(MODID, "deciduous")));
        kitMap.put(BIRCH, TreeRegistry.findCellKit(new ResourceLocation(MODID, "deciduous")));
        kitMap.put(BLACKWOOD, TreeRegistry.findCellKit(new ResourceLocation(MODID, "darkoak")));
        kitMap.put(CHESTNUT, TreeRegistry.findCellKit(new ResourceLocation(MODID, "deciduous")));
        kitMap.put(DOUGLAS_FIR, TreeRegistry.findCellKit(new ResourceLocation(MODID, "conifer")));
        kitMap.put(HICKORY, TreeRegistry.findCellKit(new ResourceLocation(MODID, "deciduous")));
        kitMap.put(KAPOK, TreeRegistry.findCellKit(new ResourceLocation(MODID, "deciduous")));
        kitMap.put(MAPLE, TreeRegistry.findCellKit(new ResourceLocation(MODID, "deciduous")));
        kitMap.put(OAK, TreeRegistry.findCellKit(new ResourceLocation(MODID, "deciduous")));
        kitMap.put(PALM, TreeRegistry.findCellKit(new ResourceLocation(MODID, "palm")));
        kitMap.put(PINE, TreeRegistry.findCellKit(new ResourceLocation(MODID, "conifer")));
        kitMap.put(ROSEWOOD, TreeRegistry.findCellKit(new ResourceLocation(MODID, "deciduous")));
        kitMap.put(SEQUOIA, TreeRegistry.findCellKit(new ResourceLocation(MODID, "conifer")));
        kitMap.put(SPRUCE, TreeRegistry.findCellKit(new ResourceLocation(MODID, "conifer")));
        kitMap.put(SYCAMORE, TreeRegistry.findCellKit(new ResourceLocation(MODID, "deciduous")));
        kitMap.put(WHITE_CEDAR, TreeRegistry.findCellKit(new ResourceLocation(MODID, "deciduous")));
        kitMap.put(WILLOW, TreeRegistry.findCellKit(new ResourceLocation(MODID, "deciduous")));
        //TFCTech
        // kitMap.put("hevea", TreeRegistry.findCellKit(new ResourceLocation(ModConstants.MODID, "deciduous")));
    }
}
