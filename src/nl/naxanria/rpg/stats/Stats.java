package nl.naxanria.rpg.stats;

import java.util.HashMap;

public class Stats
{

	public Stats()
	{
		defaultStats();
	}

	private void defaultStats()
	{
		stats = new HashMap<Stat, Double>();

		for(Stat stat : Stat.values())
			stats.put(stat, 0d);
	}

	public double getStat(Stat stat)
	{
		return stats.get(stat);
	}

	public double setStat(Stat stat, double value)
	{
		return stats.put(stat, value);
	}

	private HashMap<Stat, Double> stats;
}
