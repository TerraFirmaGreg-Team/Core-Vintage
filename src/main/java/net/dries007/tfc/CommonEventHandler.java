package net.dries007.tfc;

import su.terrafirmagreg.api.data.DamageSources;
import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityProviderFood;
import su.terrafirmagreg.modules.core.capabilities.food.IItemFoodTFC;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHandlerHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.ICapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.playerdata.CapabilityPlayerData;
import su.terrafirmagreg.modules.core.capabilities.playerdata.ICapabilityPlayerData;
import su.terrafirmagreg.modules.core.capabilities.playerdata.ProviderPlayerData;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.CalendarWorldData;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.core.feature.calendar.Month;
import su.terrafirmagreg.modules.core.feature.climate.Climate;
import su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager;
import su.terrafirmagreg.modules.core.feature.skill.SmithingSkill;
import su.terrafirmagreg.modules.core.init.EffectsCore;
import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.food.api.FoodStatsTFC;
import su.terrafirmagreg.modules.food.api.IFoodStatsTFC;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPotato;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.GameRuleChangeEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.eerussianguy.firmalife.init.FoodFL;
import com.eerussianguy.firmalife.items.ItemFruitPole;
import com.eerussianguy.firmalife.registry.BlocksFL;
import com.eerussianguy.firmalife.registry.ItemsFL;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.ForgeableHeatableHandler;
import net.dries007.tfc.api.capability.metal.CapabilityMetalItem;
import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.IAnimalTFC;
import net.dries007.tfc.api.types.ICreatureTFC;
import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.api.types.IPredator;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.Rock.Type;
import net.dries007.tfc.api.util.IGrowingPlant;
import net.dries007.tfc.network.PacketCalendarUpdate;
import net.dries007.tfc.network.PacketSimpleMessage;
import net.dries007.tfc.network.PacketSimpleMessage.MessageCategory;
import net.dries007.tfc.objects.blocks.BlockFluidTFC;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeLeaves;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeTrunk;
import net.dries007.tfc.objects.blocks.plants.BlockCactusTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockRaw;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.blocks.wood.BlockChestTFC;
import net.dries007.tfc.objects.blocks.wood.BlockFenceGateTFC;
import net.dries007.tfc.objects.blocks.wood.BlockFenceTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.blocks.wood.BlockPlanksTFC;
import net.dries007.tfc.objects.blocks.wood.BlockSaplingTFC;
import net.dries007.tfc.objects.container.CapabilityContainerListener;
import net.dries007.tfc.objects.entity.ai.EBEntityAI;
import net.dries007.tfc.objects.entity.animal.EntityAnimalTFC;
import net.dries007.tfc.objects.items.ItemQuiver;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.types.DefaultTrees;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.MonsterEquipment;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.sharkbark.cellars.init.ModItems;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.objects.blocks.wood.bamboo.BlockBambooLeaves;
import tfcflorae.objects.blocks.wood.cinnamon.BlockCassiaCinnamonLeaves;
import tfcflorae.objects.blocks.wood.cinnamon.BlockCeylonCinnamonLeaves;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFC;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = TFC)
public final class CommonEventHandler {

  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
    IBlockState state = event.getState();
    FallingBlockManager.Specification spec = FallingBlockManager.getSpecification(state);
    if (spec != null && !spec.isCollapsable()) {
      if (FallingBlockManager.checkFalling(event.getWorld(), event.getPos(), state)) {
        event.getWorld().playSound(null, event.getPos(), spec.getSoundEvent(), SoundCategory.BLOCKS, 1.0F, 1.0F);
      }
    } else {
      for (EnumFacing notifiedSide : event.getNotifiedSides()) {
        BlockPos offsetPos = event.getPos().offset(notifiedSide);
        IBlockState notifiedState = event.getWorld().getBlockState(offsetPos);
        FallingBlockManager.Specification notifiedSpec = FallingBlockManager.getSpecification(notifiedState);
        if (notifiedSpec != null && !notifiedSpec.isCollapsable()) {
          if (FallingBlockManager.checkFalling(event.getWorld(), offsetPos, notifiedState)) {
            event.getWorld().playSound(null, offsetPos, notifiedSpec.getSoundEvent(), SoundCategory.BLOCKS, 1.0F, 1.0F);
          }
        }
      }
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
    if (event.getWorld().isRemote) {
      return;
    }
    IBlockState state = event.getPlacedBlock();
    FallingBlockManager.Specification spec = FallingBlockManager.getSpecification(state);
    if (spec != null && !spec.isCollapsable()) {
      if (FallingBlockManager.checkFalling(event.getWorld(), event.getPos(), state)) {
        event.getWorld().playSound(null, event.getPos(), spec.getSoundEvent(), SoundCategory.BLOCKS, 1.0F, 1.0F);
      }
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
    if (ConfigTFC.General.FALLABLE.explosionCausesCollapse) {
      for (BlockPos pos : event.getAffectedBlocks()) {
        if (FallingBlockManager.checkCollapsingArea(event.getWorld(), pos)) {
          break;
        }
      }
    }
  }

  /**
   * Fill thirst after drinking vanilla water bottles or milk
   */
  @SubscribeEvent
  public static void onEntityUseItem(LivingEntityUseItemEvent.Finish event) {
    ItemStack usedItem = event.getItem();
    if (usedItem.getItem() == Items.MILK_BUCKET || PotionUtils.getPotionFromItem(usedItem) == PotionTypes.WATER) {
      if (event.getEntityLiving() instanceof EntityPlayerMP player) {
        if (player.getFoodStats() instanceof FoodStatsTFC) {
          ((FoodStatsTFC) player.getFoodStats()).addThirst(40); //Same as jug
        }
      }
    }
  }

  /**
   * Update harvesting tool before it takes damage
   */
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void breakEvent(BlockEvent.BreakEvent event) {
    final EntityPlayer player = event.getPlayer();
    final ItemStack heldItem = player == null ? ItemStack.EMPTY : player.getHeldItemMainhand();

    if (player != null) {
      ICapabilityPlayerData cap = player.getCapability(CapabilityPlayerData.CAPABILITY, null);
      if (cap != null) {
        cap.setHarvestingTool(player.getHeldItemMainhand());
      }
    }

    FallingBlockManager.Specification spec = FallingBlockManager.getSpecification(event.getState());
    if (spec != null && spec.isCollapsable()) {
      FallingBlockManager.checkCollapsingArea(event.getWorld(), event.getPos());
    }
  }

  @SubscribeEvent
  public static void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event) {
    final EntityPlayer player = event.getHarvester();
    final ItemStack heldItem = player == null ? ItemStack.EMPTY : player.getHeldItemMainhand();
    final IBlockState state = event.getState();
    final Block block = state.getBlock();
    final Month month = Calendar.CALENDAR_TIME.getMonthOfYear();

    // Make leaves drop sticks
    if (!event.isSilkTouching() && block instanceof BlockLeaves) {
      // Done via event so it applies to all leaves.
      double chance = ConfigTFC.General.TREE.leafStickDropChance;
      if (!heldItem.isEmpty() && Helpers.containsAnyOfCaseInsensitive(heldItem.getItem()
        .getToolClasses(heldItem), ConfigTFC.General.TREE.leafStickDropChanceBonusClasses)) {
        chance = ConfigTFC.General.TREE.leafStickDropChanceBonus;
      }
      if (Constants.RNG.nextFloat() < chance) {
        event.getDrops().add(new ItemStack(Items.STICK));
      }
    }

    // Drop shards from glass
    ItemStack stackAt = new ItemStack(Item.getItemFromBlock(state.getBlock()), 1, state.getBlock().damageDropped(state));
    if (!event.isSilkTouching() && OreDictionaryHelper.doesStackMatchOre(stackAt, "blockGlass")) {
      event.getDrops().add(new ItemStack(ItemsTFC.GLASS_SHARD));
    }

    // Apply durability modifier on tools
    if (player != null) {
      ItemStack tool = ItemStack.EMPTY;
      ICapabilityPlayerData cap = player.getCapability(CapabilityPlayerData.CAPABILITY, null);
      if (cap != null) {
        tool = cap.getHarvestingTool();
      }
      if (!tool.isEmpty()) {
        float skillModifier = SmithingSkill.getSkillBonus(tool, SmithingSkill.Type.TOOLS) / 2.0F;
        if (skillModifier > 0 && Constants.RNG.nextFloat() < skillModifier) {
          // Up to 50% negating damage, for double durability
          player.setHeldItem(EnumHand.MAIN_HAND, tool);
        }
      }
    }

    if (block instanceof BlockFruitTreeLeaves) {
      event.getDrops().add(new ItemStack(ItemsFL.FRUIT_LEAF, 2 + Constants.RNG.nextInt(4)));
    } else if (block instanceof BlockFruitTreeTrunk blockFruitTreeTrunk) {
      if (event.isCanceled()) {event.setCanceled(false);}
      IFruitTree tree = blockFruitTreeTrunk.getTree();
      ItemFruitPole pole = ItemFruitPole.get(tree);
      if (pole != null) {event.getDrops().add(new ItemStack(pole));}
    }

    if (block instanceof BlockCassiaCinnamonLeaves || block instanceof BlockCeylonCinnamonLeaves || block instanceof BlockBambooLeaves) {
      event.getDrops().add(new ItemStack(ItemsFL.FRUIT_LEAF, 2 + Constants.RNG.nextInt(4)));
    }
    if (block == BlocksFL.MELON_FRUIT && (heldItem.getItem().getHarvestLevel(heldItem, "knife", player, state) != -1)) {
      event.getDrops().clear();
      event.getDrops().add(new ItemStack(ItemsFL.getFood(FoodFL.MELON), 2 + Constants.RNG.nextInt(4)));
    }

    if (block instanceof BlockCactusTFC blockCactusTFC) {
      if (blockCactusTFC.getPlant() == TFCRegistries.PLANTS.getValue(DefaultPlants.BARREL_CACTUS)
          && (month == Month.SEPTEMBER || month == Month.OCTOBER || month == Month.NOVEMBER)) {
        int chance = Constants.RNG.nextInt(2);
        if (chance == 0) {
          event.getDrops().clear();
          event.getDrops().add(new ItemStack(ItemsTFCF.BARREL_CACTUS_FRUIT, 1 + Constants.RNG.nextInt(3)));
        }
      }
    }

    if (block instanceof BlockPackedIce) {
      if (OreDictionaryHelper.doesStackMatchOre(heldItem, "iceSaw")) {
        event.getDrops().clear();
        event.getDrops().add(new ItemStack(ModItems.PACKED_ICE_SHARD, 2 + Constants.RNG.nextInt(4)));
      }
    }

    if (block instanceof BlockStone blockStone) {
      event.getDrops().clear();
      switch (blockStone.getDefaultState().getValue(BlockStone.VARIANT)) {
        case STONE:
          event.getDrops().add(new ItemStack(BlockRockVariant.get(Rock.GRANITE, Rock.Type.RAW)));
          break;
        case GRANITE:
          event.getDrops().add(new ItemStack(BlockRockVariant.get(Rock.GRANITE, Rock.Type.RAW)));
          break;
        case DIORITE:
          event.getDrops().add(new ItemStack(BlockRockVariant.get(Rock.DIORITE, Rock.Type.RAW)));
          break;
        case ANDESITE:
          event.getDrops().add(new ItemStack(BlockRockVariant.get(Rock.ANDESITE, Rock.Type.RAW)));
      }
    }
    if (OreDictionaryHelper.doesStackMatchOre(stackAt, "cobblestone")) {
      event.getDrops().clear();
      event.getDrops().add(new ItemStack(BlockRockVariant.get(Rock.ANDESITE, Type.COBBLE)));
    }
    if (block instanceof BlockGrass) {
      event.getDrops().clear();
      event.getDrops().add(new ItemStack(BlockRockVariant.get(Rock.BASALT, Type.GRASS)));
    }
    if (block instanceof BlockGravel) {
      event.getDrops().clear();
      event.getDrops().add(new ItemStack(BlockRockVariant.get(Rock.BASALT, Type.GRAVEL)));
    }
    if (block instanceof BlockDirt blockDirt) {
      event.getDrops().clear();
      switch (blockDirt.getDefaultState().getValue(BlockDirt.VARIANT)) {
        case DIRT:
          event.getDrops().add(new ItemStack(BlockRockVariant.get(Rock.BASALT, Type.DIRT)));
          break;
        case COARSE_DIRT:
          event.getDrops().add(new ItemStack(BlockRockVariantTFCF.get(Rock.BASALT, RockTFCF.COARSE_DIRT)));
          break;
        case PODZOL:
          event.getDrops().add(new ItemStack(BlockRockVariantTFCF.get(Rock.BASALT, RockTFCF.PODZOL)));
          break;
      }
    }
    if (block instanceof BlockFarmland) {
      event.getDrops().clear();
      event.getDrops().add(new ItemStack(BlockRockVariant.get(Rock.BASALT, Type.FARMLAND)));
    }

    if (block instanceof BlockSand) {
      event.getDrops().clear();
      event.getDrops().add(new ItemStack(BlockRockVariant.get(Rock.BASALT, Type.SAND)));
    }
    if (block instanceof BlockChest blockChest) {
      event.getDrops().clear();
      if (blockChest.chestType == BlockChest.Type.BASIC) {
        event.getDrops().add(new ItemStack(BlockChestTFC.getBasic(TFCRegistries.TREES.getValue(DefaultTrees.OAK))));
      } else if (blockChest.chestType == BlockChest.Type.TRAP) {
        event.getDrops().add(new ItemStack(BlockChestTFC.getTrap(TFCRegistries.TREES.getValue(DefaultTrees.OAK))));
      }
    }
    if (block instanceof BlockPlanks) {
      event.getDrops().clear();
      event.getDrops().add(new ItemStack(BlockPlanksTFC.get(TFCRegistries.TREES.getValue(DefaultTrees.OAK))));
    }
    if (block instanceof BlockOldLog || block instanceof BlockNewLog) {
      event.getDrops().clear();
      event.getDrops().add(new ItemStack(BlockLogTFC.get(TFCRegistries.TREES.getValue(DefaultTrees.OAK))));
    }
    if (block instanceof BlockSapling) {
      event.getDrops().clear();
      event.getDrops().add(new ItemStack(BlockSaplingTFC.get(TFCRegistries.TREES.getValue(DefaultTrees.OAK))));
    }
    if (block instanceof BlockFenceGate) {
      event.getDrops().clear();
      event.getDrops().add(new ItemStack(BlockFenceGateTFC.get(TFCRegistries.TREES.getValue(DefaultTrees.OAK))));
    }
    if (block instanceof BlockFence) {
      event.getDrops().clear();
      event.getDrops().add(new ItemStack(BlockFenceTFC.get(TFCRegistries.TREES.getValue(DefaultTrees.OAK))));
    }
    if (block instanceof BlockPotato || block instanceof BlockCarrot || OreDictionaryHelper.doesStackMatchOre(stackAt, "cropWheat")) {
      event.getDrops().clear();
      event.getDrops().add(new ItemStack(Items.STICK));
    }
  }

