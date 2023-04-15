import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

public class ExplorerBot {

    private static final String TOKEN = "";
    public static final String VERSION = "V1.0";
    public static final String NAME = "ExplorerBot";
    public static final String RES = "";

    public static final String CMD_CHANNEL = "";
    private static Quo window;

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (final UnsupportedLookAndFeelException e) {
            System.err.println("Error: JVM does not support system's UI.");
        }
        catch (final ClassNotFoundException e) {
            System.err.println("Error: System UI could not be imported.");
        }
        catch (final InstantiationException e) {
            System.err.println("Error: Problems setting look and feel.");
        }
        catch (final IllegalAccessException e) {
            System.err.println("Error: EggBot does not have access to System look and feel to apply enhanced UI.");
        }

        EventQueue.invokeLater(ExplorerBot::initGui);

        final JDA bot;
        bot = JDABuilder.createDefault(TOKEN)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();

        CommandManager manager = new CommandManager(bot);
        bot.addEventListener(new BotEventHandler(manager));
    }

    private static void initGui() {
        window = new Quo();
        final PrintStream stream = new PrintStream(window.getOut());
        System.setOut(stream);
        System.setErr(stream);
        System.out.println("STANDARD OUTPUT IS REDIRECTED TO GUI.");
    }

}
