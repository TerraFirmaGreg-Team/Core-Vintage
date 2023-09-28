package su.terrafirmagreg.tfg.modules.core.api.capability.food;

import net.dries007.tfc.client.ClientProxy;
import net.dries007.tfc.common.CommonEventHandler;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.ICalendarFormatted;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Capability for any food item
 * Allows foods to have nutrients, and also to decay / rot
 */
public interface IFood extends INBTSerializable<NBTTagCompound> {
    /**
     * The timestamp that this food was created
     * Used to calculate expiration date
     * Rotten food uses {@code Long.MIN_VALUE} as the creation date
     *
     * @return the calendar time of creation
     */
    long getCreationDate();

    /**
     * Sets the creation date. DO NOT USE TO PRESERVE FOOD! Use {@link FoodTrait} instead
     *
     * @param creationDate A calendar time
     */
    void setCreationDate(long creationDate);

    /**
     * Get the date at which this food item will rot
     *
     * @return a calendar time
     */
    long getRottenDate();

    /**
     * @return true if the food is rotten / decayed.
     */
    default boolean isRotten() {
        return getRottenDate() < CalendarTFC.PLAYER_TIME.getTicks();
    }

    /**
     * Get a visible measure of all immutable data associated with food
     * - Nutrition information
     * - Hunger / Saturation
     * - Water (Thirst)
     *
     * @see FoodData
     */
    @Nonnull
    FoodData getData();

    /**
     * Gets the current decay date modifier, including traits
     * Note: there's a difference between the DECAY modifier, and the DECAY DATE modifier, in that they are reciprocals of eachother
     *
     * @return a value between 0 and infinity (0 = instant decay, infinity = never decay)
     */
    float getDecayDateModifier();

    /**
     * Called from {@link CommonEventHandler#attachItemCapabilities(AttachCapabilitiesEvent)}
     * If the item is a food capability item, and it was created before the post init, we assume that it is a technical stack, and will not appear in the world without a copy. As such, we set it to non-decaying.
     * This is NOT SERIALIZED on the capability - as a result it will not persist across {@link ItemStack#copy()},
     * See TerraFirmaGreg#458
     */
    void setNonDecaying();

    /**
     * Gets the current list of traits on this food
     * Can also be used to add traits to the food
     *
     * @return the traits of the food
     */
    @Nonnull
    List<FoodTrait> getTraits();

    /**
     * Tooltip added to the food item
     * Called from {@link ClientProxy}
     *
     * @param stack the stack in question
     * @param text  the tooltip
     */
    @SideOnly(Side.CLIENT)
    default void addTooltipInfo(@Nonnull ItemStack stack, @Nonnull List<String> text) {
        text.add("");
        text.add(I18n.format("tfc.tooltip.food_data"));

        // Expiration dates
        if (isRotten()) {
            text.add(I18n.format("tfc.tooltip.food_rotten"));
            return;
        }

        long rottenDate = getRottenDate();
        if (rottenDate == Long.MAX_VALUE) {
            text.add(I18n.format("tfc.tooltip.food_infinite_expiry"));
        } else {
            // Date food rots on.
            long rottenCalendarTime = rottenDate - CalendarTFC.PLAYER_TIME.getTicks() + CalendarTFC.CALENDAR_TIME.getTicks();
            // Days till food rots.
            long daysToRotInTicks = rottenCalendarTime - CalendarTFC.CALENDAR_TIME.getTicks();
            text.add(String.format("%s %s",
                    I18n.format("tfc.tooltip.food_expiry_date", ICalendarFormatted.getTimeAndDate(rottenCalendarTime, CalendarTFC.CALENDAR_TIME.getDaysInMonth())),
                    I18n.format("tfc.tooltip.food_expiry_date.days", String.valueOf(ICalendar.getTotalDays(daysToRotInTicks)))
            ));
        }

        // Nutrition / Hunger / Saturation / Water Values
        var saturation = getData().getSaturation();
        var water = getData().getWater();
        var nutrients = getData().getNutrients();
        var nutrientsStream = IntStream.range(0, nutrients.length).mapToDouble(i -> nutrients[i]);

        if (saturation <= 0 && water <= 0 && nutrientsStream.allMatch(s -> s <= 0)) {
            text.add(I18n.format("tfc.tooltip.nutrition.none"));
            return;
        }

        text.add(I18n.format("tfc.tooltip.nutrition"));

        if (saturation > 0) {
            // This display makes it so 100% saturation means a full hunger bar worth of saturation.
            text.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.nutrition_saturation", String.format("%d", (int) (saturation * 5))));
        }

        if (water > 0) {
            text.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.nutrition_water", String.format("%d", (int) water)));
        }

        for (var nutrient : Nutrient.values()) {
            float value = nutrients[nutrient.ordinal()];
            if (value > 0) {
                text.add(nutrient.getColor() + I18n.format("tfc.tooltip.nutrition_nutrient", I18n.format(Helpers.getEnumName(nutrient)), String.format("%.1f", value)));
            }
        }

        var foodTraits = getTraits();

        if (foodTraits.isEmpty()) {
            text.add(I18n.format("tfc.food_traits.none"));
        } else {
            text.add(I18n.format("tfc.food_traits"));
            for (var trait : foodTraits) {
                trait.addTraitInfo(stack, text);
            }
        }
    }
}
