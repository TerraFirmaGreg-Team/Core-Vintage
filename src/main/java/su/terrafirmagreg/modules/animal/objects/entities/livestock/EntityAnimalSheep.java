package su.terrafirmagreg.modules.animal.objects.entities.livestock;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.modules.animal.ModuleAnimal;
import su.terrafirmagreg.modules.animal.ModuleAnimalConfig;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalMammal;
import su.terrafirmagreg.modules.animal.objects.entities.TFCEntities;
import su.terrafirmagreg.modules.core.network.SCPacketSimpleMessage;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.IShearable;


import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import static su.terrafirmagreg.api.lib.MathConstants.RNG;

public class EntityAnimalSheep extends EntityAnimalMammal implements IShearable, ILivestock {

    private static final DataParameter<Integer> DYE_COLOR = EntityDataManager.createKey(EntityAnimalSheep.class, DataSerializers.VARINT);
    private static final DataParameter<Long> SHEARED = EntityDataManager.createKey(EntityAnimalSheep.class, TFCEntities.getLongDataSerializer());

    @SuppressWarnings("unused")
    public EntityAnimalSheep(World worldIn) {
        this(worldIn, Gender.valueOf(RNG.nextBoolean()),
                getRandomGrowth(ModuleAnimalConfig.ENTITIES.SHEEP.adulthood, ModuleAnimalConfig.ENTITIES.SHEEP.elder),
                EntitySheep.getRandomSheepColor(RNG));
    }

