package beterbee.beterbee;

import jdk.javadoc.internal.doclint.HtmlTag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Random;

public final class BeterBee extends JavaPlugin implements Listener {

    Boolean debag = false;
    public int PoziomMiodurozmnazania = 3;
    public int SzansaNaRozmnożenie = 10;


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
            if (args[0].equals("atak")){
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null)
                {
                    return false;
                }

                World world = target.getWorld();
                double x = 0,y = 0,z = 0;
                Location location = target.getLocation();


                Random random = new Random();

                world.spawnEntity(location.add(1, random.nextGaussian(1, 10),0),EntityType.BEE);
                return true;

            }
            if (args[0].equals("debag")){
                debag=true;
                sender.sendMessage("Debag bzzz włonczono bzzzzz");
                return true;
            }

            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (args[0].equals("miud")){
                    Block bl = player.getTargetBlock(10);
                    if (bl.getType() == Material.BEEHIVE)
                    {
                        Beehive ul = (Beehive) bl.getBlockData();
                        player.sendMessage("Ten ul ma "+ul.getHoneyLevel()+" miodu!");
                        return true;
                    }
                    else {
                        player.sendMessage("Można zajrzeć tylko do własnoręcznie wykonanyh pasiek");
                    }
                }


            }
            else {
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


                     if (ul.getHoneyLevel() == PoziomMiodurozmnazania ) {
                         if(bee.isAdult()){
                             Random random = new Random();
                             if (random.nextInt(1,SzansaNaRozmnożenie)==1){


                                 //jeśli pszcoła będzie czuła miłość zrobi to
                                if(bee.canBreed())
                                {
                                    bee.setLoveModeTicks(400);
                               }
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
                  ul.setHoneyLevel(0);


              }
          }

        if (spawnEvent.getEntity().getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM){
            Bee bee = (Bee) spawnEvent.getEntity();
            bee.setAnger(100);
            bee.setCanPickupItems(true);
            bee.setCustomNameVisible(true);
            bee.setCustomName("Ognista Pszczoła");
            bee.setFireTicks(800);
            bee.setGlowing(true);

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
