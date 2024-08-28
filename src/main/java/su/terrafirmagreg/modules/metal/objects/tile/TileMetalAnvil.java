package su.terrafirmagreg.modules.metal.objects.tile;

import su.terrafirmagreg.api.base.tile.BaseTileInventory;
import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.api.util.StringUtils;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.ICapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.player.CapabilityPlayer;
import su.terrafirmagreg.modules.core.network.SCPacketSimpleMessage;
import su.terrafirmagreg.modules.metal.ModuleMetal;
import su.terrafirmagreg.modules.metal.client.gui.GuiMetalAnvil;
import su.terrafirmagreg.modules.metal.objects.container.ContainerMetalAnvil;
import su.terrafirmagreg.modules.metal.objects.recipe.anvil.AnvilRecipeManager;
import su.terrafirmagreg.modules.metal.objects.recipe.anvil.IAnvilRecipe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;


import gregtech.common.items.ToolItems;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.capability.inventory.ISlotCallback;
import net.dries007.tfc.api.capability.inventory.ItemStackHandlerCallback;
import net.dries007.tfc.api.recipes.WeldingRecipe;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.util.forge.ForgeStep;
import net.dries007.tfc.util.forge.ForgeSteps;
import net.dries007.tfc.util.skills.SkillType;
import net.dries007.tfc.util.skills.SmithingSkill;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;

// TODO делать отдельный класс для каменной наковали, без интерфейса

