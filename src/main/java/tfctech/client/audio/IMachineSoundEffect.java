package tfctech.client.audio;

import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IMachineSoundEffect {
	@SideOnly(Side.CLIENT)
	SoundEvent getSoundEvent();

	boolean shouldPlay();

	boolean isPlaying();

	void setPlaying(boolean value);

	@SideOnly(Side.CLIENT)
	BlockPos getSoundPos();

	@SideOnly(Side.CLIENT)
	default void update() {
		if (shouldPlay() && !isPlaying()) {
			setPlaying(true);
			MachineSound sound = new MachineSound(this);
			// Play sound on client side
			Minecraft.getMinecraft().getSoundHandler().playSound(sound);
		} else if (!shouldPlay()) {
			setPlaying(false);
		}
	}
}
