package nl.naxanria.rpg.handler;

import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public class DamageHandler
{


	public double getDamage(RunsafeMeta item)
	{

		double damage = 1;

		if (isWeapon(item))
			if (item.hasLore() && item.getLore() != null)
				//get actual intended damage
			{
				for (String line : item.getLore())
					if (line != null && line.contains("dmg"))
					{
						damage = Double.valueOf(line.split("§f")[1]);
						break;
					}
			}
		else
			{
				damage = getDefaultDamage(item);
			}

		return damage;
	}

	public boolean isSword(RunsafeMeta item)
	{
		return item.is(Item.Combat.Sword.Diamond) ||
				item.is(Item.Combat.Sword.Iron) ||
				item.is(Item.Combat.Sword.Gold) ||
				item.is(Item.Combat.Sword.Stone) ||
				item.is(Item.Combat.Sword.Wood);
	}

	public boolean isAxe(RunsafeMeta item)
	{
		return item.is(Item.Tool.Axe.Diamond) ||
				item.is(Item.Tool.Axe.Gold) ||
				item.is(Item.Tool.Axe.Iron) ||
				item.is(Item.Tool.Axe.Stone) ||
				item.is(Item.Tool.Axe.Wood);
	}

	public boolean isBow(RunsafeMeta item)
	{
		return item.is(Item.Combat.Bow);
	}

	public boolean isWeapon(RunsafeMeta item)
	{
		return isAxe(item) || isBow(item) || isSword(item);
	}

	private double getDefaultDamage(RunsafeMeta item)
	{
		if (isBow(item))
			return 5;

		if (item.is(Item.Combat.Sword.Wood))
			return 3;
		if (item.is(Item.Combat.Sword.Stone))
			return 8;
		if (item.is(Item.Combat.Sword.Iron))
			return 14;
		if (item.is(Item.Combat.Sword.Gold))
			return 22;
		if (item.is(Item.Combat.Sword.Diamond))
			return 31;

		if (item.is(Item.Tool.Axe.Wood))
			return 2;
		if (item.is(Item.Tool.Axe.Stone))
			return 6;
		if (item.is(Item.Tool.Axe.Iron))
			return 10;
		if (item.is(Item.Tool.Axe.Gold))
			return 18;
		if (item.is(Item.Tool.Axe.Diamond))
			return 26;

		return 1;
	}

}
