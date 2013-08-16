package nl.naxanria.rpg.command.admin;

import no.runsafe.framework.api.command.argument.OptionalArgument;
import no.runsafe.framework.api.command.argument.PlayerArgument;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.Buff;
import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.RunsafeLivingEntity;
import no.runsafe.framework.minecraft.inventory.RunsafeEntityEquipment;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandSpawnCreature extends PlayerCommand
{

	public CommandSpawnCreature()
	{
		super("spawncreature", "Spawn a creature", "rpg.command.admin.creature", new RequiredArgument("type"),
				new OptionalArgument("amount"), new OptionalArgument("useMyGear"), new PlayerArgument(false));

		types = new ArrayList<String>();
		for (LivingEntity entity :LivingEntity.values())
			types.add(entity.getName());

	}

	@Nullable
	@Override
	public List<String> getParameterOptions(@Nonnull String parameter) {
		ArrayList<String> out = new ArrayList<String>();

		if(parameter.equalsIgnoreCase("type"))
		{
			out = types;
			console.fine(types.toString());

			return out;
		}
		else if(parameter.equalsIgnoreCase("useMyGear"))
		{
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
		location.incrementY(2);
		String type = parameters.get("type");
		int amount = ((parameters.containsKey("amount")) ? Integer.valueOf(parameters.get("amount")) : 1);
		boolean usePlayerGear = (parameters.containsKey("useMyGear") && Boolean.valueOf(parameters.get("useMyGear")));
		if(parameters.containsKey("player"))
		{
			RunsafePlayer target = RunsafeServer.Instance.getPlayer(parameters.get("player"));
			if(target != null && target.isOnline())
				location = target.getLocation();
		}

		for (int i = 0; i < amount; i++)
		{
			RunsafeLivingEntity entity = (RunsafeLivingEntity)  executor.getWorld().spawnCreature(location, type);

			if(usePlayerGear)
			{
				RunsafeEntityEquipment equipment = executor.getEquipment();
				entity.getEquipment()
						.setArmorContents(equipment.getArmorContents())
						.setItemInHand(equipment.getItemInHand());
			}
		}

		return null;
	}

	private ArrayList<String> types;

}
