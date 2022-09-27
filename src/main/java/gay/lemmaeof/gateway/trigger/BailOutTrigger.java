package gay.lemmaeof.gateway.trigger;

import gay.lemmaeof.gateway.api.Trigger;
import gay.lemmaeof.gateway.api.TriggerItem;
import gay.lemmaeof.gateway.api.TrionComponent;
import gay.lemmaeof.gateway.init.GatewayItems;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class BailOutTrigger implements Trigger {

	@Override
	public void tick(TrionComponent component) {
		float percent = (float) component.getTrion() / (float) component.getMaxTrion();
		if (percent < .05) {
			if (component.getPlayer() instanceof ServerPlayerEntity player) {
				BlockPos spawnPos = player.getSpawnPointPosition();
				if (spawnPos == null) spawnPos = player.getWorld().getSpawnPos();
				if (player.getBlockPos().isWithinDistance(spawnPos, 2000)) {
					//TODO: process for bailing out instead? That might take a rework on how trion components work though..
					player.teleport(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
					component.deactivateTrigger();
				}
			}
		}
	}

	@Override
	public TriggerItem getItem() {
		return (TriggerItem) GatewayItems.BAIL_OUT;
	}

}
