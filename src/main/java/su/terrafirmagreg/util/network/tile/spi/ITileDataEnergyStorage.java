package su.terrafirmagreg.util.network.tile.spi;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import su.terrafirmagreg.util.inventory.IObservableEnergyStorage;

/**
 * Energy storage data elements need to implement the
 * {@link IObservableEnergyStorage} interface.
 */
public interface ITileDataEnergyStorage
        extends IObservableEnergyStorage,
        INBTSerializable<NBTTagCompound> {

}
