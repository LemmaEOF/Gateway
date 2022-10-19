package gay.lemmaeof.gateway.item;

import com.unascribed.lib39.recoil.api.DirectClickItem;
import gay.lemmaeof.gateway.api.TriggerShifter;
import gay.lemmaeof.gateway.entity.GatewayProjectileEntity;
import gay.lemmaeof.gateway.init.GatewayEntities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TrionGunItem extends Item implements TriggerShifter, DirectClickItem {
	public TrionGunItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult onDirectAttack(PlayerEntity player, Hand hand) {
		if (player.world.isClient) return ActionResult.SUCCESS;
		GatewayProjectileEntity proj = new GatewayProjectileEntity(GatewayEntities.TRION_PROJECTILE, player.world);
		proj.setExplosiveness(2);
		proj.setPierceLevel((byte)2);
		proj.setPosition(player.getX(), player.getEyeY() - 0.1, player.getZ());
		proj.setProperties(player, player.getPitch(), player.getYaw(), 0, 3, 1);
		return ActionResult.SUCCESS;
	}

	@Override
	public ActionResult onDirectUse(PlayerEntity player, Hand hand) {
		return ActionResult.PASS;
	}

	@Override
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return false;
	}

	//	@Override
//	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
//		TriggerShifter.super.entityInventoryTick(stack, world, entity, slot, selected);
//	}
}
