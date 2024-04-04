package net.sharkbark.cellars;

import net.dries007.tfc.api.capability.food.FoodTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.sharkbark.cellars.proxy.CommonProxy;
import net.sharkbark.cellars.util.CellarsTab;
import net.sharkbark.cellars.util.Reference;
import net.sharkbark.cellars.util.handlers.PacketHandler;
import net.sharkbark.cellars.util.handlers.RegistryHandler;
import su.terrafirmagreg.Tags;

import java.util.Map;

import static su.terrafirmagreg.api.lib.Constants.MODID_CELLARS;

@Mod(modid = MODID_CELLARS, name = Reference.NAME, version = Tags.VERSION, dependencies = "required-after:tfc")
@Mod.EventBusSubscriber(modid = MODID_CELLARS)
public class Main {

	public static final CellarsTab creativeTab = new CellarsTab();

	@Mod.Instance
	public static Main INSTANCE;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	@SubscribeEvent/*
    public static void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
        if(ModConfig.firstJoinBook){
            if (event.getEntity() instanceof EntityPlayerMP) {
                EntityPlayerMP player = (EntityPlayerMP) event.getEntity();

                System.out.println("Checking for player first join.");
                if (player.getEntityData().getInteger("CellarsFirstJoin") != 1) {
                    System.out.println("Joined before, skipping book giving.");
                } else {
                    System.out.println("Setting variable.");
                    player.getEntityData().setInteger("CellarsFirstJoin",1);

                    System.out.println("Attempting to give book.");
                    NBTTagCompound bookData = new NBTTagCompound();
                    bookData.setString("patchouli:book", "cellars:book");
                    ItemStack book = new ItemStack(PatchouliItems.book);
                    book.setTagCompound(bookData);
                    ItemHandlerHelper.giveItemToPlayer(player, book);
                }
            }
        }
    }*/

	public static void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
		if (!ModConfig.firstJoinBook || event.getWorld().isRemote) {
			return;
		}
		if (event.getEntity() instanceof EntityPlayer) {
			NBTTagCompound data = event.getEntity().getEntityData();
			NBTTagCompound persistent;
			if (!data.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
				data.setTag(EntityPlayer.PERSISTED_NBT_TAG, (persistent = new NBTTagCompound()));
			} else {
				persistent = data.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
			}
		}
	}

	@Mod.EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		ModConfig.loadConfig(event);
	}

	@Mod.EventHandler
	public void Init(FMLInitializationEvent event) {
		System.out.println("Mod Config for Cellars has Cool Mod as " + ModConfig.coolMod);
		System.out.println("Mod Config for Cellars has Icy Mod as " + ModConfig.icyMod);
		System.out.println("Mod Config for Cellars has Frozen Mod as " + ModConfig.icleMod);
		System.out.println("Mod Config for Cellars has Freeze Dryed Mod as " + ModConfig.dryMod);
		System.out.println("Mod Config for Cellars has Preserving Mod as " + ModConfig.preservingMod);

		RegistryHandler.initRegistries();

		Reference.COOL = new FoodTrait("sharkCool", ModConfig.coolMod);
		Reference.ICY = new FoodTrait("sharkIcy", ModConfig.icyMod);
		Reference.FREEZING = new FoodTrait("sharkIcle", ModConfig.icleMod);
		Reference.DRY = new FoodTrait("sharkDry", ModConfig.dryMod);
		Reference.PRESERVING = new FoodTrait("sharkPreserving", ModConfig.preservingMod);

		Map<String, FoodTrait> tmp = FoodTrait.getTraits();
		for (Map.Entry<String, FoodTrait> entry : tmp.entrySet())
			System.out.println("Key = " + entry.getKey() +
					", Value = " + entry.getValue() +
					", Decay = " + entry.getValue().getDecayModifier());


	}

	@Mod.EventHandler
	public void PostInit(FMLPostInitializationEvent event) {
		Reference.initialized = true;
		PacketHandler.init();
	}

}
