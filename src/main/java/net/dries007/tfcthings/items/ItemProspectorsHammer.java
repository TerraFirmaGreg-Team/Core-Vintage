package net.dries007.tfcthings.items;

import su.terrafirmagreg.api.util.MathUtils;
import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.player.CapabilityPlayer;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager;
import su.terrafirmagreg.modules.core.feature.skills.ProspectingSkill;
import su.terrafirmagreg.modules.core.feature.skills.SkillType;
import su.terrafirmagreg.modules.core.feature.skills.SmithingSkill;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.object.block.BlockRockFallable;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodSupport;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.dries007.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.forge.ForgeableHeatableHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.ItemTFC;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemProspectorsHammer extends ItemTFC implements ICapabilityMetal, ItemOreDict, TFCThingsConfigurableItem {

  public final ToolMaterial material;
  private final Metal metal;
  private final double attackDamage;
  private final float attackSpeed;

  public ItemProspectorsHammer(Metal metal, String name) {
    this.metal = metal;
    this.material = metal.getToolMetal();
    this.setMaxDamage((int) ((double) material.getMaxUses() / 4));
    this.attackDamage = 0.5 * this.material.getAttackDamage();
    this.attackSpeed = -2.8F;
    this.setMaxStackSize(1);
    setRegistryName("prospectors_hammer/" + name);
    setTranslationKey("prospectors_hammer_" + name);
  }

  @Override
  public @NotNull Weight getWeight(@NotNull ItemStack itemStack) {
    return Weight.MEDIUM;
  }

  @Override
  public @NotNull Size getSize(@NotNull ItemStack itemStack) {
    return Size.NORMAL;
  }

  public boolean canStack(@NotNull ItemStack itemStack) {
    return false;
  }

  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack itemstack = playerIn.getHeldItem(handIn);
    RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, false);
    if (raytraceresult == null) {
      return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
    } else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
      return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
    } else {
      BlockPos blockpos = raytraceresult.getBlockPos();
      IBlockState iblockstate = worldIn.getBlockState(blockpos);
      SoundType soundType = iblockstate.getBlock().getSoundType(iblockstate, worldIn, blockpos, playerIn);
      worldIn.playSound(playerIn, blockpos, soundType.getHitSound(), SoundCategory.BLOCKS, 1.0F, soundType.getPitch());
      Block block = iblockstate.getBlock();
      if (!worldIn.isRemote) {
        ProspectingSkill skill = CapabilityPlayer.getSkill(playerIn, SkillType.PROSPECTING);
        if (playerIn.isSneaking()) {
          checkRockLayers(playerIn, worldIn, blockpos, skill);
          playerIn.getCooldownTracker().setCooldown(this, 10);
          float skillModifier = SmithingSkill.getSkillBonus(itemstack, SmithingSkill.Type.TOOLS) / 2.0F;
          boolean flag = !(skillModifier > 0.0F) || !(MathUtils.RNG.nextFloat() < skillModifier);
          if (flag) {
            playerIn.getHeldItem(handIn).damageItem(20, playerIn);
          } else {
            playerIn.getHeldItem(handIn).damageItem(10, playerIn);
          }
          return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
        int messageType = 0;
        if (FallingBlockManager.getSpecification(worldIn.getBlockState(blockpos)) != null &&
            FallingBlockManager.getSpecification(worldIn.getBlockState(blockpos))
                               .isCollapsable()) {
          boolean result = isThisBlockSafe(worldIn, blockpos);

          float falsePositiveChance = 0.3F;
          if (skill != null) {
            falsePositiveChance = 0.3F - 0.1F * (float) skill.getTier().ordinal();
          }
          if (Math.random() < falsePositiveChance) {
            result = true;
          }
          if (result) {
            messageType = 1;
          } else {
            messageType = 2;
          }
        }
        if (skill != null && skill.getTier().ordinal() > 1 && supportingFallable(worldIn, blockpos)) {
          messageType += 3;
        }
        switch (messageType) {
          case 0:
            playerIn.sendStatusMessage(new TextComponentTranslation("tfcthings.tooltip.prohammer_na"),
                                       ConfigTFC.Client.TOOLTIP.propickOutputToActionBar);
            break;
          case 1:
            playerIn.sendStatusMessage(new TextComponentTranslation("tfcthings.tooltip.prohammer_safe"),
                                       ConfigTFC.Client.TOOLTIP.propickOutputToActionBar);
            break;
          case 2:
            playerIn.sendStatusMessage(new TextComponentTranslation("tfcthings.tooltip.prohammer_unsafe"),
                                       ConfigTFC.Client.TOOLTIP.propickOutputToActionBar);
            break;
          case 3:
            playerIn.sendStatusMessage(new TextComponentTranslation("tfcthings.tooltip.prohammer_na_fall"),
                                       ConfigTFC.Client.TOOLTIP.propickOutputToActionBar);
            break;
          case 4:
            playerIn.sendStatusMessage(new TextComponentTranslation("tfcthings.tooltip.prohammer_safe_fall"),
                                       ConfigTFC.Client.TOOLTIP.propickOutputToActionBar);
            break;
          case 5:
            playerIn.sendStatusMessage(new TextComponentTranslation("tfcthings.tooltip.prohammer_unsafe_fall"),
                                       ConfigTFC.Client.TOOLTIP.propickOutputToActionBar);
            break;
        }
        float skillModifier = SmithingSkill.getSkillBonus(itemstack, SmithingSkill.Type.TOOLS) / 2.0F;
        boolean flag = !(skillModifier > 0.0F) || !(MathUtils.RNG.nextFloat() < skillModifier);
        if (flag) {
          playerIn.getHeldItem(handIn).damageItem(1, playerIn);
        }
        playerIn.getCooldownTracker().setCooldown(this, 10);
      }
      return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
  }

  public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
    Multimap<String, AttributeModifier> multimap = HashMultimap.create();
    if (slot == EntityEquipmentSlot.MAINHAND) {
      multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                   new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", this.attackDamage, 0));
      multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
                   new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", this.attackSpeed, 0));
    }

    return multimap;
  }

  @Nullable
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new ForgeableHeatableHandler(nbt, metal.getSpecificHeat(), metal.getMeltTemp());
  }

  private void checkRockLayers(EntityPlayer playerIn, World worldIn, BlockPos pos, ProspectingSkill skill) {
    int skillTier = 0;
    if (skill != null) {
      skillTier = skill.getTier().ordinal();
    }
    ArrayList<RockType> rocks = new ArrayList<>();
    BlockPos pos1 = pos;
    BlockPos pos2 = pos.up(10);
    BlockPos pos3 = pos.down(10);
    for (int i = 0; i < skillTier + 1; i++) {
      addRock(pos1, rocks, worldIn);
      addRock(pos2, rocks, worldIn);
      addRock(pos3, rocks, worldIn);
      pos1 = pos1.down(30);
      pos2 = pos2.down(30);
      pos3 = pos3.down(30);
    }
    if (rocks.isEmpty()) {
      playerIn.sendStatusMessage(new TextComponentTranslation("tfcthings.tooltip.prohammer_no_rocks"),
                                 ConfigTFC.Client.TOOLTIP.propickOutputToActionBar);
    } else if (rocks.size() == 1) {
      playerIn.sendStatusMessage(
        new TextComponentTranslation("tfcthings.tooltip.prohammer_1_rock_found", rocks.get(0).toString()),
        ConfigTFC.Client.TOOLTIP.propickOutputToActionBar);

    } else if (rocks.size() == 2) {
      playerIn.sendStatusMessage(new TextComponentTranslation("tfcthings.tooltip.prohammer_2_rocks_found",
                                                              rocks.get(0).toString(), rocks.get(1)
                                                                                            .toString()), ConfigTFC.Client.TOOLTIP.propickOutputToActionBar);
    } else if (rocks.size() >= 3) {
      playerIn.sendStatusMessage(new TextComponentTranslation("tfcthings.tooltip.prohammer_3_rocks_found",
                                                              rocks.get(0).toString(), rocks.get(1).toString(), rocks.get(2).toString()),
                                 ConfigTFC.Client.TOOLTIP.propickOutputToActionBar);
    }
  }

  private boolean isThisBlockSafe(World worldIn, BlockPos pos) {
    int radX = 4;
    int radY = 2;
    int radZ = 4;
    Iterator var6 = BlockWoodSupport.getAllUnsupportedBlocksIn(worldIn, pos.add(-radX, -radY, -radZ), pos.add(radX, radY, radZ))
                                    .iterator();

    while (var6.hasNext()) {
      BlockPos checking = (BlockPos) var6.next();
      if (FallingBlockManager.getSpecification(worldIn.getBlockState(checking)) != null && FallingBlockManager
        .getSpecification(worldIn.getBlockState(checking))
        .isCollapsable()) {
        if (FallingBlockManager.canCollapse(worldIn, checking)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean supportingFallable(World worldIn, BlockPos pos) {
    IBlockState iblockstate = worldIn.getBlockState(pos.up());
    Block block = iblockstate.getBlock();
    if (block instanceof BlockRockFallable || block instanceof BlockFalling) {
      return !BlockWoodSupport.isBeingSupported(worldIn, pos.up());
    }
    return false;
  }

  private void addRock(BlockPos pos, List<RockType> rocks, World worldIn) {
    if (worldIn.getBlockState(pos).getBlock() instanceof IRockBlock rockBlock) {
      var type = rockBlock.getType();
      if (!rocks.contains(type)) {
        rocks.add(type);
      }
    }
  }

  @Override
  public boolean canMelt(ItemStack stack) {
    return true;
  }

  @Nullable
  @Override
  public Metal getMetal(ItemStack itemStack) {
    return metal;
  }

  @Override
  public int getSmeltAmount(ItemStack itemStack) {
    if (this.isDamageable() && itemStack.isItemDamaged()) {
      double d = (double) (itemStack.getMaxDamage() - itemStack.getItemDamage()) / (double) itemStack.getMaxDamage() - 0.1D;
      return d < 0.0D ? 0 : MathHelper.floor((double) 100 * d);
    } else {
      return 100;
    }
  }

  @Override
  public void initOreDict() {
    OreDictionary.registerOre("tool", new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE));
  }

  @Override
  public boolean isEnabled() {
    return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableProspectorsHammer;
  }
}
