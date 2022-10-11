package gay.lemmaeof.gateway.mixin;

import gay.lemmaeof.gateway.api.TrionComponent;
import gay.lemmaeof.gateway.hooks.Splashable;
import gay.lemmaeof.gateway.init.GatewayComponents;
import gay.lemmaeof.gateway.init.GatewayTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Entity.class)
public class MixinEntity implements Splashable {
	@Shadow public World world;
	@Shadow private Vec3d pos;
	private int gateway$splashTicks = 0;

	@Inject(method = "tick", at = @At("HEAD"))
	private void tickSplash(CallbackInfo info) {
		if (gateway$splashTicks > 0) gateway$splashTicks--;
		//TODO: make something my own and not just Fabrication's
		if ((Object)this instanceof LivingEntity living && !world.isClient && gateway$splashTicks > 0) { //not always false don't worry mixin can handle it
			if (world.random.nextInt(20) == 0) {
				world.playSound(null, pos.x, pos.y, pos.z, SoundEvents.ENTITY_DROWNED_SWIM, living.getSoundCategory(), 0.1f, 2f);
			}
			if (gateway$splashTicks%4 == 0) {
				Box box = living.getBoundingBox();
				((ServerWorld)world).spawnParticles(ParticleTypes.RAIN, pos.x, pos.y+(box.getYLength()/2), pos.z, 1, box.getXLength()/3, box.getYLength()/4, box.getZLength()/3, 0);
			}
		}
	}

	@Inject(method = "isInvisible", at = @At("HEAD"), cancellable = true)
	private void injectInvisibility(CallbackInfoReturnable<Boolean> info) {
		Optional<TrionComponent> compOpt = GatewayComponents.TRION.maybeGet(this);
		if (compOpt.isPresent() && (Entity)(Object)this instanceof LivingEntity entity) {
			TrionComponent comp = compOpt.get();
			if (comp.isTriggerActive()
					&& entity.getStackInHand(Hand.MAIN_HAND).isEmpty()
					&& entity.getStackInHand(Hand.OFF_HAND).isEmpty()
					&& comp.getConfig().getEquippedTriggers().contains(GatewayTriggers.CHAMELEON)) info.setReturnValue(true);
		}
	}

	@Inject(method = "isWet", at = @At("HEAD"), cancellable = true)
	private void injectSplash(CallbackInfoReturnable<Boolean> info) {
		if (gateway$splashTicks > 0) info.setReturnValue(true);
	}

	@Override
	public void splash() {
		gateway$splashTicks = 100;
	}
}
