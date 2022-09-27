package gay.lemmaeof.gateway.impl;

import com.google.common.collect.ImmutableList;
import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.api.Trigger;
import gay.lemmaeof.gateway.api.TriggerConfig;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.nbt.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TriggerConfigImpl implements TriggerConfig {
	private Map<EquipmentSlot, Identifier> textures = new HashMap<>();
	private Object2IntMap<EquipmentSlot> colors = new Object2IntOpenHashMap<>();
	private List<Trigger> equippedTriggers = new ArrayList<>();

	public TriggerConfigImpl() { }

	@Override
	public Identifier getTextureId(EquipmentSlot slot) {
		if (textures.containsKey(slot)) return textures.get(slot);
		return new Identifier(Gateway.MODID, "strategist"); //Tamakoma-2 texture
	}

	public void setTextureId(EquipmentSlot slot, Identifier id) {
		textures.put(slot, id);
	}

	@Override
	public int getColor(EquipmentSlot slot) {
		if (colors.containsKey(slot)) return colors.getInt(slot);
		return 0x388e9a; //Tamakoma-2 color
	}

	public void setColor(EquipmentSlot slot, int color) {
		colors.put(slot, color);
	}

	@Override
	public ImmutableList<Trigger> getEquippedTriggers() {
		return ImmutableList.copyOf(equippedTriggers);
	}

	public void setEquippedTriggers(List<Trigger> triggerList) {
		this.equippedTriggers = triggerList;
	}

	@Override
	public void fromTag(NbtCompound tag) {
		textures.clear();
		colors.clear();
		equippedTriggers.clear();
		NbtCompound texTag = tag.getCompound("Textures");
		for (String key : texTag.getKeys()) {
			EquipmentSlot slot = EquipmentSlot.byName(key);
			textures.put(slot, new Identifier(texTag.getString(key)));
		}
		NbtCompound colorTag = tag.getCompound("Colors");
		for (String key : colorTag.getKeys()) {
			EquipmentSlot slot = EquipmentSlot.byName(key);
			colors.put(slot, colorTag.getInt(key));
		}
		List<Trigger> triggerList = new ArrayList<>();
		NbtList triggerTag = tag.getList("EquippedTriggers", NbtType.STRING);
		for (NbtElement trigger : triggerTag) {
			triggerList.add(Gateway.TRIGGERS.get(new Identifier(trigger.asString())));
		}
		this.equippedTriggers = triggerList;
	}

	@Override
	public NbtCompound toTag(NbtCompound tag) {
		NbtCompound texTag = new NbtCompound();
		for (EquipmentSlot slot : textures.keySet()) {
			texTag.putString(slot.getName(), textures.get(slot).toString());
		}
		tag.put("Textures", texTag);
		NbtCompound colorTag = new NbtCompound();
		for (EquipmentSlot slot : colors.keySet()) {
			texTag.putInt(slot.getName(), colors.getInt(slot));
		}
		tag.put("Colors", colorTag);
		NbtList triggerTag = new NbtList();
		for (Trigger trigger : equippedTriggers) {
			triggerTag.add(NbtString.of(Gateway.TRIGGERS.getId(trigger).toString()));
		}
		tag.put("EquippedTriggers", triggerTag);
		return tag;
	}
}
