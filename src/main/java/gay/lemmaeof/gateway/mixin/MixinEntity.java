package gay.lemmaeof.gateway.mixin;

import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.api.TrionComponent;
import gay.lemmaeof.gateway.registry.GatewayComponents;
import gay.lemmaeof.gateway.registry.GatewayTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Entity.class)
public class MixinEntity {
	@Inject(method = "isInvisible", at = @At("HEAD"), cancellable = true)
	private void injectInvisibility(CallbackInfoReturnable<Boolean> info) {
		Optional<TrionComponent> compOpt = GatewayComponents.TRION_COMPONENT.maybeGet(this);
		if (compOpt.isPresent() && (Entity)(Object)this instanceof LivingEntity entity) {
			TrionComponent comp = compOpt.get();
			if (comp.isTriggerActive()
					&& entity.getStackInHand(Hand.MAIN_HAND).isEmpty()
					&& entity.getStackInHand(Hand.OFF_HAND).isEmpty()
					&& comp.getConfig().getEquippedTriggers().contains(GatewayTriggers.CHAMELEON)) info.setReturnValue(true);
		}
	}
}
