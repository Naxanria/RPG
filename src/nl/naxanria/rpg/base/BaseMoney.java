package nl.naxanria.rpg.base;

import no.runsafe.framework.api.minecraft.RunsafeEntityType;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.entity.RunsafeLivingEntity;

public class BaseMoney
{

	public static double getBaseMoney(RunsafeLivingEntity entity)
	{
		return getBaseMoney(entity.getEntityType());
	}

	public static double getBaseMoney(RunsafeEntityType type)
	{

		if (type == LivingEntity.Bat)
			return 0;
		if (type == LivingEntity.Blaze)
			return 25;
		if (type == LivingEntity.CaveSpider)
			return 20;
		if (type == LivingEntity.Cow)
			return 0;
		if (type == LivingEntity.Chicken)
			return 0;
		if (type == LivingEntity.Creeper)
			return 25;
		if (type == LivingEntity.EnderDragon)
			return 267;
		if (type == LivingEntity.Enderman)
			return 35;
		if (type == LivingEntity.Ghast)
			return 25;
		if (type == LivingEntity.Giant)
			return 850;
		if (type == LivingEntity.Horse)
			return 0;
		if (type == LivingEntity.IronGolem)
			return 20;
		if (type == LivingEntity.LavaSlime)
			return 15;
		if (type == LivingEntity.Slime)
			return 15;
		if (type == LivingEntity.MushroomCow)
			return 0;
		if (type == LivingEntity.Ocelot)
			return 5;
		if (type == LivingEntity.Pig)
			return 0;
		if (type == LivingEntity.PigZombie)
			return 55;
		if (type == LivingEntity.Sheep)
			return 0;
		if (type == LivingEntity.Silverfish)
			return 10;
		if (type == LivingEntity.Skeleton)
			return 30;
		if (type == LivingEntity.Snowman)
			return 30;
		if (type == LivingEntity.Spider)
			return 25;
		if (type == LivingEntity.Squid)
			return 0;
		if (type == LivingEntity.Villager)
			return 0;
		if (type == LivingEntity.Witch)
			return 30;
		if (type == LivingEntity.Wither)
			return 120;
		if (type == LivingEntity.Wolf)
			return 25;
		if (type == LivingEntity.Zombie)
			return 45;

		if (type == LivingEntity.Player)
			return 0;

		return 5;
	}

}
