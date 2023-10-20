/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.compat.patchouli;

import net.dries007.tfc.client.TFCGuiHandler;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public class LeatherKnappingComponent extends KnappingComponent {
    @Nullable
    @Override
    protected ResourceLocation getSquareLow(int ticks) {
        return null;
    }

    @Nullable
    @Override
    protected ResourceLocation getSquareHigh(int ticks) {
        return TFCGuiHandler.LEATHER_TEXTURE;
    }
}
