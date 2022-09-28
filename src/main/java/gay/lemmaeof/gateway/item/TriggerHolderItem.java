package gay.lemmaeof.gateway.item;

import gay.lemmaeof.gateway.api.Trigger;
import gay.lemmaeof.gateway.api.TriggerConfig;
import gay.lemmaeof.gateway.api.TrionComponent;
import gay.lemmaeof.gateway.impl.TriggerConfigImpl;
import gay.lemmaeof.gateway.init.GatewayComponents;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TriggerHolderItem extends Item {
	public TriggerHolderItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		if (world.isClient) return TypedActionResult.success(stack);
		TrionComponent component = GatewayComponents.TRION.get(user);
		if (component.isTriggerActive()) {
			component.deactivateTrigger();
			user.getItemCooldownManager().set(this, 60);
		} else {
			component.activateTrigger(getConfig(stack));
			user.getItemCooldownManager().set(this, 30);
		}
		return TypedActionResult.success(stack);
	}

	private TriggerConfig getConfig(ItemStack stack) {
		TriggerConfig ret = new TriggerConfigImpl();
		NbtCompound tag = stack.getOrCreateNbt();
		if (tag.contains("TriggerConfig", NbtType.COMPOUND)) {
			ret.fromTag(tag.getCompound("TriggerConfig"));
		}
		return ret;
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		TriggerConfig config = getConfig(stack);
		if (!config.getEquippedTriggers().isEmpty()) {
			tooltip.add(Text.translatable("tooltip.gateway.triggers").formatted(Formatting.GRAY));
			for (Trigger trigger : config.getEquippedTriggers()) {
				tooltip.add(Text.literal("  - ").formatted(Formatting.GRAY).append(Text.translatable(trigger.getTranslationKey()).formatted(Formatting.GREEN)).formatted(Formatting.GRAY));
			}
		}
	}
}
