package su.terrafirmagreg.util.gui.element;

import net.minecraftforge.fluids.FluidTank;
import su.terrafirmagreg.util.gui.GuiContainerBase;

public class GuiElementFluidTankVertical
        extends GuiElementFluidTankBase {

    public GuiElementFluidTankVertical(
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
    protected int elementHeightModifiedGet() {

        int elementHeightModified = (int) (this.scalarPercentageGet() * this.elementHeight);
        int min = Math.min(elementHeightModified, this.elementHeight);
        return Math.max(0, min);
    }

    @Override
    protected int elementYModifiedGet() {

        int elementHeightModified = (int) (this.scalarPercentageGet() * this.elementHeight);
        int min = Math.min(elementHeightModified, this.elementHeight);
        return this.elementHeight - Math.max(0, min) + super.elementYModifiedGet();
    }
}
