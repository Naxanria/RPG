package nl.naxanria.rpg.spawning.handler;

import nl.naxanria.rpg.spawning.Mob;
import nl.naxanria.rpg.spawning.SpawnPoint;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.inventory.RunsafeEntityEquipment;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SpawnHandler
{


	public SpawnHandler(IOutput console)
	{
		this.console = console;
	}

	public int addSpawnPoint(RunsafeLocation location)
	{
		return addSpawnPoint(location, LivingEntity.Zombie, null);
	}

	public int addSpawnPoint(RunsafeLocation location, LivingEntity type)
	{
		return addSpawnPoint(location, type, null);
	}

	public int addSpawnPoint(RunsafeLocation location, LivingEntity type, RunsafeEntityEquipment equipment)
	{


		Mob mob = new Mob(type, equipment);

		SpawnPoint spawnPoint = getSpawnPointAtLocation(location);
		if (spawnPoint != null)
		{
			spawnPoint.addMob(mob);
			return -1;
		}

		ArrayList<Mob> mobs = new ArrayList<Mob>();

		mobs.add(mob);
		spawns.put(lastId + 1, new SpawnPoint(location, DEFAULT_RANGE, mobs));
		lastId++;
		return lastId;

	}

	public void SpawnPulse()
	{
		console.fine("SpawnPulse...");
		for (int id : spawns.keySet())
		{
			boolean loaded = spawns.get(id).isChunkLoaded();
			int amount = spawns.get(id).getAmountEntitiesNearby();
			console.fine(String.format("[%b - %d] %d", loaded, amount, id));
			if(loaded && amount  <= MAX_AMOUNT)
			{
				spawns.get(id).spawn(AMOUNT_SPAWN);
				console.fine("Spawning for id: " + id);
			}
		}
	}

	public Set<Integer> getIds()
	{
		return spawns.keySet();
	}

	public boolean remove(int id)
	{
		if (!spawns.containsKey(id))
			return false;
		spawns.remove(id);

		return true;
	}

	public void clear()
	{
		spawns.clear();
	}

	public SpawnPoint getSpawnPointAtLocation(RunsafeLocation location)
	{
		for (int id : spawns.keySet())
			if(spawns.get(id).isSameLocation(location))
				return spawns.get(id);
		return null;
	}



	private ConcurrentHashMap<Integer, SpawnPoint> spawns = new ConcurrentHashMap<Integer, SpawnPoint>();

	public final int AMOUNT_SPAWN = 2;
	public final int DEFAULT_RANGE = 128;
	public final int MAX_AMOUNT = 4;
	public final int SPAWN_PULSE_COOLDOWN = 30;

	private int lastId = 0;

	private final IOutput console;

}
