package net.dries007.tfc.objects.te;

import su.terrafirmagreg.modules.core.capabilities.forge.CapabilityForgeable;
import su.terrafirmagreg.modules.core.capabilities.forge.ICapabilityForge;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.ICapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.playerdata.CapabilityPlayerData;
import su.terrafirmagreg.modules.core.feature.skill.SkillType;
import su.terrafirmagreg.modules.core.feature.skill.SmithingSkill;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextComponentTranslation;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.recipes.WeldingRecipe;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.network.PacketSimpleMessage;
import net.dries007.tfc.network.PacketSimpleMessage.MessageCategory;
import net.dries007.tfc.objects.blocks.metal.BlockAnvilTFC;
import net.dries007.tfc.objects.blocks.stone.BlockStoneAnvil;
import net.dries007.tfc.objects.inventory.capability.ISlotCallback;
import net.dries007.tfc.objects.inventory.capability.ItemStackHandlerCallback;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.forge.ForgeStep;
import net.dries007.tfc.util.forge.ForgeSteps;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFC;

@ParametersAreNonnullByDefault
public class TEAnvilTFC extends TEInventory {

  public static final int WORK_MAX = 145;
  public static final int SLOT_INPUT_1 = 0;
  public static final int SLOT_INPUT_2 = 1;
  public static final int SLOT_HAMMER = 2;
  public static final int SLOT_FLUX = 3;
  private AnvilRecipe recipe;
  private ForgeSteps steps;
  private int workingProgress = 0;
  private int workingTarget = 0;

  public TEAnvilTFC() {
    super(AnvilItemHandler::new, 4);

    steps = new ForgeSteps();
    recipe = null;
  }

  public boolean isStone() {
    IBlockState state = world.getBlockState(pos);
    return state.getBlock() instanceof BlockStoneAnvil;
  }

  @Nonnull
  public Metal.Tier getTier() {
    IBlockState state = world.getBlockState(pos);
    if (state.getBlock() instanceof BlockAnvilTFC) {
      return ((BlockAnvilTFC) state.getBlock()).getMetal().getTier();
    }
    return Metal.Tier.TIER_0;
  }

  @Nullable
  public AnvilRecipe getRecipe() {
    return recipe;
  }

  @Nonnull
  public ForgeSteps getSteps() {
    return steps;
  }

  /**
   * Sets the anvil TE recipe, called after pressing the recipe button This is the ONLY place that should write to {@link this#recipe}
   *
   * @param recipe the recipe to set to
   * @return true if a packet needs to be sent to the client for a recipe update
   */
  public boolean setRecipe(@Nullable AnvilRecipe recipe) {
    ItemStack stack = inventory.getStackInSlot(SLOT_INPUT_1);
    ICapabilityForge cap = stack.getCapability(CapabilityForgeable.CAPABILITY, null);
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
  public void readFromNBT(NBTTagCompound nbt) {
    String recipe = nbt.getString("recipe");
    if (StringUtils.isNullOrEmpty(recipe)) {
      this.recipe = null;
    } else {
      this.recipe = TFCRegistries.ANVIL.getValue(new ResourceLocation(recipe));
    }
    this.steps.deserializeNBT(nbt.getCompoundTag("steps"));
    this.workingProgress = nbt.getInteger("work");
    this.workingTarget = nbt.getInteger("target");
    super.readFromNBT(nbt);
  }

  @SuppressWarnings("ConstantConditions")
  @Nonnull
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    if (recipe != null) {
      nbt.setString("recipe", this.recipe.getRegistryName().toString());
    }
    nbt.setTag("steps", this.steps.serializeNBT());
    nbt.setInteger("work", this.workingProgress);
    nbt.setInteger("target", this.workingTarget);
    return super.writeToNBT(nbt);
  }

  @Override
  public int getSlotLimit(int slot) {
    return slot == SLOT_FLUX ? super.getSlotLimit(slot) : 1;
  }

  @Override
  public boolean isItemValid(int slot, ItemStack stack) {
    switch (slot) {
      case SLOT_INPUT_1:
      case SLOT_INPUT_2:
        return stack.hasCapability(CapabilityForgeable.CAPABILITY, null);
      case SLOT_FLUX:
        return OreDictionaryHelper.doesStackMatchOre(stack, "dustFlux");
      case SLOT_HAMMER:
        return OreDictionaryHelper.doesStackMatchOre(stack, "toolHammer");
      default:
        return false;
    }
  }

