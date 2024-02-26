package su.terrafirmagreg.api.registry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.lib.LootBuilder;
import su.terrafirmagreg.api.models.ICustomModel;
import su.terrafirmagreg.api.models.ICustomStateMapper;
import su.terrafirmagreg.api.models.ModelManager;
import su.terrafirmagreg.api.network.NetworkEntityIdSupplier;
import su.terrafirmagreg.api.spi.block.IColorfulBlock;
import su.terrafirmagreg.api.spi.item.IColorfulItem;
import su.terrafirmagreg.api.spi.item.ICustomMesh;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.api.util.GameUtils;

import javax.annotation.Nonnull;
import java.util.List;

@Getter
public class RegistryManager {

	/**
	 * The id of the mod the registry helper instance belongs to.
	 */
	private final String modID;

	/**
	 * A list of all items registered by the helper.
	 */
	private final NonNullList<Item> items = NonNullList.create();

	/**
	 * A list of all blocks registered by the helper.
	 */
	private final NonNullList<Block> blocks = NonNullList.create();

	/**
	 * A list of all potions registered by the helper.
	 */
	private final NonNullList<Potion> potions = NonNullList.create();

	/**
	 * A list of all potion type registered by the helper.
	 */
	private final NonNullList<PotionType> potionType = NonNullList.create();

	/**
	 * A list of all biomes registered by the helper.
	 */
	private final NonNullList<Biome> biomes = NonNullList.create();

	/**
	 * A list of all the sounds registered by the helper.
	 */
	private final NonNullList<SoundEvent> sounds = NonNullList.create();

	/**
	 * A list of all entities registered by the helper.
	 */
	private final NonNullList<EntityEntry> entities = NonNullList.create();

	/**
	 * A list of all entities registered by the helper.
	 */
	private final NonNullList<ResourceLocation> entityIds = NonNullList.create();

	/**
	 * A local map of all the entries that have been added. This is on a per instance basis,
	 * used to get mod-specific entries.
	 */
	private final Multimap<ResourceLocation, LootBuilder> lootTableEntries = HashMultimap.create();

	/**
	 * A list of all the tile providers registered here.
	 */
	private final List<ITEBlock> tileProviders = NonNullList.create();

	/**
	 * A list of all recipes registered.
	 */
	private final List<IRecipe> recipes = NonNullList.create();

	/**
	 * A list of all enchantments registered.
	 */
	private final List<Enchantment> enchantments = NonNullList.create();

	/**
	 * A list of all the custom stateMapper.
	 */
	private final List<ICustomStateMapper> customStateMapper = NonNullList.create();

	/**
	 * A list of all the custom models.
	 */
	private final List<ICustomModel> customModel = NonNullList.create();

	/**
	 * A list of all the custom mesh definitions.
	 */
	private final List<Item> customMeshes = NonNullList.create();

	/**
	 * A list of all the colored items registered here.
	 */
	private final List<Item> coloredItems = NonNullList.create();

	/**
	 * A list of all the colored blocks registered here.
	 */
	private final List<Block> coloredBlocks = NonNullList.create();

	/**
	 * The creative tab used by the mod. This can be null.
	 */
	private final CreativeTabs tab;

	/**
	 * The auto registry for the helper.
	 */
	private AutoRegistry autoRegistry;

	private NetworkEntityIdSupplier networkEntityIdSupplier;

	/**
	 * Constructs a new Registry for the specified mod id. Multiple helpers can exist
	 * with the same id, but it's not recommended.
	 */
	public RegistryManager() {
		this(null);
	}

	/**
	 * Constructs a new Registry. The modid for the helper is equal to that of the active
	 * mod container, and auto model registration is enabled.
	 *
	 * @param tab The tab for the registry helper.
	 */
	public RegistryManager(@Nullable CreativeTabs tab) {
		this.modID = Loader.instance().activeModContainer().getModId();
		this.tab = tab;
	}


	/**
	 * Enables automatic registration for things like the event bus.
	 *
	 * @return The Registry, for convenience.
	 */
	public RegistryManager enableAutoRegistration() {

		this.autoRegistry = this.getNewAutoRegistry();
		MinecraftForge.EVENT_BUS.register(this.autoRegistry);
		return this;
	}

	/**
	 * Checks if the registry has automatic registration.
	 *
	 * @return Whether or not the helper has automatic registration.
	 */
	public boolean hasAutoRegistry() {
		return this.autoRegistry != null;
	}


