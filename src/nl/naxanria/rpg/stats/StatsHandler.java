package nl.naxanria.rpg.stats;

import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.HashMap;

public class StatsHandler
{


	public StatsHandler()
	{
		statMap = new HashMap<String, Stats>();
	}

	public boolean keepsTrackOf(RunsafePlayer player)
	{
		return statMap.containsKey(player.getName());
	}

	public void addPlayer(RunsafePlayer player)
	{
		if (keepsTrackOf(player))
			return;

		statMap.put(player.getName(), new Stats());
	}

	public double getStat(RunsafePlayer player, Stat stat)
	{
		return statMap.get(player.getName()).getStat(stat);
	}

	public void setStat(RunsafePlayer player, Stat stat, double value)
	{
		statMap.get(player.getName()).setStat(stat, value);
	}

	private HashMap<String, Stats> statMap;

}
