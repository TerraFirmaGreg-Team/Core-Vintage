/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.potioneffects;


import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static net.dries007.tfc.TerraFirmaCraft.MODID_TFC;
import static net.dries007.tfc.util.Helpers.getNull;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID_TFC)
@GameRegistry.ObjectHolder(MODID_TFC)
public final class PotionEffectsTFC {

  public static final Potion OVERBURDENED = getNull();
  public static final Potion THIRST = getNull();
  public static final Potion FOOD_POISON = getNull();

  @SubscribeEvent
  public static void registerPotionEffects(RegistryEvent.Register<Potion> event) {
    event.getRegistry().registerAll(
      new PotionOverburdened().setRegistryName(MODID_TFC, "overburdened"),
      new PotionThirst().setRegistryName(MODID_TFC, "thirst"),
      new PotionFoodPoison().setRegistryName(MODID_TFC, "food_poison")
    );
  }
}
