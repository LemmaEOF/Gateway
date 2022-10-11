package gay.lemmaeof.gateway.client.render;

import gay.lemmaeof.gateway.hooks.DynamicArmorMaterial;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class DynamicArmorRenderer implements ArmorRenderer {
	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
		//TODO: uh
		//body at 0.25f dilation, chest at 0.3f dilation, head at 0.75f dilation (in old numbers)
		if (stack.getItem() instanceof ArmorItem armor) {
			if (armor.getMaterial() instanceof DynamicArmorMaterial material) {
				Identifier matId = material.getId(stack);
			}
		}
	}
}
