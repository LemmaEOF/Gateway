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

	public static final TriggerHolderItem TRIGGER_HOLDER = new TriggerHolderItem(defaultSettings().maxCount(1).rarity(Rarity.UNCOMMON));
	public static final TrionArmorItem TRION_HELMET = new TrionArmorItem(TRION_ARMOR, EquipmentSlot.HEAD, triggerSettings());
	public static final TrionArmorItem TRION_CHESTPLATE = new TrionArmorItem(TRION_ARMOR, EquipmentSlot.CHEST, triggerSettings());
	public static final TrionArmorItem TRION_LEGGINGS = new TrionArmorItem(TRION_ARMOR, EquipmentSlot.LEGS, triggerSettings());
	public static final TrionArmorItem TRION_BOOTS = new TrionArmorItem(TRION_ARMOR, EquipmentSlot.FEET, triggerSettings());
	public static final TrionDebugItem DEBUG_50 = new TrionDebugItem(50, defaultSettings());
	public static final TrionDebugItem DEBUG_200 = new TrionDebugItem(200, defaultSettings());
	public static final TrionDebugItem DEBUG_1900 = new TrionDebugItem(1900, defaultSettings());
	public static final BailOutItem BAIL_OUT = new BailOutItem(triggerSettings());
	public static final RaygustItem RAYGUST = new RaygustItem(TRION_WEAPON, 2, -2.4f, 0f, triggerSettings());
	public static final TrionSwordItem KOGETSU = new TrionSwordItem(TRION_WEAPON, 5, -2.8f, 1f, triggerSettings());
	public static final TrionSwordItem SCORPION = new TrionSwordItem(TRION_WEAPON, 3, -1.4f, -0.5f, triggerSettings());
	public static final TrionShieldItem TRION_SHIELD = new TrionShieldItem(triggerSettings());
	public static final CoilItem COIL = new CoilItem(new Item.Settings().group(Gateway.GATEWAY_GROUP));
	public static final Item RECOVERY_ORDER = new Item(new Item.Settings().group(Gateway.GATEWAY_GROUP));
	public static final WaterGunItem WATER_GUN = new WaterGunItem(new Item.Settings().group(Gateway.GATEWAY_GROUP));

	public static void init() {
		Gateway.AUTOREG.autoRegister(Registry.ITEM, GatewayItems.class, Item.class);
	}

	private static Item.Settings defaultSettings() {
		return new Item.Settings().group(Gateway.GATEWAY_GROUP);
	}

	private static Item.Settings triggerSettings() {
		return new Item.Settings().maxCount(1);
	}

}