    public EntityAnimalSheep(World worldIn, Gender gender, int birthDay, EnumDyeColor dye) {
        super(worldIn, gender, birthDay);
        setSize(0.9F, 1.3F);
        setDyeColor(dye);
        setShearedTick(0);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.PLAINS)) {
            return ModuleAnimalConfig.ENTITIES.SHEEP.rarity;
        }
        return 0;
    }

    @Override
    public BiConsumer<List<EntityLiving>, Random> getGroupingRules() {
        return AnimalGroupingRules.MALE_AND_FEMALES;
    }

    @Override
    public int getMinGroupSize() {
        return 3;
    }

    @Override
    public int getMaxGroupSize() {
        return 5;
    }

    @Override
    public void birthChildren() {
        int numberOfChildren = ModuleAnimalConfig.ENTITIES.SHEEP.babies;
        for (int i = 0; i < numberOfChildren; i++) {
            EntityAnimalSheep baby = new EntityAnimalSheep(world, Gender.valueOf(RNG.nextBoolean()),
                    (int) CalendarTFC.PLAYER_TIME.getTotalDays(), getDyeColor());
            baby.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
            baby.setFamiliarity(getFamiliarity() < 0.9F ? getFamiliarity() / 2.0F : getFamiliarity() * 0.9F);
            world.spawnEntity(baby);
        }
    }

    @Override
    public long gestationDays() {
        return ModuleAnimalConfig.ENTITIES.SHEEP.gestation;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(DYE_COLOR, 0);
        dataManager.register(SHEARED, 0L);
    }

    @Override
    public void writeEntityToNBT(@NotNull NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        NBTUtils.setGenericNBTValue(nbt, "shearedTick", getShearedTick());
        NBTUtils.setGenericNBTValue(nbt, "dyecolor", getDyeColor().getMetadata());
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        setShearedTick(nbt.getLong("shearedTick"));
        setDyeColor(EnumDyeColor.byMetadata(nbt.getByte("dyecolor")));
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (OreDictUtils.contains(stack, "knife")) {
            if (!world.isRemote) {
                if (isReadyForAnimalProduct()) {
                    stack.damageItem(1, player);
                    ItemStack woolStack = new ItemStack(ItemsAnimal.WOOL, 1);
                    StackUtils.spawnItemStack(player.world, new BlockPos(posX, posY, posZ), woolStack);
                    playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
                    setProductsCooldown();
                } else {
                    TextComponentTranslation tooltip = getTooltip();
                    if (tooltip != null) {
                        ModuleAnimal.PACKET_SERVICE.sendTo(new SCPacketSimpleMessage(SCPacketSimpleMessage.MessageCategory.ANIMAL, tooltip),
                                (EntityPlayerMP) player);
                    }
                }
            }
            return true;
        } else if (OreDictUtils.contains(stack, "shears")) {
            if (!world.isRemote) {
                if (!isReadyForAnimalProduct()) {
                    TextComponentTranslation tooltip = getTooltip();
                    if (tooltip != null) {
                        ModuleAnimal.PACKET_SERVICE.sendTo(new SCPacketSimpleMessage(SCPacketSimpleMessage.MessageCategory.ANIMAL, tooltip),
                                (EntityPlayerMP) player);
                    }
                }
            }
            return false; // Process done in #onSheared by vanilla
        } else {
            return super.processInteract(player, hand);
        }
    }

    @Override
    public double getOldDeathChance() {
        return ModuleAnimalConfig.ENTITIES.SHEEP.oldDeathChance;
    }

    @Override
    protected boolean eatFood(@NotNull ItemStack stack, EntityPlayer player) {
        // Refuses to eat rotten stuff
        IFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
        if (cap != null) {
            if (cap.isRotten()) {
                return false;
            }
        }
        return super.eatFood(stack, player);
    }

    @Override
    public float getAdultFamiliarityCap() {
        return 0.35F;
    }

    @Override
    public int getDaysToAdulthood() {
        return ModuleAnimalConfig.ENTITIES.SHEEP.adulthood;
    }

    @Override
    public int getDaysToElderly() {
        return ModuleAnimalConfig.ENTITIES.SHEEP.elder;
    }

    @Override
    public boolean isReadyForAnimalProduct() {
        return getAge() != Age.CHILD && hasWool() && getFamiliarity() > 0.15f;
    }

    @Override
    public List<ItemStack> getProducts() {
        // Only white for now
        return Collections.singletonList(new ItemStack(ItemsAnimal.WOOL, 1));
    }

    @Override
    public void setProductsCooldown() {
        setShearedTick(CalendarTFC.PLAYER_TIME.getTicks());
    }

    @Override
    public long getProductsCooldown() {
        return Math.max(0, ModuleAnimalConfig.ENTITIES.SHEEP.woolTicks + getShearedTick() - CalendarTFC.PLAYER_TIME.getTicks());
    }

    @Override
    public TextComponentTranslation getTooltip() {
        if (getAge() == Age.CHILD) {
            return new TextComponentTranslation(ModUtils.localize("tooltip", "animal.product.young"), getAnimalName());
        } else if (getFamiliarity() <= 0.15f) {
            return new TextComponentTranslation(ModUtils.localize("tooltip", "animal.product.low_familiarity"), getAnimalName());
        } else if (!hasWool()) {
            return new TextComponentTranslation(ModUtils.localize("tooltip", "animal.product.no_wool"), getAnimalName());
        }
        return null;
    }

    public EnumDyeColor getDyeColor() {
        return EnumDyeColor.byMetadata(dataManager.get(DYE_COLOR));
    }

    public void setDyeColor(EnumDyeColor color) {
        dataManager.set(DYE_COLOR, color.getMetadata());
    }

    @Override
    public boolean isShearable(@NotNull ItemStack item, IBlockAccess world, BlockPos pos) {
        return isReadyForAnimalProduct();
    }

    @NotNull
    @Override
    public List<ItemStack> onSheared(@NotNull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        setProductsCooldown();
        List<ItemStack> products = getProducts();
        // Fortune makes this less random and more towards the maximum (3) amount.
        int i = 1 + fortune + rand.nextInt(3 - Math.min(2, fortune));

        List<ItemStack> ret = new ArrayList<>();
        for (ItemStack stack : products) {
            stack.setCount(i);
            ret.add(stack);
        }
        playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
    }

    public long getShearedTick() {
        return dataManager.get(SHEARED);
    }

    public void setShearedTick(long tick) {
        dataManager.set(SHEARED, tick);
    }

    public boolean hasWool() {
        return getShearedTick() <= 0 || getProductsCooldown() <= 0;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SHEEP_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SHEEP_DEATH;
    }

    @Override
    protected void initEntityAI() {
        addCommonLivestockAI(this, 1.2D);
        addCommonPreyAI(this, 1.2D);

        tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SHEEP_AMBIENT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTablesAnimal.ANIMALS_SHEEP;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }
}
