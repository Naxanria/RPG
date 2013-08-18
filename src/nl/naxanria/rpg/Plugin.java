package nl.naxanria.rpg;

import nl.naxanria.rpg.command.admin.CommandSetItemDamage;
import nl.naxanria.rpg.command.admin.CommandSetMaxHealth;
import nl.naxanria.rpg.command.admin.CommandSetSpawn;
import nl.naxanria.rpg.command.admin.CommandSpawnCreature;
import nl.naxanria.rpg.command.base.CommandDebug;
import nl.naxanria.rpg.command.base.CommandRpg;
import nl.naxanria.rpg.command.base.CommandSpawn;
import nl.naxanria.rpg.event.EntityDamageByEntityEvent;
import nl.naxanria.rpg.event.PlayerDamage;
import nl.naxanria.rpg.event.PlayerJoinsRpgWorld;
import nl.naxanria.rpg.event.PlayerRespawn;
import nl.naxanria.rpg.handler.DamageHandler;
import nl.naxanria.rpg.handler.DebugHandler;
import nl.naxanria.rpg.handler.MobHealthHandler;
import nl.naxanria.rpg.handler.PlayerHealthHandler;
import nl.naxanria.rpg.party.command.*;
import nl.naxanria.rpg.database.PlayerLocationRepository;
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
		addComponent(PlayerHealthHandler.class);
		addComponent(MobHealthHandler.class);
		addComponent(DebugHandler.class);
		addComponent(DamageHandler.class);


		//events
		addComponent(PlayerDamage.class);
		addComponent(EntityDamageByEntityEvent.class);
		addComponent(PlayerJoinsRpgWorld.class);
		addComponent(PlayerRespawn.class);


		//commands
		addComponent(CommandSpawn.class);
		addComponent(CommandSetSpawn.class);
		addComponent(CommandRpg.class);
		addComponent(CommandSpawnCreature.class);
		addComponent(CommandSetItemDamage.class);
		addComponent(CommandDebug.class);
		addComponent(CommandSetMaxHealth.class);

		//party command
		Command party = new Command("party", "party functions", "rpg.party");
		party.addSubCommand(getInstance(CommandPartyJoin.class));
		party.addSubCommand(getInstance(CommandPartyInvite.class));
		party.addSubCommand(getInstance(CommandPartyLeave.class));
		party.addSubCommand(getInstance(CommandPartyKick.class));
		party.addSubCommand(getInstance(CommandPartyDisband.class));

		addComponent(party);

		addComponent(CommandPartyChat.class);

		//update ticker
		addComponent(CoreTicker.class);

	}
}
