package gay.lemmaeof.gateway.client.model;

import gay.lemmaeof.gateway.init.GatewayComponents;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SocketedModelPredicate implements UnclampedModelPredicateProvider {
	public static final SocketedModelPredicate INSTANCE = new SocketedModelPredicate();

	@Override
	public float unclampedCall(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity holder, int seed) {
		return GatewayComponents.COIL_HOLDER.get(stack).hasCoil()? 1 : 0;
	}
}
