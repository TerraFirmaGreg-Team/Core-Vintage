package su.terrafirmagreg.api.registry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.lib.LootBuilder;
import su.terrafirmagreg.api.model.ICustomModel;
import su.terrafirmagreg.api.model.ICustomStateMapper;
import su.terrafirmagreg.api.network.NetworkEntityIdSupplier;
import su.terrafirmagreg.api.spi.block.IColorfulBlock;
import su.terrafirmagreg.api.spi.item.IColorfulItem;
import su.terrafirmagreg.api.spi.item.ICustomMesh;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.api.util.ModelUtils;

import java.util.Collection;
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
	 * A list of all the keyBinding registered by the helper.
	 */
	private final NonNullList<KeyBinding> keyBinding = NonNullList.create();

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
	 * A list of all the oreDict registered here.
	 */
	private final List<IOreDict> oreDicts = NonNullList.create();

	/**
	 * The creative tab used by the mod. This can be null.
	 */
	private final CreativeTabs tab;

	/**
	 * The auto registry for the helper.
	 */
	private Registry registry;

	private NetworkEntityIdSupplier networkEntityIdSupplier;

	/**
	 * Constructs a new Registry. The modid for the helper is equal to that of the active
	 * mod container, and auto model registration is enabled.
	 *
	 * @param tab The tab for the registry helper.
	 */
	public RegistryManager(@Nullable CreativeTabs tab) {
		this.modID = Loader.instance().activeModContainer().getModId().replaceAll("_", ".");
		this.tab = tab;
	}


	/**
	 * Enables automatic registration for things like the event bus.
	 *
	 * @return The Registry, for convenience.
	 */
	public RegistryManager enableAutoRegistration() {

		this.registry = new Registry(this);
		MinecraftForge.EVENT_BUS.register(this.registry);
		return this;
	}

	/**
	 * Checks if the registry has automatic registration.
	 *
	 * @return Whether or not the helper has automatic registration.
	 */
	public boolean hasAutoRegistry() {
		return this.registry != null;
	}

	public void setNetworkEntityIdSupplier(NetworkEntityIdSupplier supplier) {
		if (this.networkEntityIdSupplier != null)
			throw new IllegalStateException("Network entity id supplier has already been set");

		this.networkEntityIdSupplier = supplier;
	}


	//region // ===== Block ========================================================================================================================//

	public <T extends Block> void registerBlocks(Collection<T> collection) {
		for (var item : collection) {
			if (item instanceof IAutoReg provider) {
				this.registerBlock(item, provider.getItemBlock(), provider.getName());
			}
		}
	}

	public <T extends Block & IAutoReg> T registerBlock(T block) {
		return this.registerBlock(block, block.getItemBlock(), block.getName());
	}

	/**
	 * Registers a block to the game. This will also set the unlocalized name, and creative tab
	 * if {@link #tab} has been set. The block will also be cached in {@link #blocks}.
	 *
	 * @param block     The block to register.
	 * @param itemBlock The ItemBlock for the block.
	 * @param name      The name to register the block with.
	 */
	public <T extends Block> T registerBlock(@NotNull T block, @Nullable ItemBlock itemBlock, @NotNull String name) {

		block.setRegistryName(this.modID, name);
		block.setTranslationKey(this.modID + "." + name.toLowerCase().replace("_", ".").replaceAll("/", "."));
		if (this.tab != null) block.setCreativeTab(this.tab);

		this.blocks.add(block);

		if (itemBlock != null) {
			this.registerItem(itemBlock, name);
			if (block instanceof IOreDict oreDict) this.oreDicts.add(oreDict);
		}
		if (block instanceof ITEBlock te) this.tileProviders.add(te);

		if (GameUtils.isClient()) {
			if (block instanceof ICustomStateMapper state) this.customStateMapper.add(state);
			if (block instanceof ICustomModel blockModel) this.customModel.add(blockModel);
			else {
				this.registerClientModel(() ->
						ModelUtils.registerBlockInventoryModel(block)
				);
			}
			if (block instanceof IColorfulBlock) this.coloredBlocks.add(block);
		}
		return block;
	}

	//endregion

	//region // ===== Item =========================================================================================================================//

	public <T extends Item> void registerItems(Collection<T> collection) {
		for (var item : collection) {
			if (item instanceof IAutoReg provider) {
				this.registerItem(item, provider.getName());
			}
		}
	}

	public <T extends Item & IAutoReg> T registerItem(T item) {
		return this.registerItem(item, item.getName());
	}

	/**
	 * Registers an item to the game. This will also set the unlocalized name, and creative tab
	 * if {@link #tab} has been set. The item will also be cached in {@link #items}.
	 *
	 * @param item The item to register.
	 * @param name The name to register the item with.
	 */
	public <T extends Item> T registerItem(@NotNull T item, @NotNull String name) {

		item.setRegistryName(this.modID, name);
		item.setTranslationKey(this.modID + "." + name.toLowerCase().replace("_", ".").replaceAll("/", "."));
		this.items.add(item);

		if (this.tab != null)
			item.setCreativeTab(this.tab);

		if (item instanceof IOreDict oreDict)
			this.oreDicts.add(oreDict);

		if (GameUtils.isClient()) {
			if (item instanceof ICustomModel itemModel) {
				this.customModel.add(itemModel);
			} else if (!(item instanceof ItemBlock)) {
				this.registerClientModel(() ->
						ModelUtils.registerInventoryModel(item)
				);
			}
			if (item instanceof ICustomMesh) this.customMeshes.add(item);
			if (item instanceof IColorfulItem) this.coloredItems.add(item);
		}
		return item;
	}

	//endregion

	//region // ===== Potions ======================================================================================================================//


	public Potion registerPotion(@NotNull String name, @NotNull Potion potion, IAttribute attribute, String uniqueId, double ammount, int operation) {

		potion.registerPotionAttributeModifier(attribute, uniqueId, ammount, operation);
		return this.registerPotion(name, potion);
	}

	public Potion registerPotion(@NotNull String name, @NotNull Potion potion) {
		potion.setRegistryName(this.modID, name);
		potion.setPotionName(this.modID + ".effect." + name.toLowerCase().replace("_", "."));
		this.potions.add(potion);
		return potion;
	}

	//endregion

	//region // ===== Potion Types =================================================================================================================//

	public PotionType registerPotionType(@NotNull String name, @NotNull Potion potion, int duration) {

		var potionType = new PotionType(new PotionEffect(potion, duration));
		return registerPotionType(name, potionType);
	}

	public PotionType registerPotionType(@NotNull String name, @NotNull PotionType potionType) {

		potionType.setRegistryName(this.modID, name);
		this.potionType.add(potionType);
		return potionType;
	}

	//endregion

	//region // ===== Biome ========================================================================================================================//

	public Biome registerBiome(Biome biome, String name) {
		return this.registerBiome(biome, name, new BiomeDictionary.Type[0]);
	}

	public Biome registerBiome(Biome biome, String name, BiomeDictionary.Type[] types) {

		biome.setRegistryName(this.modID, name);
		this.biomes.add(biome);

		if (types.length > 0) {
			BiomeDictionary.addTypes(biome, types);
		}

		return biome;
	}

	//endregion

	//region // ===== Sound ========================================================================================================================//

	/**
	 * Registers a new sound with the game. The sound must also exist in the sounds.json file.
	 *
	 * @param name The name of the sound file. No upper case chars!
	 */
	public SoundEvent registerSound(String name) {

		final ResourceLocation soundNameIn = new ResourceLocation(this.modID, name);
		final SoundEvent sound = new SoundEvent(soundNameIn).setRegistryName(soundNameIn);
		this.sounds.add(sound);

		return sound;
	}

	//endregion

	//region // ===== KeyBinding ===================================================================================================================//

	public KeyBinding registerKeyBinding(String description, int keyCode) {

		var prefix = "key." + modID + ".";
		final KeyBinding key = new KeyBinding(prefix + description, keyCode, prefix + "categories");
		ClientRegistry.registerKeyBinding(key);

		this.keyBinding.add(key);
		return key;
	}

	//endregion
	//region // ===== Entity =======================================================================================================================//

	/**
	 * Registers any sort of entity. Will not have a spawn egg.
	 *
	 * @param entClass The entity class.
	 * @param name     The string name for the entity.
	 * @return The entity that was registered.
	 */
	public <T extends Entity> EntityEntryBuilder<T> registerEntity(String name, Class<T> entClass) {

		final EntityEntryBuilder<T> builder = EntityEntryBuilder.create();
		builder.entity(entClass);

		registerEntity(name, builder);

		return builder;
	}

	public <E extends Entity> void registerEntity(String name, EntityEntryBuilder<E> builder) {
		final ResourceLocation entId = new ResourceLocation(this.modID, name);
		builder.id(entId, this.networkEntityIdSupplier.getAndIncrement());
		builder.name(this.modID + "." + name);

		this.entities.add(builder.build());
		this.entityIds.add(entId);
	}

	/**
	 * Registers any sort of entity. Will have a spawn egg.
	 *
	 * @param entClass The entity class.
	 * @param name     The string name for the entity.
	 * @return The entity that was registered.
	 */
	public <T extends Entity> EntityEntryBuilder<T> registerEntity(String name, Class<T> entClass, int primary, int seconday) {

		final EntityEntryBuilder<T> builder = EntityEntryBuilder.create();
		builder.entity(entClass);
		builder.tracker(64, 1, true);
		builder.egg(primary, seconday);

		this.registerEntity(name, builder);
		return builder;
	}

	//endregion

	//region // ===== Enchantment ==================================================================================================================//

	/**
	 * Registers an enchantment.
	 *
	 * @param enchant The enchantment to register.
	 * @param name    The ID of the enchantment.
	 * @return The enchantment that was registered.
	 */
	public Enchantment registerEnchantment(Enchantment enchant, String name) {

		enchant.setRegistryName(new ResourceLocation(this.modID, name));
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
	 * @param name   The name of the recipe.
	 * @param output The output for the recipe.
	 * @param inputs The inputs. Pattern, then char followed by what it represents.
	 * @return The recipe registered.
	 */
	public IRecipe addShapedRecipe(String name, ItemStack output, Object... inputs) {

		return this.registerRecipe(name, new ShapedOreRecipe(null, output, inputs));
	}

	/**
	 * Adds a shapeless recipe to the game.
	 *
	 * @param name   The name of the recipe.
	 * @param output The output of the recipe.
	 * @param inputs The inputs for the recipe.
	 * @return The recipe registered.
	 */
	public IRecipe addShapelessRecipe(String name, ItemStack output, Object... inputs) {

		return this.registerRecipe(name, new ShapelessOreRecipe(null, output, inputs));
	}

	/**
	 * Adds a recipe to the game.
	 *
	 * @param name   The name of the recipe.
	 * @param recipe The recipe object.
	 * @return The registered registry object.
	 */
	public IRecipe registerRecipe(String name, IRecipe recipe) {

		recipe.setRegistryName(new ResourceLocation(this.modID, name));
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

	public <T extends LootFunction> void registerLootFunction(LootFunction.Serializer<? extends T> serializer) {

		LootFunctionManager.registerFunction(serializer);
	}

	/**
	 * Creates a new loot entry that will be added to the loot pools when a world is loaded.
	 *
	 * @param location The loot table to add the loot to. You can use
	 *                 {@link LootTableList} for convenience.
	 * @param name     The name of the entry being added. This will be prefixed with {@link #modID}
	 *                 .
	 * @param pool     The name of the pool to add the entry to. This pool must already exist.
	 * @param weight   The weight of the entry.
	 * @param item     The item to add.
	 * @param meta     The metadata for the loot.
	 * @param amount   The amount of the item to set.
	 * @return A builder object. It can be used to fine tune the loot entry.
	 */
	public LootBuilder addLoot(ResourceLocation location, String name, String pool, int weight, Item item, int meta, int amount) {

		return this.addLoot(location, name, pool, weight, item, meta, amount, amount);
	}

	/**
	 * Creates a new loot entry that will be added to the loot pools when a world is loaded.
	 *
	 * @param location The loot table to add the loot to. You can use
	 *                 {@link LootTableList} for convenience.
	 * @param name     The name of the entry being added. This will be prefixed with {@link #modID}
	 *                 .
	 * @param pool     The name of the pool to add the entry to. This pool must already exist.
	 * @param weight   The weight of the entry.
	 * @param item     The item to add.
	 * @param meta     The metadata for the loot.
	 * @param min      The smallest item size.
	 * @param max      The largest item size.
	 * @return A builder object. It can be used to fine tune the loot entry.
	 */
	public LootBuilder addLoot(ResourceLocation location, String name, String pool, int weight, Item item, int meta, int min, int max) {

		final LootBuilder loot = this.addLoot(location, name, pool, weight, item, meta);
		loot.addFunction(new SetCount(new LootCondition[0], new RandomValueRange(min, max)));
		return loot;
	}

	/**
	 * Creates a new loot entry that will be added to the loot pools when a world is loaded.
	 *
	 * @param location The loot table to add the loot to. You can use
	 *                 {@link LootTableList} for convenience.
	 * @param name     The name of the entry being added. This will be prefixed with {@link #modID}
	 *                 .
	 * @param pool     The name of the pool to add the entry to. This pool must already exist.
	 * @param weight   The weight of the entry.
	 * @param item     The item to add.
	 * @param meta     The metadata for the loot.
	 * @return A builder object. It can be used to fine tune the loot entry.
	 */
	public LootBuilder addLoot(ResourceLocation location, String name, String pool, int weight, Item item, int meta) {

		final LootBuilder loot = this.addLoot(location, name, pool, weight, item);
		loot.addFunction(new SetMetadata(new LootCondition[0], new RandomValueRange(meta, meta)));
		return loot;
	}

	/**
	 * Creates a new loot entry that will be added to the loot pools when a world is loaded.
	 *
	 * @param location The loot table to add the loot to. You can use
	 *                 {@link LootTableList} for convenience.
	 * @param name     The name of the entry being added. This will be prefixed with {@link #modID}
	 *                 .
	 * @param pool     The name of the pool to add the entry to. This pool must already exist.
	 * @param weight   The weight of the entry.
	 * @param item     The item to add.
	 * @return A builder object. It can be used to fine tune the loot entry.
	 */
	public LootBuilder addLoot(ResourceLocation location, String name, String pool, int weight, Item item) {

		return this.addLoot(location, new LootBuilder(this.modID + ":" + name, pool, weight, item));
	}

	/**
	 * Creates a new loot entry that will be added to the loot pools when a world is loaded.
	 *
	 * @param location   The loot table to add the loot to. You can use
	 *                   {@link LootTableList} for convenience.
	 * @param name       The name of the entry being added. This will be prefixed with {@link #modID}
	 *                   .
	 * @param pool       The name of the pool to add the entry to. This pool must already exist.
	 * @param weight     The weight of the entry.
	 * @param quality    The quality of the entry. Quality is an optional value which modifies the
	 *                   weight of an entry based on the player's luck level. totalWeight = weight +
	 *                   (quality * luck)
	 * @param item       The item to add.
	 * @param conditions A list of loot conditions.
	 * @param functions  A list of loot functions.
	 * @return A builder object. It can be used to fine tune the loot entry.
	 */
	public LootBuilder addLoot(ResourceLocation location, String name, String pool, int weight, int quality, Item item, List<LootCondition> conditions, List<LootFunction> functions) {

		return this.addLoot(location, new LootBuilder(this.modID + ":" + name, pool, weight, quality, item, conditions, functions));
	}

	/**
	 * Creates a new loot entry that will be added to the loot pools when a world is loaded.
	 *
	 * @param location The loot table to add the loot to. You can use
	 *                 {@link LootTableList} for convenience.
	 * @param builder  The loot builder to add.
	 * @return A builder object. It can be used to fine tune the loot entry.
	 */
	public LootBuilder addLoot(ResourceLocation location, LootBuilder builder) {

		this.lootTableEntries.put(location, builder);
		return builder;
	}

	//endregion


	//region // ===== Models =======================================================================================================================//


	@SideOnly(Side.CLIENT)
	public void registerClientModel(ICustomModel model) {
		this.customModel.add(model);

	}

	//endregion
}
