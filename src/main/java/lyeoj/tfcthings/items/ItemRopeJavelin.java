package lyeoj.tfcthings.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lyeoj.tfcthings.entity.projectile.EntityThrownRopeJavelin;
import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.capability.metal.IMetalItem;


import su.terrafirmagreg.api.capabilities.size.spi.Size;

import su.terrafirmagreg.api.capabilities.size.spi.Weight;


import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemTFC;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

import static su.terrafirmagreg.api.lib.MathConstants.RNG;

public class ItemRopeJavelin extends ItemTFC implements IMetalItem, ItemOreDict, TFCThingsConfigurableItem {

    private static final String THROWN_NBT_KEY = "Thrown";
    private static final String JAVELIN_NBT_KEY = "JavelinID";
    private static final String CAPTURED_NBT_KEY = "CapturedID";
    public final ToolMaterial material;
    protected final double attackDamage;
    protected final float attackSpeed;
    private final Metal metal;
    protected double pullStrength = 0.1;

    public ItemRopeJavelin(Metal metal, String name) {
        this.metal = metal;
        this.material = metal.getToolMetal();
        setCreativeTab(CreativeTabsTFC.CT_METAL);
        this.setMaxDamage((int) ((double) material.getMaxUses() * 0.1D));
        this.attackDamage = 0.7 * this.material.getAttackDamage();
        this.attackSpeed = -1.8F;
        this.setTranslationKey(getNamePrefix() + "_" + name);
        this.setRegistryName(getNamePrefix() + "/" + name);
        this.setMaxStackSize(1);
        this.addPropertyOverride(new ResourceLocation("thrown"), new IItemPropertyGetter() {

            @SideOnly(Side.CLIENT)
            public float apply(@NotNull ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (entityIn == null) {
                    return 0.0F;
                } else {

                    boolean flag = entityIn.getHeldItemMainhand() == stack;
                    boolean flag1 = entityIn.getHeldItemOffhand() == stack;

                    if (entityIn.getHeldItemMainhand().getItem() instanceof ItemRopeJavelin) {
                        flag1 = false;
                    }
                    return (flag || flag1) && entityIn instanceof EntityPlayer && isThrown(stack) ? 1.0F : 0.0F;
                }
            }
        });
    }

    protected String getNamePrefix() {
        return "rope_javelin";
    }

    @Nullable
    @Override
    public Metal getMetal(ItemStack itemStack) {
        return metal;
    }

    @Override
    public int getSmeltAmount(ItemStack itemStack) {
        return 100;
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack itemStack) {
        return Size.NORMAL;
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack itemStack) {
        return Weight.MEDIUM;
    }

    public boolean canStack(@NotNull ItemStack itemStack) {
        return false;
    }

