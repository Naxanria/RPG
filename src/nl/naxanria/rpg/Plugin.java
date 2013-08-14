package nl.naxanria.rpg;

import nl.naxanria.rpg.command.admin.CommandSetSpawn;
import nl.naxanria.rpg.command.admin.CommandSpawnCreature;
import nl.naxanria.rpg.command.base.CommandRpg;
import nl.naxanria.rpg.command.base.CommandSpawn;
import nl.naxanria.rpg.party.command.CommandPartyChat;
import nl.naxanria.rpg.party.command.CommandPartyInvite;
import nl.naxanria.rpg.party.command.CommandPartyJoin;
import nl.naxanria.rpg.database.PlayerLocationRepository;
import nl.naxanria.rpg.party.command.CommandPartyLeave;
import nl.naxanria.rpg.party.handler.PartyHandler;
import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.api.command.Command;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void PluginSetup()
	{

		//database
		addComponent(PlayerLocationRepository.class);
		//handlers
		addComponent(PartyHandler.class);


		//events

		//commands
		addComponent(CommandSpawn.class);
		addComponent(CommandSetSpawn.class);
		addComponent(CommandRpg.class);
		addComponent(CommandSpawnCreature.class);

		//party command
		Command party = new Command("party", "party functions", "rpg.party");
		party.addSubCommand(getInstance(CommandPartyJoin.class));
		party.addSubCommand(getInstance(CommandPartyInvite.class));
		party.addSubCommand(getInstance(CommandPartyLeave.class));

		addComponent(party);

		addComponent(CommandPartyChat.class);

	}
}
