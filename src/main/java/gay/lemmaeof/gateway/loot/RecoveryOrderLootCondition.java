package gay.lemmaeof.gateway.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import gay.lemmaeof.gateway.init.GatewayItems;
import gay.lemmaeof.gateway.init.GatewayMechanics;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.JsonSerializer;

public class RecoveryOrderLootCondition implements LootCondition {
	public static final RecoveryOrderLootCondition INSTANCE = new RecoveryOrderLootCondition();

	private RecoveryOrderLootCondition() {}
	
	@Override
	public LootConditionType getType() {
		return GatewayMechanics.RECOVERY_ORDER_CONDITION;
	}

	@Override
	public boolean test(LootContext context) {
		if (!context.hasParameter(LootContextParameters.KILLER_ENTITY)) {
			return false;
		}
		Entity killer = context.get(LootContextParameters.KILLER_ENTITY);
		if (killer instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)killer;
			int invSize = player.getInventory().size();
			for (int i = 0; i < invSize; i++) {
				ItemStack stack = player.getInventory().getStack(i);
				if (stack.getItem() == GatewayItems.RECOVERY_ORDER) {
					//side effects are, surprisingly, safe
					if (!player.isCreative()) stack.decrement(1);
					return true;
				}
			}
		}
		return false;
	}

	public static class Serializer implements JsonSerializer<RecoveryOrderLootCondition> {
		public void toJson(JsonObject jsonObject, RecoveryOrderLootCondition condition,
						   JsonSerializationContext jsonSerializationContext) {
		}

		public RecoveryOrderLootCondition fromJson(JsonObject jsonObject,
												   JsonDeserializationContext jsonDeserializationContext) {
			return RecoveryOrderLootCondition.INSTANCE;
		}
	}
}
