package su.terrafirmagreg.modules.wood.data;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;

public final class BlocksWood {

    public static final Map<Pair<WoodBlockVariant, WoodType>, Block> WOOD_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();

    public static void onRegister(RegistryManager registry) {

        registry.registerBlocks(WOOD_BLOCKS.values());
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
