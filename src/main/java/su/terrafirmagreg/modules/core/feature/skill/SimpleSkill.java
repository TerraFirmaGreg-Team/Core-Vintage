package su.terrafirmagreg.modules.core.feature.skill;

import su.terrafirmagreg.modules.core.capabilities.playerdata.ICapabilityPlayerData;

import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class SimpleSkill extends Skill {

  private float amount;

  public SimpleSkill(ICapabilityPlayerData rootSkills) {
    super(rootSkills);
    this.amount = 0;
  }

  @Override
  @Nonnull
  public SkillTier getTier() {
    return SkillTier.valueOf((int) amount);
  }

  @Override
  public float getLevel() {
    // checks >=4f for full progress bar in MASTER tier.
    return amount >= 4f ? 1.0F : amount % 1.0F;
  }

  @Override
  public void setTotalLevel(double value) {
    if (value < 0) {
      value = 0;
    }
    if (value > 1) {
      value = 1;
    }
    amount = (float) value * 4f;
    updateAndSync();
  }

  public void add(float amount) {
    this.amount += amount / Math.pow(2, (float) getTier().ordinal());
    if (this.amount > 4f) {
      this.amount = 4f;
    }
    updateAndSync();
  }

  @Override
  public NBTTagCompound serializeNBT() {
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setFloat("amount", amount);
    return nbt;
  }

  @Override
  public void deserializeNBT(NBTTagCompound nbt) {
    if (nbt != null) {
      amount = nbt.getFloat("amount");
    }
  }
}
