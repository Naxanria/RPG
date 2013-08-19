package nl.naxanria.rpg.spawning.command;

import no.runsafe.framework.api.command.Command;
import no.runsafe.framework.api.command.argument.IArgument;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SpawnerCommand extends Command
{

	public SpawnerCommand(@Nonnull String commandName, @Nonnull String description, @Nullable String permission, IArgument... arguments) {
		super(commandName, description, permission, arguments);
	}

}
