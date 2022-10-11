package gay.lemmaeof.gateway.api;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * a coil
 */
public interface Coil {

	/**
	 * power affects the capabilities of the machine
	 * @return an int from 0 to 100 of the coil's power
	 */
	int getPower();

	/**
	 * stability decreases by one each {@link CoilType#getMaxHeat()} uses
	 * @return an int from 0 to 100 of the coil's stability
	 */
	int getStability();

	int getHeat();

	boolean isCharged();

	/**
	 * @return the {@link CoilType} type of this coil, which determines decay rate
	 */
	CoilType getType();

	//TODO: figure out make-up ticks
	void tick(World world, @Nullable Entity user);

	void use(World world, @Nullable Entity user);
}
