package net.dries007.tfc.client;

import su.terrafirmagreg.modules.device.client.gui.GuiFirePit;
import su.terrafirmagreg.modules.device.object.container.ContainerFirePit;
import su.terrafirmagreg.modules.device.object.container.ContainerLogPile;
import su.terrafirmagreg.modules.device.object.tile.TileFirePit;
import su.terrafirmagreg.modules.device.object.tile.TileLogPile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFC;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.client.gui.GuiAnvilPlan;
import net.dries007.tfc.client.gui.GuiAnvilTFC;
import net.dries007.tfc.client.gui.GuiBarrel;
import net.dries007.tfc.client.gui.GuiCalendar;
import net.dries007.tfc.client.gui.GuiChestTFC;
import net.dries007.tfc.client.gui.GuiContainerTFC;
import net.dries007.tfc.client.gui.GuiInventoryCrafting;
import net.dries007.tfc.client.gui.GuiKnapping;
import net.dries007.tfc.client.gui.GuiLargeVessel;
import net.dries007.tfc.client.gui.GuiLiquidTransfer;
import net.dries007.tfc.client.gui.GuiNutrition;
import net.dries007.tfc.client.gui.GuiSalad;
import net.dries007.tfc.client.gui.GuiSkills;
import net.dries007.tfc.objects.blocks.wood.BlockChestTFC;
import net.dries007.tfc.objects.container.ContainerAnvilPlan;
import net.dries007.tfc.objects.container.ContainerAnvilTFC;
import net.dries007.tfc.objects.container.ContainerBarrel;
import net.dries007.tfc.objects.container.ContainerChestTFC;
import net.dries007.tfc.objects.container.ContainerInventoryCrafting;
import net.dries007.tfc.objects.container.ContainerKnapping;
import net.dries007.tfc.objects.container.ContainerLargeVessel;
import net.dries007.tfc.objects.container.ContainerLiquidTransfer;
import net.dries007.tfc.objects.container.ContainerQuiver;
import net.dries007.tfc.objects.container.ContainerSalad;
import net.dries007.tfc.objects.container.ContainerSimple;
import net.dries007.tfc.objects.container.ContainerSmallVessel;
import net.dries007.tfc.objects.items.ItemQuiver;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.objects.items.ceramics.ItemSmallVessel;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.dries007.tfc.objects.te.TEAnvilTFC;
import net.dries007.tfc.objects.te.TEBarrel;
import net.dries007.tfc.objects.te.TELargeVessel;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.OreDictionaryHelper;

public class TFCGuiHandler implements IGuiHandler {

  public static final ResourceLocation SMALL_INVENTORY_BACKGROUND = new ResourceLocation(TFC, "textures/gui/small_inventory.png");
  public static final ResourceLocation CLAY_TEXTURE = new ResourceLocation(TFC, "textures/gui/knapping/clay_button.png");
  public static final ResourceLocation FIRE_CLAY_TEXTURE = new ResourceLocation(TFC, "textures/gui/knapping/clay_button_fire.png");
  public static final ResourceLocation LEATHER_TEXTURE = new ResourceLocation(TFC, "textures/gui/knapping/leather_button.png");
  public static final ResourceLocation QUIVER_BACKGROUND = new ResourceLocation(TFC, "textures/gui/quiver_inventory.png");
  public static final ResourceLocation CLAY_DISABLED_TEXTURE = new ResourceLocation(TFC, "textures/gui/knapping/clay_button_disabled.png");
  public static final ResourceLocation FIRE_CLAY_DISABLED_TEXTURE = new ResourceLocation(TFC, "textures/gui/knapping/clay_button_fire_disabled.png");