	/**
	 * Used to get a new auto registry instance. Only used if {@link #enableAutoRegistration()}
	 * is used.
	 *
	 * @return The new auto registry instance.
	 */
	public AutoRegistry getNewAutoRegistry() {
		return new AutoRegistry(this);
	}

	public void setNetworkEntityIdSupplier(NetworkEntityIdSupplier supplier) {
		if (this.networkEntityIdSupplier != null)
			throw new IllegalStateException("Network entity id supplier has already been set");

		this.networkEntityIdSupplier = supplier;
	}


	//region // ===== Block ========================================================================================================================//

	public void registerAuto(Block... blocks) {
		for (Block block : blocks) {
			registerAuto(block);
		}
	}

	public void registerAuto(Block block) {
		if (block instanceof IAutoReg provider) {
			ItemBlock itemBlock = provider.getItemBlock();
			String id = provider.getName();
			this.registerBlock(block, itemBlock, id);
		}
	}

	/**
	 * Registers a block to the game. This will also set the unlocalized name, and creative tab
	 * if {@link #tab} has been set. The block will also be cached in {@link #blocks}.
	 *
	 * @param block     The block to register.
	 * @param itemBlock The ItemBlock for the block.
	 * @param id        The id to register the block with.
	 */
	public void registerBlock(@Nonnull Block block, @Nullable ItemBlock itemBlock, @Nonnull String id) {

		block.setRegistryName(this.modID, id);
		block.setTranslationKey(this.modID + "." + id.toLowerCase().replace("_", ".").replaceAll("/", "."));
		if (this.tab != null)
			block.setCreativeTab(this.tab);

		this.blocks.add(block);

		if (itemBlock != null) this.registerItem(itemBlock, id);
		if (block instanceof ITEBlock te) this.tileProviders.add(te);

		if (GameUtils.isClient()) {
			if (block instanceof ICustomStateMapper state) {
				this.customStateMapper.add(state);
			}
			if (block instanceof ICustomModel blockModel) {
				this.customModel.add(blockModel);
			} else {
				this.registerClientModel(() ->
						ModelManager.registerBlockInventoryModel(block)
				);
			}
			if (block instanceof IColorfulBlock) this.coloredBlocks.add(block);
		}
	}

	//endregion

	//region // ===== Item =========================================================================================================================//

	public void registerAuto(Item... items) {
		for (Item item : items) {
			registerAuto(item);
		}
	}

	public void registerAuto(Item item) {
		if (item instanceof IAutoReg provider) {
			String id = provider.getName();

			this.registerItem(item, id);
		}
	}

	/**
	 * Registers an item to the game. This will also set the unlocalized name, and creative tab
	 * if {@link #tab} has been set. The item will also be cached in {@link #items}.
	 *
	 * @param item The item to register.
	 * @param id   The id to register the item with.
	 */
	public void registerItem(@Nonnull Item item, @Nonnull String id) {

		item.setRegistryName(this.modID, id);
		item.setTranslationKey(this.modID + "." + id.toLowerCase().replace("_", ".").replaceAll("/", "."));
		this.items.add(item);

		if (this.tab != null) {
			item.setCreativeTab(this.tab);
		}

		if (GameUtils.isClient()) {
			if (item instanceof ICustomModel itemModel) {
				this.customModel.add(itemModel);
			} else if (!(item instanceof ItemBlock)) {
				this.registerClientModel(() ->
						ModelManager.registerInventoryModel(item)
				);
			}
			if (item instanceof ICustomMesh) this.customMeshes.add(item);
			if (item instanceof IColorfulItem) this.coloredItems.add(item);
		}
	}

	//endregion

	//region // ===== Potions ======================================================================================================================//

	public void registerPotion(@Nonnull Potion potion, @Nonnull String id) {

		potion.setRegistryName(this.modID, id);
		potion.setPotionName(this.modID + "." + id.toLowerCase().replace("_", "."));
		potion.setPotionName(this.modID + ".effect." + id);
		this.potions.add(potion);
	}

	//endregion

	//region // ===== Potion Types =================================================================================================================//

	public void registerPotionType(@Nonnull PotionType potionType, @Nonnull String id) {

		potionType.setRegistryName(this.modID, id);
		this.potionType.add(potionType);
	}

	//endregion

	//region // ===== Biome ========================================================================================================================//

	public void registerBiome(Biome biome, String id) {
		this.registerBiome(biome, id, new BiomeDictionary.Type[0]);
	}

	public void registerBiome(Biome biome, String id, BiomeDictionary.Type[] types) {

		biome.setRegistryName(this.modID, id);
		this.biomes.add(biome);

		if (types.length > 0) {
			BiomeDictionary.addTypes(biome, types);
		}


	}

