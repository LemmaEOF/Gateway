package gay.lemmaeof.gateway.api;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

/**
 * a coil, or a coil-powered machine
 */
//todo: just for coils and separate comp for machines?
//TODO: heat system
public interface CoilComponent extends ComponentV3 {
	/**
	 * @return true if this is a machine using a replacable coil, false if this is a coil itself
	 */
	boolean isMachine();

	/**
	 * power affects the capabilities of the machine
	 * @return an int from 0 to 100 of the coil's power
	 */
	int getPower();

	/**
	 * stability decreases by one each {@link CoilType#getDecayRate()} uses
	 * @return an int from 0 to 100 of the coil's stability
	 */
	int getStability();

	/**
	 * @return the {@link CoilType} type of this coil, which determines decay rate
	 */
	CoilType getType();
}
