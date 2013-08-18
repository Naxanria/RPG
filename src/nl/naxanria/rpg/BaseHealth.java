package nl.naxanria.rpg;

import no.runsafe.framework.api.minecraft.RunsafeEntityType;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.entity.RunsafeLivingEntity;

public class BaseHealth
{

	public static double getBaseHealth(RunsafeLivingEntity entity)
	{
		return getBaseHealth(entity.getEntityType());
	}

	public static double getBaseHealth(RunsafeEntityType type)
	{

		if (type == LivingEntity.Bat)
			return 5;
		if (type == LivingEntity.Blaze)
			return 25;
		if (type == LivingEntity.CaveSpider)
			return 20;
		if (type == LivingEntity.Cow)
			return 15;
		if (type == LivingEntity.Chicken)
			return 5;
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
			return 25;
		if (type == LivingEntity.IronGolem)
			return 400;
		if (type == LivingEntity.LavaSlime)
			return 15;
		if (type == LivingEntity.Slime)
			return 15;
		if (type == LivingEntity.MushroomCow)
			return 15;
		if (type == LivingEntity.Ocelot)
			return 5;
		if (type == LivingEntity.Pig)
			return 5;
		if (type == LivingEntity.PigZombie)
			return 55;
		if (type == LivingEntity.Sheep)
			return 5;
		if (type == LivingEntity.Silverfish)
			return 10;
		if (type == LivingEntity.Skeleton)
			return 30;
		if (type == LivingEntity.Snowman)
			return 30;
		if (type == LivingEntity.Spider)
			return 25;
		if (type == LivingEntity.Squid)
			return 5;
		if (type == LivingEntity.Villager)
			return 20;
		if (type == LivingEntity.Witch)
			return 30;
		if (type == LivingEntity.Wither)
			return 120;
		if (type == LivingEntity.Wolf)
			return 25;
		if (type == LivingEntity.Zombie)
			return 45;

		if (type == LivingEntity.Player)
			return 50;

		return 5;
	}


}
