package net.dries007.tfc.module.agriculture.objects.tile;

import net.dries007.tfc.module.agriculture.objects.blocks.BlockCropGrowing;
import net.dries007.tfc.module.core.objects.tiles.TETickCounter;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendarTickable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TECropBase extends TETickCounter implements ICalendarTickable, ITickable {
    protected long lastTickCalChecked;

    public TECropBase() {
        lastTickCalChecked = CalendarTFC.PLAYER_TIME.getTicks();
    }

    @Override
    public void onCalendarUpdate(long playerTickDelta) {
        BlockCropGrowing block = (BlockCropGrowing) getBlockType();
        block.checkGrowth(world, pos, world.getBlockState(pos), world.rand);
    }

    @Override
    public long getLastUpdateTick() {
        return lastTickCalChecked;
    }

    @Override
    public void setLastUpdateTick(long tick) {
        lastTickCalChecked = tick;
        markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        lastTickCalChecked = nbt.getLong("lastTickCalChecked");
        super.readFromNBT(nbt);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setLong("lastTickCalChecked", lastTickCalChecked);
        return super.writeToNBT(nbt);
    }

    @Override
    public void update() {
        ICalendarTickable.super.checkForCalendarUpdate();
    }
}
