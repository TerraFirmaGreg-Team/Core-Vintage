package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.api.library.MCDate.Month;
import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ICapabilityChunkData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.climate.Climate;
import su.terrafirmagreg.modules.core.feature.climate.ClimateHelper;

import java.util.List;

import static net.minecraft.util.text.TextFormatting.AQUA;
import static net.minecraft.util.text.TextFormatting.RED;
import static net.minecraft.util.text.TextFormatting.YELLOW;
import static su.terrafirmagreg.api.data.Unicode.DEGREE;

@SuppressWarnings("unused")
public class EventHandlerDebugInfo {

  @SubscribeEvent()
  @SideOnly(Side.CLIENT)
  public void onRenderGameOverlayText(RenderGameOverlayEvent.Text event) {

    if (!GameUtils.getGameSettings().showDebugInfo) {
      return;
    }

    List<String> list = event.getRight();

    Minecraft mc = Minecraft.getMinecraft();
    BlockPos blockPos = getPlayerBlockPos(mc);
    Chunk chunk = mc.world.getChunk(blockPos);

    if (!mc.world.isBlockLoaded(blockPos) || chunk.isEmpty()) {
      return;
    }

    var data = CapabilityChunkData.get(chunk);

    if (!data.isInitialized()) {
      if (mc.world.provider.getDimension() == 0) {
        list.add(String.format("%sInvalid Chunk Data (?)", RED));
      }
      return;
    }

    addChunkData(list, data, blockPos);
    addTemperatureData(list, data, blockPos);
    addRainfallData(list, data);
    addFloraData(list, data);
    addSpawnProtectionData(list, data);
    addTimeData(list);
    addRegisteredIdsData(list);
  }

  private BlockPos getPlayerBlockPos(Minecraft mc) {
    //noinspection ConstantConditions
    return new BlockPos(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().getEntityBoundingBox().minY, mc.getRenderViewEntity().posZ);
  }

  private void addChunkData(List<String> list, ICapabilityChunkData data, BlockPos blockPos) {

    final int x = blockPos.getX() & 15;
    final int z = blockPos.getZ() & 15;

    var regionTemp = String.format("%s%.1f" + DEGREE + "C", AQUA, data.getRegionalTemp());
    var averageTemp = String.format("%s%.1f" + DEGREE + "C", AQUA, data.getAverageTemp());

    var minTemp = String.format("%s%.1f" + DEGREE + "C", AQUA,
                                ClimateHelper.monthFactor(data.getRegionalTemp(), Month.JANUARY.getTemperatureModifier(), blockPos.getZ()));
    var maxTemp = String.format("%s%.1f" + DEGREE + "C", AQUA,
                                ClimateHelper.monthFactor(data.getRegionalTemp(), Month.JULY.getTemperatureModifier(), blockPos.getZ()));

    var monthlyTemp = String.format("%s%.1f", AQUA, Climate.getMonthlyTemp(blockPos));
    var actualTemp = String.format("%s%.1f", AQUA, Climate.getActualTemp(blockPos));

    list.add(String.format("%sRegion: %s %sAvg: %s %sMin: %s %sMax: %s", YELLOW, regionTemp, YELLOW, averageTemp, YELLOW, minTemp, YELLOW, maxTemp));
    list.add(String.format("%sTemperature: %s" + DEGREE + "C %sDaily: %s" + DEGREE + "C", YELLOW, monthlyTemp, YELLOW, actualTemp));
    list.add(String.format("%sRainfall: %s%.1f", YELLOW, AQUA, data.getRainfall()));
    list.add(String.format("%sSea level offset: %s%s", YELLOW, AQUA, data.getSeaLevelOffset(x, z)));
  }

  private void addTemperatureData(List<String> list, ICapabilityChunkData data, BlockPos blockPos) {
    list.add(String.format("%sFlora Density: %s%s", YELLOW, AQUA, data.getFloraDensity()));
    list.add(String.format("%sFlora Diversity: %s%s", YELLOW, AQUA, data.getFloraDiversity()));
  }

  private void addRainfallData(List<String> list, ICapabilityChunkData data) {
    list.add(String.format("%sRainfall: %s%.1f", YELLOW, AQUA, data.getRainfall()));
  }

  private void addFloraData(List<String> list, ICapabilityChunkData data) {
    list.add(String.format("%sFlora Density: %s%s", YELLOW, AQUA, data.getFloraDensity()));
    list.add(String.format("%sFlora Diversity: %s%s", YELLOW, AQUA, data.getFloraDiversity()));
  }

  private void addSpawnProtectionData(List<String> list, ICapabilityChunkData data) {
    list.add(String.format("%sSpawn Protection: %s%s", YELLOW, AQUA, data.isSpawnProtected()));
    list.add(String.format("%sSpawn Protection: %s%s", YELLOW, AQUA, data.getSpawnProtection()));
  }

  private void addTimeData(List<String> list) {
    list.add(I18n.format("tfc.tooltip.date", Calendar.CALENDAR_TIME.getTimeAndDate()));
    list.add(I18n.format("tfc.tooltip.debug_times", Calendar.PLAYER_TIME.getTicks(), Calendar.CALENDAR_TIME.getTicks()));
  }

  private void addRegisteredIdsData(List<String> list) {
    list.add("");
    list.add(String.format("%sBiome IDs Registered: %s%s", YELLOW, AQUA, ForgeRegistries.BIOMES.getKeys().size()));
    list.add(String.format("%sBlock IDs Registered: %s%s", YELLOW, AQUA, ForgeRegistries.BLOCKS.getKeys().size()));
    list.add(String.format("%sItem IDs Registered: %s%s", YELLOW, AQUA, ForgeRegistries.ITEMS.getKeys().size()));
    list.add(String.format("%sPotion IDs Registered: %s%s", YELLOW, AQUA, ForgeRegistries.POTIONS.getKeys().size()));
    list.add(String.format("%sEnchantment IDs Registered: %s%s", YELLOW, AQUA, ForgeRegistries.ENCHANTMENTS.getKeys().size()));
    list.add(String.format("%sEntity IDs Registered: %s%s", YELLOW, AQUA, ForgeRegistries.ENTITIES.getKeys().size()));
  }

}