  @SubscribeEvent
  public static void onBreakProgressEvent(BreakSpeed event) {
    EntityPlayer player = event.getEntityPlayer();
    if (player != null) {
      ItemStack stack = player.getHeldItemMainhand();
      float skillModifier = SmithingSkill.getSkillBonus(stack, SmithingSkill.Type.TOOLS);
      if (skillModifier > 0) {
        // Up to 2x modifier for break speed for skill bonuses on tools
        // New speed, so it will take into account other mods' modifications
        event.setNewSpeed(event.getNewSpeed() + (event.getNewSpeed() * skillModifier));
      }
    }
    if (event.getState().getBlock() instanceof BlockRockVariant) {
      event.setNewSpeed((float) (event.getNewSpeed() / ConfigTFC.General.MISC.rockMiningTimeModifier));
    }
    if (event.getState().getBlock() instanceof BlockLogTFC) {
      event.setNewSpeed((float) (event.getNewSpeed() / ConfigTFC.General.MISC.logMiningTimeModifier));
    }
  }


  @SubscribeEvent
  public static void onUseHoe(UseHoeEvent event) {
    World world = event.getWorld();
    BlockPos pos = event.getPos();
    IBlockState state = world.getBlockState(pos);
    Block block = state.getBlock();

    if (ConfigTFC.General.OVERRIDES.enableHoeing) {
      if (block instanceof BlockRockVariant blockRock) {
        if (blockRock.getType() == Rock.Type.GRASS || blockRock.getType() == Rock.Type.DIRT) {
          if (!world.isRemote) {
            world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(pos, BlockRockVariant.get(blockRock.getRock(), Rock.Type.FARMLAND).getDefaultState());
          }
          event.setResult(Event.Result.ALLOW);
        }
      }
    }
    if (block instanceof IGrowingPlant plant && !world.isRemote) {
      Entity entity = event.getEntity();
      if (entity instanceof EntityPlayerMP && entity.isSneaking()) {
        TerraFirmaCraft.getNetwork().sendTo(PacketSimpleMessage.translateMessage(MessageCategory.ANIMAL, plant.getGrowingStatus(state, world, pos)
          .toString()), (EntityPlayerMP) entity);
      }

    }
  }