public class TileMetalAnvil
        extends BaseTileInventory
        implements IProviderContainer<ContainerMetalAnvil, GuiMetalAnvil> {

    public static final int WORK_MAX = 145;
    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_INPUT_2 = 1;
    public static final int SLOT_HAMMER = 2;
    public static final int SLOT_FLUX = 3;
    private IAnvilRecipe recipe;
    private ForgeSteps steps;
    @Getter
    private int workingProgress = 0;
    @Getter
    private int workingTarget = 0;

    public TileMetalAnvil() {
        super(AnvilItemHandler::new, 4);

        this.steps = new ForgeSteps();
        this.recipe = null;
    }

    // TODO попробовать int
    public Metal.Tier getTier() {
        IBlockState state = world.getBlockState(pos);
        //        if (state.getBlock() instanceof BlockMetalAnvil anvil) {
        //            return anvil.getType()
        //                    .getMaterial()
        //                    .getProperty(TFGPropertyKey.HEAT)
        //                    .getTier();
        //        }
        return Metal.Tier.TIER_0;
    }

    @Nullable
    public IAnvilRecipe getRecipe() {
        return recipe;
    }

    @NotNull
    public ForgeSteps getSteps() {
        return steps;
    }

    /**
     * Sets the anvil TE recipe, called after pressing the recipe button This is the ONLY place that should write to {@link this#recipe}
     *
     * @param recipe the recipe to set to
     * @return true if a packet needs to be sent to the client for a recipe update
     */
    public boolean setRecipe(@Nullable IAnvilRecipe recipe) {
        ItemStack stack = inventory.getStackInSlot(SLOT_INPUT_1);
        IForgeable cap = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
        boolean recipeChanged;

        if (cap != null && recipe != null) {
            // Update recipe in both
            recipeChanged = this.recipe != recipe;
            cap.setRecipe(recipe);
            this.recipe = recipe;

            // Update server side fields
            workingProgress = cap.getWork();
            steps = cap.getSteps().copy();

            workingTarget = recipe.getTarget(world.getSeed());
        } else {
            // Set recipe to null because either item is missing, or it was requested
            recipeChanged = this.recipe != null;
            this.recipe = null;
            if (cap != null) {
                cap.reset();
            }
            resetFields();
        }

        return recipeChanged;
    }

    /**
     * Slot updates only happen on server side, so update when change is made so that items displayed on the anvil are synced up
     *
     * @param slot a slot id, or -1 if triggered by other methods
     */
    @Override
    public void setAndUpdateSlots(int slot) {
        super.setAndUpdateSlots(slot);
        if (!world.isRemote) {
            if (slot == SLOT_INPUT_1 || slot == SLOT_INPUT_2) {
                checkRecipeUpdate();
            }
            markForSync();
        }
    }

    @Override
    public void readFromNBT(@NotNull NBTTagCompound nbt) {
        String recipe = nbt.getString("recipe");
        if (StringUtils.isNullOrEmpty(recipe)) {
            this.recipe = null;
        } else {
            this.recipe = AnvilRecipeManager.findMatchingRecipe(new ResourceLocation(recipe));
        }
        this.steps.deserializeNBT(nbt.getCompoundTag("steps"));
        this.workingProgress = nbt.getInteger("work");
        this.workingTarget = nbt.getInteger("target");
        super.readFromNBT(nbt);
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public NBTTagCompound writeToNBT(@NotNull NBTTagCompound nbt) {
        if (recipe != null) {
            NBTUtils.setGenericNBTValue(nbt, "recipe", this.recipe.getRecipeName().toString());
        }
        NBTUtils.setGenericNBTValue(nbt, "steps", this.steps.serializeNBT());
        NBTUtils.setGenericNBTValue(nbt, "work", this.workingProgress);
        NBTUtils.setGenericNBTValue(nbt, "target", this.workingTarget);
        return super.writeToNBT(nbt);
    }

    @Override
    public int getSlotLimit(int slot) {
        return slot == SLOT_FLUX ? super.getSlotLimit(slot) : 1;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return switch (slot) {
            case SLOT_INPUT_1, SLOT_INPUT_2 -> stack.hasCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
            case SLOT_FLUX -> OreDictUtils.contains(stack, "dustFlux");
            case SLOT_HAMMER -> stack.getItem() == ToolItems.HARD_HAMMER.get();
            default -> false;
        };
    }

    /**
     * Only occurs on server side
     */
    public void addStep(@NotNull EntityPlayer player, @Nullable ForgeStep step) {
        ItemStack input = inventory.getStackInSlot(SLOT_INPUT_1);
        IForgeable cap = input.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
        if (cap != null) {
            // Add step to stack + tile
            if (step != null) {
                if (!cap.getSteps().hasWork() && step.getStepAmount() < 0) {
                    // Newbie helper
                    // Never start with a red step (which would immediately destroy input)
                    player.sendStatusMessage(new TextComponentTranslation("tfc.tooltip.anvil_safety"), false);
                } else {
                    cap.addStep(step);
                    steps = cap.getSteps().copy();
                    workingProgress += step.getStepAmount();
                    world.playSound(null, pos, TFCSounds.ANVIL_IMPACT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                }
            }

            // Handle possible recipe completion
            if (recipe != null) {
                IAnvilRecipe completedRecipe = recipe; // Hold the current recipe, as setting the input slot to empty will clear it
                int workMin = workingTarget - ConfigTFC.General.DIFFICULTY.acceptableAnvilRange;
                int workMax = workingTarget + ConfigTFC.General.DIFFICULTY.acceptableAnvilRange;
                if ((workingProgress <= workMax && workingProgress >= workMin) && completedRecipe.matches(steps)) {
                    //Consume input
                    inventory.setStackInSlot(SLOT_INPUT_1, ItemStack.EMPTY);

                    // Add Skill
                    SmithingSkill skill = CapabilityPlayer.getSkill(player, SkillType.SMITHING);
                    if (skill != null && completedRecipe.getSkillBonusType() != null) {
                        skill.addSkill(completedRecipe.getSkillBonusType(), 1);
                    }

                    //Produce output
                    for (ItemStack output : completedRecipe.getOutputItem(input)) {
                        if (!output.isEmpty()) {
                            var outputCap = CapabilityHeat.get(output);
                            if (outputCap != null && cap instanceof ICapabilityHeat heat) {
                                outputCap.setTemperature(heat.getTemperature());
                            }
                            if (skill != null && completedRecipe.getSkillBonusType() != null) {
                                SmithingSkill.applySkillBonus(skill, output, completedRecipe.getSkillBonusType());
                            }

                            if (inventory.getStackInSlot(SLOT_INPUT_1).isEmpty()) {
                                inventory.setStackInSlot(SLOT_INPUT_1, output);
                            } else if (inventory.getStackInSlot(SLOT_INPUT_2).isEmpty()) {
                                inventory.setStackInSlot(SLOT_INPUT_2, output);
                            } else {
                                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), output);
                            }
                        }
                    }

                    world.playSound(null, pos, TFCSounds.ANVIL_IMPACT, SoundCategory.PLAYERS, 1.0f, 1.0f);

                    // Reset forge stuff
                    resetFields();
                    // if we have a valid single item recipe for our output, use it!
                    IAnvilRecipe newRecipe = null;
                    if (inventory.getStackInSlot(SLOT_INPUT_2).isEmpty()) {
                        List<IAnvilRecipe> recipes = AnvilRecipeManager.getAllFor(inventory.getStackInSlot(SLOT_INPUT_1));
                        if (recipes.size() == 1) {
                            newRecipe = recipes.get(0);
                        }
                    }
                    setRecipe(newRecipe);
                } else if (workingProgress < 0 || workingProgress > WORK_MAX) {
                    // Consume input, produce no output
                    inventory.setStackInSlot(SLOT_INPUT_1, ItemStack.EMPTY);
                    world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
                }
            }
            markForSync();
        }
    }

    /**
     * Attempts to weld the two items in the input slots.
     *
     * @param player The player that clicked the anvil (must be server player . . .)
     * @return true if the weld was successful
     */
    public boolean attemptWelding(EntityPlayer player) {
        ItemStack input1 = inventory.getStackInSlot(SLOT_INPUT_1), input2 = inventory.getStackInSlot(SLOT_INPUT_2);
        if (input1.isEmpty() || input2.isEmpty()) {
            // No message as this will happen whenever the player clicks the anvil with a hammer
            return false;
        }

        // Find a matching welding recipe
        WeldingRecipe recipe = WeldingRecipe.get(input1, input2, getTier());
        if (recipe != null) {
            ItemStack fluxStack = inventory.getStackInSlot(SLOT_FLUX);
            if (fluxStack.isEmpty()) {
                // No flux
                ModuleMetal.getPacketService()
                        .sendTo(SCPacketSimpleMessage.translateMessage(SCPacketSimpleMessage.MessageCategory.ANVIL, ModUtils.localize("tooltip", "metal.anvil_no_flux")),
                                (EntityPlayerMP) player);
                return false;
            }

            // Check if both are weldable
            IForgeable cap1 = input1.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
            IForgeable cap2 = input2.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
            if (cap1 == null || cap2 == null || !cap1.isWeldable() || !cap2.isWeldable()) {
                if (cap1 instanceof ICapabilityHeat && cap2 instanceof ICapabilityHeat) {
                    ModuleMetal.getPacketService()
                            .sendTo(SCPacketSimpleMessage.translateMessage(SCPacketSimpleMessage.MessageCategory.ANVIL, ModUtils.localize("tooltip", "metal.anvil_too_cold")),
                                    (EntityPlayerMP) player);
                } else {
                    ModuleMetal.getPacketService().sendTo(
                            SCPacketSimpleMessage.translateMessage(SCPacketSimpleMessage.MessageCategory.ANVIL, ModUtils.localize("tooltip", "metal.anvil_not_weldable")),
                            (EntityPlayerMP) player);
                }
                return false;
            }
            ItemStack result = recipe.getOutput(player);
            var heatResult = CapabilityHeat.get(result);
            float resultTemperature = 0;
            if (cap1 instanceof ICapabilityHeat capabilityHeat) {
                resultTemperature = capabilityHeat.getTemperature();
            }
            if (cap2 instanceof ICapabilityHeat capabilityHeat) {
                resultTemperature = Math.min(resultTemperature, capabilityHeat.getTemperature());
            }
            if (heatResult != null) {
                // Every welding result should have this capability, but don't fail if it doesn't
                heatResult.setTemperature(resultTemperature);
            }

            // Set stacks in slots
            inventory.setStackInSlot(SLOT_INPUT_1, result);
            inventory.setStackInSlot(SLOT_INPUT_2, ItemStack.EMPTY);
            inventory.setStackInSlot(SLOT_FLUX, StackUtils.consumeItem(fluxStack, 1));
            markForSync();

            return true;
        }

        // For when there is both inputs but no recipe that matches
        ModuleMetal.getPacketService().sendTo(
                SCPacketSimpleMessage.translateMessage(SCPacketSimpleMessage.MessageCategory.ANVIL, ModUtils.localize("tooltip", "anvil_not_weldable")), (EntityPlayerMP) player);
        return false;
    }

    private void checkRecipeUpdate() {
        ItemStack stack = inventory.getStackInSlot(SLOT_INPUT_1);
        IForgeable cap = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
        if (cap == null && recipe != null) {
            // Check for item removed / broken
            setRecipe(null);
        } else if (cap != null) {
            // Check for mismatched recipe
            IAnvilRecipe capRecipe = AnvilRecipeManager.findMatchingRecipe(cap.getRecipeName());
            if (capRecipe != recipe) {
                setRecipe(capRecipe);
            } else if (AnvilRecipeManager.getAllFor(stack).size() == 1) {
                setRecipe(AnvilRecipeManager.getAllFor(stack).get(0));
            }
        }
    }

    private void resetFields() {
        workingProgress = 0;
        workingTarget = 0;
        steps.reset();
    }

    @Override
    public ContainerMetalAnvil getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
        return new ContainerMetalAnvil(inventoryPlayer, this);
    }

    @Override
    public GuiMetalAnvil getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
        return new GuiMetalAnvil(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, this);
    }

    private static class AnvilItemHandler extends ItemStackHandlerCallback {

        public AnvilItemHandler(ISlotCallback callback, int slots) {
            super(callback, slots);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            ItemStack result = super.extractItem(slot, amount, simulate);
            if (slot == SLOT_INPUT_1 || slot == SLOT_INPUT_2) {
                IForgeable cap = result.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap != null && cap.getRecipeName() != null && (!cap.getSteps().hasWork() || cap.getWork() == 0)) {
                    cap.reset();
                }

            }
            return result;
        }
    }

}
