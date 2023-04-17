import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.exceptions.ContextException;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandManager {

    private final JDA bot;
    private final FileViewer explorer;

    public CommandManager(JDA bot, FileViewer explorer){
        this.bot = bot;
        this.explorer = explorer;
    }

    public void sendHelpMessage(SlashCommandInteractionEvent event) {

        event.reply("If you have setup ExplorerBot on your local system," +
                        "\nDm the bot and send 'help' to get started." +
                        "\nOtherwise, download ExplorerBot at https://github.com/PostMM/ExplorerBot," +
                        "\nAnd follow the instructions in the provided README.")
                .setEphemeral(true)
                .queue();

    }

    public void handleRequest(Message message, PrivateChannel channel) {

        User user = channel.getUser();
        String content = message.getContentRaw();

        if (user == null) {
            return;
        }
        if (content.equals("ls")) {
            explorer.getFiles().forEach(fileName -> sendPrivateMessage(user, fileName));
        }
        if (content.equals("ld")) {
            explorer.getDirs().forEach(fileName -> sendPrivateMessage(user, fileName));
        }
        if (content.equals("pd")) {
            explorer.parentDir();
            sendPrivateMessage(user, explorer.getPath());
        }
        if (content.startsWith("cd")) {
            String[] cmd = content.split(" ", 2);
            if (cmd.length > 1) {
                explorer.gotoDir(cmd[1]);
            }
            sendPrivateMessage(user, explorer.getPath());
        }
        if (content.startsWith("sd")) {
            String[] cmd = content.split(" ", 2);
            if (cmd.length > 1) {
                explorer.setDir(cmd[1]);
            }
            sendPrivateMessage(user, explorer.getPath());
        }
        if (content.startsWith("get")) {
            String[] cmd = content.split(" ", 2);
            if (cmd.length > 1) {
                File requestedFile;
                if ((requestedFile = explorer.get(cmd[1])) != null) {
                    FileUpload file = FileUpload.fromData(requestedFile);
                    channel.sendFiles(file).queue();

                }
            }
        }
        if (content.equals("clear")) {
            explorer.clearMessages(channel);
        }
        if (content.equals("help")) {
            sendPrivateMessage(user, "Welcome to the ExplorerBot remote terminal." +
                    "\nUse commands to navigate your file system and download files remotely:" +
                    "\nclear: clear messages from ExplorerBot" +
                    "\nld: list directories current directory" +
                    "\nls: list files in current directory" +
                    "\ncd: [dir]: change to specified directory" +
                    "\npd: return to parent directory" +
                    "\nsd: [path]: jump to specified directory" +
                    "\nget: [file]: request the specified file (limit 8MB) to download");
        }
    }

    public void openPrivateMessage() {
        if (bot.getUserById(ExplorerBot.CMD_USER_ID) != null) {
            sendPrivateMessage(Objects.requireNonNull(bot.getUserById(ExplorerBot.CMD_USER_ID)), "<ENTER COMMAND>");
        }
    }
    private void sendPrivateMessage(User user, String content) {
        user.openPrivateChannel().queue(channel -> {
            channel.sendMessage(content).queue();
        });
    }



    public List<CommandData> getCommands() {

        List<CommandData> commands = new ArrayList<>();

        // Help
        commands.add(Commands.slash("help", "Demystify Explorer."));

        return commands;
    }

}
