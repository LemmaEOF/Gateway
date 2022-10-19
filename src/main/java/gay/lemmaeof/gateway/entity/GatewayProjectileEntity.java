package gay.lemmaeof.gateway.entity;

import gay.lemmaeof.gateway.hooks.CustomDamageSource;
import gay.lemmaeof.gateway.mixin.PersistentProjectileEntityAccessor;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

public class GatewayProjectileEntity extends PersistentProjectileEntity {
	public static final TrackedData<Byte> EXPLOSIVENESS = DataTracker.registerData(GatewayProjectileEntity.class,  TrackedDataHandlerRegistry.BYTE);
	public static final TrackedData<Byte> PIERCED = DataTracker.registerData(GatewayProjectileEntity.class, TrackedDataHandlerRegistry.BYTE);

	private float lastPitch;
	private float lastYaw;
	private Vec3d lastVelocity;

	public GatewayProjectileEntity(EntityType<GatewayProjectileEntity> type, World world) {
		super(type, world);
		this.setNoGravity(true);
	}

	@Override
	protected ItemStack asItemStack() {
		return ItemStack.EMPTY;
	}

	public void setExplosiveness(int explosiveness) {
		this.dataTracker.set(EXPLOSIVENESS, (byte)explosiveness);
	}

	public int getExplosiveness() {
		return this.dataTracker.get(EXPLOSIVENESS);
	}

	public boolean isExplosive() {
		return this.dataTracker.get(EXPLOSIVENESS) > 0;
	}

	@Override
	public boolean isImmuneToExplosion() {
		return this.isExplosive();
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(EXPLOSIVENESS, (byte)0);
		this.dataTracker.startTracking(PIERCED, (byte)0);
	}

	@Override
	public void tick() {
		if (!world.isClient) {
			if (this.age >= 500) this.remove(RemovalReason.DISCARDED);
		}
		super.tick();
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		if (!world.isClient) {
			byte piercing = this.getPierceLevel();
			this.lastPitch = getPitch();
			this.lastYaw = getYaw();
			this.lastVelocity = getVelocity();
			super.onBlockHit(blockHitResult);
			if (isExplosive()) {
				world.createExplosion(this, new CustomDamageSource("trion", getOwner()), new ExplosionBehavior(), this.getX(), this.getY(), this.getZ(), getExplosiveness(), false, Explosion.DestructionType.BREAK);
				byte pierced = this.dataTracker.get(PIERCED);
				if (pierced < piercing) {
					this.dataTracker.set(PIERCED, (byte) (pierced + 1));
					this.setPitch(lastPitch);
					this.setYaw(lastYaw);
					this.setVelocity(lastVelocity);
					((PersistentProjectileEntityAccessor)this).setInBlockState(null);
					this.velocityModified = true;
					this.setPierceLevel(piercing);
					this.inGround = false;
					this.shake = 0;
				} else {
					this.remove(RemovalReason.KILLED);
				}
			} else {
				this.remove(RemovalReason.KILLED);
			}
		} else {
			super.onBlockHit(blockHitResult);
		}
	}

	@Override
	protected void onHit(LivingEntity target) {
		super.onHit(target);
		if (isExplosive()) {
			world.createExplosion(this, new CustomDamageSource("trion", getOwner()), new ExplosionBehavior(), this.getX(), this.getY(), this.getZ(), 4, false, Explosion.DestructionType.BREAK);
		}
		if (this.dataTracker.get(PIERCED) >= getPierceLevel()) {
			this.remove(RemovalReason.KILLED);
		}
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.setExplosiveness(tag.getByte("Explosiveness"));
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putByte("Explosiveness", (byte)this.getExplosiveness());
	}

}
