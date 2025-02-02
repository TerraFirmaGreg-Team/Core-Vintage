package net.dries007.tfctech.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Tree;

import su.terrafirmagreg.api.data.enums.Mods;

import static net.dries007.tfc.types.DefaultTrees.GEN_TALL;
import static net.dries007.tfc.util.Helpers.getNull;
import static su.terrafirmagreg.api.data.enums.Mods.Names.TFCTECH;

@Mod.EventBusSubscriber(modid = TFCTECH)
public final class TechTrees {

  @GameRegistry.ObjectHolder(Mods.Names.TFC + ":hevea")
  public static final Tree HEVEA = getNull();

  @SubscribeEvent
  public static void onPreRegisterTrees(TFCRegistryEvent.RegisterPreBlock<Tree> event) {
    event.getRegistry().registerAll(
      new Tree.Builder(new ResourceLocation(Mods.Names.TFC, "hevea"), 140f, 350f, 7f, 27f, GEN_TALL)
        .setDensity(0.1f, 0.6f).setRadius(2).setGrowthTime(10).setBurnInfo(762f, 2000).build()
    );
  }
}

