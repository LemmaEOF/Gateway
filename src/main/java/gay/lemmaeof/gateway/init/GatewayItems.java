package gay.lemmaeof.gateway.init;

import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.hooks.CustomToolMaterial;
import gay.lemmaeof.gateway.hooks.DynamicArmorMaterial;
import gay.lemmaeof.gateway.item.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class GatewayItems {
	public static final ArmorMaterial TRION_ARMOR = new DynamicArmorMaterial(1, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, Ingredient.EMPTY, 0f, 0f, new Identifier(Gateway.MODID, "strategist_shaded"));
	public static final ToolMaterial TRION_WEAPON = new CustomToolMaterial(1, 1, 6f, 2f, 2, () -> Ingredient.EMPTY);

	public static final Item TRIGGER_HOLDER = register(new TriggerHolderItem(defaultSettings().maxCount(1).rarity(Rarity.UNCOMMON)), "trigger_holder");
	public static final Item TRION_HELMET = register(new TrionArmorItem(TRION_ARMOR, EquipmentSlot.HEAD, triggerSettings()), "trion_helmet");
	public static final Item TRION_CHESTPLATE = register(new TrionArmorItem(TRION_ARMOR, EquipmentSlot.CHEST, triggerSettings()), "trion_chestplate");
	public static final Item TRION_LEGGINGS = register(new TrionArmorItem(TRION_ARMOR, EquipmentSlot.LEGS, triggerSettings()), "trion_leggings");
	public static final Item TRION_BOOTS = register(new TrionArmorItem(TRION_ARMOR, EquipmentSlot.FEET, triggerSettings()), "trion_boots");
	public static final Item DEBUG_50 = register(new TrionDebugItem(50, defaultSettings()), "debug_50");
	public static final Item DEBUG_200 = register(new TrionDebugItem(200, defaultSettings()), "debug_200");
	public static final Item DEBUG_1900 = register(new TrionDebugItem(1900, defaultSettings()), "debug_1900");
	public static final Item BAIL_OUT = register(new BailOutItem(triggerSettings()), "bail_out");
	public static final Item RAYGUST = register(new RaygustItem(TRION_WEAPON, 2, -2.4f, 0f, triggerSettings()), "raygust");
	public static final Item KOGETSU = register(new TrionSwordItem(TRION_WEAPON, 5, -2.8f, 1f, triggerSettings()), "kogetsu");
	public static final Item SCORPION = register(new TrionSwordItem(TRION_WEAPON, 3, -1.4f, -0.5f, triggerSettings()), "scorpion");
	public static final Item TRION_SHIELD = register(new TrionShieldItem(triggerSettings()), "trion_shield");
	public static final Item COIL = register(new CoilItem(new Item.Settings().group(Gateway.GATEWAY_GROUP)), "coil");
	public static final Item RECOVERY_ORDER = register(new Item(new Item.Settings().group(Gateway.GATEWAY_GROUP)), "recovery_order");
	public static final Item WATER_GUN = register(new WaterGunItem(new Item.Settings().group(Gateway.GATEWAY_GROUP)), "water_gun");

	public static void init() { }

	private static Item register(Item item, String name) {
		return Registry.register(Registry.ITEM, new Identifier(Gateway.MODID, name), item);
	}

	private static Item.Settings defaultSettings() {
		return new Item.Settings().group(Gateway.GATEWAY_GROUP);
	}

	private static Item.Settings triggerSettings() {
		return new Item.Settings().maxCount(1);
	}

}
