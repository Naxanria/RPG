package nl.naxanria.rpg;

import nl.naxanria.rpg.base.BaseDamage;
import nl.naxanria.rpg.command.admin.*;
import nl.naxanria.rpg.command.base.CommandDebug;
import nl.naxanria.rpg.command.base.CommandRpg;
import nl.naxanria.rpg.command.base.CommandSpawn;
import nl.naxanria.rpg.event.*;
import nl.naxanria.rpg.handler.DebugHandler;
import nl.naxanria.rpg.handler.MobHealthHandler;
import nl.naxanria.rpg.handler.PlayerHealthHandler;
import nl.naxanria.rpg.party.command.*;
import nl.naxanria.rpg.database.PlayerLocationRepository;
import nl.naxanria.rpg.party.handler.PartyHandler;
import nl.naxanria.rpg.spawning.command.CommandAddSpawner;
import nl.naxanria.rpg.spawning.command.CommandRemoveSpawner;
import nl.naxanria.rpg.spawning.command.SpawnerCommand;
import nl.naxanria.rpg.spawning.handler.SpawnHandler;
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
		addComponent(BaseDamage.class);
		addComponent(SpawnHandler.class);


		//events
		addComponent(PlayerDamage.class);
		addComponent(EntityDamageByEntityEvent.class);
		addComponent(PlayerJoinsRpgWorld.class);
		addComponent(PlayerRespawn.class);
		addComponent(EntityDeathEvent.class);


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

		SpawnerCommand spawner = new SpawnerCommand("spawner", "Spawning stuff", "rpg.spawner");
		spawner.addSubCommand(getInstance(CommandAddSpawner.class));
		spawner.addSubCommand(getInstance(CommandRemoveSpawner.class));

		addComponent(spawner);

		//update ticker
		addComponent(CoreTicker.class);

	}
}
