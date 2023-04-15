import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private final JDA bot;

    public CommandManager(JDA bot){
        this.bot = bot;
    }

    public void sendHelpMessage(SlashCommandInteractionEvent event) {

        event.reply("")
                .setEphemeral(true)
                .queue();

    }

    public List<CommandData> getCommands() {

        List<CommandData> commands = new ArrayList<>();

        // Help
        commands.add(Commands.slash("help", "Demystify Explorer."));

        return commands;
    }

}
