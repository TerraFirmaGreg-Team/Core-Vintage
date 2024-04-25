package net.dries007.tfc.api.capability.food;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.sharkbark.cellars.ModConfig;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a trait that can be applied to a food to modify it's decay date. To add new traits, simply create new instances of this class, and assign a
 * unique name
 */
public class FoodTrait {

    public static final FoodTrait SALTED;
    public static final FoodTrait BRINED; // No decay modifier, required to pickle foods
    public static final FoodTrait PICKLED;
    public static final FoodTrait PRESERVED; // Large / Small vessels
    public static final FoodTrait VINEGAR; // Used for the state of being sealed in vinegar
    public static final FoodTrait CHARCOAL_GRILLED; // Slight debuff from cooking in a charcoal forge
    public static final FoodTrait WOOD_GRILLED; // Slight bugg when cooking in a grill
    public static final FoodTrait BURNT_TO_A_CRISP; // Cooking food in something that's WAY TOO HOT too cook food in you fool!
    public static final FoodTrait COOL;
    public static final FoodTrait ICY;
    public static final FoodTrait FREEZING;
    public static final FoodTrait DRY;
    public static final FoodTrait PRESERVING;
    public static final FoodTrait COLD;
    public static final FoodTrait FROZEN;
    public static final FoodTrait SMOKED;
    public static final FoodTrait FRESH; // These should eventually do something besides just modifying decay rate, for now they're here as an incomplete feature
    public static final FoodTrait AGED;
    public static final FoodTrait VINTAGE;

    private static final Map<String, FoodTrait> TRAITS = new HashMap<>();

    static {
        // These must be initialized after TRAITS is, to avoid NPE

        BRINED = new FoodTrait("brined", 1.0f);
        SALTED = new FoodTrait("salted", 0.5f);
        PICKLED = new FoodTrait("pickled", 0.5f);
        PRESERVED = new FoodTrait("preserved", 0.5f);
        VINEGAR = new FoodTrait("vinegar", 0.1f);
        CHARCOAL_GRILLED = new FoodTrait("charcoal_grilled", 1.25f);
        WOOD_GRILLED = new FoodTrait("wood_grilled", 0.8f);
        BURNT_TO_A_CRISP = new FoodTrait("burnt_to_a_crisp", 2.5f); // This one is so high as it is meant to be > the existing gain from cooking meat.
        COOL = new FoodTrait("sharkCool", ModConfig.coolMod);
        ICY = new FoodTrait("sharkIcy", ModConfig.icyMod);
        FREEZING = new FoodTrait("sharkIcle", ModConfig.icleMod);
        DRY = new FoodTrait("sharkDry", ModConfig.dryMod);
        PRESERVING = new FoodTrait("sharkPreserving", ModConfig.preservingMod);
        COLD = new FoodTrait("cold", 0.25f);
        FROZEN = new FoodTrait("frozen", 0.1f);
        SMOKED = new FoodTrait("smoked", 0.25F);
        FRESH = new FoodTrait("fresh", 1.4F);
        AGED = new FoodTrait("aged", 1.0F);
        VINTAGE = new FoodTrait("vintage", 0.6F);
    }

    private final String name;
    private final float decayModifier;
    private final boolean hasTooltip;

    public FoodTrait(@NotNull String name, float decayModifier) {
        this(name, decayModifier, true);
    }

    public FoodTrait(@NotNull String name, float decayModifier, boolean hasTooltip) {
        this.name = name;
        this.decayModifier = decayModifier;
        this.hasTooltip = hasTooltip;

        // Require a unique trait
        if (TRAITS.containsKey(name)) {
            throw new IllegalStateException("There is already a trait with the name '" + name + "'");
        }
        TRAITS.put(name, this);
    }

    public static Map<String, FoodTrait> getTraits() {
        return TRAITS;
    }

    public float getDecayModifier() {
        return decayModifier;
    }

    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Adds information about the trait to the food stack
     *
     * @param stack The stack
     * @param text  The tooltip strings
     */
    @SideOnly(Side.CLIENT)
    public void addTraitInfo(@NotNull ItemStack stack, @NotNull List<String> text) {
        if (hasTooltip) {
            text.add(I18n.format("tfc.food_traits." + getName()));
        }
    }
}
