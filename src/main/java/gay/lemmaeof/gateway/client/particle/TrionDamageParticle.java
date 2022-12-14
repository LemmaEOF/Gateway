package gay.lemmaeof.gateway.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.world.World;

//TODO: custom particle texture
public class TrionDamageParticle extends AnimatedParticle {
	protected TrionDamageParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
		super(world, x, y, z, spriteProvider, -0.02f);
		this.velocityX = velocityX / 2;
		this.velocityY = velocityY / 2;
		this.velocityZ = velocityZ / 2;
		this.scale *= 0.5f;
		this.maxAge = 30 + this.random.nextInt(12);
		this.collidesWithWorld = false;
		this.setSpriteForAge(spriteProvider);
		if (this.random.nextInt(4) == 0) {
			this.setColor(0.37F + this.random.nextFloat() * 0.2F, Math.min(1F, 0.93F + this.random.nextFloat() * 0.3F), 0.58F + this.random.nextFloat() * 0.2F);
		} else {
			this.setColor(0.33F + this.random.nextFloat() * 0.2F, Math.min(1F, 0.87F + this.random.nextFloat() * 0.3F), 0.6F + this.random.nextFloat() * 0.2F);
		}

//		this.setResistance(0.6F);
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		@Override
		public Particle createParticle(DefaultParticleType type, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			return new TrionDamageParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
		}
	}
}
