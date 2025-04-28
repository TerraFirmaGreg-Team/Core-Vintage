package su.terrafirmagreg.modules.animal.plugin.top.provider;

import su.terrafirmagreg.api.base.plugin.top.provider.spi.BaseEntityProvider;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalMammal;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendarFormatted;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;

import java.util.ArrayList;
import java.util.List;

public class AnimalProvider extends BaseEntityProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "animal.gender");
  }

  @Override
  public void addProbeEntityInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, Entity entity, IProbeHitEntityData hitData) {

    if (entity instanceof IAnimal animal) {
      List<String> currentTooltip = new ArrayList<>();

      boolean familiarized = animal.getFamiliarity() > 0.15f;
      if (animal.getAdultFamiliarityCap() > 0) {
        currentTooltip.add(new TextComponentTranslation(familiarized
                                                        ? ModUtils.localize("top", "animal.familiarized")
                                                        : ModUtils.localize("top", "animal.not_familiarized")).getFormattedText());
      }
      switch (animal.getAge()) {
        case CHILD:
          long endPlayerTick = (long) (animal.getBirthDay() + animal.getDaysToAdulthood()) * ICalendar.TICKS_IN_DAY;
          long delta = endPlayerTick - Calendar.PLAYER_TIME.getTicks();
          long endCalendarTick = Calendar.CALENDAR_TIME.getTicks() + delta;
          String date = ICalendarFormatted.getTimeAndDate(endCalendarTick, Calendar.CALENDAR_TIME.getDaysInMonth());
          currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "animal.childhood_end"), date).getFormattedText());
          break;
        case OLD:
          currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "animal.old")).getFormattedText());
          // fall through here, can become old yet still be pregnant and give birth and/or give wool. All data retrieval below check correctly for age.
        case ADULT:
          if (familiarized) {
            if (animal.isReadyToMate()) {
              currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "animal.can_mate")).getFormattedText());
            }
            if (animal.isFertilized()) {
              if (animal.getType() == IAnimal.Type.MAMMAL) {
                currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "animal.pregnant")).getFormattedText());
                // In 1.15+ this will move to AnimalProperties and everything needed will be there
                // For 1.12, addons will need to either extend EntityAnimalMammal or handle the tooltip themselves
                if (animal instanceof EntityAnimalMammal mother) {
                  long gestationDaysRemaining = mother.getPregnantTime() + mother.gestationDays() - Calendar.PLAYER_TIME.getTotalDays();
                  currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "animal.pregnant_end"), gestationDaysRemaining).getFormattedText());
                }
              } else {
                currentTooltip.add(new TextComponentTranslation("tfc.tooltip.fertilized").getFormattedText());
              }
            }
            if (animal.isReadyForAnimalProduct()) {
              if (animal instanceof IShearable) {
                currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "animal.can_shear")).getFormattedText());
              } else if (animal.getType() == IAnimal.Type.OVIPAROUS) {
                currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "animal.has_eggs")).getFormattedText());
              } else {
                currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "animal.has_milk")).getFormattedText());
              }
            }
          }
          break;
      }
      switch (animal.getGender()) {
        case MALE:
          currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "animal.male")).getFormattedText());
          break;
        case FEMALE:
          currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "animal.female")).getFormattedText());
          break;
      }

      for (String string : currentTooltip) {
        info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).text(string);
      }
    }
  }
}