    public @NotNull Multimap<String, AttributeModifier> getAttributeModifiers(@NotNull EntityEquipmentSlot slot, @NotNull ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EntityEquipmentSlot.MAINHAND && !isThrown(stack)) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                    new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
                    new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", this.attackSpeed, 0));
        }

        return multimap;
    }

    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (isThrown(itemstack)) {
            EntityThrownRopeJavelin javelin = getJavelin(itemstack, worldIn);
            if (javelin != null) {
                if (getCapturedEntity(itemstack, worldIn) != null) {
                    Entity entity = getCapturedEntity(itemstack, worldIn);
                    if (entity.isRiding()) {
                        entity.dismountRidingEntity();
                    }
                    double d0 = playerIn.posX - javelin.posX;
                    double d1 = playerIn.posY - javelin.posY;
                    double d2 = playerIn.posZ - javelin.posZ;
                    entity.motionX += d0 * pullStrength;
                    entity.motionY += d1 * pullStrength;
                    entity.motionZ += d2 * pullStrength;
                }
            }
            retractJavelin(itemstack, worldIn);
        } else {
            playerIn.setActiveHand(handIn);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    @NotNull
    public EnumAction getItemUseAction(@NotNull ItemStack stack) {
        return isThrown(stack) ? EnumAction.NONE : EnumAction.BOW;
    }

    public int getMaxItemUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    public void onPlayerStoppedUsing(@NotNull ItemStack stack, @NotNull World worldIn, @NotNull EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer player) {
            int charge = this.getMaxItemUseDuration(stack) - timeLeft;
            if (charge > 5) {
                float f = ItemBow.getArrowVelocity(charge);
                if (!worldIn.isRemote) {
                    setThrown(stack, true);
                    EntityThrownRopeJavelin javelin = makeNewJavelin(worldIn, player);
                    javelin.setDamage(this.attackDamage);
                    javelin.setWeapon(stack);
                    javelin.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, f * 1.5F, 0.5F);
                    setJavelin(stack, javelin);
                    getJavelin(stack, worldIn);
                    worldIn.spawnEntity(javelin);
                    worldIn.playSound(null, player.posX, player.posY, player.posZ, TFCSounds.ITEM_THROW, SoundCategory.PLAYERS, 1.0F,
                            1.0F / (RNG.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                }
            }
        }

    }

    protected EntityThrownRopeJavelin makeNewJavelin(World worldIn, EntityPlayer player) {
        return new EntityThrownRopeJavelin(worldIn, player);
    }

    public boolean isThrown(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        assert stack.getTagCompound() != null;
        if (!stack.getTagCompound().hasKey(THROWN_NBT_KEY)) {
            return false;
        }
        return stack.getTagCompound().getBoolean(THROWN_NBT_KEY);
    }

    public void setThrown(ItemStack stack, boolean thrown) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        assert stack.getTagCompound() != null;
        stack.getTagCompound().setBoolean(THROWN_NBT_KEY, thrown);
    }

    public EntityThrownRopeJavelin getJavelin(ItemStack stack, World world) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        assert stack.getTagCompound() != null;
        UUID javelinID = stack.getTagCompound().getUniqueId(JAVELIN_NBT_KEY);
        if (javelinID != null && world instanceof WorldServer) {
            Entity entity = ((WorldServer) world).getEntityFromUuid(javelinID);
            if (entity instanceof EntityThrownRopeJavelin) {
                return (EntityThrownRopeJavelin) entity;
            }
        }
        return null;
    }

    public void setJavelin(ItemStack stack, EntityThrownRopeJavelin javelin) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        assert stack.getTagCompound() != null;
        if (javelin != null) {
            stack.getTagCompound().setUniqueId(JAVELIN_NBT_KEY, javelin.getUniqueID());
        } else {

            stack.getTagCompound().setUniqueId(JAVELIN_NBT_KEY, UUID.randomUUID());
        }
    }

    public Entity getCapturedEntity(ItemStack stack, World world) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        assert stack.getTagCompound() != null;
        UUID capturedID = stack.getTagCompound().getUniqueId(CAPTURED_NBT_KEY);
        if (capturedID != null && world instanceof WorldServer) {
            return ((WorldServer) world).getEntityFromUuid(capturedID);

        }
        return null;
    }

    public void setCapturedEntity(ItemStack stack, Entity entity) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        assert stack.getTagCompound() != null;
        if (entity != null) {
            stack.getTagCompound().setUniqueId(CAPTURED_NBT_KEY, entity.getUniqueID());
        } else {
            stack.getTagCompound().setUniqueId(CAPTURED_NBT_KEY, UUID.randomUUID());
        }
    }

    public void retractJavelin(ItemStack stack, World world) {
        setThrown(stack, false);
        setCapturedEntity(stack, null);
        if (getJavelin(stack, world) != null) {
            getJavelin(stack, world).setDead();
            setJavelin(stack, null);
        }
    }

    @Override
    public void initOreDict() {
        OreDictionary.registerOre("tool", new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE));
    }

    @Override
    public boolean isEnabled() {
        return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableRopeJavelins;
    }
}
