package me.brofan11.erme.commands;

import me.brofan11.erme.ErmeAPI;
import me.brofan11.erme.utils.CoinsProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) return true;
        final Player player = (Player) sender;

        if(args.length != 2) {
            player.sendMessage(ErmeAPI.getInstance().getPrefix() + ErmeAPI.getInstance().getConfig().getString("Messages.commandSyntaxHelp")
                    .replace("&", "§").replace("%commandSyntax%", "/payerme <player> <amount>"));
            return true;
        }

        String target = args[0];
        try {
            int amount = Integer.parseInt(args[1]);
            Player player1 = Bukkit.getPlayer(target);
            if(player1 == null) {
                player.sendMessage(ErmeAPI.getInstance().getPrefix() + ErmeAPI.getInstance().getConfig().getString("Messages.targetIsNull")
                        .replace("&", "§"));
                return true;
            }
            if(CoinsProvider.isPlayerInDatabase(player1)) {
                CoinsProvider.addCoins(player1, amount);
                player1.sendMessage(ErmeAPI.getInstance().getPrefix() + ErmeAPI.getInstance().getConfig().getString("Messages.payPlayerCoinsTargetMessage")
                        .replace("%targetPlayer%", player1.getName()).replace("%amount%", String.valueOf(amount)).replace("&", "§"));

                player.sendMessage(ErmeAPI.getInstance().getPrefix() + ErmeAPI.getInstance().getConfig().getString("Messages.payPlayerCoinsPlayerMessage")
                        .replace("%targetPlayer%", player1.getName()).replace("%amount%", String.valueOf(amount)).replace("&", "§"));
            } else {
                player.sendMessage(ErmeAPI.getInstance().getPrefix() + ErmeAPI.getInstance().getConfig().getString("Messages.playerIsNotInDatabase")
                        .replace("&", "§"));
            }
        }catch (NumberFormatException ignored) {

        }
        return false;
    }
}
