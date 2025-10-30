package su.terrafirmagreg.modules.rock.feature.rocktype;

import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.modules.rock.api.types.category.RockCategoryHandler;
import su.terrafirmagreg.modules.rock.api.types.type.RockTypeHandler;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class FeatureRockType extends BaseFeature {


  public FeatureRockType() {
    super(FeatureSettings.of()
      .registryKey("rock_type")
    );

    RockCategoryHandler.init();
    RockTypeHandler.init();
  }


  @SubscribeEvent(priority = EventPriority.HIGHEST)
  @SideOnly(Side.CLIENT)
  public static void onItemTooltipEvent(ItemTooltipEvent event) {

    var stack = event.getItemStack();
    var tooltip = event.getToolTip();

    if (!StackUtils.isValid(stack)) {return;}

    if (stack.getItem() instanceof IRockEntry rockEntry) {
      var rockCategory = rockEntry.getType().getCategory();
      tooltip.add(String.format("%s: %s",
        new TextComponentTranslation("tooltip.tfg.rock.layer.category").getFormattedText(),
        new TextComponentTranslation(rockCategory.getLocalizedName()).setStyle(new Style().setColor(rockCategory.getTextFormatting())).getFormattedText()
      ));

      if (rockEntry.getType().isFlux()) {
        tooltip.add(TextFormatting.GREEN + new TextComponentTranslation("is_flux_rock.name").getFormattedText());
      }
    }


  }
}
