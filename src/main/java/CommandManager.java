import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

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
