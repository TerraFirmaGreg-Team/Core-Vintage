package pieman.caffeineaddon.client;

import net.dries007.tfc.util.climate.ClimateTFC;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ColorizerGrass;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pieman.caffeineaddon.Reference;
import pieman.caffeineaddon.init.ModBlocks;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Reference.MOD_ID)
public final class ClientRegisterEvents {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerColorHandlerBlocks(ColorHandlerEvent.Block event) {
        BlockColors blockColors = event.getBlockColors();

        // Grass Colors
        IBlockColor grassColor = (state, worldIn, pos, tintIndex) -> {
            if (pos != null) {
                double temp = MathHelper.clamp((ClimateTFC.getMonthlyTemp(pos) + 30) / 60, 0, 1);
                double rain = MathHelper.clamp((ClimateTFC.getRainfall(pos) - 50) / 400, 0, 1);
                return ColorizerGrass.getGrassColor(temp, rain);
            }
            return ColorizerGrass.getGrassColor(0.5, 0.5);
        };

        // Foliage Color
        // todo: do something different for conifers - they should have a different color mapping through the seasons
        IBlockColor foliageColor = (state, worldIn, pos, tintIndex) -> {
            if (pos != null) {
                double temp = MathHelper.clamp((ClimateTFC.getMonthlyTemp(pos) + 30) / 60, 0, 1);
                double rain = MathHelper.clamp((ClimateTFC.getRainfall(pos) - 50) / 400, 0, 1);
                return ColorizerGrass.getGrassColor(temp, rain);
            }
            return ColorizerGrass.getGrassColor(0.5, 0.5);
        };

        blockColors.registerBlockColorHandler(foliageColor, ModBlocks.LEAVES);

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public static void registerColorHandlerItems(ColorHandlerEvent.Item event) {
        ItemColors itemColors = event.getItemColors();

        itemColors.registerItemColorHandler((stack, tintIndex) ->
                        event.getBlockColors().colorMultiplier(((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata()), null, null, tintIndex),
                ModBlocks.LEAVES);
    }

}
