package me.nickax.dropconfirm.command.commands;

import me.nickax.dropconfirm.DropConfirm;
import me.nickax.dropconfirm.command.CommandBuilder;
import me.nickax.dropconfirm.command.CommandManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Help extends CommandBuilder {

    private final DropConfirm plugin = DropConfirm.getPlugin(DropConfirm.class);

    @Override
    public String command() {
        return "help";
    }

    @Override
    public String syntax() {
        return null;
    }

    @Override
    public String permission() {
        return "dropconfirm.usage";
    }

    @Override
    public String description() {
        return "- shows the plugin help.";
    }

    @Override
    public String usage(CommandSender sender) {
        return "/dropconfirm help";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            CommandManager commandManager = new CommandManager();
            if (sender instanceof Player) {
                Player player = (Player) sender;
                int c = 0;
                for (CommandBuilder player_command : commandManager.getPlayerCommands()) {
                    if (player_command.permission() != null) {
                        if (player.hasPermission(player_command.permission())) {
                            c++;
                        }
                    } else {
                        c++;
                    }
                }
                if (c > 0) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8===== &6&lDropConfirm &8| &eCommand List &f1/1 &8====="));
                    player.sendMessage("");
                }
                for (CommandBuilder player_command : commandManager.getPlayerCommands()) {
                    if (player_command.permission() != null) {
                        if (player.hasPermission(player_command.permission())) {
                            player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.GREEN + player_command.usage(sender) + " " + ChatColor.GRAY + player_command.description());
                        }
                    } else {
                        player.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.GREEN + player_command.usage(sender) + " " + ChatColor.GRAY + player_command.description());
                    }
                }
                if (c > 0) {
                    player.sendMessage("");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8--- &eDisplaying &6" + c + "&e commands &8---"));
                } else {
                    plugin.getMessageManager().noPermission(sender);
                }
            } else if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8===== &6&lDropConfirm &8| &eCommand List &f1/1 &8====="));
                sender.sendMessage("");
                for (CommandBuilder console_command : commandManager.getConsoleCommands()) {
                    sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.GREEN + console_command.usage(sender) + " " + ChatColor.GRAY + console_command.description());
                }
                sender.sendMessage("");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8--- &eDisplaying &6" + commandManager.getConsoleCommands().size() + "&e commands &8---"));
            }
        }
    }
}
