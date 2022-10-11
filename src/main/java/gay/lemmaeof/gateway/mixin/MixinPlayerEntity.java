package gay.lemmaeof.gateway.mixin;

import gay.lemmaeof.gateway.api.TrionComponent;
import gay.lemmaeof.gateway.hooks.CustomDamageSource;
import gay.lemmaeof.gateway.api.TrionShield;
import gay.lemmaeof.gateway.init.GatewayComponents;
import gay.lemmaeof.gateway.init.GatewayParticles;
import gay.lemmaeof.gateway.init.GatewayStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
	@Shadow public abstract boolean isCreative();

	@Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

	@Shadow public abstract ItemCooldownManager getItemCooldownManager();

	protected MixinPlayerEntity(EntityType<? extends LivingEntity> type, World world) {
		super(type, world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void tickTrion(CallbackInfo info) {
		TrionComponent comp = GatewayComponents.TRION.get(this);
		if (!world.isClient) comp.tick();
	}

	//TODO: actually make an entity damage event
	@Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;dropShoulderEntities()V"), cancellable = true)
	private void doTrionDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
		//trion won't protect you against the void, unblockable+piercing damage, or magic
		if ((source.isUnblockable() && source.bypassesArmor()) || source.isOutOfWorld() || source.isMagic()) return;
		TrionComponent comp = GatewayComponents.TRION.get(this);
		//TODO: better way to fix so virtual combat can't be used to cheese PvE?
		if (hasStatusEffect(GatewayStatusEffects.VIRTUAL_COMBAT) && !(source instanceof CustomDamageSource custom && custom.isTrion())) return;

		if (comp.isTriggerActive()) {
			if (amount > 0.0F && blockedByShield(source)) {
				damageShield(amount);
				if (!source.isProjectile()) {
					Entity entity = source.getSource();
					if (entity instanceof LivingEntity) {
						takeShieldHit((LivingEntity) entity);
						this.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 0.8f, 0.8F + world.random.nextFloat() * 0.4F);
					}
				}
				//TODO: blocked-by-shield stat increase?
				info.setReturnValue(false);
				return;
			}
			int trionCost = source.bypassesArmor() || (source instanceof CustomDamageSource custom && custom.isTrion()) ? (int) Math.ceil(amount * 4) : (int) Math.ceil(amount * 2.5); //TODO: rebalance?
			comp.setTrion(comp.getTrion() - trionCost);
			if (!world.isClient) {
				((ServerWorld) world).spawnParticles(GatewayParticles.TRION_DAMAGE, getX(), getY() + 1.25f, getZ(), trionCost * 2, 0.0F, 0.0F, 0.0F, 0.25F);
			}
			info.setReturnValue(super.damage(source, 0f));
		}
	}

	@Inject(method = "attack", at = @At("HEAD"), cancellable = true)
	private void doTrionAttack(Entity target, CallbackInfo info) {
		if (isCreative()) return;
		TrionComponent comp = GatewayComponents.TRION.get(this);
		if (comp.isTriggerActive()
				&& hasStatusEffect(GatewayStatusEffects.VIRTUAL_COMBAT)
				&& target instanceof LivingEntity
				&& !(target instanceof PlayerEntity)) {
			//TODO: check if using trion weapon?
			info.cancel();
		}
	}

	@Inject(method = "damageShield", at = @At("HEAD"), cancellable = true)
	private void damageTrionShield(float amount, CallbackInfo info) {
		if (amount > 1.0F && activeItemStack.getItem() instanceof TrionShield) {
			PlayerEntity self = (PlayerEntity) (Object) this;
			TrionShield shield = (TrionShield) activeItemStack.getItem();
			int damage = 1 + MathHelper.floor(amount);
			shield.damageShield(self, activeItemStack, damage);
			if (shield.getShieldDamage(activeItemStack) == shield.getMaxShieldDamage(activeItemStack)) {
				playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + world.random.nextFloat() * 0.4F);
				getItemCooldownManager().set(activeItemStack.getItem(), shield.getCooldownTime(self, activeItemStack));
				clearActiveItem();
				world.sendEntityStatus(self, (byte)30);
			}
		}
		info.cancel();
	}

	@Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
	private void injectTrionSound(DamageSource source, CallbackInfoReturnable<SoundEvent> info) {
		TrionComponent comp = GatewayComponents.TRION.get(this);
		if (comp.isTriggerActive()) {
			//TODO: trion damage sound effect here
		}
	}

}
