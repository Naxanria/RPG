package nl.naxanria.rpg.spawning.command;

import nl.naxanria.rpg.spawning.handler.SpawnHandler;
import no.runsafe.framework.api.command.argument.OptionalArgument;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.inventory.RunsafeEntityEquipment;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandAddSpawner extends PlayerCommand
{


	public CommandAddSpawner(SpawnHandler spawnHandler)
	{
		super("add", "add a spawnpoint", "rpg.command.admin.spawner", new RequiredArgument("type"), new OptionalArgument("useEquipment"));
		this.spawnHandler = spawnHandler;

		types = new ArrayList<String>();
		for (LivingEntity entity :LivingEntity.values())
		{
			if (entity.getName() == null)
				continue;
			types.add(entity.getName());
		}
	}

	@Nullable
	@Override
	public List<String> getParameterOptions(@Nonnull String parameter) {
		if (parameter.equalsIgnoreCase("type"))
			return types;

		if (parameter.equalsIgnoreCase("useEquipment"))
		{
			ArrayList<String> out = new ArrayList<String>();
			out.add("true");
			out.add("false");
			return out;
		}

		return super.getParameterOptions(parameter);
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{

		RunsafeLocation location = executor.getTarget().getLocation();
		if (location == null)
			return "&cOut of range!";

		LivingEntity type = LivingEntity.valueOf(parameters.get("type"));
		if (type == null || type == LivingEntity.Player)
			return "&cInvalid type: &f" + parameters.get("type");

		boolean useEquipment = (parameters.containsKey("useEquipment") && Boolean.valueOf(parameters.get("useEquipment")));
		RunsafeEntityEquipment equipment = (useEquipment) ? executor.getEquipment() : null;

		int id = spawnHandler.addSpawnPoint(location, type, equipment);


		return String.format("&2Added a new mob spawn. &3Location: &f%s &3Id: &f%d &3Type: &f%s &3Equipment: &f%b", location.toString(), id, type.name(), useEquipment);
	}


	private final SpawnHandler spawnHandler;
	private final ArrayList<String> types;

}
