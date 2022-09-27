package gay.lemmaeof.gateway.api;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

//TODO: documentation
public interface TriggerConfig {
	Identifier getTextureId(EquipmentSlot slot);
	int getColor(EquipmentSlot slot);
	ImmutableList<Trigger> getEquippedTriggers();
	void fromTag(NbtCompound tag);
	NbtCompound toTag(NbtCompound tag);
}
