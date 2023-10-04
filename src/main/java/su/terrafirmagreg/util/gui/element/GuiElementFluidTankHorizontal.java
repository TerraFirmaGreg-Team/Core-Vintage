package su.terrafirmagreg.util.gui.element;

import net.minecraftforge.fluids.FluidTank;
import su.terrafirmagreg.util.gui.GuiContainerBase;

public class GuiElementFluidTankHorizontal
        extends GuiElementFluidTankBase {

    public GuiElementFluidTankHorizontal(
            GuiContainerBase guiBase,
            FluidTank fluidTank,
            int elementX,
            int elementY,
            int elementWidth,
            int elementHeight
    ) {

        super(
                guiBase,
                elementX,
                elementY,
                elementWidth,
                elementHeight,
                fluidTank
        );
    }

    @Override
    protected int elementWidthModifiedGet() {

        int elementWidthModified = (int) (this.scalarPercentageGet() * this.elementWidth);
        int min = Math.min(elementWidthModified, this.elementWidth);
        return Math.max(0, min);
    }
}
