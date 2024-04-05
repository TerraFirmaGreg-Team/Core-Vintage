package pieman.caffeineaddon.potion;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static net.dries007.tfc.util.Helpers.getNull;
import static su.terrafirmagreg.api.lib.Constants.MODID_CAFFEINEADDON;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID_CAFFEINEADDON)
@GameRegistry.ObjectHolder(MODID_CAFFEINEADDON)
public final class PotionEffects {

	public static final Potion CAFFEINE = getNull();

	@SubscribeEvent
	public static void registerPotionEffects(RegistryEvent.Register<Potion> event) {
		event.getRegistry().registerAll(
				new PotionCaffeine().setRegistryName(MODID_CAFFEINEADDON, "caffeine")
				                    .registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.15D, 2)
		);
	}
}
