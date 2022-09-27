package gay.lemmaeof.gateway.init;

import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.loot.RandomCoilLootFunction;
import gay.lemmaeof.gateway.loot.RecoveryOrderLootCondition;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class GatewayMechanics {


	public static final TagKey<EntityType<?>> COIL_DROPPING = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Gateway.MODID, "coil_dropping"));

	public static final LootConditionType RECOVERY_ORDER_CONDITION = register("recovery_order",
			new LootConditionType(new RecoveryOrderLootCondition.Serializer()));

	public static final LootFunctionType RANDOM_COIL = register("random_coil",
			new LootFunctionType(new RandomCoilLootFunction.Serializer()));

	private static final List<Identifier> tableIds = new ArrayList<>();


	public static void init() {
		LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (tableIds.contains(id)) {
				LootPool.Builder builder  = new LootPool.Builder()
						.conditionally(RecoveryOrderLootCondition.INSTANCE)
						.with(ItemEntry.builder(GatewayItems.COIL).build())
						.apply(RandomCoilLootFunction.INSTANCE);
				tableBuilder.pool(builder);
			}
		}));
	}

	public static void prepareTableIds() {
		tableIds.clear();
		for (Holder<EntityType<?>> holder : Registry.ENTITY_TYPE.getTagOrEmpty(COIL_DROPPING)) {
			tableIds.add(holder.value().getLootTableId());
		}
	}

	private static LootConditionType register(String name, LootConditionType type) {
		return Registry.register(Registry.LOOT_CONDITION_TYPE, new Identifier(Gateway.MODID, name), type);
	}

	private static LootFunctionType register(String name, LootFunctionType type) {
		return Registry.register(Registry.LOOT_FUNCTION_TYPE, new Identifier(Gateway.MODID, name), type);
	}
}
