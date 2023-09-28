package su.terrafirmagreg.proxy;

import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class CommonProxy {

    public boolean isRemote() {

        return false;
    }

    public void playSound(SoundEvent soundEvent, SoundCategory soundCategory) {}

    public void playSound(SoundEvent soundEvent, SoundCategory soundCategory, float volume, float pitch, boolean distanceDelay) {}

    public void playSound(BlockPos pos, SoundEvent soundEvent, SoundCategory soundCategory, float volume, float pitch, boolean distanceDelay) {}
}
