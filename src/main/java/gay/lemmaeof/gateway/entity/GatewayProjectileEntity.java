package gay.lemmaeof.gateway.entity;

import gay.lemmaeof.gateway.hooks.CustomDamageSource;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

public class GatewayProjectileEntity extends PersistentProjectileEntity {

	public GatewayProjectileEntity(EntityType<GatewayProjectileEntity> type, World world) {
		super(type, world);
		this.setNoGravity(true);
	}

	@Override
	protected ItemStack asItemStack() {
		return ItemStack.EMPTY;
	}

	public void setExplosive(boolean explosive) {
		this.setFlag(8, explosive);
	}

	public boolean isExplosive() {
		return this.getFlag(0);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.age >= 500) this.remove(RemovalReason.DISCARDED);
	}

	//TODO: explosive trion damage source?
	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		if (isExplosive()) {
			world.createExplosion(this, new CustomDamageSource("trion", getOwner()), new ExplosionBehavior(), this.getX(), this.getY(), this.getZ(), 4, false, Explosion.DestructionType.BREAK);
		}
	}

	@Override
	protected void onBlockCollision(BlockState state) {
		super.onBlockCollision(state);
		if (isExplosive()) {
			world.createExplosion(this, new CustomDamageSource("trion", getOwner()), new ExplosionBehavior(), this.getX(), this.getY(), this.getZ(), 4, false, Explosion.DestructionType.BREAK);
		}
		this.remove(RemovalReason.DISCARDED);
	}

	@Override
	public void readNbt(NbtCompound tag) {
		this.setExplosive(tag.getBoolean("Explosive"));
	}

	@Override
	public NbtCompound writeNbt(NbtCompound tag) {
		NbtCompound ret = super.writeNbt(tag);
		ret.putBoolean("Explosive", this.isExplosive());
		return ret;
	}

}
