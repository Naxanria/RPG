package nl.naxanria.rpg.command.admin;

import no.runsafe.framework.api.command.argument.IArgument;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.ArrayList;
import java.util.Map;

public class CommandSetItemDamage extends PlayerCommand
{


	public CommandSetItemDamage() {
		super("setitemdamage", "sets the damage for this item", "rpg.admin.set-item", new RequiredArgument("damage"));
	}

	@Override
	public String OnExecute(RunsafePlayer player, Map<String, String> parameters)
	{

		RunsafeMeta meta = player.getItemInHand();

		if(meta == null)
			return "&cNo item in hand!";


		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§cdmg: §f" + parameters.get("damage"));

		meta.setLore(lore);

		return null;
	}
}
