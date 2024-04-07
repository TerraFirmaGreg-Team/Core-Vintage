package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.api.spi.item.ItemBase;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.ModuleCoreConfig;
import su.terrafirmagreg.modules.device.data.BlocksDevice;
import su.terrafirmagreg.modules.device.data.SoundDevice;
import su.terrafirmagreg.modules.device.objects.tiles.TEFirePit;
import su.terrafirmagreg.modules.device.objects.tiles.TELogPile;
import su.terrafirmagreg.modules.device.objects.tiles.TEPitKiln;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import mcp.MethodsReturnNonnullByDefault;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static su.terrafirmagreg.api.util.PropertyUtils.LIT;

@MethodsReturnNonnullByDefault
public class ItemFireStarter extends ItemBase {

    public ItemFireStarter() {
        setMaxDamage(8);
        setMaxStackSize(1);
        setNoRepair();

    }

    /**
     * Causes ignition of fire based devices, consume or damage items if valid
     *
     * @param stack the itemstack that is used to cause ignition
     * @return true if item is a valid item to ignite devices
     */
    public static boolean onIgnition(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (OreDictUtils.contains(stack, "fireStarter")) {
            if (stack.getItem().isDamageable()) {
                StackUtils.damageItem(stack);
            } else {
                stack.shrink(1);
            }
            return true;
        }
        // Infinite items aren't damaged or consumed
        return OreDictUtils.contains(stack, "infiniteFire");
    }

    /**
     * Checks if item can cause ignition
     *
     * @param stack the itemstack that is used to cause ignition
     * @return true if item is a valid item to ignite devices
     */
    public static boolean canIgnite(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        return OreDictUtils.contains(stack, "fireStarter") || OreDictUtils.contains(stack, "infiniteFire");
    }

    @Override
    public @NotNull String getName() {
        return "device/firestarter";
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "fireStarter");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY,
                                      float hitZ) {
        if (hand != EnumHand.MAIN_HAND || worldIn.isRemote) {
            return EnumActionResult.PASS;
        }
        if (canStartFire(worldIn, player) == null) {
            return EnumActionResult.FAIL;
        }
        player.setActiveHand(hand);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase entityLivingBase, int countLeft) {
        if (!(entityLivingBase instanceof EntityPlayer player)) return;
        final RayTraceResult result = canStartFire(player.world, player);
        if (result == null) {
            player.resetActiveHand();
            return;
        }
        final int total = getMaxItemUseDuration(stack);
        final int count = total - countLeft;
        final BlockPos pos = result.getBlockPos().add(0, 1, 0);
        final World world = player.world;
        // Base chance
        float chance = (float) ModuleCoreConfig.ITEMS.fireStarterChance;
        // Raining reduces chance by half
        if (world.isRainingAt(pos)) {
            chance *= 0.5F;
        }

        if (world.isRemote) // Client
        {
            if (itemRand.nextFloat() + 0.3 < count / (double) total) {
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, result.hitVec.x, result.hitVec.y, result.hitVec.z, 0.0F, 0.1F, 0.0F);
            }
            if (countLeft < 10 && itemRand.nextFloat() + 0.3 < count / (double) total) {
                world.spawnParticle(EnumParticleTypes.FLAME, result.hitVec.x, result.hitVec.y, result.hitVec.z, 0.0F, 0.0F, 0.0F);
            }

            if (count % 3 == 1) {
                player.playSound(SoundDevice.FIRE_STARTER, 0.5f, 0.05F);
            }
        } else if (countLeft == 1) // Server, and last tick of use
        {
            stack.damageItem(1, player);
            final IBlockState state = world.getBlockState(pos.down());
            if (state.getBlock() == BlocksDevice.LOG_PILE) {
                // Log pile
                if (itemRand.nextFloat() < chance) {
                    world.setBlockState(pos.down(), state.withProperty(LIT, true));
                    TELogPile te = TileUtils.getTile(world, pos.down(), TELogPile.class);
                    if (te != null) {
                        te.light();
                    }
                    if (Blocks.FIRE.canPlaceBlockAt(world, pos)) {
                        world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                    }
                }
            } else if (state.getBlock() == BlocksDevice.PIT_KILN) {
                // Pit Kiln
                if (itemRand.nextFloat() < chance) {
                    TEPitKiln te = TileUtils.getTile(world, pos.down(), TEPitKiln.class);
                    if (te != null) {
                        te.tryLight();
                    }
                }
            } else {
                // Try to make a fire pit

                final List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(1, 2, 1)));
                final List<EntityItem> stuffToUse = new ArrayList<>();

                int sticks = 0, kindling = 0;
                EntityItem log = null;

                for (EntityItem entity : items) {
                    if (OreDictUtils.contains(entity.getItem(), "stickWood")) {
                        sticks += entity.getItem().getCount();
                        stuffToUse.add(entity);
                    } else if (OreDictUtils.contains(entity.getItem(), "kindling")) {
                        kindling += entity.getItem().getCount();
                        stuffToUse.add(entity);
                    } else if (log == null && (OreDictUtils.contains(entity.getItem(), "logWood") ||
                            OreDictUtils.contains(entity.getItem(), "driftwood") ||
                            OreDictUtils.contains(entity.getItem(), "twig"))) {
                        log = entity;
                    }
                }

                if (sticks >= 3 && log != null) {
                    final float kindlingModifier = Math.min(0.1f * (float) kindling, 0.5f);
                    if (itemRand.nextFloat() < chance + kindlingModifier) {
                        world.setBlockState(pos, BlocksDevice.FIRE_PIT.getDefaultState().withProperty(LIT, true));
                        TEFirePit te = TileUtils.getTile(world, pos, TEFirePit.class);
                        if (te != null) {
                            te.onCreate(log.getItem());
                        }
                        stuffToUse.forEach(Entity::setDead);
                        log.getItem().shrink(1);
                        if (log.getItem().getCount() == 0) {
                            log.setDead();
                        }
                    }
                } else {
                    // Can't make fire pit, so start a fire
                    if (Blocks.FIRE.canPlaceBlockAt(world, pos)) {
                        world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                    }
                }
            }
        }
    }

    @Override
    public boolean canStack(@NotNull ItemStack stack) {
        return false;
    }

    @Nullable
    private RayTraceResult canStartFire(World world, EntityPlayer player) {
        RayTraceResult result = rayTrace(world, player, true);
        //noinspection ConstantConditions
        if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos pos = result.getBlockPos();
            final IBlockState current = world.getBlockState(pos);
            if (result.sideHit == EnumFacing.UP && current.isSideSolid(world, pos, EnumFacing.UP) && !current.getMaterial()
                    .isLiquid()) {
                if (world.isAirBlock(pos.up())) {
                    return result;
                }
            }
        }
        return null;
    }

}
