package nl.naxanria.rpg.database;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.internal.database.jdbc.Database;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.HashMap;
import java.util.List;

public class PlayerHealthRepository extends Repository
{

	public PlayerHealthRepository(Database database)
	{
		this.database = database;
	}

	@Override
	public String getTableName()
	{
		return null;
	}

	@Override
	public HashMap<Integer, List<String>> getSchemaUpdateQueries()
	{
		return null;
	}

	public boolean knowsPlayer(RunsafePlayer player)
	{
		return false;
	}

	public double getHealth(RunsafePlayer player)
	{
		return 0;
	}

	public void safeHealth(RunsafePlayer player)
	{

	}


	private final Database database;


}
