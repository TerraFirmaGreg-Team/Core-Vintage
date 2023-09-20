package net.dries007.tfc.module.core.common.objects.container;

import net.dries007.tfc.network.PacketGuiButton;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

public interface IButtonHandler {
    /**
     * An interface for containers that need to receive button presses from a client-side GUI
     * If you implement this interface you should also use {@link PacketGuiButton} to send update packets from the GUI
     *
     * @param buttonID the button ID that was pressed
     * @param extraNBT any extra NBT stored data from the individual button, null if empty
     */
    void onButtonPress(int buttonID, @Nullable NBTTagCompound extraNBT);
}
