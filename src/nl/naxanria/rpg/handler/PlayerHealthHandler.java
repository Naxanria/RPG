package nl.naxanria.rpg.handler;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerHealthHandler implements IConfigurationChanged
{

	public PlayerHealthHandler(IOutput console)
	{
		healthMap = new ConcurrentHashMap<String, HashMap<String, Double>>();
		inCombat = new ConcurrentHashMap<String, Integer>();
		players = new ConcurrentHashMap<String, RunsafePlayer>();

		this.console = console;

	}

	public boolean keepsTrackOf(RunsafePlayer player)
	{
		return healthMap.containsKey(player.getName());
	}

	public void addPlayer(RunsafePlayer player)
	{
		addPlayer(player, baseHealth, baseHealth, baseRegen);
	}

	public void addPlayer(RunsafePlayer player, double health, double maxHealth)
	{
		addPlayer(player, health, maxHealth, baseRegen);
	}


	public void addPlayer(RunsafePlayer player, double health, double maxHealth, double regen)
	{
		if (keepsTrackOf(player))
			return;
		HashMap<String, Double> playerMap = new HashMap<String, Double>();
		playerMap.put("health", health);
		playerMap.put("maxHealth", maxHealth);
		playerMap.put("regen", regen);
		playerMap.put("regenCountdown", 5d);

		healthMap.put(player.getName(), playerMap);

		inCombat.put(player.getName(), 0);

		players.put(player.getName(), player);

		player.setLevel((int) health);
	}

	public void updateHealth(RunsafePlayer player, double change)
	{
		if (!keepsTrackOf(player))
			addPlayer(player);

		double currentHealth = healthMap.get(player.getName()).get("health");
		double maxHealth = healthMap.get(player.getName()).get("maxHealth");

		currentHealth += change;
		if (currentHealth > maxHealth) currentHealth = maxHealth;
		if (currentHealth <= 0) player.setHealth(0f); //kill the player

		healthMap.get(player.getName()).put("health", currentHealth);

		player.setLevel((int) currentHealth);

	}

	public void setMaxHealth(RunsafePlayer player, double maxHealth)
	{
		if (!keepsTrackOf(player))
		{
			addPlayer(player, maxHealth, maxHealth);
			return;
		}

		healthMap.get(player.getName()).put("maxHealth", maxHealth);
	}

	public double getRegen(RunsafePlayer player)
	{
		return healthMap.get(player.getName()).get("regen");
	}

	public double getHealth(RunsafePlayer player)
	{
		return healthMap.get(player.getName()).get("health");
	}

	public double getMaxHealth(RunsafePlayer player)
	{
		return healthMap.get(player.getName()).get("maxHealth");
	}


	public boolean canRegen(RunsafePlayer player)
	{
		if (!keepsTrackOf(player))
		{
			addPlayer(player);
			return false;
		}

		return  (inCombat.get(player.getName()) <= 0);
	}

	public RunsafePlayer getPlayer(String name)
	{
		return (players.containsKey(name)) ? players.get(name) : null;
	}

	public void regenAll()
	{
		for(String playerName : healthMap.keySet())
		{
			RunsafePlayer player = getPlayer(playerName);
			if(canRegen(player))
				updateHealth(player, getRegen(player));
		}
	}

	public void decreaseCombatCooldownAll()
	{
		for (String playerName : inCombat.keySet())
		{
			int combatCd = inCombat.get(playerName);
			if (combatCd > 0)
			{
				inCombat.put(playerName, combatCd - 1);
				console.fine("%s cooldown is now %d", playerName, combatCd -1);
			}
		}
	}

	public void startCombatCooldown(RunsafePlayer player)
	{
		int cd = 5;
		inCombat.put(player.getName(), cd);
		console.fine("Cooldown stated");

	}


	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		baseHealth = configuration.getConfigValueAsDouble("health.base");
		baseRegen = configuration.getConfigValueAsDouble("health.regen");
	}

	private ConcurrentHashMap<String, HashMap<String, Double>> healthMap;
	private ConcurrentHashMap<String, Integer> inCombat;
	private ConcurrentHashMap<String, RunsafePlayer> players;

	private double baseHealth = 50;
	private double baseRegen = 1;

	private final IOutput console;
}
