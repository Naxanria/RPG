package nl.naxanria.rpg.command.admin;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.Map;

public class CommandSetSpawn extends PlayerCommand
{

	public CommandSetSpawn(IConfiguration configuration)
	{

		super("setspawn", "Sets the spawn", "rpg.command.admin.set-spawn");

		this.configuration = configuration;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters) {

		configuration.setConfigValue("spawn.world", executor.getWorld().getName());
		configuration.setConfigValue("spawn.x", executor.getLocation().getX());
		configuration.setConfigValue("spawn.y", executor.getLocation().getY());
		configuration.setConfigValue("spawn.z", executor.getLocation().getZ());
		configuration.setConfigValue("spawn.pitch", executor.getLocation().getPitch());
		configuration.setConfigValue("spawn.yaw", executor.getLocation().getYaw());

		configuration.save();

		return "&3Saved the spawn as &f" + executor.getLocation().toString();
	}

	private final IConfiguration configuration;

}
