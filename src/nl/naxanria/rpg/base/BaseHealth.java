package nl.naxanria.rpg.base;

import no.runsafe.framework.api.minecraft.RunsafeEntityType;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.entity.RunsafeLivingEntity;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public class BaseHealth
{

	public static double getBaseHealth(RunsafeLivingEntity entity)
	{

		double base = getBaseHealthEntityType(entity.getEntityType());

		base += getBaseHealth(entity.getEquipment().getBoots());
		base += getBaseHealth(entity.getEquipment().getLeggings());
		base += getBaseHealth(entity.getEquipment().getChestplate());
		base += getBaseHealth(entity.getEquipment().getHelmet());

		return base;
	}

	public static double getBaseHealthEntityType(RunsafeEntityType type)
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
			return 27;
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
			return 35;

		if (type == LivingEntity.Player)
			return 50;

		return 5;
	}

	public static double getBaseHealth(RunsafeMeta item)
	{

		if (item == null)
			return 0;
		double base = getBaseHealthItem(item);

		if (item.hasLore())
			for(String line : item.getLore())
				if (line.contains("hp"))
				{
					base = Double.valueOf(line.split("§f")[1]);
					break;
				}
		else
			item.addLore("§2hp: §f" + base);

		return base;
	}

	public static double getBaseHealthItem(RunsafeMeta item)
	{

		if(item.is(Item.Combat.Boots.Leather))
			return 3;
		if(item.is(Item.Combat.Boots.Chainmail))
			return 8;
		if(item.is(Item.Combat.Boots.Iron))
			return 15;
		if(item.is(Item.Combat.Boots.Gold))
			return 27;
		if(item.is(Item.Combat.Boots.Diamond))
			return 41;

		if(item.is(Item.Combat.Leggings.Leather))
			return 6;
		if(item.is(Item.Combat.Leggings.Chainmail))
			return 11;
		if(item.is(Item.Combat.Leggings.Iron))
			return 20;
		if(item.is(Item.Combat.Leggings.Gold))
			return 34;
		if(item.is(Item.Combat.Leggings.Diamond))
			return 62;

		if(item.is(Item.Combat.Chestplate.Leather))
			return 7;
		if(item.is(Item.Combat.Chestplate.Chainmail))
			return 14;
		if(item.is(Item.Combat.Chestplate.Iron))
			return 22;
		if(item.is(Item.Combat.Chestplate.Gold))
			return 37;
		if(item.is(Item.Combat.Chestplate.Diamond))
			return 67;

		if(item.is(Item.Combat.Helmet.Leather))
			return 3;
		if(item.is(Item.Combat.Helmet.Chainmail))
			return 8;
		if(item.is(Item.Combat.Helmet.Iron))
			return 14;
		if(item.is(Item.Combat.Helmet.Gold))
			return 25;
		if(item.is(Item.Combat.Helmet.Diamond))
			return 38;

		return 0;
	}


}
