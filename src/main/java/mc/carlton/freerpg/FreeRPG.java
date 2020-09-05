package mc.carlton.freerpg;

import mc.carlton.freerpg.brewingEvents.BrewingInventoryClick;
import mc.carlton.freerpg.brewingEvents.FinishedBrewing;
import mc.carlton.freerpg.clickEvents.PlayerLeftClick;
import mc.carlton.freerpg.clickEvents.PlayerLeftClickDeveloper;
import mc.carlton.freerpg.clickEvents.PlayerRightClick;
import mc.carlton.freerpg.clickEvents.PlayerRightClickEntity;
import mc.carlton.freerpg.combatEvents.*;
import mc.carlton.freerpg.commands.*;
import mc.carlton.freerpg.enchantingEvents.*;
import mc.carlton.freerpg.furnaceEvents.FurnaceBurn;
import mc.carlton.freerpg.furnaceEvents.FurnaceInventoryClick;
import mc.carlton.freerpg.furnaceEvents.FurnaceSmelt;
import mc.carlton.freerpg.globalVariables.*;
import mc.carlton.freerpg.guiEvents.*;
import mc.carlton.freerpg.leaveAndJoin.LoginProcedure;
import mc.carlton.freerpg.leaveAndJoin.LogoutProcedure;
import mc.carlton.freerpg.miscEvents.*;
import mc.carlton.freerpg.leaveAndJoin.PlayerJoin;
import mc.carlton.freerpg.leaveAndJoin.PlayerLeave;
import mc.carlton.freerpg.pistonEvents.PistonExtend;
import mc.carlton.freerpg.pistonEvents.PistonRetract;
import mc.carlton.freerpg.playerAndServerInfo.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class FreeRPG extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Plugin plugin = FreeRPG.getPlugin(FreeRPG.class);
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //config Load
        ConfigLoad loadConfig = new ConfigLoad();
        loadConfig.initializeConfig();

        //Saves Custom Languages YAML file
        saveResource("languages.yml",false);
        LanguagesYMLManager languagesYMLManager = new LanguagesYMLManager();
        languagesYMLManager.checkLanguagesYML();

        //Initialize Placed Blocks Map
        PlacedBlocksManager placedBlocksManager = new PlacedBlocksManager();
        placedBlocksManager.startConditions();
        placedBlocksManager.initializePlacedBlocks();


        //Check if the server uses world guard
        WorldGuardChecks CheckWorldGuardExistence = new WorldGuardChecks();
        CheckWorldGuardExistence.initializeWorldGuardPresent();

        //Initializes all "global" variables
        MinecraftVersion minecraftVersion = new MinecraftVersion();
        minecraftVersion.initializeVersion();
        EntityGroups entityGroups = new EntityGroups();
        entityGroups.initializeAllEntityGroups();
        ExpMaps expMaps = new ExpMaps();
        expMaps.initializeAllExpMaps();
        ItemGroups itemGroups =  new ItemGroups();
        itemGroups.initializeItemGroups();
        CraftingRecipes craftingRecipes = new CraftingRecipes();
        craftingRecipes.initializeAllCraftingRecipes();
        StringsAndOtherData stringsAndOtherData = new StringsAndOtherData();
        stringsAndOtherData.initializeData();


        //Initiliazes periodically saving stats
        PeriodicSaving saveStats = new PeriodicSaving();
        saveStats.periodicallySaveStats();

        //Events
        ConfigLoad configLoad = new ConfigLoad();
        if (!configLoad.isSaveRunTimeData()) {
            getServer().getPluginManager().registerEvents(new PlayerLeftClick(), this);
            getServer().getPluginManager().registerEvents(new PlayerBlockBreak(), this);
        }
        else{
            getServer().getPluginManager().registerEvents(new PlayerLeftClickDeveloper(), this);
            getServer().getPluginManager().registerEvents(new PlayerBlockBreakDeveloper(), this);
        }
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
        getServer().getPluginManager().registerEvents(new MainGUIclick(), this);
        getServer().getPluginManager().registerEvents(new SkillsGUIclick(), this);
        getServer().getPluginManager().registerEvents(new CraftingGUIclick(), this);
        getServer().getPluginManager().registerEvents(new ConfirmationGUIClick(), this);
        getServer().getPluginManager().registerEvents(new ConfigurationGUIClick(), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockPlace(), this);
        getServer().getPluginManager().registerEvents(new PlayerRightClick(), this);
        getServer().getPluginManager().registerEvents(new PlayerTakeDamage(), this);
        getServer().getPluginManager().registerEvents(new PlayerCraft(), this);
        getServer().getPluginManager().registerEvents(new PlayerPrepareCrafting(), this);
        getServer().getPluginManager().registerEvents(new EntityHitEntity(), this);
        getServer().getPluginManager().registerEvents(new PistonExtend(), this);
        getServer().getPluginManager().registerEvents(new PistonRetract(), this);
        getServer().getPluginManager().registerEvents(new PlayerKillEntity(), this);
        getServer().getPluginManager().registerEvents(new PlayerConsumeItem(), this);
        getServer().getPluginManager().registerEvents(new PlayerRightClickEntity(), this);
        getServer().getPluginManager().registerEvents(new PlayerShear(), this);
        getServer().getPluginManager().registerEvents(new PlayerFish(), this);
        getServer().getPluginManager().registerEvents(new PlayerShootBow(), this);
        getServer().getPluginManager().registerEvents(new ArrowLand(), this);
        getServer().getPluginManager().registerEvents(new EntityGetDamaged(), this);
        getServer().getPluginManager().registerEvents(new TameEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerMount(), this);
        getServer().getPluginManager().registerEvents(new PlayerDismount(), this);
        getServer().getPluginManager().registerEvents(new PlayerBreedEntity(), this);
        getServer().getPluginManager().registerEvents(new PlayerToggleSprint(), this);
        getServer().getPluginManager().registerEvents(new BrewingInventoryClick(), this);
        getServer().getPluginManager().registerEvents(new PotionSplash(), this);
        getServer().getPluginManager().registerEvents(new LingeringPotionSplash(), this);
        getServer().getPluginManager().registerEvents(new FinishedBrewing(), this);
        getServer().getPluginManager().registerEvents(new PlayerGetExperience(), this);
        getServer().getPluginManager().registerEvents(new PrepareEnchanting(), this);
        getServer().getPluginManager().registerEvents(new PrepareRepair(), this);
        getServer().getPluginManager().registerEvents(new AnvilClick(), this);
        getServer().getPluginManager().registerEvents(new PlayerEnchant(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new FurnaceSmelt(), this);
        getServer().getPluginManager().registerEvents(new FurnaceInventoryClick(), this);
        getServer().getPluginManager().registerEvents(new FurnaceBurn(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItem(), this);
        getServer().getPluginManager().registerEvents(new ExperienceBottleBreak(), this);
        getServer().getPluginManager().registerEvents(new SkillsConfigGUIClick(), this);
        getServer().getPluginManager().registerEvents(new EntityPickUpItem(), this);

        getCommand("frpg").setExecutor(new FrpgCommands());
        getCommand("spite").setExecutor(new SpiteQuote());

        //If the plugin starts with players online
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerStats pStatClass = new PlayerStats(p);
            if (pStatClass.isPlayerRegistered()) {
                LogoutProcedure logout = new LogoutProcedure(p);
                try {
                    logout.playerLogout(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            LoginProcedure login = new LoginProcedure(p);
            login.playerLogin();
        }
    }

    public void onDisable() {
        //Does everything that would normally be done if a player were to log out
        for (Player p : Bukkit.getOnlinePlayers()) {
            LogoutProcedure logout = new LogoutProcedure(p);
            try {
                logout.playerLogout(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        PlacedBlocksManager saveBlocks = new PlacedBlocksManager();
        saveBlocks.writePlacedBlocks();

        ConfigLoad configLoad = new ConfigLoad();
        if (configLoad.isSaveRunTimeData()) {
            RunTimeData runTimeData = new RunTimeData();
            runTimeData.logRunTimeData();
        }
    }
}
