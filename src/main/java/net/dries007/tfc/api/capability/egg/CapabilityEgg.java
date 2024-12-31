/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.api.capability.egg;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import net.dries007.tfc.api.capability.DumbStorage;

import static su.terrafirmagreg.old.api.data.Reference.TFC;

public class CapabilityEgg {

  public static final ResourceLocation KEY = new ResourceLocation(TFC, "egg");
  @CapabilityInject(IEgg.class)
  public static Capability<IEgg> CAPABILITY;

  public static void preInit() {
    CapabilityManager.INSTANCE.register(IEgg.class, new DumbStorage<>(), EggHandler::new);
  }
}
