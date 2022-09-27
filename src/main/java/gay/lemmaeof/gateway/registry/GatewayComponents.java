package gay.lemmaeof.gateway.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer;
import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.api.TrionComponent;
import gay.lemmaeof.gateway.impl.TrionComponentImpl;
import net.minecraft.util.Identifier;

public class GatewayComponents implements EntityComponentInitializer, ItemComponentInitializer {
	public static final ComponentKey<TrionComponent> TRION_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Gateway.MODID, "trion"), TrionComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(TRION_COMPONENT, TrionComponentImpl::new);
	}

	@Override
	public void registerItemComponentFactories(ItemComponentFactoryRegistry registry) {

	}
}
