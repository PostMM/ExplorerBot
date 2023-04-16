import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotEventHandler extends ListenerAdapter {

    private final CommandManager manager;

    public BotEventHandler(CommandManager manager) {
        this.manager = manager;
    }

    /**
     * Triggers when the bot has connected to discord and may receive messages.
     * @param event on ready event.
     */
    public void onReady(ReadyEvent event) {

        System.out.println("System: User Login: " + event.getJDA().getSelfUser());
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        event.getGuild().updateCommands().addCommands(manager.getCommands()).queue();
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        event.getGuild().updateCommands().addCommands(manager.getCommands()).queue();
    }

    /**
     * Triggers when the bot receives a message from a user.
     * Handles a message received event.
     * Contains command handler.
     * @param event message received event.
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.isFromType(ChannelType.PRIVATE) && event.getAuthor().getId().equals(ExplorerBot.CMD_USER_ID)) {

            manager.handleRequest(event.getMessage(), event.getChannel().asPrivateChannel());

        }
    }

    /**
     * Triggers when a message in the EggChannel is updated.
     * Enforces updated eggs.
     *
     * @param event message update event.
     */
    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
        // TODO
    }


    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        final String command = event.getName();
        User user = event.getUser();

        if (command.equals("help")) {
            //TODO
        }

    }
}