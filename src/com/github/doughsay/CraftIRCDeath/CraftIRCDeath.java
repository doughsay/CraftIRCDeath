package com.github.doughsay.CraftIRCDeath;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ensifera.animosity.craftirc.CraftIRC;

public class CraftIRCDeath extends JavaPlugin {

	private Logger log = Logger.getLogger("Minecraft");
	private String logName;
	private String version;
	private CraftIRC craftIrc;
    private DeathListener deathListener;

    public void onEnable() {
    	logName = getDescription().getName();
		version = getDescription().getVersion();

        PluginManager pm = getServer().getPluginManager();
        this.craftIrc = (CraftIRC)pm.getPlugin("CraftIRC");

        if(craftIrc == null || !craftIrc.isEnabled()) {
			log.info("["+logName+"] CraftIRC not loaded, disabling plugin.");
			pm.disablePlugin(((org.bukkit.plugin.Plugin) (this)));
			return;
		}

        Plugin multiverse = pm.getPlugin("Multiverse-Core");

        DeathPoint deathPoint = new DeathPoint();
        craftIrc.registerEndPoint("death", deathPoint);

        deathListener = new DeathListener(craftIrc, deathPoint, multiverse);
        pm.registerEvent(Event.Type.ENTITY_DEATH, deathListener, Event.Priority.Monitor, this);

        log.info("["+logName+"] version "+version+" enabled!");
    }

    public void onDisable() {
    	log.info("["+logName+"] disabled.");
    }
}
