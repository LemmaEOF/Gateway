package gay.lemmaeof.gateway.item;

import com.unascribed.lib39.recoil.api.DirectClickItem;
import net.minecraft.command.EntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class WaterGunItem extends Item implements DirectClickItem {
	public WaterGunItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult onDirectAttack(PlayerEntity player, Hand hand) {
		if (player.world.isClient) return ActionResult.SUCCESS;
		Vec3d view = player.getRotationVec(1);
		Vec3d eye = player.getEyePos();
		Vec3d limit = eye.add(view.x * 10, view.y * 10, view.z * 10);
		EntityHitResult hit = ProjectileUtil.raycast(player, eye, limit, new Box(eye, limit), EntityPredicates.VALID_LIVING_ENTITY, 0f);
		if (hit != null && hit.getEntity() instanceof LivingEntity living) {
			//no shooting water through walls!
			HitResult blockHit = player.raycast(10, 1f, false);
			if (blockHit.getType() == HitResult.Type.MISS) {
				//TODO: fancier, use coil
				player.world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.PLAYERS, 1f, 1f);
				((ServerWorld) player.world).spawnParticles(ParticleTypes.WATER_SPLASH, living.getX(), living.getY(), living.getZ(), 10, 0.1, 0.1, 0.1, 0);
				living.damage(DamageSource.DROWN, 1f);
			}
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public ActionResult onDirectUse(PlayerEntity player, Hand hand) {
		//TODO: some right-click action?
		return ActionResult.PASS;
	}
}
