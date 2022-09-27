package gay.lemmaeof.gateway.mixin;

import java.util.Map;

import com.google.gson.JsonObject;
import gay.lemmaeof.gateway.init.GatewayMechanics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.loot.LootManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

@Mixin(LootManager.class)
public class MixinLootManager {
	@Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At("HEAD"))
	private void prepIds(Map<Identifier, JsonObject> resources, ResourceManager manager, Profiler profiler, CallbackInfo info) {
		GatewayMechanics.prepareTableIds();
	}
}
