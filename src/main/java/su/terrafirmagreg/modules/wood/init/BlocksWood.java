package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodBarrel;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodBookshelf;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodButton;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodChest;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodDoor;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodFence;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodFenceGate;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodFenceGateLog;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodFenceLog;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodLadder;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodLoom;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodPlanks;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodPressurePlate;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodSlab;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodStairs;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodSupport;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodToolRack;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodTrapDoor;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodWall;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodWorkbench;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;

public final class BlocksWood {

    public static final Map<Pair<WoodBlockVariant, WoodType>, Block> WOOD_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();

    public static WoodBlockVariant LOG;
    public static WoodBlockVariant STRIPPED_LOG;
    public static WoodBlockVariant LEAVES;
    public static WoodBlockVariant SAPLING;
    public static WoodBlockVariant POTTED_SAPLING;
    public static WoodBlockVariant PLANKS;
    public static WoodBlockVariant STAIRS_PLANKS;
    public static WoodBlockVariant SLAB_DOUBLE_PLANKS;
    public static WoodBlockVariant SLAB_PLANKS;
    public static WoodBlockVariant WALL_PLANKS;
    public static WoodBlockVariant BOOKSHELF;
    public static WoodBlockVariant DOOR;
    public static WoodBlockVariant TRAPDOOR;
    public static WoodBlockVariant FENCE;
    public static WoodBlockVariant FENCE_LOG;
    public static WoodBlockVariant FENCE_GATE;
    public static WoodBlockVariant FENCE_GATE_LOG;
    public static WoodBlockVariant BUTTON;
    public static WoodBlockVariant PRESSURE_PLATE;
    public static WoodBlockVariant TOOL_RACK;
    public static WoodBlockVariant SUPPORT;
    public static WoodBlockVariant WORKBENCH;
    public static WoodBlockVariant CHEST_TRAPPED;
    public static WoodBlockVariant CHEST;
    public static WoodBlockVariant LOOM;
    public static WoodBlockVariant BARREL;
    public static WoodBlockVariant LADDER;
    public static WoodBlockVariant CHOPPER;

