package infiniterocket.infiniterocket.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import infiniterocket.infiniterocket.InfiniteRocket;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RocketCommands implements CommandExecutor, TabCompleter {
    private InfiniteRocket ir;

    public RocketCommands(InfiniteRocket ir) {
        this.ir = ir;
        ir.getCommand("infiniterocket").setExecutor(this);
        ir.getCommand("infiniterocket").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("infiniterocket")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("give")) {
                if (args.length > 1) {
                    if (sender.hasPermission("infiniterocket.command")) {
                        Player p = Bukkit.getPlayer(args[1]);

                        ItemStack firework = new ItemStack(Material.FIREWORK_ROCKET, 1);
                        ItemMeta im = firework.getItemMeta();

                        if (im != null) {
                            im.setCustomModelData(8998621);
                            List<String> lore = new ArrayList<>();
                            lore.add(ChatColor.DARK_AQUA + "Infinite Firework");
                            im.setLore(lore);
                            firework.setItemMeta(im);
                        } else {
                            ir.getLogger().warning("[Infinite Rocket] item was null!");
                            return false;
                        }

                        if (p != null) {
                            p.getInventory().addItem(firework);
                        } else {
                            ir.getLogger().warning("[Infinite Rocket] player was null!");
                            return false;
                        }
                        ir.getLogger().info("[Infinite Rocket] Gave " + p.getName()
                                + " an Infinite Rocket!");
                    } else {
                        sender.sendMessage("You do not have permission to give yourself an " +
                                "infinite rocket!");
                    }
                }
            } else {
                sender.sendMessage("[" + ChatColor.DARK_AQUA + "InfiniteRocket" + ChatColor.RESET + "]" + ChatColor.COLOR_CHAR + "6" +
                        "No Player Given!");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias,
                                      String[] args) {
        if (command.getName().equalsIgnoreCase("infiniterocket")) {
            if (args.length == 1) return Collections.singletonList("give");
            return sender.getServer().getOnlinePlayers().stream().map(CommandSender::getName)
                    .collect(Collectors.toList());
        } else return null;
    }
}