  // use this instead of player.openGui() -> avoids magic numbers
  public static void openGui(World world, BlockPos pos, EntityPlayer player, Type type) {
    player.openGui(TerraFirmaCraft.getInstance(), type.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
  }

  // Only use this for things that don't need a BlockPos to identify TE's!!!
  public static void openGui(World world, EntityPlayer player, Type type) {
    player.openGui(TerraFirmaCraft.getInstance(), type.ordinal(), world, 0, 0, 0);
  }

  @Override
  @Nullable
  public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    BlockPos pos = new BlockPos(x, y, z);
    ItemStack stack = player.getHeldItemMainhand();
    Type type = Type.valueOf(ID);
    switch (type) {
      case LOG_PILE:
        TileLogPile teLogPile = Helpers.getTE(world, pos, TileLogPile.class);
        return teLogPile == null ? null : new ContainerLogPile(player.inventory, teLogPile);
      case SMALL_VESSEL:
        return new ContainerSmallVessel(player.inventory, stack.getItem() instanceof ItemSmallVessel ? stack : player.getHeldItemOffhand());
      case SMALL_VESSEL_LIQUID:
        return new ContainerLiquidTransfer(player.inventory, stack.getItem() instanceof ItemSmallVessel ? stack : player.getHeldItemOffhand());
      case MOLD:
        return new ContainerLiquidTransfer(player.inventory, stack.getItem() instanceof ItemMold ? stack : player.getHeldItemOffhand());
      case FIRE_PIT:
        //noinspection ConstantConditions
        return new ContainerFirePit(player.inventory, Helpers.getTE(world, pos, TileFirePit.class));
      case BARREL:
        return new ContainerBarrel(player.inventory, Helpers.getTE(world, pos, TEBarrel.class));
      case ANVIL:
        //noinspection ConstantConditions
        return new ContainerAnvilTFC(player.inventory, Helpers.getTE(world, pos, TEAnvilTFC.class));
      case ANVIL_PLAN:
        return new ContainerAnvilPlan(player.inventory, Helpers.getTE(world, pos, TEAnvilTFC.class));
      case KNAPPING_STONE:
        return new ContainerKnapping(KnappingType.STONE, player.inventory, stack.getItem() instanceof ItemRock ? stack : player.getHeldItemOffhand());
      case KNAPPING_CLAY:
        return new ContainerKnapping(KnappingType.CLAY, player.inventory,
          OreDictionaryHelper.doesStackMatchOre(stack, "clay") ? stack : player.getHeldItemOffhand());
      case KNAPPING_LEATHER:
        return new ContainerKnapping(KnappingType.LEATHER, player.inventory,
          OreDictionaryHelper.doesStackMatchOre(stack, "leather") ? stack : player.getHeldItemOffhand());
      case KNAPPING_FIRE_CLAY:
        return new ContainerKnapping(KnappingType.FIRE_CLAY, player.inventory,
          OreDictionaryHelper.doesStackMatchOre(stack, "fireClay") ? stack : player.getHeldItemOffhand());
      case LARGE_VESSEL:
        return new ContainerLargeVessel(player.inventory, Helpers.getTE(world, pos, TELargeVessel.class));
      case CALENDAR:
      case SKILLS:
      case NUTRITION:
        return new ContainerSimple(player.inventory);
      case CRAFTING:
        return new ContainerInventoryCrafting(player.inventory, player.world);
      case QUIVER:
        return new ContainerQuiver(player.inventory, stack.getItem() instanceof ItemQuiver ? stack : player.getHeldItemOffhand());
      case CHEST:
        if (world.getBlockState(pos).getBlock() instanceof BlockChestTFC) {
          ILockableContainer chestContainer = ((BlockChestTFC) world.getBlockState(pos).getBlock()).getLockableContainer(world, pos);
          if (chestContainer == null) // This is null if the chest is blocked
          {
            return null;
          }
          return new ContainerChestTFC(player.inventory, chestContainer, player);
        }
        return null;
      case SALAD:
        return new ContainerSalad(player.inventory);
      default:
        return null;
    }
  }

