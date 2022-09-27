package gay.lemmaeof.gateway.item;

import gay.lemmaeof.gateway.api.TrionComponent;
import gay.lemmaeof.gateway.api.TrionShield;
import gay.lemmaeof.gateway.init.GatewayComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class RaygustItem extends TrionSwordItem implements TrionShield {
	public RaygustItem(ToolMaterial material, int attackDamage, float attackSpeed, float rangeBoost, Settings settings) {
		super(material, attackDamage, attackSpeed, rangeBoost, settings);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	public int getMaxUseTime(ItemStack stack) {
		return 72000;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		if (getShieldDamage(stack) == getMaxShieldDamage(stack)) return TypedActionResult.consume(stack);
		user.setCurrentHand(hand);
		return TypedActionResult.consume(stack);
	}

	@Override
	public int getMaxShieldDamage(ItemStack stack) {
		return 64;
	}

	@Override
	public void tickShield(PlayerEntity wielder, ItemStack stack) {
		TrionComponent component = GatewayComponents.TRION.get(wielder);
		int currentDamage = getShieldDamage(stack);
		if (component.isTriggerActive() && wielder.world.getTime() % 60 == 0 && currentDamage != 0) {
			component.setTrion(component.getTrion() - 2, true);
			setShieldDamage(stack, currentDamage - 1);
		}
	}

	@Override
	public int getCooldownTime(PlayerEntity wielder, ItemStack stack) {
		return 200;
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (entity instanceof PlayerEntity && !world.isClient) {
			tickShield((PlayerEntity) entity, stack);
		}
	}
}
