package nl.naxanria.rpg.stats;

public enum Stat
{

	STRENGTH("Strength"),
	STAMINA("Stamina"),
	VITALITY("Vitality");

  Stat(String name)
	{
		this.name = name;
		this.onItemName = name.toLowerCase().substring(name.length() -3);
	}

	Stat(String name, String onItemName)
	{
		this.name = name;
		this.onItemName = onItemName;

	}

	public String getName()
	{
		return name;
	}

	public String getOnItemName()
	{
		return onItemName;
	}

	private String name;
	private String onItemName;

}
