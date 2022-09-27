package gay.lemmaeof.gateway.api;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface TrionShield {
	default int getShieldDamage(ItemStack stack) {
		if (stack.getOrCreateNbt().contains("ShieldDamage", NbtType.INT)) {
			return stack.getOrCreateNbt().getInt("ShieldDamage");
		}
		return 0;
	}

	int getMaxShieldDamage(ItemStack stack);

	default void setShieldDamage(ItemStack stack, int value) {
		stack.getOrCreateNbt().putInt("ShieldDamage", Math.min(value, getMaxShieldDamage(stack)));
	}

	default void damageShield(PlayerEntity wielder, ItemStack stack, int amount) {
		setShieldDamage(stack, getShieldDamage(stack) + amount);
	}

	void tickShield(PlayerEntity wielder, ItemStack stack);

	int getCooldownTime(PlayerEntity wielder, ItemStack stack);
}
