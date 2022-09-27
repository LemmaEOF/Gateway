package gay.lemmaeof.gateway.registry;

import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.hooks.CustomParticleType;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GatewayParticles {

	public static final DefaultParticleType TRANSFORMATION = register(true, "transformation");
	public static final DefaultParticleType TRION_DAMAGE = register(true, "trion_damage");

	public static void init() { }

	public static DefaultParticleType register(boolean alwaysShow, String name) {
		return Registry.register(Registry.PARTICLE_TYPE, new Identifier(Gateway.MODID, name), new CustomParticleType(alwaysShow));
	}
}
