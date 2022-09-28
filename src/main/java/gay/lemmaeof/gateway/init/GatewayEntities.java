package gay.lemmaeof.gateway.init;

import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.entity.GatewayProjectileEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GatewayEntities {
	public static final EntityType<GatewayProjectileEntity> TRION_PROJECTILE = FabricEntityTypeBuilder
			.create(SpawnGroup.MISC, GatewayProjectileEntity::new)
			.dimensions(EntityDimensions.fixed(0.5f, 0.5f))
			.fireImmune()
			.build();

	public static void init() {
		Gateway.AUTOREG.autoRegister(Registry.ENTITY_TYPE,GatewayEntities.class, EntityType.class);
	}
}
