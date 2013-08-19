package nl.naxanria.rpg.spawning;

import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.RunsafeLivingEntity;
import no.runsafe.framework.minecraft.inventory.RunsafeEntityEquipment;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

import javax.annotation.Nonnull;

public class Mob
{

	public Mob(@Nonnull LivingEntity type)
	{
		equipment = null;
		this.type = type;
		setEquipment();
	}

	public Mob(@Nonnull LivingEntity type, RunsafeEntityEquipment equipment)
	{
		this.equipment = equipment;
		this.type = type;
		setEquipment();
	}

	private void setEquipment()
	{
		helmet = equipment.getHelmet();
		chestPlate = equipment.getChestplate();
		leggings = equipment.getLeggings();
		boots = equipment.getBoots();
		weapon = equipment.getItemInHand();

	}

	public RunsafeLivingEntity spawn(RunsafeLocation location)
	{
		RunsafeEntity entity = location.getWorld().spawnCreature(location, type.getName());

		RunsafeLivingEntity livingEntity = (RunsafeLivingEntity) entity;

		if(helmet != null)
			livingEntity.getEquipment().setHelmet(helmet);
		if(chestPlate != null)
			livingEntity.getEquipment().setChestplate(chestPlate);
		if(leggings != null)
			livingEntity.getEquipment().setLeggings(leggings);
		if(boots != null)
			livingEntity.getEquipment().setBoots(boots);
		if(weapon != null)
			livingEntity.getEquipment().setItemInHand(weapon);

		return livingEntity;
	}

	private RunsafeEntityEquipment equipment;
	private LivingEntity type;
	private RunsafeMeta helmet;
	private RunsafeMeta chestPlate;
	private RunsafeMeta leggings;
	private RunsafeMeta boots;
	private RunsafeMeta weapon;


}
