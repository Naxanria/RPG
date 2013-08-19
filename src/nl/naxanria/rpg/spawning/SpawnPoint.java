package nl.naxanria.rpg.spawning;

import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.RunsafeLivingEntity;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.ArrayList;
import java.util.Random;

public class SpawnPoint
{
	public SpawnPoint(RunsafeLocation location, double r)
	{

		this.location = location;

		this.r = r;

		mobs = new ArrayList<Mob>();
		mobs.add(new Mob(LivingEntity.Zombie));

	}

	public SpawnPoint(RunsafeLocation location, double r, ArrayList<Mob> mobs) {

		this.location = location;
		this.r = r;
		this.mobs = mobs;
	}

	public void spawn(int amount)
	{
		for (int i = 0; i < amount; i++)
		{
			mobs.get(rand.nextInt(mobs.size())).spawn(location);
		}
	}

	public boolean isChunkLoaded()
	{

		return location.getWorld().getRaw().isChunkLoaded((int) Math.floor(location.getBlockX() / 16), (int) Math.floor(location.getBlockZ() / 16));

	}

	public int getAmountEntitiesNearby()
	{

		int amount = 0;

		for (RunsafeEntity entity : location.getWorld().getEntities())
			if (entity instanceof RunsafeLivingEntity && !(entity instanceof RunsafePlayer))
				if(entity.getLocation().distanceSquared(location) <= r)
					amount++;

		return amount;
	}

	public RunsafeLocation getLocation()
	{
		return location;
	}

	public void addMob(Mob mob)
	{
		mobs.add(mob);

	}

	public boolean isSameLocation(RunsafeLocation test)
	{
		return (location.getX() == test.getX()
		 				&& location.getY() == test.getY()
						&& location.getZ() == test.getZ());
	}

	private double r;

	private RunsafeLocation location;

	private ArrayList<Mob> mobs;
	private Random rand = new Random();

}
