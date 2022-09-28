package gay.lemmaeof.gateway.init;

import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.hooks.CustomParticleType;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GatewayParticles {

	public static final CustomParticleType TRANSFORMATION = new CustomParticleType(true);
	public static final CustomParticleType TRION_DAMAGE = new CustomParticleType(true);

	public static void init() {
		Gateway.AUTOREG.autoRegister(Registry.PARTICLE_TYPE, GatewayParticles.class, ParticleType.class);
	}
}
