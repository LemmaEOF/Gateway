package gay.lemmaeof.gateway.hooks;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class DynamicArmorMaterial implements ArmorMaterial {
	private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};

	private int durabilityMultiplier;
	private int[] protectionAmounts;
	private int enchantability;
	private SoundEvent equipSound;
	private Ingredient repairIngredient;
	private float toughness;
	private float knockbackResistance;
	private Identifier defaultId;

	public DynamicArmorMaterial(int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, Ingredient repairIngredient, float toughness, float knockbackResistance, Identifier defaultId) {
		this.durabilityMultiplier = durabilityMultiplier;
		this.protectionAmounts = protectionAmounts;
		this.enchantability = enchantability;
		this.equipSound = equipSound;
		this.repairIngredient = repairIngredient;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.defaultId = defaultId;
	}

	@Override
	public int getDurability(EquipmentSlot slot) {
		return BASE_DURABILITY[slot.getEntitySlotId()] * this.durabilityMultiplier;
	}

	@Override
	public int getProtectionAmount(EquipmentSlot slot) {
		return this.protectionAmounts[slot.getEntitySlotId()];
	}

	@Override
	public int getEnchantability() {
		return enchantability;
	}

	@Override
	public SoundEvent getEquipSound() {
		return equipSound;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return repairIngredient;
	}

	@Override
	public String getName() {
		return defaultId.getPath();
	}

	@Override
	public float getToughness() {
		return toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return knockbackResistance;
	}

	public Identifier getDefaultId() {
		return defaultId;
	}

	public Identifier getId(ItemStack stack) {
		if (stack.getOrCreateSubNbt("display").contains("Texture", NbtType.STRING)) {
			return new Identifier(stack.getOrCreateSubNbt("display").getString("Texture"));
		} else {
			return getDefaultId();
		}
	}
}
