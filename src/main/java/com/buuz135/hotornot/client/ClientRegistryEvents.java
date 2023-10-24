package com.buuz135.hotornot.client;

import com.buuz135.hotornot.object.item.HONItems;
import com.buuz135.hotornot.object.item.ItemMetalTongsJawMold;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static com.buuz135.hotornot.HotOrNot.MOD_ID;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid = MOD_ID, value = Side.CLIENT)
public final class ClientRegistryEvents {

	@SubscribeEvent
	public static void onModelRegister(final ModelRegistryEvent event) {
		HONItems.getAllSimpleItems().forEach(ClientRegistryEvents::registerModel);

		{ // Registering of our jaw molds
			final ItemMetalTongsJawMold moldItem = HONItems.TONGS_JAW_FIRED_MOLD;

			//noinspection DataFlowIssue
			ModelBakery.registerItemVariants(moldItem, new ModelResourceLocation(moldItem.getRegistryName().toString() + "/empty"));

			//noinspection DataFlowIssue
			ModelBakery.registerItemVariants(moldItem, TFCRegistries.METALS.getValuesCollection()
					.stream()
					.filter(metal -> metal.isToolMetal() && metal.getTier().isAtMost(Metal.Tier.TIER_II))
					.map(metal -> new ModelResourceLocation(
							MOD_ID + ":" + moldItem.getRegistryName().getPath() + "/" + metal.getRegistryName().getPath()))
					.toArray(ModelResourceLocation[]::new));
			ModelLoader.setCustomMeshDefinition(moldItem, new ItemMeshDefinition() {
				private final ModelResourceLocation FALLBACK = new ModelResourceLocation(moldItem.getRegistryName().toString() + "/empty");

				@Override
				@Nonnull
				public ModelResourceLocation getModelLocation(final ItemStack itemStack) {
					final IFluidHandler fluidHandler = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
					if (!(fluidHandler instanceof IMoldHandler)) return FALLBACK;

					final Metal metal = ((IMoldHandler) fluidHandler).getMetal();
					//noinspection DataFlowIssue
					return metal != null ? new ModelResourceLocation(
							itemStack.getItem().getRegistryName() + "/" + metal.getRegistryName().getPath()) : FALLBACK;
				}
			});
		}
	}

	private static void registerModel(final Item item) {
		//noinspection DataFlowIssue
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}