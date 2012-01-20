package com.github.doughsay.CraftIRCDeath;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import com.ensifera.animosity.craftirc.CraftIRC;
import com.ensifera.animosity.craftirc.RelayedMessage;

public class DeathListener extends EntityListener {

    private CraftIRC craftIrc;
	private DeathPoint deathPoint;

    public DeathListener(CraftIRC craftIrc, DeathPoint deathPoint) {
        this.craftIrc = craftIrc;
        this.deathPoint = deathPoint;
    }

    public void onEntityDeath(EntityDeathEvent event) {
        String msg = "";

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            String name = player.getDisplayName();

            if (player.getLastDamageCause() != null) {
                EntityDamageEvent lastDamageEvent = player.getLastDamageCause();
                DamageCause cause = lastDamageEvent.getCause();

                if (lastDamageEvent instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent lastDamageByEntityEvent = (EntityDamageByEntityEvent) lastDamageEvent;
                    Entity damager = lastDamageByEntityEvent.getDamager();

                    if (damager instanceof Skeleton) {
                        LivingEntity livingEntity = (LivingEntity) damager;
                        msg = " was shot by " + getNameFromLivingEntity(livingEntity);
                    } else if (cause.equals(DamageCause.ENTITY_EXPLOSION)) {
                        msg = " blew up";
                    } else if (damager instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) damager;
                        msg = " was slain by " + getNameFromLivingEntity(livingEntity);
                    } else {
                        msg = " died";
                    }
                } else if (lastDamageEvent instanceof EntityDamageByBlockEvent) {
                    EntityDamageByBlockEvent lastDamageByBlockEvent = (EntityDamageByBlockEvent) lastDamageEvent;
                    Block damager = lastDamageByBlockEvent.getDamager();

                    if (cause.equals(DamageCause.CONTACT)) {
                        if (damager.getType() == Material.CACTUS) {
                            msg = " was pricked to death";
                        } else {
                            msg = " died";
                        }
                    } else if (cause.equals(DamageCause.LAVA)) {
                        msg = " tried to swim in lava";
                    } else if (cause.equals(DamageCause.VOID)) {
                        msg = " fell out of the world";
                    } else {
                        msg = " died";
                    }
                } else {
                    if (cause.equals(DamageCause.FIRE)) {
                        msg = " went up in flames";
                    } else if (cause.equals(DamageCause.FIRE_TICK)) {
                        msg = " burned to death";
                    } else if (cause.equals(DamageCause.SUFFOCATION)) {
                        msg = " suffocated in a wall";
                    } else if (cause.equals(DamageCause.DROWNING)) {
                        msg = " drowned";
                    } else if (cause.equals(DamageCause.STARVATION)) {
                        msg = " starved to death";
                    } else if (cause.equals(DamageCause.FALL)) {
                        msg = " hit the ground too hard";
                    } else {
                        msg = " died";
                    }
                }
            } else {
                msg = " died";
            }

            if (!msg.isEmpty()) {
                Location location = player.getLocation();
                sendToCraftIRC(msg, name, location);

                //this.logger.info(msg + " ([" + location.getWorld().getName() + "] " + ((int) location.getX()) + ", " + ((int) location.getY()) + ", " + ((int) location.getZ()) + ")");
            }
        }
    }

    private void sendToCraftIRC(String msg, String name, Location location) {
    	RelayedMessage rMsg = craftIrc.newMsg(deathPoint, null, "death");
		rMsg.setField("message", msg);
		rMsg.setField("player", name);
		rMsg.setField("world", location.getWorld().getName());
		rMsg.post();
    }

    public static String getNameFromLivingEntity(LivingEntity livingEntity) {
        String name = "";

        if (livingEntity instanceof Player) {
            name = ((Player) livingEntity).getDisplayName();
        } else {
            name = livingEntity.toString().substring(5);
        }

        return name;
    }
}
