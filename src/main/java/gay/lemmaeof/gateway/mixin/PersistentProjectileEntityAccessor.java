package gay.lemmaeof.gateway.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.PersistentProjectileEntity;

@Mixin(PersistentProjectileEntity.class)
public interface PersistentProjectileEntityAccessor {
	@Accessor
	void setInBlockState(BlockState state);
}
