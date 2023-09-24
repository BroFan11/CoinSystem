package me.brofan11.erme.commands;

import me.brofan11.erme.ErmeAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Check permissions
            if (!player.hasPermission("erme.reload") && !player.hasPermission("erme.administrator")) {
                player.sendMessage(ErmeAPI.getInstance().getPrefix() +
                        ErmeAPI.getInstance().getConfig().getString("Messages.noPermission")
                                .replace("§", "&"));  // Use replace here if needed
                return true;
            }
        }

        // Reload the plugin configuration
        ErmeAPI.getInstance().reloadConfig();

        // Get the reload command message from the config
        String reloadMessage = ErmeAPI.getInstance().getConfig().getString("Messages.ReloadCommandMessage");

        sender.sendMessage(ErmeAPI.getInstance().getPrefix() + ChatColor.translateAlternateColorCodes('&', reloadMessage));  // Use ChatColor.translateAlternateColorCodes to translate §

        return true;
    }
}