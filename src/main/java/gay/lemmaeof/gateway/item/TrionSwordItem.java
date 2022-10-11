package gay.lemmaeof.gateway.item;

import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import gay.lemmaeof.gateway.api.TriggerConfig;
import gay.lemmaeof.gateway.api.TriggerShifter;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * Trion sword item which has an optional range boost.
 */
public class TrionSwordItem extends SwordItem implements TriggerShifter {
	private float rangeBoost;
	private static final UUID REACH_UUID = UUID.fromString("ad6b3b12-6647-49a2-bff4-b234ea3ea532");
	private static final UUID ATTACK_RANGE_UUID = UUID.fromString("43e6b5f0-2a29-4d9f-95ae-bacf99ee9320");

	public TrionSwordItem(ToolMaterial material, int attackDamage, float attackSpeed, float rangeBoost, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
		this.rangeBoost = rangeBoost;
	}

	@Override
	public ItemStack equip(ItemStack previous, TriggerConfig config) {
		ItemStack equipped = TriggerShifter.super.equip(previous, config);
		NbtCompound tag = equipped.getOrCreateNbt();
		tag.putBoolean("Unbreakable", true); //so trion swords don't get damaged, since ToolItem overrides that setting
		tag.putInt("HideFlags", 0b00000001); // hide unbreakable
		return equipped;
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		Multimap<EntityAttribute, EntityAttributeModifier> ret = super.getAttributeModifiers(slot);
		if (slot == EquipmentSlot.MAINHAND && rangeBoost != 0) {
			ret.put(ReachEntityAttributes.REACH, new EntityAttributeModifier(REACH_UUID, "Reach modifier", rangeBoost, EntityAttributeModifier.Operation.ADDITION));
			ret.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(ATTACK_RANGE_UUID, "Attack range modifier", rangeBoost, EntityAttributeModifier.Operation.ADDITION));
		}
		return ret;
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		TriggerShifter.super.entityInventoryTick(stack, world, entity, slot, selected);
	}
}
