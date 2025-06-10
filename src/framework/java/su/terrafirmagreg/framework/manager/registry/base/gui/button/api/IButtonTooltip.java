package su.terrafirmagreg.framework.manager.registry.base.gui.button.api;

/**
 * An interface for {@link net.minecraft.client.gui.GuiButton}'s that have a tooltip when hovered over
 */
public interface IButtonTooltip {

  String getTooltip();

  boolean hasTooltip();
}
