package beterbee.beterbee;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class BeterBee extends JavaPlugin implements Listener {

    Boolean debag = true;


    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info( "Pszczoły zostały uzbrojaone");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("Bee")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage("Bzzzzzzz!!!");
                return true;
            } else {
                sender.sendMessage("Bzzzzzzz!!!");
                return true;
            }
        }
        return false;


    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpawn(CreatureSpawnEvent spawnEvent){
        if (debag){
            if(Objects.equals(spawnEvent.getEntity().toString(), "CraftBee")){
                getLogger().info(spawnEvent.getEventName()+"//"+spawnEvent.getEntity() + "//"+spawnEvent.getEntity().getEntitySpawnReason());}
        }
        ///////////////////////////////
        if (spawnEvent.getEntity().getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.BEEHIVE ||spawnEvent.getEntity().getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG
        && Objects.equals(spawnEvent.getEntity().toString(), "CraftBee")){
            try {
                Bee bee = (Bee) spawnEvent.getEntity();

                 if (debag){getLogger().info( "[bee]" + bee.toString());}

                 Location ulLoc =   bee.getHive();
                 if(ulLoc !=null) {

                     if (debag) {
                         getLogger().info("[bee ul]" + ulLoc.toString());

                     }



                     Block ulblock = ulLoc.getBlock();

                     if (debag) {
                         getLogger().info("[bee ul]" + ulblock.getType().toString());
                     }

                     Beehive ul = (Beehive) ulblock.getBlockData();


                     if (ul.getHoneyLevel() == ul.getMaximumHoneyLevel()) {
                         if(bee.isAdult()){
                             if(bee.canBreed())
                             {

                               bee.setLoveModeTicks(400);
                             }

                         }
                         //bee.setBreed(true);
                         //bee.setBaby();

                         if (debag) {
                             getLogger().info("Pszczoła w roju");
                         }

                     }
                 }
            } catch (Exception e) {
                getLogger().info( "Błąd " + e.toString());
                throw new RuntimeException(e);
            }

        }

          if (spawnEvent.getEntity().getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING && Objects.equals(spawnEvent.getEntity().toString(), "CraftBee"))
          {
            Bee bee = (Bee) spawnEvent.getEntity();

            if (debag){getLogger().info( "[bee kids sp]" + bee.getHive().toString());}

              Location ulLoc =   bee.getHive();
              if(ulLoc !=null) {

                  if (debag) {
                      getLogger().info("[bee ul]" + ulLoc.toString());

                  }


                  Block ulblock = ulLoc.getBlock();


                  if (debag) {
                      getLogger().info("[bee ul]" + ulblock.getType().toString());
                  }

                  Beehive ul = (Beehive) ulblock.getBlockData();
                  ul.setHoneyLevel(ul.getHoneyLevel()-1);


              }
          }

    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityBreed(EntityBreedEvent breedEvent){
        getLogger().info("[bee kids]" + breedEvent.getMother().toString());
       // if (Objects.equals(breedEvent.getMother().toString(), "CraftBee")) {
            try {
                Bee bee = (Bee) breedEvent.getMother();
                if (debag) {
                    getLogger().info("[bee kids]" + bee.getHive().toString());
                }

                Location ulLoc = bee.getHive();
                if (ulLoc != null) {

                    if (debag) {
                        getLogger().info("[bee ul]" + ulLoc.toString());

                    }


                    Block ulblock = ulLoc.getBlock();

                    if (debag) {
                        getLogger().info("[bee kids ul]" + ulblock.getType().toString());
                    }

                    Beehive ul = (Beehive) ulblock.getBlockData();


                    if (ul.getHoneyLevel() > 0) {

                        if (debag) {
                            getLogger().info("[bee kids ul] poziom miodu" + ul.getHoneyLevel());
                        }

                        ul.setHoneyLevel(ul.getHoneyLevel() - 1);

                        if (debag) {
                            getLogger().info("[bee kids ul] poziom miodu" + ul.getHoneyLevel());
                        }
                    }

                }
            } catch (Exception e) {
                getLogger().info( "Błąd " + e.toString());
                throw new RuntimeException(e);
            }
       // }
    }
}
