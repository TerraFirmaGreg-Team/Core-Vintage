package net.dries007.tfc.api.capability.damage;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;

public class DamageResistance implements IDamageResistance, ICapabilityProvider {
	private final float crushingModifier;
	private final float piercingModifier;
	private final float slashingModifier;

	public DamageResistance() {
		this(0, 0, 0);
	}

	public DamageResistance(float crushingModifier, float piercingModifier, float slashingModifier) {
		this.crushingModifier = crushingModifier;
		this.piercingModifier = piercingModifier;
		this.slashingModifier = slashingModifier;
	}

	@Override
	public float getCrushingModifier() {
		return crushingModifier;
	}

	@Override
	public float getPiercingModifier() {
		return piercingModifier;
	}

	@Override
	public float getSlashingModifier() {
		return slashingModifier;
	}

	@Override
	public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityDamageResistance.CAPABILITY;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
		return hasCapability(capability, facing) ? (T) this : null;
	}
}
