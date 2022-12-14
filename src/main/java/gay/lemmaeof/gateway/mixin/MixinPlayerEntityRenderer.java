package gay.lemmaeof.gateway.mixin;

import gay.lemmaeof.gateway.api.TrionComponent;
import gay.lemmaeof.gateway.init.GatewayComponents;
import gay.lemmaeof.gateway.init.GatewayTriggers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class MixinPlayerEntityRenderer extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

	public MixinPlayerEntityRenderer(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
		super(ctx, model, shadowRadius);
	}

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void hideWithChameleon(AbstractClientPlayerEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo info) {

		TrionComponent comp = GatewayComponents.TRION.get(entity);
		//only hide if we're not holding anything and the Chameleon trigger is in use!
		//TODO: hide shadow too
		if (comp.isTriggerActive() //if trigger is inactive, we're visible
				&& entity.getStackInHand(Hand.MAIN_HAND).isEmpty() //if main hand has a stack, we're visible
				&& entity.getStackInHand(Hand.OFF_HAND).isEmpty() //if off hand has a tack, we're visible
				&& comp.getConfig().getEquippedTriggers().contains(GatewayTriggers.CHAMELEON) //if we don't hae Chameleon, we're visible
				&& (MinecraftClient.getInstance().player != null && entity.isInvisibleTo(MinecraftClient.getInstance().player))) //if the viewer should be allowed to see us we're visible
			info.cancel();
	}

	@Override
	protected boolean isVisible(AbstractClientPlayerEntity entity) {
		TrionComponent comp = GatewayComponents.TRION.get(entity);
		return (!comp.isTriggerActive() //if trigger is inactive, we're visible
				|| !entity.getStackInHand(Hand.MAIN_HAND).isEmpty() //if main hand has a stack, we're visible
				|| !entity.getStackInHand(Hand.OFF_HAND).isEmpty() //if off hand has a stack, we're visible
				|| !comp.getConfig().getEquippedTriggers().contains(GatewayTriggers.CHAMELEON)) //if we don't have Chameleon, we're bisible
				&& super.isVisible(entity);
	}
}
