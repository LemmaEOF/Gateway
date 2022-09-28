package gay.lemmaeof.gateway;

import com.mojang.datafixers.util.Pair;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import gay.lemmaeof.gateway.api.CoilComponent;
import gay.lemmaeof.gateway.api.CoilType;
import gay.lemmaeof.gateway.client.hud.TrionResourceBar;
import gay.lemmaeof.gateway.client.model.DynamicArmorBakedModel;
import gay.lemmaeof.gateway.client.particle.TransformationParticle;
import gay.lemmaeof.gateway.client.particle.TrionDamageParticle;
import gay.lemmaeof.gateway.init.GatewayComponents;
import gay.lemmaeof.gateway.init.GatewayItems;
import gay.lemmaeof.gateway.init.GatewayParticles;
import gay.lemmaeof.gateway.init.GatewayStatusEffects;
import gay.lemmaeof.impulse.api.ResourceBars;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

@Environment(EnvType.CLIENT)
public class GatewayClient implements ClientModInitializer {

	public static final String[] ARMOR_SETS = new String[]{"strategist", "ranger", "commander"}; //TODO: more configurable
	public static final Map<EquipmentSlot, String> ARMOR_TYPES = new HashMap<>();

	@Override
	public void onInitializeClient(ModContainer container) {
		ResourceBars.addBar(new TrionResourceBar());

		List<Identifier> ids = Stream.of(
				GatewayItems.TRION_HELMET,
				GatewayItems.TRION_CHESTPLATE,
				GatewayItems.TRION_LEGGINGS,
				GatewayItems.TRION_BOOTS
		).map(Registry.ITEM::getId).toList();

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			if (tintIndex == 0 && stack.getItem() instanceof DyeableArmorItem) {
				return ((DyeableArmorItem)stack.getItem()).getColor(stack);
			}
			return 0xFFFFFF;
		}, GatewayItems.TRION_HELMET, GatewayItems.TRION_CHESTPLATE, GatewayItems.TRION_LEGGINGS, GatewayItems.TRION_BOOTS);
		ColorProviderRegistry.ITEM.register((stack, index) -> {
			if (index == 0) return 0xFFFFFF;
			PlayerEntity player = MinecraftClient.getInstance().player;
			CoilComponent comp = GatewayComponents.COIL.get(stack);
			if (player != null && !stack.getOrCreateNbt().getBoolean("AlwaysGlow") && !comp.getType().isAlwaysCharged()) {
				//TODO: more efficient way to do this? lib39-sandman?
				if (player.hasStatusEffect(GatewayStatusEffects.CHARGED)) {
					for (int i = 0; i < player.getInventory().size(); i++) {
						if (player.getInventory().getStack(i) == stack) {
							return getCoilColor(comp);
						}
					}
				}
			} else {
				return getCoilColor(comp);
			}
			return 0;

		}, GatewayItems.COIL);

		ModelPredicateProviderRegistry.register(GatewayItems.TRION_SHIELD, new Identifier("blocking"), (stack, world, entity, i) ->
				//TODO: returns true whether this one is the one doing the blocking or not, but picking a specific hand would make it break the model
				entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
		ModelPredicateProviderRegistry.register(GatewayItems.RAYGUST, new Identifier("blocking"), (stack, world, entity, i) ->
				entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);

		ParticleFactoryRegistry.getInstance().register(GatewayParticles.TRANSFORMATION, TransformationParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(GatewayParticles.TRION_DAMAGE, TrionDamageParticle.Factory::new);

		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, consumer) -> { //TODO: any way to make this easy with the current variant system?
			for (String set : ARMOR_SETS) {
				for (EquipmentSlot slot : EquipmentSlot.values()) {
					if (slot.getType() == EquipmentSlot.Type.ARMOR) {
						consumer.accept(new ModelIdentifier(new Identifier(Gateway.MODID, "armor/" + set + "_" + ARMOR_TYPES.get(slot)), "inventory"));
					}
				}
			}
			for (Identifier id : ids) {
				consumer.accept(new ModelIdentifier(id, "inventory"));
			}
		});

		ModelLoadingRegistry.INSTANCE.registerVariantProvider(manager -> (modelId, context) -> {
			for (Identifier id : ids) {
				if (modelId.getNamespace().equals(id.getNamespace()) && modelId.getPath().equals(id.getPath())) {
					return new UnbakedModel() {
						@Override
						public Collection<Identifier> getModelDependencies() {
							return Collections.emptyList();
						}

						@Override
						public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
							return Collections.emptyList();
						}

						@Override
						public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
							return new DynamicArmorBakedModel();
						}
					};
				}
			}
			return null;
		});

		TrinketRendererRegistry.registerRenderer(GatewayItems.COIL, (stack, slotReference, contextModel, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch) -> {
			if (entity instanceof AbstractClientPlayerEntity player && contextModel instanceof PlayerEntityModel<? extends LivingEntity>) {
				TrinketRenderer.translateToChest(matrices, (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel, player);
				matrices.scale(0.5f, 0.5f, 0.5f);
				matrices.translate(0f, -.25f, 0f);
				MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
			}
		});
	}

	public static int getCoilColor(CoilComponent component) {
		float hue = component.getStability() / 200f;
		float powerMul = component.getPower() / 100f;
		float decis = System.nanoTime() / 100_000_000f;
		float value = (float) (Math.sin(decis * powerMul) + 2) / 3;
		return MathHelper.hsvToRgb(hue, 1f, value);
	}

	static {
		ARMOR_TYPES.put(EquipmentSlot.HEAD, "helmet");
		ARMOR_TYPES.put(EquipmentSlot.CHEST, "chestplate");
		ARMOR_TYPES.put(EquipmentSlot.LEGS, "leggings");
		ARMOR_TYPES.put(EquipmentSlot.FEET, "boots");
	}

}
