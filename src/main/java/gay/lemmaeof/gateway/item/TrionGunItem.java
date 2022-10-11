package gay.lemmaeof.gateway.item;

import com.unascribed.lib39.recoil.api.DirectClickItem;
import gay.lemmaeof.gateway.api.TriggerShifter;
import gay.lemmaeof.gateway.entity.GatewayProjectileEntity;
import gay.lemmaeof.gateway.init.GatewayEntities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class TrionGunItem extends Item implements TriggerShifter, DirectClickItem {
	public TrionGunItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult onDirectAttack(PlayerEntity player, Hand hand) {
		if (player.world.isClient) return ActionResult.SUCCESS;
		System.out.println("Trying to shoot trion gun!");
		GatewayProjectileEntity proj = new GatewayProjectileEntity(GatewayEntities.TRION_PROJECTILE, player.world);
		proj.setExplosive(true);
		proj.setProperties(player, player.getPitch(), player.getYaw(), 0, 3, 1);
		player.world.spawnEntity(proj);
		return ActionResult.SUCCESS;
	}

	@Override
	public ActionResult onDirectUse(PlayerEntity player, Hand hand) {
		return ActionResult.PASS;
	}

//	@Override
//	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
//		TriggerShifter.super.entityInventoryTick(stack, world, entity, slot, selected);
//	}
}
