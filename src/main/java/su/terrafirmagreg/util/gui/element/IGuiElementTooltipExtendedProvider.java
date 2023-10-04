package su.terrafirmagreg.util.gui.element;

import java.util.List;

public interface IGuiElementTooltipExtendedProvider extends
        IGuiElementTooltipProvider {

    List<String> tooltipTextExtendedGet(List<String> tooltip);

}
