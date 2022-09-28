package gay.lemmaeof.gateway.init;

import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.loot.RandomCoilLootFunction;
import gay.lemmaeof.gateway.loot.RecoveryOrderLootCondition;
import gay.lemmaeof.gateway.mixin.LootTableBuilderAccessor;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GatewayMechanics {


	public static final TagKey<EntityType<?>> COIL_DROPPING = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Gateway.MODID, "coil_dropping"));

	public static final LootConditionType RECOVERY_ORDER_CONDITION = register("recovery_order",
			new LootConditionType(new RecoveryOrderLootCondition.Serializer()));

	public static final LootFunctionType RANDOM_COIL = register("random_coil",
			new LootFunctionType(new RandomCoilLootFunction.Serializer()));

	public static void init() {
		LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (((LootTableBuilderAccessor) tableBuilder).getType() == LootContextTypes.ENTITY) {
				LootPool.Builder builder  = new LootPool.Builder()
						.conditionally(EntityPropertiesLootCondition.builder(
								LootContext.EntityTarget.THIS, new EntityPredicate.Builder().m_fydefeme(COIL_DROPPING))
						)
						.conditionally(RandomChanceWithLootingLootCondition.builder(0.3f, 0.1f))
						.conditionally(RecoveryOrderLootCondition.INSTANCE)
						//tags don't load before loot tables anymore so we have to check this at roll time
						.with(ItemEntry.builder(GatewayItems.COIL).build())
						.apply(RandomCoilLootFunction.INSTANCE);
				tableBuilder.pool(builder);
			}
		}));
	}

	private static LootConditionType register(String name, LootConditionType type) {
		return Registry.register(Registry.LOOT_CONDITION_TYPE, new Identifier(Gateway.MODID, name), type);
	}

	private static LootFunctionType register(String name, LootFunctionType type) {
		return Registry.register(Registry.LOOT_FUNCTION_TYPE, new Identifier(Gateway.MODID, name), type);
	}
}
