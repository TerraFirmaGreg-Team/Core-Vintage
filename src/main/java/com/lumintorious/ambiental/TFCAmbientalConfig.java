package com.lumintorious.ambiental;

import net.minecraftforge.common.config.Config;

import static com.lumintorious.ambiental.TFCAmbiental.MODID;

@Config(modid = MODID)
public class TFCAmbientalConfig {

	@Config.Comment("Client Settings")
	public static final ClientCFG CLIENT = new ClientCFG();

	@Config.Comment("General Settings")
	public static final GeneralCFG GENERAL = new GeneralCFG();

	public static class ClientCFG {

		@Config.Comment("If true, you will get extra details about your temperature when sneaking, when false they are always visible.")
		public boolean sneakyDetails = false;

		@Config.Comment("Overlay when you burning or freezing.")
		public boolean enableOverlay = true;
	}

	public static class GeneralCFG {

		@Config.Comment("The average point for temperature, the not too warm and not too cool point")
		@Config.RangeDouble(min = 0F, max = 30F)
		public float averageTemperature = 15F;

		@Config.Comment("The point where warmth starts to affect the screen, but only mildly")
		@Config.RangeDouble(min = 5F, max = 35F)
		public float hotThreshold = 25F;

		@Config.Comment("The point where cold starts to affect the screen, but only mildly")
		@Config.RangeDouble(min = -15F, max = 25F)
		public float coolThreshold = 5F;

		@Config.Comment("The point where warmth starts to hurt the player")
		@Config.RangeDouble(min = 15F, max = 45F)
		public float burnThreshold = 30F;

		@Config.Comment("The point where cold starts to hurt the player")
		@Config.RangeDouble(min = -15F, max = 15F)
		public float freezeThreshold = 0F;

		@Config.Comment("How quickly player temperature changes towards the target environment temperature")
		@Config.RangeDouble(min = 0F, max = 30F)
		public float temperatureChangeSpeed = 1F;

		@Config.Comment("How quickly player temperature changes towards the target environment temperature when it's beneficial to do so")
		@Config.RangeDouble(min = 0F, max = 50F)
		public float goodTemperatureChangeSpeed = 4F;

		@Config.Comment("How quickly player temperature changes towards the target environment temperature when it's not beneficial")
		@Config.RangeDouble(min = 0F, max = 50F)
		public float badTemperatureChangeSpeed = 1F;



		@Config.Comment("How potent are multipliers with more than one instance. (Eg. 2 fire pits nearby means they have 2 * this effectiveness). Default = 0.7")
		public float diminishedModifierMultiplier = 0.7F;

		@Config.Comment("If harsherTemperateAreas is true, environmental temperatures going away from the average are multiplied by this number. (The less temperate an area is, the less the modifier affects it). Default = 1.2 ")
		public float harsherMultiplier = 1.20F;

		@Config.Comment("How many modifiers of the same type until they stop adding together. Default = 4")
		public int modifierCap = 4;

		@Config.Comment("The temperature that nano or quantum armor will do when you are in a full set.")
		public int nanoOrQuarkTemp = 20;

		@Config.Comment("Allowed Dims")
		public int[] allowedDims = new int[] {0};

		@Config.Comment("If true, you will start taking damage when below freezing or above burning temperatures. Default = true")
		public boolean takeDamage = false;

		@Config.Comment("If true, you will start losing hunger when below cold temperatures and losing thirst when above hot temperatures. Default = true")
		public boolean loseHungerThirst = true;

		@Config.Comment("If true, temperate areas won't be as mild. Default = true")
		public boolean harsherTemperateAreas = true;
	}
}
