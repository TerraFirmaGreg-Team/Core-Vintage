package net.dries007.tfc.compat.waila.providers;

import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalMammal;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.IShearable;

import net.dries007.tfc.compat.waila.interfaces.IWailaEntity;
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.ICalendarFormatted;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnimalProvider implements IWailaEntity {

  @NotNull
  @Override
  public List<String> getTooltip(@NotNull Entity entity, @NotNull NBTTagCompound nbt) {
    List<String> currentTooltip = new ArrayList<>();
    if (entity instanceof IAnimal animal) {
      boolean familiarized = animal.getFamiliarity() > 0.15f;
      if (animal.getAdultFamiliarityCap() > 0) {
        currentTooltip.add(new TextComponentTranslation(
          familiarized ? "waila.tfc.animal.familiarized" : "waila.tfc.animal.not_familiarized").getFormattedText());
      }
      switch (animal.getAge()) {
        case CHILD:
          long endPlayerTick = (long) (animal.getBirthDay() + animal.getDaysToAdulthood()) * ICalendar.TICKS_IN_DAY;
          long delta = endPlayerTick - Calendar.PLAYER_TIME.getTicks();
          long endCalendarTick = Calendar.CALENDAR_TIME.getTicks() + delta;
          String date = ICalendarFormatted.getTimeAndDate(endCalendarTick, Calendar.CALENDAR_TIME.getDaysInMonth());
          currentTooltip.add(new TextComponentTranslation("waila.tfc.animal.childhood_end", date).getFormattedText());
          break;
        case OLD:
          currentTooltip.add(new TextComponentTranslation("waila.tfc.animal.old").getFormattedText());
          // fall through here, can become old yet still be pregnant and give birth and/or give wool. All data retrieval below check correctly for age.
        case ADULT:
          if (familiarized) {
            if (animal.isReadyToMate()) {
              currentTooltip.add(new TextComponentTranslation("waila.tfc.animal.can_mate").getFormattedText());
            }
            if (animal.isFertilized()) {
              if (animal.getType() == IAnimal.Type.MAMMAL) {
                currentTooltip.add(new TextComponentTranslation("waila.tfc.animal.pregnant").getFormattedText());
                // In 1.15+ this will move to AnimalProperties and everything needed will be there
                // For 1.12, addons will need to either extend EntityAnimalMammal or handle the tooltip themselves
                if (animal instanceof EntityAnimalMammal mother) {
                  long gestationDaysRemaining =
                    mother.getPregnantTime() + mother.gestationDays() - Calendar.PLAYER_TIME.getTotalDays();
                  currentTooltip.add(
                    new TextComponentTranslation("waila.tfc.animal.pregnant_end", gestationDaysRemaining).getFormattedText());
                }
              } else {
                currentTooltip.add(new TextComponentTranslation("tfc.tooltip.fertilized").getFormattedText());
              }
            }
            if (animal.isReadyForAnimalProduct()) {
              if (animal instanceof IShearable) {
                currentTooltip.add(new TextComponentTranslation("waila.tfc.animal.can_shear").getFormattedText());
              } else if (animal.getType() == IAnimal.Type.OVIPAROUS) {
                currentTooltip.add(new TextComponentTranslation("waila.tfc.animal.has_eggs").getFormattedText());
              } else {
                currentTooltip.add(new TextComponentTranslation("waila.tfc.animal.has_milk").getFormattedText());
              }
            }
          }
          break;
      }
    }
    return currentTooltip;
  }

  @NotNull
  @Override
  public List<Class<?>> getLookupClass() {
    return Collections.singletonList(IAnimal.class);
  }
}
