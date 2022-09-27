package gay.lemmaeof.gateway;

import com.mojang.serialization.Lifecycle;
import gay.lemmaeof.gateway.api.Trigger;
import gay.lemmaeof.gateway.init.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;


public class Gateway implements ModInitializer {
	public static final String MODID = "gateway";
	public static final ItemGroup GATEWAY_GROUP = QuiltItemGroup.createWithIcon(new Identifier(MODID, "gateway_group"), () -> new ItemStack(GatewayItems.TRIGGER_HOLDER));

	public static final RegistryKey<Registry<Trigger>> TRIGGERS_KEY = RegistryKey.ofRegistry(new Identifier(MODID, "triggers"));
	public static final Registry<Trigger> TRIGGERS = new DefaultedRegistry<>("trion:empty", TRIGGERS_KEY, Lifecycle.stable(), null);

	//green trion color is 0x5fec94, yellow trion color is 0xe4e072, takamoma-2 color is 0x388e9a

	public static final Logger logger = LogManager.getLogger(MODID);

	@Override
	public void onInitialize(ModContainer container) {
		GatewayItems.init();
		GatewayParticles.init();
		GatewaySounds.init();
		GatewayStatusEffects.init();
		GatewayTriggers.init();
		GatewayMechanics.init();
	}
}