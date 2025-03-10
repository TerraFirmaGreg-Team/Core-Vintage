package net.dries007.tfc.objects.blocks.property;

import net.minecraft.block.properties.PropertyBool;


/**
 * Marker interface for blocks that have a lit/unlit state. Removes property duplication / confusion errors Includes the obnoxious static field.
 */
public interface ILightableBlock {

  PropertyBool LIT = PropertyBool.create("lit");
}
