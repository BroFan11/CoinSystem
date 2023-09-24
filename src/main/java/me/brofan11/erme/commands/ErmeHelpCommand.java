package me.brofan11.erme.commands;

import me.brofan11.erme.ErmeAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ErmeHelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;

        // Check permissions (if you have specific permission nodes for help)
        if (!player.hasPermission("erme.help") && !player.hasPermission("erme.administrator")) {
            player.sendMessage("You do not have permission to use this command.");
            return true;
        }

        // Retrieve the help message from the configuration
        List<String> helpMessage = ErmeAPI.getInstance().getConfig().getStringList("Messages.ErmeHelpMessage");

        // Display the help message
        for (String line : helpMessage) {
            player.sendMessage(line.replace("&", "§")); // Translate color codes
        }

        return true;
    }
}
