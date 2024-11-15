package net.dries007.tfcfarming.network;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.player.CapabilityPlayer;
import su.terrafirmagreg.modules.core.feature.skills.Skill;
import su.terrafirmagreg.modules.core.feature.skills.SkillTier;
import su.terrafirmagreg.modules.core.feature.skills.SkillType;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import net.dries007.tfcfarming.NutrientValues;
import net.dries007.tfcfarming.TFCFarming;
import net.dries007.tfcfarming.firmalife.TEHangingPlanterN;
import net.dries007.tfcfarming.firmalife.TEPlanterN;

public class CPacketRequestNutrientDataHandler implements IMessageHandler<CPacketRequestNutrientData, IMessage> {

  @Override
  public IMessage onMessage(CPacketRequestNutrientData message, MessageContext ctx) {
    EntityPlayerMP player = ctx.getServerHandler().player;
    // Request protection
    var cap = CapabilityPlayer.get(player);
    Skill skill = cap.getSkill(SkillType.AGRICULTURE);
    // TODO: V config
    if (Math.abs(player.posX - message.x) <= 5 && Math.abs(player.posZ - message.z) <= 5
        && (skill.getTier().isAtLeast(SkillTier.ADEPT)
            || player.capabilities.isCreativeMode)) {
      if (message.y == -1) {
        NutrientValues values = TFCFarming.INSTANCE.worldStorage.getNutrientValues(message.x, message.z);
        int[] NPK = values.getNPKSet();
        return new SPacketNutrientDataResponse(true, NPK[0], NPK[1], NPK[2], message.x, message.z);
      } else if (Math.abs(player.posY - message.y) <= 5 && TFCFarming.firmalifeLoaded) {
        var tePlanterN = TileUtils.getTile(player.getEntityWorld(), new BlockPos(message.x, message.y, message.z), TEPlanterN.class);
        var teHangingPlanterN = TileUtils.getTile(player.getEntityWorld(), new BlockPos(message.x, message.y, message.z), TEHangingPlanterN.class);
        if (!tePlanterN.isPresent() && !teHangingPlanterN.isPresent()) {
          return new SPacketNutrientDataResponse(false, 0, 0, 0, message.x, message.y, message.z, false);
        }
        boolean isHanging = teHangingPlanterN.isPresent();
        NutrientValues nutrientValues = (!isHanging ? tePlanterN.get().getNutrientValues() : teHangingPlanterN.get().getNutrientValues());
        int[] NPK = nutrientValues.getNPKSet();
        return new SPacketNutrientDataResponse(true, NPK[0], NPK[1], NPK[2], message.x, message.y, message.z,
                                               (!isHanging ? tePlanterN.get().anyLowNutrients() : teHangingPlanterN.get().isLow()));
      } else {
        return new SPacketNutrientDataResponse(false, 0, 0, 0, message.x, message.y, message.z, false);
      }
    } else {
      return new SPacketNutrientDataResponse(false, 0, 0, 0, message.x, message.z);
    }
  }
}
