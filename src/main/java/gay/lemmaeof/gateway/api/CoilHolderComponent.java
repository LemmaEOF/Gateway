package gay.lemmaeof.gateway.api;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

import java.util.Optional;

public interface CoilHolderComponent extends ComponentV3 {
	Optional<Coil> getCoil();
	//todo coil use and heat
	void tick();
}
