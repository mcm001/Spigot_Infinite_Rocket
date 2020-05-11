package infiniterocket.infiniterocket.listener;

import infiniterocket.infiniterocket.InfiniteRocket;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class OnClick implements Listener {
    private final InfiniteRocket plugin;

    public OnClick(InfiniteRocket plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void OnRocketClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack item = e.getItem();

        if (item != null && item.getType() == Material.FIREWORK_ROCKET && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            // if it's the firework rocket and we're gliding
            if (meta != null && meta.getCustomModelData() == 8998621) {
                e.setUseItemInHand(Event.Result.DENY); // cancel the interaction if we're using a infinite rocket

                // if we are't gliding this is an illegal move
                if(!player.isGliding()) {
                    return;
                }

                // if we don't have an elytra on do nothing
                if (player.getInventory().getChestplate() == null && player.getInventory().getChestplate().getType() != Material.ELYTRA) {
                    return;
                }

                final int totalLifetimeTicks = 20;

                // ty minecraft; in EntityFirework.class
                new BukkitRunnable() {
                    int i = 0;

                    @Override
                    public void run() {
                        // only last the total ticks
                        if (i >= totalLifetimeTicks) {
                            this.cancel();
                            return;
                        }

                        Vector currentDir = player.getEyeLocation().getDirection();
                        Vector currVel = player.getVelocity();
                        player.setVelocity(currVel.add(new Vector(
                                currentDir.getX() * 0.1D + (currentDir.getX() * 1.5D - currVel.getX()) * 0.5D,
                                currentDir.getY() * 0.1D + (currentDir.getY() * 1.5D - currVel.getY()) * 0.5D,
                                currentDir.getZ() * 0.1D + (currentDir.getZ() * 1.5D - currVel.getZ()) * 0.5D)));

                        i ++;
                    }
                }.runTaskTimerAsynchronously(plugin, 0L, 1L);

                // Mooooooo
                player.playSound(
                        player.getLocation(), // on the player
                        Sound.ENTITY_COW_DEATH,
                        1f, 1f
                );
            }
        } else {
            // don't cancel
            e.setCancelled(false);
        }
    }

}
