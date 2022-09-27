package gay.lemmaeof.gateway.mixin;

import gay.lemmaeof.gateway.api.TrionComponent;
import gay.lemmaeof.gateway.combat.TrionDamageSource;
import gay.lemmaeof.gateway.init.GatewayComponents;
import gay.lemmaeof.gateway.init.GatewayEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageSource.class)
public class MixinDamageSource {

	@Inject(method = "player", at = @At("HEAD"), cancellable = true)
	private static void injectTrionDamageSource(PlayerEntity attacker, CallbackInfoReturnable<DamageSource> info) {
		TrionComponent comp = GatewayComponents.TRION.get(attacker);
		if (comp.isTriggerActive()) {
			info.setReturnValue(new TrionDamageSource("trion", attacker));
		}
	}

	@Inject(method = "arrow", at = @At("HEAD"), cancellable = true)
	private static void injectTrionDamageSource(PersistentProjectileEntity projectile, Entity attacker, CallbackInfoReturnable<DamageSource> info) {
		if (projectile.getType() == GatewayEntities.TRION_PROJECTILE) {
			info.setReturnValue(new TrionDamageSource("trion", attacker)); //TODO: trion projectile damage source?
		}
	}
}
