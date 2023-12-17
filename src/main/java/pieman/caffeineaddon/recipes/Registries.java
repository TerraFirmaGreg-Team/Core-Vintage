package pieman.caffeineaddon.recipes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;
import pieman.caffeineaddon.Reference;
import pieman.caffeineaddon.blocks.BlockDryingMat;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class Registries {
    public static final ResourceLocation DRYINGMAT_RECIPE = new ResourceLocation(Reference.MOD_ID, "drying_mat_recipe");
    public static IForgeRegistry<DryingMatRecipe> DRYINGMAT = null;

    @SubscribeEvent
    public static void onNewRegistryEvent(RegistryEvent.NewRegistry event) {
        // Normal registries
        newRegistry(DRYINGMAT_RECIPE, DryingMatRecipe.class);
        DRYINGMAT = GameRegistry.findRegistry(DryingMatRecipe.class);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        final World world = event.getWorld();
        final BlockPos pos = event.getPos();
        final IBlockState state = world.getBlockState(pos);

        // Fire onBlockActivated for in world crafting devices
        if (state.getBlock() instanceof BlockDryingMat) {
            event.setUseBlock(Event.Result.ALLOW);
        }
    }

    private static <T extends IForgeRegistryEntry<T>> void newRegistry(ResourceLocation name, Class<T> tClass) {
        IForgeRegistry<T> reg = new RegistryBuilder<T>().setName(name).allowModification().setType(tClass).create();
    }


}
