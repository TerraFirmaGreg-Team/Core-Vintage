package tfcflorae.client;

import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;


import net.dries007.tfc.api.recipes.knapping.KnappingTypes;
import net.dries007.tfc.client.gui.GuiContainerTFC;
import net.dries007.tfc.client.gui.GuiUrn;
import net.dries007.tfc.objects.container.ContainerBag;
import net.dries007.tfc.objects.container.ContainerKnapping;
import net.dries007.tfc.objects.container.ContainerSack;
import net.dries007.tfc.objects.container.ContainerUrn;
import net.dries007.tfc.objects.items.ItemBag;
import net.dries007.tfc.objects.items.ItemSack;
import net.dries007.tfc.objects.te.TEUrn;
import tfcflorae.TFCFlorae;
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.data.Constants.MODID_TFCF;

public class GuiHandler
        implements IGuiHandler {

  public static final ResourceLocation SACK_INVENTORY_BACKGROUND = new ResourceLocation(MODID_TFCF,
          "textures/gui/sack_inventory.png");
  public static final ResourceLocation BAG_INVENTORY_BACKGROUND = new ResourceLocation(MODID_TFCF,
          "textures/gui/bag_inventory.png");
  public static final ResourceLocation PINEAPPLE_LEATHER_TEXTURE = new ResourceLocation(MODID_TFCF,
          "textures/gui/knapping/pineapple_leather_button.png");
  public static final ResourceLocation BURLAP_CLOTH_TEXTURE = new ResourceLocation(MODID_TFCF,
          "textures/blocks/devices/loom/product/burlap.png");
  public static final ResourceLocation WOOL_CLOTH_TEXTURE = new ResourceLocation(MODID_TFCF,
          "textures/blocks/devices/loom/product/wool.png");
  public static final ResourceLocation SILK_CLOTH_TEXTURE = new ResourceLocation(MODID_TFCF,
          "textures/blocks/devices/loom/product/silk.png");
  public static final ResourceLocation SISAL_CLOTH_TEXTURE = new ResourceLocation(MODID_TFCF,
          "textures/blocks/devices/loom/product/sisal.png");
  public static final ResourceLocation COTTON_CLOTH_TEXTURE = new ResourceLocation(MODID_TFCF,
          "textures/blocks/devices/loom/product/cotton.png");
  public static final ResourceLocation LINEN_CLOTH_TEXTURE = new ResourceLocation(MODID_TFCF,
          "textures/blocks/devices/loom/product/linen.png");
  public static final ResourceLocation HEMP_CLOTH_TEXTURE = new ResourceLocation(MODID_TFCF,
          "textures/blocks/devices/loom/product/hemp.png");
  public static final ResourceLocation YUCCA_CANVAS_TEXTURE = new ResourceLocation(MODID_TFCF,
          "textures/blocks/devices/loom/product/yucca.png");
  public static final ResourceLocation MUD_TEXTURE = new ResourceLocation(MODID_TFCF,
          "textures/gui/knapping/mud_button.png");
  public static final ResourceLocation MUD_DISABLED_TEXTURE = new ResourceLocation(MODID_TFCF,
          "textures/gui/knapping/mud_button_disabled.png");
  public static final ResourceLocation FLINT_TEXTURE = new ResourceLocation(MODID_TFCF,
          "textures/gui/knapping/flint_button.png");
  public static final ResourceLocation FLINT_DISABLED_TEXTURE = new ResourceLocation(MODID_TFCF,
          "textures/gui/knapping/flint_button_disabled.png");

  public static void openGui(World world, BlockPos pos, EntityPlayer player, Type type) {
    player.openGui(TFCFlorae.instance, type.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
  }

  public static void openGui(World world, EntityPlayer player, Type type) {
    player.openGui(TFCFlorae.instance, type.ordinal(), world, 0, 0, 0);
  }

  public enum Type {
    SACK,
    BAG,
    PINEAPPLE_LEATHER,
    BURLAP_CLOTH,
    WOOL_CLOTH,
    SILK_CLOTH,
    SISAL_CLOTH,
    COTTON_CLOTH,
    LINEN_CLOTH,
    HEMP_CLOTH,
    YUCCA_CANVAS,
    MUD,
    FLINT,
    URN,
    NULL;

    private static final Type[] values = values();

    @NotNull
    public static Type valueOf(int id) {
      while (id >= values.length) {
        id -= values.length;
      }
      while (id < 0) {
        id += values.length;
      }
      return values[id];
    }
  }

  @Override
  @Nullable
  public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y,
          int z) {
    BlockPos pos = new BlockPos(x, y, z);
    ItemStack stack = player.getHeldItemMainhand();
    Type type = Type.valueOf(ID);
    return switch (type) {
      case SACK -> new ContainerSack(player.inventory,
              stack.getItem() instanceof ItemSack ? stack : player.getHeldItemOffhand());
      case BAG -> new ContainerBag(player.inventory,
              stack.getItem() instanceof ItemBag ? stack : player.getHeldItemOffhand());
      case PINEAPPLE_LEATHER -> new ContainerKnapping(KnappingTypes.PINEAPPLE_LEATHER, player.inventory,
              OreDictionaryHelper.doesStackMatchOre(stack, "leatherPineapple") ? stack
                      : player.getHeldItemOffhand());
      case BURLAP_CLOTH -> new ContainerKnapping(KnappingTypes.BURLAP_CLOTH, player.inventory,
              OreDictionaryHelper.doesStackMatchOre(stack, "clothBurlap") ? stack
                      : player.getHeldItemOffhand());
      case WOOL_CLOTH -> new ContainerKnapping(KnappingTypes.WOOL_CLOTH, player.inventory,
              OreDictionaryHelper.doesStackMatchOre(stack, "clothWool") ? stack
                      : player.getHeldItemOffhand());
      case SILK_CLOTH -> new ContainerKnapping(KnappingTypes.SILK_CLOTH, player.inventory,
              OreDictionaryHelper.doesStackMatchOre(stack, "clothSilk") ? stack
                      : player.getHeldItemOffhand());
      case SISAL_CLOTH -> new ContainerKnapping(KnappingTypes.SISAL_CLOTH, player.inventory,
              OreDictionaryHelper.doesStackMatchOre(stack, "clothSisal") ? stack
                      : player.getHeldItemOffhand());
      case COTTON_CLOTH -> new ContainerKnapping(KnappingTypes.COTTON_CLOTH, player.inventory,
              OreDictionaryHelper.doesStackMatchOre(stack, "clothCotton") ? stack
                      : player.getHeldItemOffhand());
      case LINEN_CLOTH -> new ContainerKnapping(KnappingTypes.LINEN_CLOTH, player.inventory,
              OreDictionaryHelper.doesStackMatchOre(stack, "clothLinen") ? stack
                      : player.getHeldItemOffhand());
      case HEMP_CLOTH -> new ContainerKnapping(KnappingTypes.HEMP_CLOTH, player.inventory,
              OreDictionaryHelper.doesStackMatchOre(stack, "clothHemp") ? stack
                      : player.getHeldItemOffhand());
      case YUCCA_CANVAS -> new ContainerKnapping(KnappingTypes.YUCCA_CANVAS, player.inventory,
              OreDictionaryHelper.doesStackMatchOre(stack, "canvasYucca") ? stack
                      : player.getHeldItemOffhand());
      case FLINT -> new ContainerKnapping(KnappingTypes.FLINT, player.inventory,
              OreDictionaryHelper.doesStackMatchOre(stack, "flint") ? stack
                      : player.getHeldItemOffhand());
      case URN -> new ContainerUrn(player.inventory, TileUtils.getTile(world, pos, TEUrn.class));
      default -> null;
    };
  }

  @Override
  @Nullable
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    Container container = getServerGuiElement(ID, player, world, x, y, z);
    Type type = Type.valueOf(ID);
    BlockPos pos = new BlockPos(x, y, z);
    switch (type) {
      case SACK:
        return new GuiContainerTFC(container, player.inventory, SACK_INVENTORY_BACKGROUND);
      case BAG:
        return new GuiContainerTFC(container, player.inventory, BAG_INVENTORY_BACKGROUND);
      case PINEAPPLE_LEATHER:
        return new GuiKnappingTFCF(container, player, KnappingTypes.PINEAPPLE_LEATHER,
                PINEAPPLE_LEATHER_TEXTURE);
      case BURLAP_CLOTH:
        return new GuiKnappingTFCF(container, player, KnappingTypes.BURLAP_CLOTH,
                BURLAP_CLOTH_TEXTURE);
      case WOOL_CLOTH:
        return new GuiKnappingTFCF(container, player, KnappingTypes.WOOL_CLOTH, WOOL_CLOTH_TEXTURE);
      case SILK_CLOTH:
        return new GuiKnappingTFCF(container, player, KnappingTypes.SILK_CLOTH, SILK_CLOTH_TEXTURE);
      case SISAL_CLOTH:
        return new GuiKnappingTFCF(container, player, KnappingTypes.SISAL_CLOTH,
                SISAL_CLOTH_TEXTURE);
      case COTTON_CLOTH:
        return new GuiKnappingTFCF(container, player, KnappingTypes.COTTON_CLOTH,
                COTTON_CLOTH_TEXTURE);
      case LINEN_CLOTH:
        return new GuiKnappingTFCF(container, player, KnappingTypes.LINEN_CLOTH,
                LINEN_CLOTH_TEXTURE);
      case HEMP_CLOTH:
        return new GuiKnappingTFCF(container, player, KnappingTypes.HEMP_CLOTH, HEMP_CLOTH_TEXTURE);
      case YUCCA_CANVAS:
        return new GuiKnappingTFCF(container, player, KnappingTypes.YUCCA_CANVAS,
                YUCCA_CANVAS_TEXTURE);
      case FLINT:
        return new GuiKnappingTFCF(container, player, KnappingTypes.FLINT, FLINT_TEXTURE);
      case URN:
        return new GuiUrn(container, player.inventory, TileUtils.getTile(world, pos, TEUrn.class),
                world.getBlockState(new BlockPos(x, y, z))
                        .getBlock()
                        .getTranslationKey());
      default:
        return null;
    }
  }


}