  @SubscribeEvent
  public static void onLivingHurt(LivingHurtEvent event) {
    float actualDamage = event.getAmount();
    // Add damage bonus for weapons
    Entity entity = event.getSource().getTrueSource();
    if (entity instanceof EntityLivingBase damager) {
      ItemStack stack = damager.getHeldItemMainhand();
      float skillModifier = SmithingSkill.getSkillBonus(stack, SmithingSkill.Type.WEAPONS);
      if (skillModifier > 0) {
        // Up to 1.5x damage
        actualDamage *= 1 + (skillModifier / 2.0F);
      }
    }
    // Modifier for damage type + damage resistance
    actualDamage *= DamageType.getModifier(event.getSource(), event.getEntityLiving());
    if (event.getEntityLiving() instanceof EntityPlayer player) {
      if (player.getFoodStats() instanceof IFoodStatsTFC) {
        float healthModifier = ((IFoodStatsTFC) player.getFoodStats()).getHealthModifier();
        if (healthModifier < ConfigTFC.General.PLAYER.minHealthModifier) {
          healthModifier = (float) ConfigTFC.General.PLAYER.minHealthModifier;
        }
        if (healthModifier > ConfigTFC.General.PLAYER.maxHealthModifier) {
          healthModifier = (float) ConfigTFC.General.PLAYER.maxHealthModifier;
        }
        actualDamage /= healthModifier;
      }
    }
    event.setAmount(actualDamage);
  }

