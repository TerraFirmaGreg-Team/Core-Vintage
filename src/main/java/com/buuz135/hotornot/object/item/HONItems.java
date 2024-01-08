package com.buuz135.hotornot.object.item;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Metal.Tier;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ceramics.ItemPottery;
import net.dries007.tfc.util.Helpers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

import static com.buuz135.hotornot.HotOrNot.HOTORNOT_TAB;
import static su.terrafirmagreg.Constants.MODID_HOTORNOT;

@ObjectHolder(value = MODID_HOTORNOT)
@EventBusSubscriber(modid = MODID_HOTORNOT)
public final class HONItems {

	@ObjectHolder("ceramics/fired/mold/tongs_jaw")
	public static final ItemMetalTongsJawMold TONGS_JAW_FIRED_MOLD = Helpers.getNull();
	@ObjectHolder("ceramics/unfired/mold/tongs_jaw")
	public static final ItemPottery TONGS_JAW_UNFIRED_MOLD = Helpers.getNull();
	private static ImmutableList<Item> allSimpleItems;

	public static ImmutableList<Item> getAllSimpleItems() {
		return allSimpleItems;
	}

	@SubscribeEvent
	public static void registerItem(final Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		final Builder<Item> simpleItems = ImmutableList.builder();

		simpleItems.add(register(registry, "ceramics/unfired/mold/tongs_jaw", new ItemPottery(), CreativeTabsTFC.CT_POTTERY));

		register(registry, "ceramics/fired/mold/tongs_jaw", new ItemMetalTongsJawMold(), CreativeTabsTFC.CT_POTTERY);

		simpleItems.add(register(registry, "wooden_tongs",
				new ItemHotHolder(Tier.TIER_0)
						.setMaxDamage(200)));

		simpleItems.add(register(registry, "mitts",
				new ItemHotHolder(Tier.TIER_II)
						.setMaxDamage(5_000)));

		for (final Metal metal : TFCRegistries.METALS.getValuesCollection()) {
			// Only make tongs for metals that make tools
			if (!metal.isToolMetal()) continue;

			simpleItems.add(register(registry, "metal/tongs/" + metal, new ItemMetalTongs(metal)));
			simpleItems.add(register(registry, "metal/tongs_jaw/" + metal, new ItemMetalTongsHead(metal)));
		}

		allSimpleItems = simpleItems.build();
	}

	private static <T extends Item> T register(final IForgeRegistry<Item> r, final String name, final T item) {
		return register(r, name, item, HOTORNOT_TAB);
	}

	private static <T extends Item> T register(final IForgeRegistry<Item> r, final String name, final T item, final CreativeTabs ct) {
		item.setRegistryName(MODID_HOTORNOT, name);
		item.setTranslationKey(MODID_HOTORNOT + "." + name.replace('/', '.'));
		item.setCreativeTab(ct);
		r.register(item);
		return item;
	}
}
