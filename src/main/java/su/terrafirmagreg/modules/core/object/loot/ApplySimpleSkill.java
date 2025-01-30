package su.terrafirmagreg.modules.core.object.loot;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.capabilities.playerdata.CapabilityPlayerData;
import su.terrafirmagreg.modules.core.capabilities.playerdata.ICapabilityPlayerData;
import su.terrafirmagreg.modules.core.feature.skill.SimpleSkill;
import su.terrafirmagreg.modules.core.feature.skill.SkillType;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ApplySimpleSkill extends LootFunction {

  private final SkillType<? extends SimpleSkill> skillType;
  private final RandomValueRange valueRange;
  private final float incrementAmount;

  private ApplySimpleSkill(LootCondition[] conditionsIn, RandomValueRange valueRange, SkillType<? extends SimpleSkill> skillType, float incrementAmount) {
    super(conditionsIn);
    this.valueRange = valueRange;
    this.skillType = skillType;
    this.incrementAmount = incrementAmount;
  }

  @Override
  public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
    Entity entity = context.getKillerPlayer();
    if (entity instanceof EntityPlayer) {
      ICapabilityPlayerData skills = entity.getCapability(CapabilityPlayerData.CAPABILITY, null);
      if (skills != null) {
        SimpleSkill skill = skills.getSkill(this.skillType);
        if (skill != null) {
          // Minimum of 1, At 0 skill, returns a bonus of an amount between the difference, At max skill, returns the actual range
          stack.setCount(1 + (int) (valueRange.generateInt(rand) - valueRange.getMin() * (1 - skill.getTotalLevel())));
          skill.add(incrementAmount);
        }
      }
    }
    return stack;
  }

  public static class Serializer extends LootFunction.Serializer<ApplySimpleSkill> {

    private static final String PROPERTY_SKILL = "skill";
    private static final String PROPERTY_ADD = "add";
    private static final String PROPERTY_COUNT = "count";

    public Serializer() {
      super(ModUtils.resource("apply_skill"), ApplySimpleSkill.class);
    }

    @Override
    public void serialize(JsonObject object, ApplySimpleSkill functionClazz, JsonSerializationContext serializationContext) {
      object.add(PROPERTY_SKILL, serializationContext.serialize(functionClazz.skillType.getName()));
      object.add(PROPERTY_ADD, serializationContext.serialize(functionClazz.incrementAmount));
      object.add(PROPERTY_COUNT, serializationContext.serialize(functionClazz.valueRange));
    }

    @Override
    @NotNull
    public ApplySimpleSkill deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
      String skillName = JsonUtils.getString(object, PROPERTY_SKILL);
      float amount = JsonUtils.getFloat(object, PROPERTY_ADD);
      SkillType<? extends SimpleSkill> skillType = SkillType.get(skillName, SimpleSkill.class);
      RandomValueRange valueRange = JsonUtils.deserializeClass(object, PROPERTY_COUNT, deserializationContext, RandomValueRange.class);
      if (skillType == null) {
        throw new JsonParseException("Unknown skill type: '" + skillName + "'");
      }
      return new ApplySimpleSkill(conditionsIn, valueRange, skillType, amount);
    }
  }
}
