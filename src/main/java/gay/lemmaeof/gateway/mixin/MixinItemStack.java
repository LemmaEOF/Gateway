package gay.lemmaeof.gateway.mixin;

import gay.lemmaeof.gateway.api.TriggerShifter;
import gay.lemmaeof.gateway.api.TrionComponent;
import gay.lemmaeof.gateway.item.TrionArmorItem;
import gay.lemmaeof.gateway.init.GatewayComponents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
	@Shadow public abstract Item getItem();

	@Inject(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hasCustomName()Z"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void injectTriggerFormatting(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> info, List<Text> ret, MutableText name) {
		if (getItem() instanceof TriggerShifter) {
			name.formatted(Formatting.GREEN);
		}
	}
}