  @SubscribeEvent
  public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
    ItemStack stack = event.getObject();
    Item item = stack.getItem();
    if (!stack.isEmpty()) {

      // Food
      // Because our foods supply a custom capability in Item#initCapabilities, we need to avoid attaching a duplicate, otherwise it breaks food stacking recipes.
      // This problem goes away in 1.15 as all of these definitions (including ours) become tags)
      // We allow custom defined capabilities to attach to non-food items, that should have rot (such as eggs).
      ICapabilityProvider foodHandler = CapabilityFood.getCustomFood(stack);
      if (foodHandler != null || stack.getItem() instanceof ItemFood) {
        if (stack.getItem() instanceof IItemFoodTFC) {
          foodHandler = ((IItemFoodTFC) stack.getItem()).getCustomFoodHandler();
        }
        if (foodHandler == null) {
          foodHandler = new CapabilityProviderFood(stack.getTagCompound(), new FoodData());
        }
        event.addCapability(CapabilityFood.KEY, foodHandler);
      }

      // Forge / Metal / Heat. Try forge first, because it's more specific
      ICapabilityProvider forgeHandler = CapabilityForgeable.getCustomForgeable(stack);
      boolean isForgeable = false;
      boolean isHeatable = false;
      if (forgeHandler != null) {
        isForgeable = true;
        event.addCapability(CapabilityForgeable.KEY, forgeHandler);
        isHeatable = forgeHandler instanceof ICapabilityHeat;
      }
      // Metal
      ICapabilityProvider metalCapability = CapabilityMetalItem.getCustomMetalItem(stack);
      if (metalCapability != null) {
        event.addCapability(CapabilityMetalItem.KEY, metalCapability);
        if (!isForgeable) {
          // Add a forgeable capability for this item, if none is found
          IMetalItem cap = (IMetalItem) metalCapability;
          Metal metal = cap.getMetal(stack);
          if (metal != null) {
            event.addCapability(CapabilityForgeable.KEY, new ForgeableHeatableHandler(null, metal.getSpecificHeat(), metal.getMeltTemp()));
            isHeatable = true;
          }
        }
      }
      // If one of the above is also heatable, skip this
      if (!isHeatable) {
        ICapabilityProvider heatHandler = CapabilityHandlerHeat.getCustom(stack);
        if (heatHandler != null) {
          event.addCapability(CapabilityHeat.KEY, heatHandler);
        }
      }
    }
  }


  /**
   * Fired on server only when a player dies and respawns. Used to copy skill level before respawning since we need the original (AKA the body) player entity
   *
   * @param event {@link net.minecraftforge.event.entity.player.PlayerEvent.Clone}
   */
  @SubscribeEvent
  public static void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
    if (event.getEntityPlayer() instanceof EntityPlayerMP player) {

      // Skills
      ICapabilityPlayerData newSkills = player.getCapability(CapabilityPlayerData.CAPABILITY, null);
      ICapabilityPlayerData originalSkills = event.getOriginal().getCapability(CapabilityPlayerData.CAPABILITY, null);
      if (newSkills != null && originalSkills != null) {
        newSkills.deserializeNBT(originalSkills.serializeNBT());
        // To properly sync, we need to use PlayerRespawnEvent
      }
    }
  }


  /**
   * Only fired on server
   */
  @SubscribeEvent
  public static void onContainerOpen(PlayerContainerEvent.Open event) {
    if (event.getEntityPlayer() instanceof EntityPlayerMP) {
      // Capability Sync Handler
      CapabilityContainerListener.addTo(event.getContainer(), (EntityPlayerMP) event.getEntityPlayer());
    }
  }

  @SubscribeEvent
  public static void onLivingSpawnEvent(LivingSpawnEvent.CheckSpawn event) {
    World world = event.getWorld();
    BlockPos pos = new BlockPos(event.getX(), event.getY(), event.getZ());
    if (world.getWorldType() == TerraFirmaCraft.getWorldType() && event.getWorld().provider.getDimensionType() == DimensionType.OVERWORLD) {
      if (ConfigTFC.General.SPAWN_PROTECTION.preventMobs && event.getEntity().isCreatureType(EnumCreatureType.MONSTER, false)) {
        // Prevent Mobs
        ChunkDataTFC data = ChunkDataTFC.get(event.getWorld(), pos);
        int minY = ConfigTFC.General.SPAWN_PROTECTION.minYMobs;
        int maxY = ConfigTFC.General.SPAWN_PROTECTION.maxYMobs;
        if (data.isSpawnProtected() && minY <= maxY && event.getY() >= minY && event.getY() <= maxY) {
          event.setResult(Event.Result.DENY);
        }
      }

      if (ConfigTFC.General.SPAWN_PROTECTION.preventPredators && event.getEntity() instanceof IPredator) {
        // Prevent Predators
        ChunkDataTFC data = ChunkDataTFC.get(event.getWorld(), pos);
        int minY = ConfigTFC.General.SPAWN_PROTECTION.minYPredators;
        int maxY = ConfigTFC.General.SPAWN_PROTECTION.maxYPredators;
        if (data.isSpawnProtected() && minY <= maxY && event.getY() >= minY && event.getY() <= maxY) {
          event.setResult(Event.Result.DENY);
        }
      }

      if (event.getEntity() instanceof EntitySquid && world.getBlockState(pos).getBlock() instanceof BlockFluidTFC) {
        // Prevents squids spawning outside of salt water (eg: oceans)
        Fluid fluid = ((BlockFluidTFC) world.getBlockState(pos).getBlock()).getFluid();
        if (FluidsCore.SALT_WATER.get() != fluid) {
          event.setResult(Event.Result.DENY);
        }
      }

      // Check creature spawning - Prevents vanilla's respawning mechanic to spawn creatures outside their allowed conditions
      if (event.getEntity() instanceof ICreatureTFC creature) {
        float rainfall = ChunkDataTFC.getRainfall(world, pos);
        float temperature = Climate.getAvgTemp(world, pos);
        float floraDensity = ChunkDataTFC.getFloraDensity(world, pos);
        float floraDiversity = ChunkDataTFC.getFloraDiversity(world, pos);
        Biome biome = world.getBiome(pos);

        // We don't roll spawning again since vanilla is handling it
        if (creature.getSpawnWeight(biome, temperature, rainfall, floraDensity, floraDiversity) <= 0) {
          event.setResult(Event.Result.DENY);
        }
      }

      // Stop mob spawning on surface
      if (ConfigTFC.General.DIFFICULTY.preventMobsOnSurface) {
        if (Helpers.shouldPreventOnSurface(event.getEntity())) {
          int maximumY = (WorldTypeTFC.SEALEVEL - WorldTypeTFC.ROCKLAYER2) / 2 + WorldTypeTFC.ROCKLAYER2; // Half through rock layer 1
          if (pos.getY() >= maximumY || world.canSeeSky(pos)) {
            event.setResult(Event.Result.DENY);
          }
        }
      }
    }

    // Stop mob spawning in thatch - the list of non-spawnable light-blocking, non-collidable blocks is hardcoded in WorldEntitySpawner#canEntitySpawnBody
    // This is intentionally outside the previous world type check as this is a fix for the thatch block, not a generic spawning check.
    if (event.getWorld().getBlockState(pos).getBlock() == BlocksTFC.THATCH || event.getWorld().getBlockState(pos.up()).getBlock() == BlocksTFC.THATCH) {
      event.setResult(Event.Result.DENY);
    }
  }

  @SubscribeEvent
  public static void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
    Entity entity = event.getEntity();
    if (event.getWorld().getWorldType() == TerraFirmaCraft.getWorldType() && event.getWorld().provider.getDimensionType() == DimensionType.OVERWORLD) {
      // Fix skeleton rider traps spawning during thunderstorms
      if (entity instanceof EntitySkeletonHorse && ConfigTFC.General.DIFFICULTY.preventMobsOnSurface && ((EntitySkeletonHorse) entity).isTrap()) {
        entity.setDropItemsWhenDead(false);
        entity.setDead();
        event.setCanceled(true);
      }

      // Fix chickens spawning in caves (which is caused by zombie jockeys)
      if (entity instanceof EntityMob) {
        if (entity.isRiding()) {

          Entity rider = entity.getRidingEntity();
          //so we don't kill spider jockey's cause they are hilarious.
          if (rider instanceof EntityChicken) {

            entity.setDropItemsWhenDead(false);
            entity.setDead();
            rider.setDropItemsWhenDead(false);
            rider.setDead();
            event.setCanceled(true);
          }

        }
      }
      //Just nuke them from orbit. It's the only way to be sure
      if (entity instanceof EntityChicken) {
        entity.setDropItemsWhenDead(false);
        entity.setDead();
        event.setCanceled(true); // NO!
      }

      // Prevent vanilla animals (that have a TFC counterpart) from mob spawners / egg throws / other mod mechanics
      if (ConfigTFC.General.OVERRIDES.forceReplaceVanillaAnimals && Helpers.isVanillaAnimal(entity)) {
        Entity TFCReplacement = Helpers.getTFCReplacement(entity);
        if (TFCReplacement != null) {
          TFCReplacement.setPositionAndRotation(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
          event.getWorld().spawnEntity(TFCReplacement); // Fires another spawning event for the TFC replacement
        }
        event.setCanceled(true); // Cancel the vanilla spawn
      }
      if (ConfigTFC.General.DIFFICULTY.giveVanillaMobsEquipment) {
        // Set equipment to some mobs
        MonsterEquipment equipment = MonsterEquipment.get(entity);
        if (equipment != null) {
          for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
            equipment.getEquipment(slot, Constants.RNG).ifPresent(stack -> entity.setItemStackToSlot(slot, stack));
          }
        }
      }
    }
    if (ConfigTFC.Devices.HEAT.coolHeatablesInWorld && entity instanceof EntityItem entityItem) {
      ItemStack stack = entityItem.getItem();
      ICapabilityHeat heatCap = stack.getCapability(CapabilityHeat.CAPABILITY, null);
      if (heatCap != null) {
        // Add a NBT tag here to make sure our ItemExpireEvent listener picks this entity up as valid (and as an extra check)
        entityItem.addTag("TFCHeatableItem");
        entityItem.lifespan = ConfigTFC.Devices.HEAT.ticksBeforeAttemptToCool;
      }
    }
  }

  /**
   * This implementation utilizes EntityJoinWorldEvent and ItemExpireEvent, they go hand-in-hand with each other.
   * <p>
   * By manually editing the tag of the EntityItem upon spawning, we can identify what EntityItems should be subjected to the cooling process. We also apply an
   * extremely short lifespan to mimic the speed of the barrel recipe, albeit slightly longer (half a second, but modifiable via config). Then all the checks
   * are done in ItemExpireEvent to set a new cooler temperature depending on if the conditions are met.
   * <p>
   * First of all, if temperature is 0 or less, then nothing needs to be done and the original lifespan is restored/added on.
   * <p>
   * If no conditions are met, the same, short length of lifespan is added on so a quick check can be done once again in case the condition will be met later.
   * <p>
   * Now if, this has been repeated for a long time up until it meets the item's normal lifespan, it will despawn.
   * <p>
   * Finally, if a condition is met, we extend its lifespan once again so more checks can be done to cool down even further.
   */
  @SubscribeEvent
  public static void onItemEntityExpire(ItemExpireEvent event) {
    EntityItem entityItem = event.getEntityItem();
    ItemStack stack = entityItem.getItem();
    ICapabilityHeat heatCap;
    if (ConfigTFC.Devices.HEAT.coolHeatablesInWorld && entityItem.getTags().contains("TFCHeatableItem")
        && (heatCap = stack.getCapability(CapabilityHeat.CAPABILITY, null)) != null) {
      int lifespan = stack.getItem().getEntityLifespan(stack, entityItem.world);
      if (entityItem.lifespan >= lifespan) {
        return; // If the ItemEntity has been there for as long or if not longer than the original unmodified lifespan, we return and setDead
      }
      float itemTemp = heatCap.getTemperature();
      if (itemTemp > 0) {
        float rand = Constants.RNG.nextFloat();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos((int) entityItem.posX, (int) entityItem.posY, (int) entityItem.posZ);
        IBlockState state;
        if ((state = entityItem.world.getBlockState(pos)).getBlock() instanceof BlockFluidBase) {
          int fluidTemp = ((BlockFluidBase) state.getBlock()).getFluid().getTemperature();
          if (fluidTemp <= 300) {
            heatCap.setTemperature(Math.max(0, Math.min(itemTemp, itemTemp - 350 + fluidTemp)));
            entityItem.world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 0.8f + rand * 0.4f);
            ((WorldServer) entityItem.world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, entityItem.posX, entityItem.posY, entityItem.posZ, 42, 0.0D, 0.15D, 0.0D, 0.08D);
            if (rand <= 0.001F) {
              entityItem.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2); // 1/1000 chance of the fluid being used up. Attempts to match the barrel recipe as it takes 1mb of water per operation.
            }
          }
          event.setExtraLife(ConfigTFC.Devices.HEAT.ticksBeforeAttemptToCool); // Set half a second onto the lifespan
          event.setCanceled(true);
          entityItem.setNoPickupDelay(); // For some reason when lifespan is added, pickup delay is reset, we disable this to make the experience seamless
          return;
        } else if (state.getPropertyKeys().contains(BlockSnow.LAYERS)) {
          heatCap.setTemperature(Math.max(0, itemTemp - 70));
          entityItem.world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.55f, 0.8f + rand * 0.4f);
          ((WorldServer) entityItem.world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, entityItem.posX, entityItem.posY, entityItem.posZ, 42, 0.0D, 0.15D, 0.0D, 0.08D);
          if (rand <= 0.1F) {
            if (state.getValue(BlockSnow.LAYERS) > 1) {
              entityItem.world.setBlockState(pos, state.withProperty(BlockSnow.LAYERS, state.getValue(BlockSnow.LAYERS) - 1), 2);
            } else {
              entityItem.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            }
          }
        }
        pos.setY(pos.getY() - 1);
        if ((state = entityItem.world.getBlockState(pos)).getMaterial() == Material.SNOW || state.getMaterial() == Material.CRAFTED_SNOW) {
          heatCap.setTemperature(Math.max(0, itemTemp - 75));
          entityItem.world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.65f, 0.8f + rand * 0.4f);
          ((WorldServer) entityItem.world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, entityItem.posX, entityItem.posY, entityItem.posZ, 42, 0.0D, 0.15D, 0.0D, 0.08D);
          if (rand <= 0.01F) {
            entityItem.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2); // 1/100 chance of the snow block evaporating.
          }
        } else if (state.getMaterial() == Material.ICE) {
          heatCap.setTemperature(Math.max(0, itemTemp - 100));
          entityItem.world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.8f, 0.8f + rand * 0.4f);
          ((WorldServer) entityItem.world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, entityItem.posX, entityItem.posY, entityItem.posZ, 42, 0.0D, 0.15D, 0.0D, 0.08D);
          if (rand <= 0.01F) {
            entityItem.world.setBlockState(pos, FluidsCore.FRESH_WATER.get().getBlock().getDefaultState(), 2); // 1/100 chance of the ice turning into water.
          }
        } else if (state.getMaterial() == Material.PACKED_ICE) {
          heatCap.setTemperature(Math.max(0, itemTemp - 125));
          entityItem.world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1f, 0.8f + rand * 0.4f);
          ((WorldServer) entityItem.world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, entityItem.posX, entityItem.posY, entityItem.posZ, 42, 0.0D, 0.15D, 0.0D, 0.08D);
          if (rand <= 0.005F) {
            entityItem.world.setBlockState(pos, FluidsCore.FRESH_WATER.get().getBlock()
              .getDefaultState(), 2); // 1/200 chance of the packed ice turning into water.
          }
        }
        event.setExtraLife(itemTemp == 0 ? lifespan : ConfigTFC.Devices.HEAT.ticksBeforeAttemptToCool); // Set lifespan accordingly
        entityItem.setNoPickupDelay(); // For some reason when lifespan is added, pickup delay is reset, we disable this to make the experience seamless
      } else {
        event.setExtraLife(lifespan); // Sets the original lifespan, next time it expires it will be setDead
      }
      event.setCanceled(true);
    }
  }

  @SubscribeEvent
  public static void onProjectileImpactEvent(ProjectileImpactEvent.Throwable event) {
    if (event.getThrowable() instanceof EntityEgg) {
      // Only way of preventing EntityEgg from spawning a chicken is to cancel the impact altogether
      // Side effect: The impact will not hurt entities
      event.setCanceled(true);
    }
  }

  @SubscribeEvent
  public static void onGameRuleChange(GameRuleChangeEvent event) {
    GameRules rules = event.getRules();
    if ("naturalRegeneration".equals(event.getRuleName()) && ConfigTFC.General.OVERRIDES.forceNoVanillaNaturalRegeneration) {
      // Natural regeneration should be disabled, allows TFC to have custom regeneration
      event.getRules().setOrCreateGameRule("naturalRegeneration", "false");
      TerraFirmaCraft.getLog().warn("Something tried to set natural regeneration to true, reverting!");
    }
  }

  @SubscribeEvent
  public static void onWorldLoad(WorldEvent.Load event) {
    final World world = event.getWorld();

    if (world.provider.getDimension() == 0 && !world.isRemote) {
      // Calendar Sync / Initialization
      CalendarWorldData data = CalendarWorldData.get(world);
      Calendar.INSTANCE.resetTo(data.getCalendar());
      TerraFirmaCraft.getNetwork().sendToAll(new PacketCalendarUpdate(Calendar.INSTANCE));
    }

    if (ConfigTFC.General.OVERRIDES.forceNoVanillaNaturalRegeneration) {
      // Natural regeneration should be disabled, allows TFC to have custom regeneration
      event.getWorld().getGameRules().setOrCreateGameRule("naturalRegeneration", "false");
      TerraFirmaCraft.getLog().warn("Updating gamerule naturalRegeneration to false!");
    }
  }

  /**
   * This will disable the bonus chest, cheaty cheaty players >:(
   *
   * @param event {@link net.minecraftforge.event.world.WorldEvent.CreateSpawnPosition}
   */
  @SubscribeEvent
  public static void onCreateSpawn(WorldEvent.CreateSpawnPosition event) {
    event.getSettings().bonusChestEnabled = false;
    TerraFirmaCraft.getLog().info("Disabling bonus chest, you cheaty cheater!");
  }

  @SubscribeEvent
  public static void onFluidPlaceBlock(BlockEvent.FluidPlaceBlockEvent event) {
    // Since cobble is a gravity block, placing it can lead to world crashes, so we avoid doing that and place rhyolite instead
    if (ConfigTFC.General.OVERRIDES.enableLavaWaterPlacesTFCBlocks) {
      if (event.getNewState().getBlock() == Blocks.STONE) {
        event.setNewState(BlockRockVariant.get(Rock.BASALT, Rock.Type.RAW).getDefaultState().withProperty(BlockRockRaw.CAN_FALL, false));
      }
      if (event.getNewState().getBlock() == Blocks.COBBLESTONE) {
        event.setNewState(BlockRockVariant.get(Rock.RHYOLITE, Rock.Type.RAW).getDefaultState().withProperty(BlockRockRaw.CAN_FALL, false));
      }
    }
  }

  @SubscribeEvent
  public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
    if (event.phase == TickEvent.Phase.START && event.player.ticksExisted % 100 == 0) {
      // Add spawn protection to surrounding chunks
      BlockPos basePos = new BlockPos(event.player);
      for (int i = -2; i <= 2; i++) {
        for (int j = -2; j <= 2; j++) {
          BlockPos chunkPos = basePos.add(16 * i, 0, 16 * j);
          World world = event.player.getEntityWorld();
          if (world.isBlockLoaded(basePos)) {
            ChunkDataTFC data = ChunkDataTFC.get(world, chunkPos);
            data.addSpawnProtection(1);
          }
        }
      }
    }

    if (event.phase == TickEvent.Phase.START && !(event.player.isCreative() || event.player.isSpectator()) && event.player.ticksExisted % 20 == 0) {
      // Update overburdened state
      int hugeHeavyCount = countPlayerOverburdened(event.player.inventory);
      if (hugeHeavyCount >= 1) {
        // Add extra exhaustion from carrying a heavy item
        // This is equivalent to an additional 25% of passive exhaustion
        event.player.addExhaustion(FoodStatsTFC.PASSIVE_EXHAUSTION * 20 * 0.25f / 0.4f);
      }
      if (hugeHeavyCount >= 2) {
        // Player is barely able to move
        event.player.addPotionEffect(new PotionEffect(EffectsCore.OVERBURDENED.get(), 25, 125, false, false));
      }
    }

    if (event.phase == TickEvent.Phase.START && event.player.openContainer != null && event.player instanceof EntityPlayerMP) {
      // Sync capability only changes in the player's container
      // We do this for containers (such as the player's inventory), which don't sync capability changes through detectAndSendChanges
      CapabilityContainerListener.syncCapabilityOnlyChanges(event.player.openContainer, (EntityPlayerMP) event.player);
    }
  }

  //go last, so if other mods handle this event, we don't.
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void checkArrowFill(ArrowNockEvent event) {
    //if we didn't have ammo in main inventory and no other mod has handled the event
    if (!event.hasAmmo() && event.getAction() == null) {
      final EntityPlayer player = event.getEntityPlayer();
      if (player != null && !player.capabilities.isCreativeMode) {
        if (ItemQuiver.replenishArrow(player)) {
          event.setAction(new ActionResult<>(EnumActionResult.PASS, event.getBow()));
        }
      }
    }
  }

  //go last to avoid cancelled events
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void pickupQuiverItems(EntityItemPickupEvent event) //only pickups of EntityItem, not EntityArrow
  {
    if (!event.isCanceled()) {
      if (ItemQuiver.pickupAmmo(event)) {
        event.setResult(Event.Result.ALLOW);
        event.getItem().getItem().setCount(0);
      }
    }
  }

  @SubscribeEvent
  public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
    ResourceLocation entityType = EntityList.getKey(event.getTarget());
    Entity target = event.getTarget();
    EntityPlayer player = event.getEntityPlayer();

    if (entityType != null && target.hurtResistantTime == 0 && !target.getEntityWorld().isRemote && player.getHeldItemMainhand().isEmpty()
        && player.isSneaking()) {
      String entityTypeName = entityType.toString();
      for (String pluckable : ConfigTFC.General.MISC.pluckableEntities) {
        if (pluckable.equals(entityTypeName)) {
          target.dropItem(Items.FEATHER, 1);
          target.attackEntityFrom(DamageSources.PLUCKING, (float) ConfigTFC.General.MISC.damagePerFeather);
          if (target instanceof IAnimalTFC animalTarget) {
            animalTarget.setFamiliarity(animalTarget.getFamiliarity() - 0.04f);
          }
          return;
        }
      }
    }
  }

  @SubscribeEvent
  public static void onServerChatEvent(ServerChatEvent event) {
    ICapabilityPlayerData cap = event.getPlayer().getCapability(CapabilityPlayerData.CAPABILITY, null);
    if (cap != null) {
      long intoxicatedTicks = cap.getIntoxicatedTime() - 6 * ICalendar.TICKS_IN_HOUR; // Only apply intoxication after 6 hr
      if (intoxicatedTicks > 0) {
        float drunkChance = MathHelper.clamp((float) intoxicatedTicks / ProviderPlayerData.MAX_INTOXICATED_TICKS, 0, 0.7f);
        String originalMessage = event.getMessage();
        String[] words = originalMessage.split(" ");
        for (int i = 0; i < words.length; i++) {
          String word = words[i];
          if (word.isEmpty()) {
            continue;
          }

          // Swap two letters
          if (Constants.RNG.nextFloat() < drunkChance && word.length() >= 2) {
            int pos = Constants.RNG.nextInt(word.length() - 1);
            word = word.substring(0, pos) + word.charAt(pos + 1) + word.charAt(pos) + word.substring(pos + 2);
          }

          // Repeat / slur letters
          if (Constants.RNG.nextFloat() < drunkChance) {
            int pos = Constants.RNG.nextInt(word.length());
            char repeat = word.charAt(pos);
            int amount = 1 + Constants.RNG.nextInt(3);
            word = word.substring(0, pos) + new String(new char[amount]).replace('\0', repeat) + (pos + 1 < word.length() ? word.substring(pos + 1) : "");
          }

          // Add additional letters
          if (Constants.RNG.nextFloat() < drunkChance) {
            int pos = Constants.RNG.nextInt(word.length());
            char replacement = ALPHABET.charAt(Constants.RNG.nextInt(ALPHABET.length()));
            if (Character.isUpperCase(word.charAt(Constants.RNG.nextInt(word.length())))) {
              replacement = Character.toUpperCase(replacement);
            }
            word = word.substring(0, pos) + replacement + (pos + 1 < word.length() ? word.substring(pos + 1) : "");
          }

          words[i] = word;
        }
        event.setComponent(new TextComponentTranslation("<" + event.getUsername() + "> " + String.join(" ", words)));
      }
    }
  }

  private static int countPlayerOverburdened(InventoryPlayer inventory) {
    // This is just optimized (probably uselessly, but whatever) for use in onPlayerTick
    int hugeHeavyCount = 0;
    for (ItemStack stack : inventory.mainInventory) {
      if (CapabilitySize.checkItemSize(stack, Size.HUGE, Weight.VERY_HEAVY)) {
        hugeHeavyCount++;
        if (hugeHeavyCount >= 2) {
          return hugeHeavyCount;
        }
      }
    }
    for (ItemStack stack : inventory.armorInventory) {
      if (CapabilitySize.checkItemSize(stack, Size.HUGE, Weight.VERY_HEAVY)) {
        hugeHeavyCount++;
        if (hugeHeavyCount >= 2) {
          return hugeHeavyCount;
        }
      }
    }
    for (ItemStack stack : inventory.offHandInventory) {
      if (CapabilitySize.checkItemSize(stack, Size.HUGE, Weight.VERY_HEAVY)) {
        hugeHeavyCount++;
        if (hugeHeavyCount >= 2) {
          return hugeHeavyCount;
        }
      }
    }
    return hugeHeavyCount;
  }

  @SubscribeEvent
  public void addAI(LivingEvent.LivingUpdateEvent event) {
    if (event.getEntityLiving() instanceof EntityAnimalTFC animal && event.getEntityLiving().ticksExisted < 5 && !event.getEntityLiving().isChild()) {
      animal.tasks.addTask(2, new EBEntityAI(animal));
    }
  }


}
