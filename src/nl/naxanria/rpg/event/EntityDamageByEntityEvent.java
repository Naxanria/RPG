package nl.naxanria.rpg.event;

import nl.naxanria.rpg.PlayerHealthHandler;
import nl.naxanria.rpg.party.handler.PartyHandler;
import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.event.entity.IEntityDamageByEntityEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class EntityDamageByEntityEvent implements IEntityDamageByEntityEvent, IConfigurationChanged
{

	public EntityDamageByEntityEvent(PartyHandler partyHandler, IOutput console, PlayerHealthHandler playerHealthHandler)
	{
		this.partyHandler = partyHandler;
		this.console = console;
		this.playerHealthHandler = playerHealthHandler;
	}

	@Override
	public void OnEntityDamageByEntity(RunsafeEntityDamageByEntityEvent event)
	{
		if(!event.getEntity().getWorld().getName().equalsIgnoreCase(world))
			return;

		RunsafeEntity damageActor = event.getDamageActor();
		RunsafeEntity damaged = event.getEntity();

		if(damageActor instanceof RunsafePlayer  && damaged instanceof RunsafePlayer)
		{

			if (partyHandler.isInSameParty((RunsafePlayer) damageActor, (RunsafePlayer) damaged))
			{
				try {
					event.cancel();
				}catch (Exception e){
					return;
				}
			}


			double damage = 3;

			RunsafeMeta hittingItem = ((RunsafePlayer) damageActor).getItemInHand();
			if (hittingItem == null) // fist
				damage = 1;
			else
				if (hittingItem.hasLore() && hittingItem.getLore() != null)
				 //get actual intended damage
					for (String line : hittingItem.getLore())
						if (line != null && line.contains("dmg"))
						{
							damage = Double.valueOf(line.split("§f")[1]);
							break;
						}
			playerHealthHandler.updateHealth((RunsafePlayer) damaged, -damage);
			try {
				event.cancel();
			}catch (Exception e)
			{
				return;
			}
		}


	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		world = configuration.getConfigValueAsString("world");
	}

	private String world;
	private final PartyHandler partyHandler;
	private final PlayerHealthHandler playerHealthHandler;
	private final IOutput console;



}
