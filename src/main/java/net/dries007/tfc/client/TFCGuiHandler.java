package net.dries007.tfc.client;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.metal.client.gui.GuiGlassworking;
import su.terrafirmagreg.modules.metal.objects.container.ContainerGlassworking;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;


import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.recipes.knapping.KnappingTypes;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.client.gui.GuiCalendar;
import net.dries007.tfc.client.gui.GuiContainerTFC;
import net.dries007.tfc.client.gui.GuiInventoryCrafting;
import net.dries007.tfc.client.gui.GuiKnapping;
import net.dries007.tfc.client.gui.GuiLargeVessel;
import net.dries007.tfc.client.gui.GuiLiquidTransfer;
import net.dries007.tfc.client.gui.GuiNutrition;
import net.dries007.tfc.client.gui.GuiSalad;
import net.dries007.tfc.client.gui.GuiSkills;
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
import net.dries007.tfc.objects.items.glassworking.ItemBlowpipe;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.dries007.tfc.objects.te.TELargeVessel;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.data.Constants.MODID_TFC;

public class TFCGuiHandler implements IGuiHandler {

    public static final ResourceLocation SMALL_INVENTORY_BACKGROUND = new ResourceLocation(MODID_TFC, "textures/gui/small_inventory.png");
    public static final ResourceLocation CLAY_TEXTURE = new ResourceLocation(MODID_TFC, "textures/gui/knapping/clay_button.png");
    public static final ResourceLocation FIRE_CLAY_TEXTURE = new ResourceLocation(MODID_TFC, "textures/gui/knapping/clay_button_fire.png");
    public static final ResourceLocation LEATHER_TEXTURE = new ResourceLocation(MODID_TFC, "textures/gui/knapping/leather_button.png");
    public static final ResourceLocation QUIVER_BACKGROUND = new ResourceLocation(MODID_TFC, "textures/gui/quiver_inventory.png");
    public static final ResourceLocation CLAY_DISABLED_TEXTURE = new ResourceLocation(MODID_TFC, "textures/gui/knapping/clay_button_disabled.png");
    public static final ResourceLocation FIRE_CLAY_DISABLED_TEXTURE = new ResourceLocation(MODID_TFC,
            "textures/gui/knapping/clay_button_fire_disabled.png");

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
        return switch (type) {
            case SMALL_VESSEL ->
                    new ContainerSmallVessel(player.inventory, stack.getItem() instanceof ItemSmallVessel ? stack : player.getHeldItemOffhand());
            case SMALL_VESSEL_LIQUID ->
                    new ContainerLiquidTransfer(player.inventory, stack.getItem() instanceof ItemSmallVessel ? stack : player.getHeldItemOffhand());
            case MOLD -> new ContainerLiquidTransfer(player.inventory, stack.getItem() instanceof ItemMold ? stack : player.getHeldItemOffhand());
            case KNAPPING_STONE -> new ContainerKnapping(KnappingTypes.STONE, player.inventory,
                    stack.getItem() instanceof ItemRock ? stack : player.getHeldItemOffhand());
            case KNAPPING_CLAY -> new ContainerKnapping(KnappingTypes.CLAY, player.inventory,
                    OreDictionaryHelper.doesStackMatchOre(stack, "clay") ? stack : player.getHeldItemOffhand());
            case KNAPPING_LEATHER -> new ContainerKnapping(KnappingTypes.LEATHER, player.inventory,
                    OreDictionaryHelper.doesStackMatchOre(stack, "leather") ? stack : player.getHeldItemOffhand());
            case KNAPPING_FIRE_CLAY -> new ContainerKnapping(KnappingTypes.FIRE_CLAY, player.inventory,
                    OreDictionaryHelper.doesStackMatchOre(stack, "fireClay") ? stack : player.getHeldItemOffhand());
            case LARGE_VESSEL -> new ContainerLargeVessel(player.inventory, TileUtils.getTile(world, pos, TELargeVessel.class));
            case CALENDAR, SKILLS, NUTRITION -> new ContainerSimple(player.inventory);
            case CRAFTING -> new ContainerInventoryCrafting(player.inventory, player.world);
            case QUIVER -> new ContainerQuiver(player.inventory, stack.getItem() instanceof ItemQuiver ? stack : player.getHeldItemOffhand());
            case GLASSWORKING ->
                    new ContainerGlassworking(player.inventory, stack.getItem() instanceof ItemBlowpipe ? stack : player.getHeldItemOffhand());
            case SALAD -> new ContainerSalad(player.inventory);
            default -> null;
        };
    }

    @Override
    @Nullable
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Container container = getServerGuiElement(ID, player, world, x, y, z);
        Type type = Type.valueOf(ID);
        BlockPos pos = new BlockPos(x, y, z);
        return switch (type) {
            case SMALL_VESSEL -> new GuiContainerTFC(container, player.inventory, SMALL_INVENTORY_BACKGROUND);
            case SMALL_VESSEL_LIQUID -> new GuiLiquidTransfer(container, player, player.getHeldItemMainhand().getItem() instanceof ItemSmallVessel);
            case MOLD -> new GuiLiquidTransfer(container, player, player.getHeldItemMainhand().getItem() instanceof ItemMold);
            case KNAPPING_STONE -> {
                ItemStack stack = player.getHeldItemMainhand();
                Rock rock = stack.getItem() instanceof IRockObject iRockObject ? iRockObject.getRock(stack) :
                        ((IRockObject) player.getHeldItemOffhand().getItem()).getRock(player.getHeldItemOffhand());
                //noinspection ConstantConditions
                yield new GuiKnapping(container, player, KnappingTypes.STONE, rock.getTexture());
            }
            case KNAPPING_CLAY -> new GuiKnapping(container, player, KnappingTypes.CLAY, CLAY_TEXTURE);
            case KNAPPING_LEATHER -> new GuiKnapping(container, player, KnappingTypes.LEATHER, LEATHER_TEXTURE);
            case KNAPPING_FIRE_CLAY -> new GuiKnapping(container, player, KnappingTypes.FIRE_CLAY, FIRE_CLAY_TEXTURE);
            case LARGE_VESSEL -> new GuiLargeVessel(container, player.inventory, TileUtils.getTile(world, pos, TELargeVessel.class), world
                    .getBlockState(new BlockPos(x, y, z))
                    .getBlock()
                    .getTranslationKey());
            case CALENDAR -> new GuiCalendar(container, player.inventory);
            case NUTRITION -> new GuiNutrition(container, player.inventory);
            case SKILLS -> new GuiSkills(container, player.inventory);
            case CRAFTING -> new GuiInventoryCrafting(container);
            case QUIVER -> new GuiContainerTFC(container, player.inventory, QUIVER_BACKGROUND);
            case GLASSWORKING -> new GuiGlassworking(container, player);
            case SALAD -> new GuiSalad(container, player.inventory);
            default -> null;
        };
    }

    public enum Type {
        SMALL_VESSEL,
        SMALL_VESSEL_LIQUID,
        MOLD,
        KNAPPING_STONE,
        KNAPPING_CLAY,
        KNAPPING_FIRE_CLAY,
        KNAPPING_LEATHER,
        LARGE_VESSEL,
        POWDERKEG,
        CALENDAR,
        NUTRITION,
        SKILLS,
        SALAD,
        INVENTORY, // This is special, it is used by GuiButtonPlayerInventoryTab to signal to open the vanilla inventory
        CRAFTING, // In-inventory 3x3 crafting grid
        QUIVER,
        GLASSWORKING,
        NULL; // This is special, it is a non-null null.

        private static final Type[] values = values();

        @NotNull
        public static Type valueOf(int id) {
            return id < 0 || id >= values.length ? NULL : values[id];
        }
    }
}
