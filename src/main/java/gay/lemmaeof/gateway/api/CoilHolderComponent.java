package gay.lemmaeof.gateway.api;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface CoilHolderComponent extends ComponentV3 {
	default boolean hasCoil() {
		return getCoil() != null;
	}
	@Nullable Coil getCoil();
	default Optional<Coil> maybeGetCoil() {
		return Optional.ofNullable(getCoil());
	}
	boolean tryUse(World world, @Nullable Entity user);
}
