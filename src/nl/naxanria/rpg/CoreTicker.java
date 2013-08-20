package nl.naxanria.rpg;

import nl.naxanria.rpg.base.BaseHealth;
import nl.naxanria.rpg.handler.PlayerHealthHandler;
import nl.naxanria.rpg.spawning.handler.SpawnHandler;
import nl.naxanria.rpg.stats.Stat;
import nl.naxanria.rpg.stats.StatsHandler;
import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.event.plugin.IPluginEnabled;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class CoreTicker implements IPluginEnabled, IConfigurationChanged
{

	public CoreTicker(IScheduler scheduler, PlayerHealthHandler playerHealthHandler, SpawnHandler spawnHandler, StatsHandler statsHandler)
	{
		this.scheduler = scheduler;
		this.playerHealthHandler = playerHealthHandler;
		this.spawnHandler = spawnHandler;
		this.statsHandler = statsHandler;
	}


	public void tick()
	{

		ticker++;

		for (RunsafePlayer player : RunsafeServer.Instance.getWorld(world).getPlayers())
		{
			this.playerHealthHandler.decreaseCombatCooldown(player);
			this.playerHealthHandler.regen(player);
			double playerHealth = BaseHealth.getBaseHealth(player);

			if (!statsHandler.keepsTrackOf(player))
			{
				double staminaStat = statsHandler.getStat(player, Stat.STAMINA);
				double vitalityStat = statsHandler.getStat(player, Stat.VITALITY);

				double healthIncrease = staminaStat * 8.7;
				playerHealth += healthIncrease;


				double regenIncrease = vitalityStat * 0.78;
				playerHealthHandler.setRegen(player, regenIncrease + playerHealthHandler.getBaseRegen());


			}
			playerHealthHandler.setMaxHealth(player, playerHealth);
			player.setLevel((int) this.playerHealthHandler.getHealth(player));
		}
		if(ticker % spawnHandler.SPAWN_PULSE_COOLDOWN == 0)
			spawnHandler.SpawnPulse();

		id = this.scheduler.startSyncTask(
				new Runnable() {
					@Override
					public void run() {
						tick();
					}
				}, 1
		);

	}

	public void stopTicking()
	{
		scheduler.cancelTask(id);
	}

	public void startTicking()
	{
		tick();
	}

	public void restartTicking()
	{
		stopTicking();
		startTicking();
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		stopTicking();
		world = configuration.getConfigValueAsString("world");
		startTicking();
	}

	@Override
	public void OnPluginEnabled()
	{
		startTicking();
	}

	private final IScheduler scheduler;
	private final PlayerHealthHandler playerHealthHandler;
	private final SpawnHandler spawnHandler;
	private final StatsHandler statsHandler;

	private int healtUpdateTicks = 3;
	private int ticker = 0;
	int id;

	private String world;

}
