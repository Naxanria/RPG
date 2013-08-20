package nl.naxanria.rpg.event;

import nl.naxanria.rpg.base.BaseDamage;
import nl.naxanria.rpg.base.BaseHealth;
import nl.naxanria.rpg.handler.DebugHandler;
import nl.naxanria.rpg.handler.MobHealthHandler;
import nl.naxanria.rpg.handler.PlayerHealthHandler;
import nl.naxanria.rpg.party.handler.PartyHandler;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.event.entity.IEntityDamageByEntityEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.entity.*;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class EntityDamageByEntityEvent implements IEntityDamageByEntityEvent, IConfigurationChanged
{

	public EntityDamageByEntityEvent(PartyHandler partyHandler, IOutput console, PlayerHealthHandler playerHealthHandler,
																	 MobHealthHandler mobHealthHandler, DebugHandler debugHandler)
	{
		this.partyHandler = partyHandler;
		this.console = console;
		this.playerHealthHandler = playerHealthHandler;
		this.mobHealthHandler = mobHealthHandler;
		this.debugHandler = debugHandler;
	}

	@Override
	public void OnEntityDamageByEntity(RunsafeEntityDamageByEntityEvent event)
	{
		if(!event.getEntity().getWorld().getName().equalsIgnoreCase(world))
			return;
		double damage;
		double damageFactor = 1;
		boolean projectile = false;

		RunsafeEntity damageActor = event.getDamageActor();
		RunsafeEntity damaged = event.getEntity();
		console.fine(event.getCause().name());

		if(damageActor instanceof RunsafePlayer  && damaged instanceof RunsafePlayer)
		{

			//pvp off... for now...

			debugHandler.debugMsg((RunsafePlayer) damageActor, "pvp disabled");
			debugHandler.debugMsg((RunsafePlayer) damaged, "pvp disabled");

			event.setDamage(0);
			return;

//			if (partyHandler.isInSameParty((RunsafePlayer) damageActor, (RunsafePlayer) damaged))
//			{
//				debugHandler.debugMsg((RunsafePlayer) damageActor, "canceled damage - same party");
//				debugHandler.debugMsg((RunsafePlayer) damaged, "canceled damage - same party");
//				event.setDamage(0);
//				return;
//			}
		}

		if (damageActor instanceof RunsafeProjectile)
		{
			projectile = true;
			if(event.getDamage() < 2)
				damageFactor = 0;
			else if (event.getDamage() < 5)
				damageFactor = 0.3;
			else
				damageFactor = Math.min(event.getDamage() / 10 + 0.15 , 1);


			if(((RunsafeProjectile) damageActor).getShooterPlayer() != null)
				damageActor = ((RunsafeProjectile) damageActor).getShooterPlayer();
			else if(((RunsafeProjectile)damageActor).getShooter() != null)
			{
				damageActor = ((RunsafeProjectile) damageActor).getShooter();
				damageFactor = 1;
			}
		}

		//damage calculations
		if (damageActor instanceof RunsafeLivingEntity && damaged instanceof RunsafeLivingEntity)
		{

			RunsafeMeta hittingItem = ((RunsafeLivingEntity)damageActor).getEquipment().getItemInHand();
			damage = BaseDamage.getDamage(hittingItem);
			if (projectile && !BaseDamage.isBow(hittingItem))
				damage = 1;

			damage *= damageFactor;

			double baseHealth = BaseHealth.getBaseHealth((RunsafeLivingEntity) damaged);

			if(damaged instanceof RunsafePlayer)
			{
				if (!playerHealthHandler.keepsTrackOf((RunsafePlayer) damaged))
					playerHealthHandler.addPlayer((RunsafePlayer) damaged, baseHealth, baseHealth);
				playerHealthHandler.updateHealth((RunsafePlayer) damaged, -damage);
				playerHealthHandler.startCombatCooldown((RunsafePlayer) damaged);
				event.setDamage(0);
				debugHandler.debugMsg((RunsafePlayer) damaged,
						String.format("Damage: %.1f &6<--", damage));
				if(damageActor instanceof  RunsafePlayer)
					debugHandler.debugMsg((RunsafePlayer) damageActor,
							String.format("[%.1f - %.1f] damage: %.1f &6->", playerHealthHandler.getHealth((RunsafePlayer) damaged),
									playerHealthHandler.getMaxHealth((RunsafePlayer) damaged), damage));

			}
			else
			{
				if(!mobHealthHandler.keepsTrack((RunsafeLivingEntity) damaged))
					mobHealthHandler.addEntity((RunsafeLivingEntity) damaged, baseHealth);
				mobHealthHandler.updateHealth((RunsafeLivingEntity) damaged, -damage);
				event.setDamage(0);
				if(damageActor instanceof  RunsafePlayer)
					debugHandler.debugMsg((RunsafePlayer) damageActor,
						String.format("[%.1f - %.1f] damage: %.1f", mobHealthHandler.getHealth((RunsafeLivingEntity) damaged),
								mobHealthHandler.getMaxHealth((RunsafeLivingEntity) damaged), damage));
			}

			if(damageActor instanceof RunsafePlayer)
			{
				playerHealthHandler.startCombatCooldown((RunsafePlayer) damageActor);
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
	private final MobHealthHandler mobHealthHandler;
	private final IOutput console;
	private final DebugHandler debugHandler;

}
