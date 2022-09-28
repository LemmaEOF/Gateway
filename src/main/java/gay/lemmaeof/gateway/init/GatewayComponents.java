package gay.lemmaeof.gateway.init;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer;
import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.api.CoilComponent;
import gay.lemmaeof.gateway.api.TrionComponent;
import gay.lemmaeof.gateway.impl.PlainCoilComponent;
import gay.lemmaeof.gateway.impl.TrionComponentImpl;
import net.minecraft.util.Identifier;

public class GatewayComponents implements EntityComponentInitializer, ItemComponentInitializer {
	public static final ComponentKey<TrionComponent> TRION = ComponentRegistry.getOrCreate(new Identifier(Gateway.MODID, "trion"), TrionComponent.class);
	public static final ComponentKey<CoilComponent> COIL = ComponentRegistry.getOrCreate(new Identifier(Gateway.MODID, "coil"), CoilComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(TRION, TrionComponentImpl::new);
	}

	@Override
	public void registerItemComponentFactories(ItemComponentFactoryRegistry registry) {
		registry.register(GatewayItems.COIL, COIL, PlainCoilComponent::new);
	}
}