  @Override
  @Nullable
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    Container container = getServerGuiElement(ID, player, world, x, y, z);
    Type type = Type.valueOf(ID);
    BlockPos pos = new BlockPos(x, y, z);
    switch (type) {
      case SMALL_VESSEL:
      case LOG_PILE:
        return new GuiContainerTFC(container, player.inventory, SMALL_INVENTORY_BACKGROUND);
      case SMALL_VESSEL_LIQUID:
        return new GuiLiquidTransfer(container, player, player.getHeldItemMainhand().getItem() instanceof ItemSmallVessel);
      case MOLD:
        return new GuiLiquidTransfer(container, player, player.getHeldItemMainhand().getItem() instanceof ItemMold);
      case FIRE_PIT:
        return new GuiFirePit(container, player.inventory, Helpers.getTE(world, pos, TileFirePit.class));
      case BARREL:
        return new GuiBarrel(container, player.inventory, Helpers.getTE(world, pos, TEBarrel.class), world.getBlockState(new BlockPos(x, y, z)).getBlock()
          .getTranslationKey());
      case ANVIL:
        return new GuiAnvilTFC(container, player.inventory, Helpers.getTE(world, pos, TEAnvilTFC.class));
      case ANVIL_PLAN:
        return new GuiAnvilPlan(container, player.inventory, Helpers.getTE(world, pos, TEAnvilTFC.class));
      case KNAPPING_STONE:
        ItemStack stack = player.getHeldItemMainhand();
        Rock rock = stack.getItem() instanceof IRockObject ? ((IRockObject) stack.getItem()).getRock(stack) :
                    ((IRockObject) player.getHeldItemOffhand().getItem()).getRock(player.getHeldItemOffhand());
        //noinspection ConstantConditions
        return new GuiKnapping(container, player, KnappingType.STONE, rock.getTexture());
      case KNAPPING_CLAY:
        return new GuiKnapping(container, player, KnappingType.CLAY, CLAY_TEXTURE);
      case KNAPPING_LEATHER:
        return new GuiKnapping(container, player, KnappingType.LEATHER, LEATHER_TEXTURE);
      case KNAPPING_FIRE_CLAY:
        return new GuiKnapping(container, player, KnappingType.FIRE_CLAY, FIRE_CLAY_TEXTURE);
      case LARGE_VESSEL:
        return new GuiLargeVessel(container, player.inventory, Helpers.getTE(world, pos, TELargeVessel.class), world.getBlockState(new BlockPos(x, y, z))
          .getBlock().getTranslationKey());
      case CALENDAR:
        return new GuiCalendar(container, player.inventory);
      case NUTRITION:
        return new GuiNutrition(container, player.inventory);
      case SKILLS:
        return new GuiSkills(container, player.inventory);
      case CRAFTING:
        return new GuiInventoryCrafting(container);
      case QUIVER:
        return new GuiContainerTFC(container, player.inventory, QUIVER_BACKGROUND);
      case CHEST:
        if (container instanceof ContainerChestTFC) {
          return new GuiChestTFC((ContainerChestTFC) container, player.inventory);
        }
        return null;
      case SALAD:
        return new GuiSalad(container, player.inventory);
      default:
        return null;
    }
  }

  public enum Type {
    LOG_PILE,
    SMALL_VESSEL,
    SMALL_VESSEL_LIQUID,
    MOLD,
    FIRE_PIT,
    BARREL,
    KNAPPING_STONE,
    KNAPPING_CLAY,
    KNAPPING_FIRE_CLAY,
    KNAPPING_LEATHER,
    ANVIL,
    ANVIL_PLAN,
    LARGE_VESSEL,
    CALENDAR,
    NUTRITION,
    SKILLS,
    CHEST,
    SALAD,
    INVENTORY, // This is special, it is used by GuiButtonPlayerInventoryTab to signal to open the vanilla inventory
    CRAFTING, // In-inventory 3x3 crafting grid
    QUIVER,
    NULL; // This is special, it is a non-null null.

    private static final Type[] values = values();

    @Nonnull
    public static Type valueOf(int id) {
      return id < 0 || id >= values.length ? NULL : values[id];
    }
  }
}
