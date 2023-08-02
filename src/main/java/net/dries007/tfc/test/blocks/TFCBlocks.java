package net.dries007.tfc.test.blocks;

import net.dries007.tfc.api.types2.plant.PlantType;
import net.dries007.tfc.api.types2.rock.RockBlockType;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.objects.blocks.BlockDebug;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

import static net.dries007.tfc.api.registries.TFCStorage.*;

public class TFCBlocks {

	public static BlockDebug DEBUG;

	// Тут возможно размещать карты
	private TFCBlocks() {
	}

	public static void preInit() {

		//=== Rock ===================================================================================================//

		for (RockType rockType : RockType.values()) {
			for (RockBlockType rockBlockType : RockBlockType.values()) {
				for (RockVariant rockVariant : rockBlockType.getRockVariants()) {
					var rockTypeBlock = rockBlockType.createBlockType(rockVariant, rockType);

					if (ROCK_BLOCKS.put(new Triple<>(rockBlockType, rockVariant, rockType), rockTypeBlock) != null)
						throw new RuntimeException(String.format("Duplicate registry detected: %s, %s, %s", rockBlockType, rockVariant, rockType));
				}
			}
		}

		//=== Soil ===================================================================================================//

		for (SoilType soilType : SoilType.values()) {
			for (SoilVariant soilVariant : SoilVariant.values()) {
				var soilTypeBlock = soilVariant.createBlock(soilType);

				if (SOIL_BLOCKS.put(new Pair<>(soilVariant, soilType), soilTypeBlock) != null)
					throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", soilVariant, soilType));
			}
		}

		//=== Plant ==================================================================================================//

		for (PlantType plantType : PlantType.values()) {
			var plantTypeBlock = plantType.getPlantVariant().create(plantType);

			if (PLANT_BLOCKS.put(new Pair<>(plantType.getPlantVariant(), plantType), plantTypeBlock) != null)
				throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", plantType.getPlantVariant(), plantType));
		}

		//=== Other ==================================================================================================//

		NORMAL_ITEM_BLOCKS.add(new ItemBlockTFC(new BlockDebug()));
	}
}
