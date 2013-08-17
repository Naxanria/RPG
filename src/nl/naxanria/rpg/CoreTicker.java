package nl.naxanria.rpg;

import nl.naxanria.rpg.handler.PlayerHealthHandler;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.event.plugin.IPluginEnabled;

public class CoreTicker implements IPluginEnabled
{

	public CoreTicker(IScheduler scheduler, PlayerHealthHandler playerHealthHandler)
	{
		this.scheduler = scheduler;
		this.playerHealthHandler = playerHealthHandler;
	}


	public void tick()
	{
		this.playerHealthHandler.regenAll();
		this.playerHealthHandler.decreaseCombatCooldownAll();

		this.scheduler.startAsyncTask(
				new Runnable() {
					@Override
					public void run() {
						tick();
					}
				}, 1
		);

	}

	@Override
	public void OnPluginEnabled()
	{
		int id = this.scheduler.startAsyncTask(
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

}