    public static void onRegister(RegistryManager registry) {

        //        LOG = WoodBlockVariant
        //                .builder("log")
        //                .setFactory(BlockWoodLog::new)
        //                .build();
        //
        //        LEAVES = WoodBlockVariant
        //                .builder("leaves")
        //                .setFactory(BlockWoodLeaves::new)
        //                .build();
        //
        //        SAPLING = WoodBlockVariant
        //                .builder("sapling")
        //                .setFactory(BlockWoodSapling::new)
        //                .build();

        PLANKS = WoodBlockVariant
                .builder("planks")
                .setFactory(BlockWoodPlanks::new)
                .setFireInfo(5, 20)
                .build();

        STAIRS_PLANKS = WoodBlockVariant
                .builder("stairs/planks")
                .setFactory((v, t) -> new BlockWoodStairs(PLANKS, v, t))
                .setFireInfo(5, 20)
                .build();

        SLAB_DOUBLE_PLANKS = WoodBlockVariant
                .builder("slab_double/planks")
                .setFactory((v, t) -> new BlockWoodSlab.Double(PLANKS, v, t))
                .setFireInfo(5, 20)
                .build();

        SLAB_PLANKS = WoodBlockVariant
                .builder("slab/planks")
                .setFactory((v, t) -> new BlockWoodSlab.Half(PLANKS, SLAB_DOUBLE_PLANKS, v, t))
                .setFireInfo(5, 20)
                .build();

        WALL_PLANKS = WoodBlockVariant
                .builder("wall/planks")
                .setFactory((v, t) -> new BlockWoodWall(PLANKS, v, t))
                .setFireInfo(5, 20)
                .build();

        BOOKSHELF = WoodBlockVariant
                .builder("bookshelf")
                .setFactory(BlockWoodBookshelf::new)
                .setFireInfo(30, 20)
                .build();

        DOOR = WoodBlockVariant
                .builder("door")
                .setFactory(BlockWoodDoor::new)
                .setFireInfo(5, 20)
                .build();

        TRAPDOOR = WoodBlockVariant
                .builder("trapdoor")
                .setFactory(BlockWoodTrapDoor::new)
                .setFireInfo(5, 20)
                .build();

        FENCE = WoodBlockVariant
                .builder("fence")
                .setFactory(BlockWoodFence::new)
                .setFireInfo(5, 20)
                .build();

        FENCE_LOG = WoodBlockVariant
                .builder("fence_log")
                .setFactory(BlockWoodFenceLog::new)
                .setFireInfo(5, 20)
                .build();

        FENCE_GATE = WoodBlockVariant
                .builder("fence_gate")
                .setFactory(BlockWoodFenceGate::new)
                .setFireInfo(5, 20)
                .build();

        FENCE_GATE_LOG = WoodBlockVariant
                .builder("fence_gate_log")
                .setFactory(BlockWoodFenceGateLog::new)
                .setFireInfo(5, 20)
                .build();

        BUTTON = WoodBlockVariant
                .builder("button")
                .setFactory(BlockWoodButton::new)
                .setFireInfo(5, 20)
                .build();

        PRESSURE_PLATE = WoodBlockVariant
                .builder("pressure_plate")
                .setFactory(BlockWoodPressurePlate::new)
                .setFireInfo(5, 20)
                .build();

        TOOL_RACK = WoodBlockVariant
                .builder("tool_rack")
                .setFactory(BlockWoodToolRack::new)
                .setFireInfo(5, 20)
                .build();

        SUPPORT = WoodBlockVariant
                .builder("support")
                .setFactory(BlockWoodSupport::new)
                .setFireInfo(5, 20)
                .build();

        WORKBENCH = WoodBlockVariant
                .builder("workbench")
                .setFactory(BlockWoodWorkbench::new)
                .setFireInfo(5, 20)
                .build();

        CHEST_TRAPPED = WoodBlockVariant
                .builder("chest_trapped")
                .setFactory(BlockWoodChest::new)
                .setFireInfo(5, 20)
                .build();

        CHEST = WoodBlockVariant
                .builder("chest")
                .setFactory(BlockWoodChest::new)
                .setFireInfo(5, 20)
                .build();

        LOOM = WoodBlockVariant
                .builder("loom")
                .setFactory(BlockWoodLoom::new)
                .setFireInfo(5, 20)
                .build();

        BARREL = WoodBlockVariant
                .builder("barrel")
                .setFactory(BlockWoodBarrel::new)
                .setFireInfo(5, 20)
                .build();

        LADDER = WoodBlockVariant
                .builder("ladder")
                .setFactory(BlockWoodLadder::new)
                .setFireInfo(5, 20)
                .build();

        //        CHOPPER = WoodBlockVariant
        //                .builder("chopper")
        //                .setFactory(BlockWoodChopper::new)
        //                .build();

        registry.blocks(WOOD_BLOCKS.values());
        //for (var block : TREE_BLOCKS) registry.registerBlock(block);

        //registry.registerBlocks(LeavesPaging.getLeavesMapForModId(Tags.MOD_ID).values().toArray(new Block[0]));

    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(RegistryManager registry) {
        ModelResourceLocation blockModelLocation = new ModelResourceLocation("tfg:leaves");
        ModelResourceLocation itemModelLocation = new ModelResourceLocation("tfg:leaves", "inventory");

        //        registry.registerClientModelRegistrationStrategy(() -> {
        //            //for (var tree : WoodTreeVariant.getTreeTypes()) ModelHelperTFC.regModel(tree);
        //        });

    }

    public static void onPostInitialization() {
        //        for (var type : WoodType.getTypes()) {
        //            FuelManager.addFuel(new Fuel(IIngredient.of(new ItemStack(StorageWood.get(LOG, type))), type.getBurnTicks(), type.getBurnTemp()));
        //        }
    }
}
