package su.terrafirmagreg.core.modules.ambiental.api;

import su.terrafirmagreg.core.TFGConfig;
import su.terrafirmagreg.core.modules.ambiental.modifier.EnvironmentalModifier;
import su.terrafirmagreg.core.modules.ambiental.modifier.TempModifier;
import su.terrafirmagreg.core.modules.ambiental.modifier.TempModifierStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import java.util.Optional;

// Add an example of this into TemperatureRegistry for tile entities you didn't create personally
@FunctionalInterface
public interface ITileEntityTemperatureProvider {
	float mod = 0.3F;

	static void evaluateAll(EntityPlayer player, TempModifierStorage storage) {
		IBlockTemperatureProvider.evaluateAll(player, storage);
	}

	static boolean hasProtection(EntityPlayer player) {
//		var item = CuriosApi.getCuriosHelper().findCurios(player, TFCAmbientalItems.LEATHER_APRON.get());
//		if(item.isEmpty()) return false;
		float environmentTemperature = EnvironmentalModifier.getEnvironmentTemperatureWithTimeOfDay(player);
		float AVERAGE = TFGConfig.GENERAL.averageTemperature;
		return environmentTemperature > AVERAGE;
	}

	Optional<TempModifier> getModifier(EntityPlayer player, TileEntity tile);

//	public static Optional<TempModifier>  handleIHeatBlock(EntityPlayer player, TileEntity tile) {
//		return tile.getCapability(HeatCapability.BLOCK_CAPABILITY).map(cap -> {
//			return new TempModifier(
//					tile.getClass().getName().toLowerCase(Locale.ROOT),
//					cap.getField() / 140f,
//					0
//			);
//		});
//	}
}
