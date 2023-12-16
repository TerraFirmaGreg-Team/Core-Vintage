package com.lumintorious.ambiental.api;

import com.lumintorious.ambiental.compat.Cellars;
import com.lumintorious.ambiental.compat.FirmaLife;
import com.lumintorious.ambiental.compat.TFC;
import com.lumintorious.ambiental.compat.TFCTech;
import com.lumintorious.ambiental.modifier.EnvironmentalModifier;
import com.lumintorious.ambiental.modifier.TempModifier;
import net.minecraft.init.Blocks;
import net.minecraft.world.EnumSkyBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;


public class AmbientalRegistry<Type> implements Iterable<Type> {
	// Add functions that provide modifiers, you can use checks and return null to skip
	// Use the "ModifierOwner" interfaces for self implemented objects
	// Use the these only for objects out of your control
	public static final AmbientalRegistry<IItemTemperatureProvider> ITEMS = new AmbientalRegistry<>();
	public static final AmbientalRegistry<IBlockTemperatureProvider> BLOCKS = new AmbientalRegistry<>();
	public static final AmbientalRegistry<ITileEntityTemperatureProvider> TILE_ENTITIES = new AmbientalRegistry<>();
	public static final AmbientalRegistry<IEnvironmentalTemperatureProvider> ENVIRONMENT = new AmbientalRegistry<>();

	public static final AmbientalRegistry<IEquipmentTemperatureProvider> EQUIPMENT = new AmbientalRegistry<>();


	static {
		// GTCEu
//		BLOCKS.register((state, pos, player) -> state.equals(MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.CUPRONICKEL)) ? new BlockModifier("cupronickel_coil", 3f, 3f) : null);
//		BLOCKS.register((state, pos, player) -> state.equals(MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.KANTHAL)) ? new BlockModifier("kanthal_coil", 3f, 3f) : null);
//		BLOCKS.register((state, pos, player) -> state.equals(MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.NICHROME)) ? new BlockModifier("nichrome_coil", 3f, 3f) : null);
//		BLOCKS.register((state, pos, player) -> state.equals(MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.TUNGSTENSTEEL)) ? new BlockModifier("tungstensteel_coil", 3f, 3f) : null);
//		BLOCKS.register((state, pos, player) -> state.equals(MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.HSS_G)) ? new BlockModifier("hss_g_coil", 3f, 3f) : null);
//		BLOCKS.register((state, pos, player) -> state.equals(MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.NAQUADAH)) ? new BlockModifier("naquadah_coil", 3f, 3f) : null);
//		BLOCKS.register((state, pos, player) -> state.equals(MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.TRINIUM)) ? new BlockModifier("trinium_coil", 3f, 3f) : null);
//		BLOCKS.register((state, pos, player) -> state.equals(MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.TRITANIUM)) ? new BlockModifier("tritanium_coil", 3f, 3f) : null);

		BLOCKS.register((player, pos, state) -> Optional.of(new TempModifier("torch", 3f, 0f))
		                                                .filter((mod) -> state.getBlock() == Blocks.TORCH));
		BLOCKS.register((player, pos, state) -> Optional.of(new TempModifier("fire", 3f, 0f))
		                                                .filter((mod) -> state.getBlock() == Blocks.FIRE));
		BLOCKS.register((player, pos, state) -> Optional.of(new TempModifier("lava", 3f, 0f))
		                                                .filter((mod) -> state.getBlock() == Blocks.LAVA));
		BLOCKS.register((player, pos, state) -> Optional.of(new TempModifier("flowing_lava", 3f, 0f))
		                                                .filter((mod) -> state.getBlock() == Blocks.FLOWING_LAVA));
		BLOCKS.register((player, pos, state) -> Optional.of(new TempModifier("snow", -1.5f, 0.2f))
		                                                .filter((mod) -> state.getBlock() == Blocks.SNOW_LAYER));
		BLOCKS.register((player, pos, state) -> Optional.of(new TempModifier("snow", -0.5f, 0.2f))
		                                                .filter((mod) -> state.getBlock() == Blocks.SNOW && player.world.getLightFor(EnumSkyBlock.SKY, pos) == 15));

		// TFC-Tech
		TILE_ENTITIES.register(TFCTech::handleSmelteryFirebox); // Топило для печи для стекла
		TILE_ENTITIES.register(TFCTech::handleSmelteryCauldron); // Печь для стекла
		TILE_ENTITIES.register(TFCTech::handleElectricForge); // Тигель
		TILE_ENTITIES.register(TFCTech::handleInductionCrucible); // Кузня
		TILE_ENTITIES.register(TFCTech::handleFridge); // Холодос

		// Cellar
		TILE_ENTITIES.register(Cellars::handleCellar); // Подвал

		// Firmalife
		TILE_ENTITIES.register(FirmaLife::handleClayOven); // Oven

		// TFC
		TILE_ENTITIES.register(TFC::handleCharcoalForge); // Угольная кузня
		TILE_ENTITIES.register(TFC::handleFirePit); // Костер
		TILE_ENTITIES.register(TFC::handleBloomery); // Доменка
		TILE_ENTITIES.register(TFC::handleLamps); // Лампа
		TILE_ENTITIES.register(TFC::handleCrucible); // Тигель

		ENVIRONMENT.register(EnvironmentalModifier::handleGeneralTemperature);
		ENVIRONMENT.register(EnvironmentalModifier::handleTimeOfDay);
		ENVIRONMENT.register(EnvironmentalModifier::handleShade);
		ENVIRONMENT.register(EnvironmentalModifier::handleCozy);
		ENVIRONMENT.register(EnvironmentalModifier::handleThirst);
		ENVIRONMENT.register(EnvironmentalModifier::handleFood);
		ENVIRONMENT.register(EnvironmentalModifier::handleDiet);
		ENVIRONMENT.register(EnvironmentalModifier::handleFire);
		ENVIRONMENT.register(EnvironmentalModifier::handleWater);
		ENVIRONMENT.register(EnvironmentalModifier::handleRain);
		ENVIRONMENT.register(EnvironmentalModifier::handleSprinting);
		ENVIRONMENT.register(EnvironmentalModifier::handleUnderground);
		ENVIRONMENT.register(EnvironmentalModifier::handlePotionEffects);
	}

	private final ArrayList<Type> list = new ArrayList<>();
	private final HashMap<String, Type> map = new HashMap<>();

	private AmbientalRegistry() {}

	public void register(Type type) {
		list.add(type);
	}

	public boolean has(Type type) {
		return map.containsValue(type) || list.contains(type);
	}

	@Override
	public Iterator<Type> iterator() {
		return new Iterator<Type>() {

			private Iterator listIterator = list.iterator();

			@Override
			public boolean hasNext() {
				return listIterator.hasNext();
			}

			@Override
			public Type next() {
				return (Type) listIterator.next();
			}

		};
	}


}
