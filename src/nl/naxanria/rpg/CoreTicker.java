package nl.naxanria.rpg;

import nl.naxanria.rpg.handler.PlayerHealthHandler;
import nl.naxanria.rpg.spawning.handler.SpawnHandler;
import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.event.plugin.IPluginEnabled;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class CoreTicker implements IPluginEnabled, IConfigurationChanged
{

	public CoreTicker(IScheduler scheduler, PlayerHealthHandler playerHealthHandler, SpawnHandler spawnHandler)
	{
		this.scheduler = scheduler;
		this.playerHealthHandler = playerHealthHandler;
		this.spawnHandler = spawnHandler;
	}


	public void tick()
	{
		this.playerHealthHandler.decreaseCombatCooldownAll();
		ticker++;
		if(ticker % 5 == 0)
			this.playerHealthHandler.regenAll();
		if (ticker % healtUpdateTicks == 0)
			for (RunsafePlayer player : RunsafeServer.Instance.getWorld(world).getPlayers())
				player.setLevel((int) this.playerHealthHandler.getHealth(player));
		if(ticker % spawnHandler.SPAWN_PULSE_COOLDOWN == 0)
			spawnHandler.SpawnPulse();

		this.scheduler.startSyncTask(
				new Runnable() {
					@Override
					public void run() {
						tick();
					}
				}, 1
		);

	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		world = configuration.getConfigValueAsString("world");
	}

	@Override
	public void OnPluginEnabled()
	{
		int id = this.scheduler.startSyncTask(
				new Runnable() {
					@Override
					public void run() {
						tick();
					}
				}, 1
		);

	}

	private final IScheduler scheduler;
	private final PlayerHealthHandler playerHealthHandler;
	private final SpawnHandler spawnHandler;

	private int healtUpdateTicks = 3;
	private int ticker = 0;

	private String world;

}
