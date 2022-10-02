package gay.lemmaeof.gateway.api;

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

	/**
	 * @return the {@link CoilType} type of this coil, which determines decay rate
	 */
	CoilType getType();

}