  /**
   * Only occurs on server side
   */
  public void addStep(@Nonnull EntityPlayer player, @Nullable ForgeStep step) {
    ItemStack input = inventory.getStackInSlot(SLOT_INPUT_1);
    ICapabilityForge cap = input.getCapability(CapabilityForgeable.CAPABILITY, null);
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
        AnvilRecipe completedRecipe = recipe; // Hold the current recipe, as setting the input slot to empty will clear it
        int workMin = workingTarget - ConfigTFC.General.DIFFICULTY.acceptableAnvilRange;
        int workMax = workingTarget + ConfigTFC.General.DIFFICULTY.acceptableAnvilRange;
        if ((workingProgress <= workMax && workingProgress >= workMin) && completedRecipe.matches(steps)) {
          //Consume input
          inventory.setStackInSlot(SLOT_INPUT_1, ItemStack.EMPTY);

          // Add Skill
          SmithingSkill skill = CapabilityPlayerData.getSkill(player, SkillType.SMITHING);
          if (skill != null && completedRecipe.getSkillBonusType() != null) {
            skill.addSkill(completedRecipe.getSkillBonusType(), 1);
          }

          //Produce output
          for (ItemStack output : completedRecipe.getOutput(input)) {
            if (!output.isEmpty()) {
              ICapabilityHeat outputCap = output.getCapability(CapabilityHeat.CAPABILITY, null);
              if (outputCap != null && cap instanceof ICapabilityHeat) {
                outputCap.setTemperature(((ICapabilityHeat) cap).getTemperature());
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
          AnvilRecipe newRecipe = null;
          if (inventory.getStackInSlot(SLOT_INPUT_2).isEmpty()) {
            List<AnvilRecipe> recipes = AnvilRecipe.getAllFor(inventory.getStackInSlot(SLOT_INPUT_1));
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
        TerraFirmaCraft.getNetwork()
          .sendTo(PacketSimpleMessage.translateMessage(MessageCategory.ANVIL, TFC + ".tooltip.anvil_no_flux"), (EntityPlayerMP) player);
        return false;
      }

      // Check if both are weldable
      ICapabilityForge cap1 = input1.getCapability(CapabilityForgeable.CAPABILITY, null);
      ICapabilityForge cap2 = input2.getCapability(CapabilityForgeable.CAPABILITY, null);
      if (cap1 == null || cap2 == null || !cap1.isWeldable() || !cap2.isWeldable()) {
        if (cap1 instanceof ICapabilityHeat && cap2 instanceof ICapabilityHeat) {
          TerraFirmaCraft.getNetwork()
            .sendTo(PacketSimpleMessage.translateMessage(MessageCategory.ANVIL, TFC + ".tooltip.anvil_too_cold"), (EntityPlayerMP) player);
        } else {
          TerraFirmaCraft.getNetwork()
            .sendTo(PacketSimpleMessage.translateMessage(MessageCategory.ANVIL, TFC + ".tooltip.anvil_not_weldable"), (EntityPlayerMP) player);
        }
        return false;
      }
      ItemStack result = recipe.getOutput(player);
      ICapabilityHeat heatResult = result.getCapability(CapabilityHeat.CAPABILITY, null);
      float resultTemperature = 0;
      if (cap1 instanceof ICapabilityHeat) {
        resultTemperature = ((ICapabilityHeat) cap1).getTemperature();
      }
      if (cap2 instanceof ICapabilityHeat) {
        resultTemperature = Math.min(resultTemperature, ((ICapabilityHeat) cap2).getTemperature());
      }
      if (heatResult != null) {
        // Every welding result should have this capability, but don't fail if it doesn't
        heatResult.setTemperature(resultTemperature);
      }

      // Set stacks in slots
      inventory.setStackInSlot(SLOT_INPUT_1, result);
      inventory.setStackInSlot(SLOT_INPUT_2, ItemStack.EMPTY);
      inventory.setStackInSlot(SLOT_FLUX, Helpers.consumeItem(fluxStack, 1));
      markForSync();

      return true;
    }

    // For when there is both inputs but no recipe that matches
    TerraFirmaCraft.getNetwork()
      .sendTo(PacketSimpleMessage.translateMessage(MessageCategory.ANVIL, TFC + ".tooltip.anvil_not_weldable"), (EntityPlayerMP) player);
    return false;
  }

  public int getWorkingProgress() {
    return workingProgress;
  }

  public int getWorkingTarget() {
    return workingTarget;
  }

  private void checkRecipeUpdate() {
    ItemStack stack = inventory.getStackInSlot(SLOT_INPUT_1);
    ICapabilityForge cap = stack.getCapability(CapabilityForgeable.CAPABILITY, null);
    if (cap == null && recipe != null) {
      // Check for item removed / broken
      setRecipe(null);
    } else if (cap != null) {
      // Check for mismatched recipe
      AnvilRecipe capRecipe = TFCRegistries.ANVIL.getValue(cap.getRecipeName());
      if (capRecipe != recipe) {
        setRecipe(capRecipe);
      } else if (AnvilRecipe.getAllFor(stack).size() == 1) {
        setRecipe(AnvilRecipe.getAllFor(stack).get(0));
      }
    }
  }

  private void resetFields() {
    workingProgress = 0;
    workingTarget = 0;
    steps.reset();
  }

  private static class AnvilItemHandler extends ItemStackHandlerCallback {

    public AnvilItemHandler(ISlotCallback callback, int slots) {
      super(callback, slots);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
      ItemStack result = super.extractItem(slot, amount, simulate);
      if (slot == SLOT_INPUT_1 || slot == SLOT_INPUT_2) {
        ICapabilityForge cap = result.getCapability(CapabilityForgeable.CAPABILITY, null);
        if (cap != null && cap.getRecipeName() != null && (!cap.getSteps().hasWork() || cap.getWork() == 0)) {
          cap.reset();
        }

      }
      return result;
    }
  }

}
