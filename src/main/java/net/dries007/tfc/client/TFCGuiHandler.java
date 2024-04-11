package net.dries007.tfc.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;


import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.client.gui.GuiAnvilPlan;
import net.dries007.tfc.client.gui.GuiAnvilTFC;
import net.dries007.tfc.client.gui.GuiCalendar;
import net.dries007.tfc.client.gui.GuiContainerTFC;
import net.dries007.tfc.client.gui.GuiInventoryCrafting;
import net.dries007.tfc.client.gui.GuiKnapping;
import net.dries007.tfc.client.gui.GuiLargeVessel;
import net.dries007.tfc.client.gui.GuiLiquidTransfer;
import net.dries007.tfc.client.gui.GuiNutrition;
import net.dries007.tfc.client.gui.GuiPowderkeg;
import net.dries007.tfc.client.gui.GuiSalad;
import net.dries007.tfc.client.gui.GuiSkills;
import net.dries007.tfc.objects.container.ContainerAnvilPlan;
import net.dries007.tfc.objects.container.ContainerAnvilTFC;
import net.dries007.tfc.objects.container.ContainerInventoryCrafting;
import net.dries007.tfc.objects.container.ContainerKnapping;
import net.dries007.tfc.objects.container.ContainerLargeVessel;
import net.dries007.tfc.objects.container.ContainerLiquidTransfer;
import net.dries007.tfc.objects.container.ContainerPowderKeg;
import net.dries007.tfc.objects.container.ContainerQuiver;
import net.dries007.tfc.objects.container.ContainerSalad;
import net.dries007.tfc.objects.container.ContainerSimple;
import net.dries007.tfc.objects.container.ContainerSmallVessel;
import net.dries007.tfc.objects.items.ItemQuiver;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.objects.items.ceramics.ItemSmallVessel;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.dries007.tfc.objects.te.TEAnvilTFC;
import net.dries007.tfc.objects.te.TELargeVessel;
import net.dries007.tfc.objects.te.TEPowderKeg;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

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
            case ANVIL ->
                //noinspection ConstantConditions
                    new ContainerAnvilTFC(player.inventory, Helpers.getTE(world, pos, TEAnvilTFC.class));
            case ANVIL_PLAN -> new ContainerAnvilPlan(player.inventory, Helpers.getTE(world, pos, TEAnvilTFC.class));
            case KNAPPING_STONE -> new ContainerKnapping(KnappingType.STONE, player.inventory,
                    stack.getItem() instanceof ItemRock ? stack : player.getHeldItemOffhand());
            case KNAPPING_CLAY -> new ContainerKnapping(KnappingType.CLAY, player.inventory,
                    OreDictionaryHelper.doesStackMatchOre(stack, "clay") ? stack : player.getHeldItemOffhand());
            case KNAPPING_LEATHER -> new ContainerKnapping(KnappingType.LEATHER, player.inventory,
                    OreDictionaryHelper.doesStackMatchOre(stack, "leather") ? stack : player.getHeldItemOffhand());
            case KNAPPING_FIRE_CLAY -> new ContainerKnapping(KnappingType.FIRE_CLAY, player.inventory,
                    OreDictionaryHelper.doesStackMatchOre(stack, "fireClay") ? stack : player.getHeldItemOffhand());
            case LARGE_VESSEL -> new ContainerLargeVessel(player.inventory, Helpers.getTE(world, pos, TELargeVessel.class));
            case POWDERKEG -> new ContainerPowderKeg(player.inventory, Helpers.getTE(world, pos, TEPowderKeg.class));
            case CALENDAR, SKILLS, NUTRITION -> new ContainerSimple(player.inventory);
            case CRAFTING -> new ContainerInventoryCrafting(player.inventory, player.world);
            case QUIVER -> new ContainerQuiver(player.inventory, stack.getItem() instanceof ItemQuiver ? stack : player.getHeldItemOffhand());
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
        switch (type) {
            case SMALL_VESSEL:
                return new GuiContainerTFC(container, player.inventory, SMALL_INVENTORY_BACKGROUND);
            case SMALL_VESSEL_LIQUID:
                return new GuiLiquidTransfer(container, player, player.getHeldItemMainhand().getItem() instanceof ItemSmallVessel);
            case MOLD:
                return new GuiLiquidTransfer(container, player, player.getHeldItemMainhand().getItem() instanceof ItemMold);
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
                return new GuiLargeVessel(container, player.inventory, Helpers.getTE(world, pos, TELargeVessel.class), world
                        .getBlockState(new BlockPos(x, y, z))
                        .getBlock()
                        .getTranslationKey());
            case POWDERKEG:
                return new GuiPowderkeg(container, player.inventory, Helpers.getTE(world, pos, TEPowderKeg.class), world
                        .getBlockState(new BlockPos(x, y, z))
                        .getBlock()
                        .getTranslationKey());
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
            case SALAD:
                return new GuiSalad(container, player.inventory);
            default:
                return null;
        }
    }

    public enum Type {
        SMALL_VESSEL,
        SMALL_VESSEL_LIQUID,
        MOLD,
        KNAPPING_STONE,
        KNAPPING_CLAY,
        KNAPPING_FIRE_CLAY,
        KNAPPING_LEATHER,
        ANVIL,
        ANVIL_PLAN,
        LARGE_VESSEL,
        POWDERKEG,
        CALENDAR,
        NUTRITION,
        SKILLS,
        SALAD,
        INVENTORY, // This is special, it is used by GuiButtonPlayerInventoryTab to signal to open the vanilla inventory
        CRAFTING, // In-inventory 3x3 crafting grid
        QUIVER,
        NULL; // This is special, it is a non-null null.

        private static final Type[] values = values();

        @NotNull
        public static Type valueOf(int id) {
            return id < 0 || id >= values.length ? NULL : values[id];
        }
    }
}
