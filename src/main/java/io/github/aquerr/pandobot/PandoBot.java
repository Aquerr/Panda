package io.github.aquerr.pandobot;

import io.github.aquerr.pandobot.commands.CommandManager;
import io.github.aquerr.pandobot.events.MessageListener;
import io.github.aquerr.pandobot.secret.SecretProperties;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PandoBot
{
    private static PandoBot pandoBot;

    private CommandManager commandManager;
    private JDA jda;
    private Timer onHourTimer;

    public static PandoBot getInstance()
    {
        if(pandoBot != null)
            return pandoBot;
        return new PandoBot();
    }

    public static void main(String[] args)
    {
        pandoBot = new PandoBot();
    }

    private PandoBot()
    {
        setup();
    }

    public CommandManager getCommandManager()
    {
        return this.commandManager;
    }

    private List<String> parseCommandArguments(String text, String commandAlias)
    {
        boolean isArgument = false;
        List<String> argsList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (char character : text.substring(commandAlias.length()).toCharArray())
        {
            if (character == '\"' && !isArgument)
            {
                isArgument = true;
            }
            else if(character == '\"' && isArgument)
            {
                isArgument = false;
                argsList.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
            else if(isArgument)
            {
                stringBuilder.append(character);
            }
        }

        return argsList;
    }

    private void setup()
    {
        try
        {
            //START TIMER HERE.
            onHourTimer = new Timer();
            onHourTimer.schedule(scheduleOneMinute(), 60_000L);

            this.jda = new JDABuilder(AccountType.BOT)
                    .setToken(SecretProperties.BOT_TOKEN)
                    .setGame(Game.of(Game.GameType.DEFAULT, "o Bambus", "https://github.com/Aquerr/PandoBot"))
                    .buildBlocking();

            System.out.println("Setting up commandManager...");
            this.commandManager = new CommandManager();

            System.out.println("Connectd!");
            this.jda.addEventListener(new MessageListener());
            this.jda.setAutoReconnect(true);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private TimerTask scheduleOneMinute()
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                jda.getPresence().setGame(getBotGame());
                onHourTimer.schedule(scheduleOneMinute(), 60_000L);
            }
        };
    }

    private Game getBotGame()
    {
      // DO PRZETESTOWANIA! (OD 7 DO 10)
        LocalTime time = LocalTime.now();
          if (time.getHour() > 10 || time.getHour() < 7)
            {
              return Game.of(Game.GameType.DEFAULT, "Bambus Life <3");
            }
          else if (time.getHour() > 22 || time.getHour() < 7)
            {
              return Game.of(Game.GameType.DEFAULT, "#VTEAMPO22");
            }
        return Game.of(Game.GameType.DEFAULT, "o Bambus");
    }
}
