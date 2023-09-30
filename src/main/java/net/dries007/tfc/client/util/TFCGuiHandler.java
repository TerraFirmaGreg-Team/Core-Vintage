package net.dries007.tfc.client.util;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.client.gui.*;
import net.dries007.tfc.module.animal.common.container.ContainerNestBox;
import net.dries007.tfc.module.animal.common.tiles.TENestBox;
import net.dries007.tfc.module.ceramic.client.gui.GuiLargeVessel;
import net.dries007.tfc.module.ceramic.common.container.ContainerLargeVessel;
import net.dries007.tfc.module.ceramic.common.container.ContainerSmallVessel;
import net.dries007.tfc.module.ceramic.common.items.ItemMold;
import net.dries007.tfc.module.ceramic.common.items.ItemSmallVessel;
import net.dries007.tfc.module.ceramic.common.tiles.TELargeVessel;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.core.objects.container.*;
import net.dries007.tfc.module.core.objects.items.ItemQuiver;
import net.dries007.tfc.module.core.objects.tiles.TEPowderKeg;
import net.dries007.tfc.module.devices.client.gui.GuiBlastFurnace;
import net.dries007.tfc.module.devices.client.gui.GuiCharcoalForge;
import net.dries007.tfc.module.devices.client.gui.GuiCrucible;
import net.dries007.tfc.module.devices.client.gui.GuiFirePit;
import net.dries007.tfc.module.devices.common.container.*;
import net.dries007.tfc.module.devices.common.tile.*;
import net.dries007.tfc.module.food.common.container.ContainerSalad;
import net.dries007.tfc.module.metal.client.gui.GuiMetalAnvil;
import net.dries007.tfc.module.metal.client.gui.GuiMetalAnvilPlan;
import net.dries007.tfc.module.metal.common.container.ContainerAnvilPlan;
import net.dries007.tfc.module.metal.common.container.ContainerAnvilTFC;
import net.dries007.tfc.module.metal.common.tiles.TEMetalAnvil;
import net.dries007.tfc.module.rock.common.items.ItemRockLoose;
import net.dries007.tfc.module.wood.client.gui.GuiWoodBarrel;
import net.dries007.tfc.module.wood.client.gui.GuiWoodChest;
import net.dries007.tfc.module.wood.common.blocks.BlockWoodChest;
import net.dries007.tfc.module.wood.common.container.ContainerWoodBarrel;
import net.dries007.tfc.module.wood.common.container.ContainerWoodChest;
import net.dries007.tfc.module.wood.common.tiles.TEWoodBarrel;
import net.dries007.tfc.util.OreDictionaryHelper;
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

