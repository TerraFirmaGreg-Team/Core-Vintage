package com.speeedcraft.tfgmod;

import com.speeedcraft.tfgmod.gregtech.material.TFGMaterialHandler;
import com.speeedcraft.tfgmod.gregtech.oreprefix.TFGOrePrefixHandler;
import com.speeedcraft.tfgmod.gregtech.recipes.TFGRecipeHandlerList;
import com.speeedcraft.tfgmod.gregtech.stonetypes.StoneTypeHandler;
import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.event.PostMaterialEvent;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.ToolProperty;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = TFGMod.MOD_ID)
public final class TFGCommonEventHandler {

	@SubscribeEvent
	public static void registerMaterials(MaterialEvent event) {
		TFGMaterialHandler.init();
		TFGOrePrefixHandler.init();
		StoneTypeHandler.init();
	}

	@SubscribeEvent
	public static void registerMaterialsPost(PostMaterialEvent event) {
		TFGMaterialHandler.postInit();
		for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {
			if (material.hasProperty(PropertyKey.TOOL)) {
				ToolProperty toolProperty = material.getProperty(PropertyKey.TOOL);
				toolProperty.setToolDurability(toolProperty.getToolDurability() * 7);
			}
		}
	}

	@SubscribeEvent
	public static void onRegisterRecipes(RegistryEvent.Register<IRecipe> event) {
		TFGRecipeHandlerList.register();
	}

	@SubscribeEvent
	public static void onNetherPortalActivating(BlockEvent.PortalSpawnEvent event) {
		event.setCanceled(true);
	}
}
