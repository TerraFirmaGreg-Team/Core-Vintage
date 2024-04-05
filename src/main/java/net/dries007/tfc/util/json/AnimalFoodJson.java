package net.dries007.tfc.util.json;

import com.google.gson.*;
import net.dries007.tfc.objects.entity.animal.AnimalFood;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;

import java.lang.reflect.Type;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

public class AnimalFoodJson implements JsonDeserializer<AnimalFood> {
	@Override
	public AnimalFood deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = JsonUtils.getJsonObject(json, "entity");
		AnimalFood animalFood = new AnimalFood(jsonObject.get("eat_rotten").getAsBoolean());
		JsonObject food = JsonUtils.getJsonObject(jsonObject, "foods");
		food.entrySet().forEach(entry ->
		{
			Ingredient ingredient = CraftingHelper.getIngredient(entry.getValue(), new JsonContext(MODID_TFC));
			animalFood.addFood(ingredient);
		});
		return animalFood;
	}
}
