package BananaFructa.tfcfarming.network;

import su.terrafirmagreg.api.capabilities.skill.CapabilitySkill;
import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


import BananaFructa.tfcfarming.NutrientValues;
import BananaFructa.tfcfarming.TFCFarming;
import BananaFructa.tfcfarming.firmalife.TEHangingPlanterN;
import BananaFructa.tfcfarming.firmalife.TEPlanterN;
import net.dries007.tfc.util.skills.Skill;
import net.dries007.tfc.util.skills.SkillTier;
import net.dries007.tfc.util.skills.SkillType;

public class CPacketRequestNutrientDataHandler implements IMessageHandler<CPacketRequestNutrientData, IMessage> {

    @Override
    public IMessage onMessage(CPacketRequestNutrientData message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        // Request protection
        var cap = CapabilitySkill.get(player);
        Skill skill = cap.getSkill(SkillType.AGRICULTURE);
        // TODO: V config
        if (Math.abs(player.posX - message.x) <= 5 && Math.abs(player.posZ - message.z) <= 5 && (skill.getTier()
                .isAtLeast(SkillTier.ADEPT) || player.capabilities.isCreativeMode)) {
            if (message.y == -1) {
                NutrientValues values = TFCFarming.INSTANCE.worldStorage.getNutrientValues(message.x, message.z);
                int[] NPK = values.getNPKSet();
                return new SPacketNutrientDataResponse(true, NPK[0], NPK[1], NPK[2], message.x, message.z);
            } else if (Math.abs(player.posY - message.y) <= 5 && TFCFarming.firmalifeLoaded) {
                TEPlanterN tePlanterN = TileUtils.getTile(player.getEntityWorld(), new BlockPos(message.x, message.y, message.z), TEPlanterN.class);
                TEHangingPlanterN teHangingPlanterN = TileUtils.getTile(player.getEntityWorld(), new BlockPos(message.x, message.y, message.z),
                        TEHangingPlanterN.class);
                if (tePlanterN == null && teHangingPlanterN == null)
                    return new SPacketNutrientDataResponse(false, 0, 0, 0, message.x, message.y, message.z, false);
                boolean isHanging = teHangingPlanterN != null;
                NutrientValues nutrientValues = (!isHanging ? tePlanterN.getNutrientValues() : teHangingPlanterN.getNutrientValues());
                int NPK[] = nutrientValues.getNPKSet();
                return new SPacketNutrientDataResponse(true, NPK[0], NPK[1], NPK[2], message.x, message.y, message.z,
                        (!isHanging ? tePlanterN.anyLowNutrients() : teHangingPlanterN.isLow()));
            } else {
                return new SPacketNutrientDataResponse(false, 0, 0, 0, message.x, message.y, message.z, false);
            }
        } else {
            return new SPacketNutrientDataResponse(false, 0, 0, 0, message.x, message.z);
        }
    }
}