	//endregion

	//region // ===== Sound ========================================================================================================================//

	/**
	 * Registers a new sound with the game. The sound must also exist in the sounds.json file.
	 *
	 * @param name The name of the sound file. No upper case chars!
	 */
	public void registerSound(String name) {

		final ResourceLocation id = new ResourceLocation(this.modID, name);
		final SoundEvent sound = new SoundEvent(id).setRegistryName(id);
		this.sounds.add(sound);
	}

	//endregion

	//region // ===== Entity =======================================================================================================================//

	/**
	 * Registers any sort of entity. Will not have a spawn egg.
	 *
	 * @param entClass The entity class.
	 * @param id       The string id for the entity.
	 * @return The entity that was registered.
	 */
	public <T extends Entity> EntityEntryBuilder<T> registerEntity(String id, Class<T> entClass) {

		final EntityEntryBuilder<T> builder = EntityEntryBuilder.create();
		builder.entity(entClass);

		registerEntity(id, builder);

		return builder;
	}

	public <E extends Entity> void registerEntity(String id, EntityEntryBuilder<E> builder) {
		final ResourceLocation entId = new ResourceLocation(this.modID, id);
		builder.id(entId, this.networkEntityIdSupplier.getAndIncrement());
		builder.name(this.modID + "." + id);

		this.entities.add(builder.build());
		this.entityIds.add(entId);
	}

	/**
	 * Registers any sort of entity. Will not have a spawn egg.
	 *
	 * @param entClass The entity class.
	 * @param id       The string id for the entity.
	 * @return The entity that was registered.
	 */
	public <T extends Entity> EntityEntryBuilder<T> registerMob(String id, Class<T> entClass, int primary, int seconday) {

		final EntityEntryBuilder<T> builder = this.registerEntity(id, entClass);

		builder.tracker(64, 1, true);
		builder.egg(primary, seconday);
		return builder;
	}

	//endregion

	//region // ===== Enchantment ==================================================================================================================//

	/**
	 * Registers an enchantment.
	 *
	 * @param enchant The enchantment to register.
	 * @param id      The ID of the enchantment.
	 * @return The enchantment that was registered.
	 */
	public Enchantment registerEnchantment(Enchantment enchant, String id) {

		enchant.setRegistryName(new ResourceLocation(this.modID, id));
		this.enchantments.add(enchant);
		return enchant;
	}

	//endregion

	//region // ===== Villager Profession ==========================================================================================================//


	//endregion

	//region // ===== Recipe =======================================================================================================================//

	/**
	 * Adds a shaped recipe to the game.
	 *
	 * @param id     The id of the recipe.
	 * @param output The output for the recipe.
	 * @param inputs The inputs. Pattern, then char followed by what it represents.
	 * @return The recipe registered.
	 */
	public IRecipe addShapedRecipe(String id, ItemStack output, Object... inputs) {

		return this.registerRecipe(id, new ShapedOreRecipe(null, output, inputs));
	}

	/**
	 * Adds a shapeless recipe to the game.
	 *
	 * @param id     The id of the recipe.
	 * @param output The output of the recipe.
	 * @param inputs The inputs for the recipe.
	 * @return The recipe registered.
	 */
	public IRecipe addShapelessRecipe(String id, ItemStack output, Object... inputs) {

		return this.registerRecipe(id, new ShapelessOreRecipe(null, output, inputs));
	}

	/**
	 * Adds a recipe to the game.
	 *
	 * @param id     The id of the recipe.
	 * @param recipe The recipe object.
	 * @return The registered registry object.
	 */
	public IRecipe registerRecipe(String id, IRecipe recipe) {

		recipe.setRegistryName(new ResourceLocation(this.modID, id));
		this.recipes.add(recipe);
		return recipe;
	}

	//endregion


	//region // ===== Loot Table ===================================================================================================================//


	/**
	 * Registers a loot table with the loot table list. This needs to be called before a loot
	 * table can be used.
	 *
	 * @param name The name of the loot table to use.
	 * @return A ResourceLocation pointing to the table.
	 */
	public ResourceLocation registerLootTable(String name) {

		return LootTableList.register(new ResourceLocation(this.modID, name));
	}

	//endregion


	//region // ===== Models =======================================================================================================================//


	@SideOnly(Side.CLIENT)
	public void registerClientModel(ICustomModel model) {
		this.customModel.add(model);
	}

	//endregion
}
