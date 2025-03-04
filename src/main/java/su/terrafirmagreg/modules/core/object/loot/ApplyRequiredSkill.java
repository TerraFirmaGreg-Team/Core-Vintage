package su.terrafirmagreg.modules.core.object.loot;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.capabilities.playerdata.CapabilityPlayerData;
import su.terrafirmagreg.modules.core.capabilities.playerdata.ICapabilityPlayerData;
import su.terrafirmagreg.modules.core.feature.skill.SimpleSkill;
import su.terrafirmagreg.modules.core.feature.skill.SkillTier;
import su.terrafirmagreg.modules.core.feature.skill.SkillType;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import java.util.Random;

/**
 * Skill and random affect chance of any drop at all. Can require a minimum Skill Tier
 */

public class ApplyRequiredSkill extends LootFunction {

  private final SkillType<? extends SimpleSkill> skillType;
  private final SkillTier tier;
  private final float rarity;

  private ApplyRequiredSkill(LootCondition[] conditionsIn, SkillType<? extends SimpleSkill> skillType, SkillTier tier, float rarity) {
    super(conditionsIn);
    this.skillType = skillType;
    this.tier = tier;
    this.rarity = rarity;
  }

  @Override
  public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
    Entity entity = context.getKillerPlayer();
    if (entity instanceof EntityPlayer) {
      ICapabilityPlayerData skills = entity.getCapability(CapabilityPlayerData.CAPABILITY, null);
      if (skills != null) {
        stack.setCount(0);
        SimpleSkill skill = skills.getSkill(this.skillType);
        if (skill != null && skill.getTier().isAtLeast(tier)) {
          //[0..1] + [0..1] > .5 for 50 rarity. Since Adept = .25, setting rarity to 75 means
          // a 50% chance of drop at ADEPT, and 100% at MASTER
          if (rand.nextDouble() + skill.getTotalLevel() > rarity / 100F) {
            stack.setCount(1);
          }
        }
      }
    }
    return stack;
  }

  public static class Serializer extends LootFunction.Serializer<ApplyRequiredSkill> {

    private static final String PROPERTY_SKILL = "skill";
    private static final String PROPERTY_TIER = "tier";
    private static final String PROPERTY_RARITY = "rarity";

    public Serializer() {
      super(ModUtils.resource("apply_req_skill"), ApplyRequiredSkill.class);
    }

    @Override
    public void serialize(JsonObject object, ApplyRequiredSkill functionClazz, JsonSerializationContext serializationContext) {
      object.add(PROPERTY_SKILL, serializationContext.serialize(functionClazz.skillType.getName()));
      object.add(PROPERTY_TIER, serializationContext.serialize(functionClazz.tier));
      object.add(PROPERTY_RARITY, serializationContext.serialize(functionClazz.rarity));
    }

    @Override
    public ApplyRequiredSkill deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
      String skillName = JsonUtils.getString(object, PROPERTY_SKILL);
      SkillType<? extends SimpleSkill> skillType = SkillType.get(skillName, SimpleSkill.class);
      if (skillType == null) {
        throw new JsonParseException("Unknown skill type: '" + skillName + "'");
      }
      int tierIndex = JsonUtils.getInt(object, PROPERTY_TIER);
      float amount = JsonUtils.getFloat(object, PROPERTY_RARITY);
      return new ApplyRequiredSkill(conditionsIn, skillType, SkillTier.valueOf(tierIndex), amount);
    }
  }
}
