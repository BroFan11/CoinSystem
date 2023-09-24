package me.brofan11.erme.commands;

import me.brofan11.erme.ErmeAPI;
import me.brofan11.erme.utils.CoinsProvider;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TopCoinsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;

        // Check permissions
        if (!(player.hasPermission("erme.ermetop")) && !(player.hasPermission("erme.administrator"))) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    ErmeAPI.getInstance().getPrefix() +
                            ErmeAPI.getInstance().getConfig().getString("Messages.noPermission")));
            return true;
        }

        // Retrieve online players and their coins, then sort by coins
        List<Player> onlinePlayers = Bukkit.getOnlinePlayers().stream()
                .sorted(Comparator.comparingInt(p -> -CoinsProvider.getPlayerCoins(p))) // Reverse order (highest coins first)
                .limit(10)
                .collect(Collectors.toList());

        // Display the top 10 players and their coins using configured messages
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                ErmeAPI.getInstance().getConfig().getString("Messages.ErmetopHeader")));
        for (int i = 0; i < onlinePlayers.size(); i++) {
            Player topPlayer = onlinePlayers.get(i);
            int coins = CoinsProvider.getPlayerCoins(topPlayer);
            String message = ChatColor.translateAlternateColorCodes('&',
                    ErmeAPI.getInstance().getConfig().getString("Messages.ErmetopPlayerFormat")
                            .replace("%position%", Integer.toString(i + 1))
                            .replace("%player%", topPlayer.getName())
                            .replace("%coins%", Integer.toString(coins)));
            player.sendMessage(message);
        }

        return true;
    }
}