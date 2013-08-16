package nl.naxanria.rpg.database;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.database.IDatabase;
import no.runsafe.framework.api.database.IRow;
import no.runsafe.framework.api.database.ISet;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerLocationRepository extends Repository implements IConfigurationChanged
{

	public PlayerLocationRepository(IDatabase database, IOutput console)
	{
		this.database = database;
		this.console = console;
	}


	@Override
	public String getTableName()
	{
		return "playerlocations";
	}

	@Override
	public HashMap<Integer, List<String>> getSchemaUpdateQueries()
	{

		HashMap<Integer, List<String>> updates = new HashMap<Integer, List<String>>();
		String sql = "CREATE TABLE IF NOT EXISTS `playerlocations` (\n" +
				"  `player` varchar(64) NOT NULL,\n" +
				"  `location` varchar(128) NOT NULL,\n" +
				"  PRIMARY KEY (`player`)\n" +
				")";

		ArrayList<String> sqlList = new ArrayList<String>();
		sqlList.add(sql);
		updates.put(1, sqlList);
		return updates;
	}

	private boolean isNew(RunsafePlayer player)
	{
		String sql = String.format("SELECT player FROM playerlocations WHERE player='%s'", player.getName());
		console.fine("Sql: " + sql);
		ISet result = database.Query(sql);

		return (result.size() == 0);

	}

	public RunsafeLocation getPlayerLocation(RunsafePlayer player)
	{

		String sql = "SELECT player,location FROM playerlocations WHERE player = '%s'";
		IRow row = database.QueryRow(String.format(sql, player.getName()));

		if(row.isEmpty())
			return new RunsafeLocation(RunsafeServer.Instance.getWorld(rpgWorld).getRaw().getSpawnLocation());

		String[] raw = row.String("location").split(" ");

		double x = 0;
		double y = 0;
		double z = 0;
		String world = "";

		for (int i = 0; i < raw.length; i += 2)
		{
			if(raw[i].equalsIgnoreCase("world:"))
				world = raw[i+1];
			else if(raw[i].equalsIgnoreCase("x:"))
				x = Double.valueOf(raw[i+1]);
			else if(raw[i].equalsIgnoreCase("y:"))
				y = Double.valueOf(raw[i+1]);
			else if(raw[i].equalsIgnoreCase("z:"))
				z = Double.valueOf(raw[i+1]);
			else
			{
				console.write("An error has occured!");
				return new RunsafeLocation(RunsafeServer.Instance.getWorld(rpgWorld).getRaw().getSpawnLocation());
			}
		}
		return new RunsafeLocation(RunsafeServer.Instance.getWorld(world), x, y, z);
	}

	public void safePlayerLocation(RunsafePlayer player)
	{
		safePlayerLocation(player, player.getLocation());
	}

	public void safePlayerLocation(RunsafePlayer player, RunsafeLocation location) {
		String sql;
		if(isNew(player))
			sql = "INSERT INTO playerlocations (location, player) VALUES ('%s', '%s')";
		else
			sql = "UPDATE playerlocations SET location = '%s' WHERE player = '%s'";
		console.fine("Sql: " + String.format(sql, location.toString(), player.getName()));
		database.Update(String.format(sql, location.toString(), player.getName()));
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		rpgWorld = configuration.getConfigValueAsString("world");
	}

	public String getRpgWorld()
	{
		return rpgWorld;
	}

	private final IDatabase database;
	private final IOutput console;
	private String rpgWorld = "rpgworld";

}
