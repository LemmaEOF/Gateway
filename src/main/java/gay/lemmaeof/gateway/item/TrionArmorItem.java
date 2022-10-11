package gay.lemmaeof.gateway.item;

import gay.lemmaeof.gateway.api.TriggerConfig;
import gay.lemmaeof.gateway.api.TriggerShifter;
import gay.lemmaeof.gateway.init.GatewayItems;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class TrionArmorItem extends DyeableArmorItem implements TriggerShifter {
	public TrionArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
		super(material, slot, settings);
	}

	@Override
	public ItemStack equip(ItemStack previous, TriggerConfig config) {
		int color = config.getColor(this.slot);
		ItemStack equipped = TriggerShifter.super.equip(previous, config);
		NbtCompound tag = equipped.getOrCreateNbt();
		tag.putBoolean("Unbreakable", true); //so armor doesn't get damaged, since ArmorItem overrides that
		equipped.addEnchantment(Enchantments.BINDING_CURSE, 1); //since equipment slots override canRemove
		tag.putInt("HideFlags", 0b00000101); //hide curse of binding, hide unbreakable
		NbtCompound displayTag = equipped.getOrCreateSubNbt("display");
		displayTag.putString("Texture", config.getTextureId(slot).toString());
		this.setColor(equipped, color);
		return equipped;
	}

	public static ItemStack getTrionStack(EquipmentSlot slot, ItemStack previous, TriggerConfig config) {
		TriggerShifter item;
		switch(slot) {
			case HEAD:
				item = GatewayItems.TRION_HELMET;
				break;
			case CHEST:
				item = GatewayItems.TRION_CHESTPLATE;
				break;
			case LEGS:
				item = GatewayItems.TRION_LEGGINGS;
				break;
			case FEET:
				item = GatewayItems.TRION_BOOTS;
				break;
			default:
				return previous;
		}
		return item.equip(previous, config);
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		entityInventoryTick(stack, world, entity, slot, selected);
	}
}
