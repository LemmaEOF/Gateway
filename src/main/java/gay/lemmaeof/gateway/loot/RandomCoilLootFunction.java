package gay.lemmaeof.gateway.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import gay.lemmaeof.gateway.api.Coil;
import gay.lemmaeof.gateway.api.CoilHolderComponent;
import gay.lemmaeof.gateway.api.CoilType;
import gay.lemmaeof.gateway.impl.DirectCoilComponent;
import gay.lemmaeof.gateway.init.GatewayComponents;
import gay.lemmaeof.gateway.init.GatewayMechanics;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.util.JsonSerializer;

public class RandomCoilLootFunction implements LootFunction {
	public static final RandomCoilLootFunction INSTANCE = new RandomCoilLootFunction();

	private RandomCoilLootFunction() {}

	@Override
	public LootFunctionType getType() {
		return GatewayMechanics.RANDOM_COIL;
	}

	@Override
	public ItemStack apply(ItemStack stack, LootContext context) {
		if (GatewayComponents.COIL_HOLDER.isProvidedBy(stack)) {
			CoilHolderComponent component = GatewayComponents.COIL_HOLDER.get(stack);
			if (component instanceof DirectCoilComponent comp) {
				//only legal or illegal coils from recovery orders
				if (context.getRandom().nextInt(4) == 0) {
					comp.setPower(context.getRandom().nextInt(20) + 10);
					comp.setStability(100);
					comp.setType(CoilType.LEGAL);
				} else {
					comp.setPower(context.getRandom().nextInt(20) + 60);
					comp.setStability(context.getRandom().nextInt(15) + (85 - comp.getPower() / 5));
					comp.setType(CoilType.ILLEGAL);
				}
			}
		}
		return stack;
	}

	public static class Serializer implements JsonSerializer<RandomCoilLootFunction> {
		public void toJson(JsonObject jsonObject, RandomCoilLootFunction function, JsonSerializationContext jsonSerializationContext) {
		}

		public RandomCoilLootFunction fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			return RandomCoilLootFunction.INSTANCE;
		}
	}
}