public class TFCGuiHandler implements IGuiHandler {
    public static final ResourceLocation SMALL_INVENTORY_BACKGROUND = Helpers.getID("textures/gui/small_inventory.png");
    public static final ResourceLocation CLAY_TEXTURE = Helpers.getID("textures/gui/knapping/clay_button.png");
    public static final ResourceLocation FIRE_CLAY_TEXTURE = Helpers.getID("textures/gui/knapping/clay_button_fire.png");
    public static final ResourceLocation LEATHER_TEXTURE = Helpers.getID("textures/gui/knapping/leather_button.png");
    public static final ResourceLocation QUIVER_BACKGROUND = Helpers.getID("textures/gui/quiver_inventory.png");
    public static final ResourceLocation CLAY_DISABLED_TEXTURE = Helpers.getID("textures/gui/knapping/clay_button_disabled.png");
    public static final ResourceLocation FIRE_CLAY_DISABLED_TEXTURE = Helpers.getID("textures/gui/knapping/clay_button_fire_disabled.png");

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
        // This is null if the chest is blocked
        return switch (type) {
            case NEST_BOX -> {
                TENestBox teNestBox = Helpers.getTE(world, pos, TENestBox.class);
                yield teNestBox == null ? null : new ContainerNestBox(player.inventory, teNestBox);
            }
            case LOG_PILE -> {
                TELogPile teLogPile = Helpers.getTE(world, pos, TELogPile.class);
                yield teLogPile == null ? null : new ContainerLogPile(player.inventory, teLogPile);
            }
            case SMALL_VESSEL ->
                    new ContainerSmallVessel(player.inventory, stack.getItem() instanceof ItemSmallVessel ? stack : player.getHeldItemOffhand());
            case SMALL_VESSEL_LIQUID ->
                    new ContainerLiquidTransfer(player.inventory, stack.getItem() instanceof ItemSmallVessel ? stack : player.getHeldItemOffhand());
            case MOLD ->
                    new ContainerLiquidTransfer(player.inventory, stack.getItem() instanceof ItemMold ? stack : player.getHeldItemOffhand());
            case FIRE_PIT ->
                //noinspection ConstantConditions
                    new ContainerFirePit(player.inventory, Helpers.getTE(world, pos, TEFirePit.class));
            case BARREL -> new ContainerWoodBarrel(player.inventory, Helpers.getTE(world, pos, TEWoodBarrel.class));
            case CHARCOAL_FORGE ->
                //noinspection ConstantConditions
                    new ContainerCharcoalForge(player.inventory, Helpers.getTE(world, pos, TECharcoalForge.class));
            case ANVIL ->
                //noinspection ConstantConditions
                    new ContainerAnvilTFC(player.inventory, Helpers.getTE(world, pos, TEMetalAnvil.class));
            case ANVIL_PLAN -> new ContainerAnvilPlan(player.inventory, Helpers.getTE(world, pos, TEMetalAnvil.class));
            case KNAPPING_STONE ->
                    new ContainerKnapping(KnappingType.STONE, player.inventory, stack.getItem() instanceof ItemRockLoose ? stack : player.getHeldItemOffhand());
            case KNAPPING_CLAY ->
                    new ContainerKnapping(KnappingType.CLAY, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "clay") ? stack : player.getHeldItemOffhand());
            case KNAPPING_LEATHER ->
                    new ContainerKnapping(KnappingType.LEATHER, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "leather") ? stack : player.getHeldItemOffhand());
            case KNAPPING_FIRE_CLAY ->
                    new ContainerKnapping(KnappingType.FIRE_CLAY, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "fireClay") ? stack : player.getHeldItemOffhand());
            case CRUCIBLE -> new ContainerCrucible(player.inventory, Helpers.getTE(world, pos, TECrucible.class));
            case LARGE_VESSEL ->
                    new ContainerLargeVessel(player.inventory, Helpers.getTE(world, pos, TELargeVessel.class));
            case POWDERKEG -> new ContainerPowderKeg(player.inventory, Helpers.getTE(world, pos, TEPowderKeg.class));
            case CALENDAR, SKILLS, NUTRITION -> new ContainerSimple(player.inventory);
            case BLAST_FURNACE ->
                    new ContainerBlastFurnace(player.inventory, Helpers.getTE(world, pos, TEBlastFurnace.class));
            case CRAFTING -> new ContainerInventoryCrafting(player.inventory, player.world);
            case QUIVER ->
                    new ContainerQuiver(player.inventory, stack.getItem() instanceof ItemQuiver ? stack : player.getHeldItemOffhand());
            case CHEST -> {
                if (world.getBlockState(pos).getBlock() instanceof BlockWoodChest) {
                    ILockableContainer chestContainer = ((BlockWoodChest) world.getBlockState(pos).getBlock()).getLockableContainer(world, pos);
                    if (chestContainer == null) // This is null if the chest is blocked
                    {
                        yield null;
                    }
                    yield new ContainerWoodChest(player.inventory, chestContainer, player);
                }
                yield null;
            }
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
            case NEST_BOX:
            case SMALL_VESSEL:
            case LOG_PILE:
                return new GuiContainerTFC(container, player.inventory, SMALL_INVENTORY_BACKGROUND);
            case SMALL_VESSEL_LIQUID:
                return new GuiLiquidTransfer(container, player, player.getHeldItemMainhand().getItem() instanceof ItemSmallVessel);
            case MOLD:
                return new GuiLiquidTransfer(container, player, player.getHeldItemMainhand().getItem() instanceof ItemMold);
            case FIRE_PIT:
                return new GuiFirePit(container, player.inventory, Helpers.getTE(world, pos, TEFirePit.class));
            case BARREL:
                return new GuiWoodBarrel(container, player.inventory, Helpers.getTE(world, pos, TEWoodBarrel.class), world.getBlockState(new BlockPos(x, y, z)).getBlock().getTranslationKey());
            case CHARCOAL_FORGE:
                return new GuiCharcoalForge(container, player.inventory, Helpers.getTE(world, pos, TECharcoalForge.class));
            case ANVIL:
                return new GuiMetalAnvil(container, player.inventory, Helpers.getTE(world, pos, TEMetalAnvil.class));
            case ANVIL_PLAN:
                return new GuiMetalAnvilPlan(container, player.inventory, Helpers.getTE(world, pos, TEMetalAnvil.class));
            case KNAPPING_STONE:
                var stackInMainHand = player.getHeldItemMainhand();
                var stackInOffHand = player.getHeldItemOffhand();

                if (stackInMainHand.getItem() instanceof ItemRockLoose itemRockLoose) {
                    return new GuiKnapping(container, player, KnappingType.STONE, itemRockLoose.getType().getTexture());
                } else if (stackInOffHand.getItem() instanceof ItemRockLoose itemRockLoose) {
                    return new GuiKnapping(container, player, KnappingType.STONE, itemRockLoose.getType().getTexture());
                }

                throw new RuntimeException("Bad itemstack on open knapping gui");
            case KNAPPING_CLAY:
                return new GuiKnapping(container, player, KnappingType.CLAY, CLAY_TEXTURE);
            case KNAPPING_LEATHER:
                return new GuiKnapping(container, player, KnappingType.LEATHER, LEATHER_TEXTURE);
            case KNAPPING_FIRE_CLAY:
                return new GuiKnapping(container, player, KnappingType.FIRE_CLAY, FIRE_CLAY_TEXTURE);
            case CRUCIBLE:
                return new GuiCrucible(container, player.inventory, Helpers.getTE(world, pos, TECrucible.class));
            case LARGE_VESSEL:
                return new GuiLargeVessel(container, player.inventory, Helpers.getTE(world, pos, TELargeVessel.class), world.getBlockState(new BlockPos(x, y, z)).getBlock().getTranslationKey());
            case POWDERKEG:
                return new GuiPowderkeg(container, player.inventory, Helpers.getTE(world, pos, TEPowderKeg.class), world.getBlockState(new BlockPos(x, y, z)).getBlock().getTranslationKey());
            case CALENDAR:
                return new GuiCalendar(container, player.inventory);
            case NUTRITION:
                return new GuiNutrition(container, player.inventory);
            case SKILLS:
                return new GuiSkills(container, player.inventory);
            case BLAST_FURNACE:
                return new GuiBlastFurnace(container, player.inventory, Helpers.getTE(world, pos, TEBlastFurnace.class));
            case CRAFTING:
                return new GuiInventoryCrafting(container);
            case QUIVER:
                return new GuiContainerTFC(container, player.inventory, QUIVER_BACKGROUND);
            case CHEST:
                if (container instanceof ContainerWoodChest) {
                    return new GuiWoodChest((ContainerWoodChest) container, player.inventory);
                }
                return null;
            case SALAD:
                return new GuiSalad(container, player.inventory);
            default:
                return null;
        }
    }

    public enum Type {
        NEST_BOX,
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
        CHARCOAL_FORGE,
        ANVIL,
        ANVIL_PLAN,
        CRUCIBLE,
        BLAST_FURNACE,
        LARGE_VESSEL,
        POWDERKEG,
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
