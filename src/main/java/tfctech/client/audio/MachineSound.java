package tfctech.client.audio;

import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@SuppressWarnings("WeakerAccess")
public class MachineSound extends PositionedSound implements ITickableSound {
    protected IMachineSoundEffect machine;

    public MachineSound(IMachineSoundEffect machine) {
        super(machine.getSoundEvent(), SoundCategory.BLOCKS);
        volume = 0.5F;
        repeat = true;
        repeatDelay = 0;
        xPosF = machine.getSoundPos().getX() + 0.5F;
        yPosF = machine.getSoundPos().getY() + 0.5F;
        zPosF = machine.getSoundPos().getZ() + 0.5F;
        this.machine = machine;
    }

    @Override
    public void update() {
    }

    @Override
    public int hashCode() {
        return machine.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MachineSound)) {
            return false;
        }
        return ((MachineSound) obj).machine.equals(machine);
    }

    @Override
    public boolean isDonePlaying() {
        return !machine.shouldPlay();
    }
}
