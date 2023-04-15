import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private final JDA bot;
    private final FileViewer explorer;

    public CommandManager(JDA bot, FileViewer explorer){
        this.bot = bot;
        this.explorer = explorer;
    }

    public void sendHelpMessage(SlashCommandInteractionEvent event) {

        event.reply("")
                .setEphemeral(true)
                .queue();

    }

    public void handleRequest(Message message, PrivateChannel channel) {

        User user = channel.getUser();
        String content = message.getContentRaw();

        if (content.equals("ls") && user != null) {
            explorer.getFiles().forEach(fileName -> sendPrivateMessage(user, fileName));
        }
        if (content.equals("ld") && user != null) {
            explorer.getDirs().forEach(fileName -> sendPrivateMessage(user, fileName));
        }
        if (content.equals("pd") && user != null) {
            explorer.parentDir();
            sendPrivateMessage(user, explorer.getPath());
        }
        if (content.startsWith("cd") && user != null) {
            String[] cmd = content.split(" ");
            if (cmd.length > 1) {
                explorer.gotoDir(cmd[1]);
            }
            sendPrivateMessage(user, explorer.getPath());
        }
        if (content.startsWith("sd") && user != null) {
            String[] cmd = content.split(" ");
            if (cmd.length > 1) {
                explorer.setDir(cmd[1]);
            }
            sendPrivateMessage(user, explorer.getPath());
        }
        if (content.startsWith("get") && user != null) {
            String[] cmd = content.split(" ");
            if (cmd.length > 1) {
                File requestedFile;
                if ((requestedFile = explorer.get(cmd[1])) != null) {
                    FileUpload file = FileUpload.fromData(requestedFile);
                    channel.sendFiles(file).queue();
                }
            }
        }
        if (content.equals("clear") && user != null) {
            explorer.clearMessages(channel);
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
