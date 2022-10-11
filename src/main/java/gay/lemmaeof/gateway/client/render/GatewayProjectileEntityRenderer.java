package gay.lemmaeof.gateway.client.render;

import gay.lemmaeof.gateway.entity.GatewayProjectileEntity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

//TODO: actual custom renderer later
public class GatewayProjectileEntityRenderer extends ProjectileEntityRenderer<GatewayProjectileEntity> {
	public static final Identifier TEXTURE = new Identifier("textures/entity/projectiles/arrow.png");

	public GatewayProjectileEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
	}

	@Override
	public Identifier getTexture(GatewayProjectileEntity entity) {
		return TEXTURE;
	}
}
