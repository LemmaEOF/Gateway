package gay.lemmaeof.gateway.mixin;

import gay.lemmaeof.gateway.api.TriggerShifter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class MixinSlot {
	@Shadow public abstract ItemStack getStack();

	//TODO: will this totally work? Spinnery's definitely gonna fuck with this...
	@Inject(method = "canTakeItems", at = @At("HEAD"), cancellable = true)
	private void blockTriggerRemoval(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
		if (getStack().getItem() instanceof TriggerShifter && !player.isCreative()) info.setReturnValue(false);
	}
}
