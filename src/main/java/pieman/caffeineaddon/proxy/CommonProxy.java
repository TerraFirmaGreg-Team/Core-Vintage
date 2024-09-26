package pieman.caffeineaddon.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;

import static su.terrafirmagreg.data.Constants.MODID_CAFFEINEADDON;

@Mod.EventBusSubscriber(modid = MODID_CAFFEINEADDON)
public class CommonProxy {

  public void registerItemRenderer(Item item, int metadata, String id) {
  }

  public <E extends Enum<?>> void registerMetaItemRenderer(Item item, Class<E> variants, String id) {
  }

  public void preinit() {
  }

  public void init() {
  }

  public void postinit() {
  }

}
