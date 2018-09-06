package io.github.aquerr.pandobot;

import io.github.aquerr.pandobot.commands.Commands;
import io.github.aquerr.pandobot.events.MessageListener;
import io.github.aquerr.pandobot.secret.SecretProperties;
import javafx.concurrent.ScheduledService;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;

import java.time.LocalTime;
import java.util.*;

public class PandoBot
{
    public Commands commands = new Commands();

    private static PandoBot pandoBot;

    private JDA jda;
    private Timer onHourTimer;

    private PandoBot()
    {
        setup();
    }

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

    private void setup()
    {
        try
        {
            //START TIMER HERE.
            onHourTimer = new Timer();
            onHourTimer.schedule(scheduleOneHour(), 3_600_000L);

            this.jda = new JDABuilder(AccountType.BOT)
                    .setToken(SecretProperties.BOT_TOKEN)
                    .setGame(Game.of(Game.GameType.DEFAULT, "o Bambus", "https://github.com/Aquerr/PandoBot"))
                    .buildBlocking();

            System.out.println("Setting up commands...");


            System.out.println("Connectd!");
            this.jda.addEventListener(new MessageListener());
            this.jda.setAutoReconnect(true);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private TimerTask scheduleOneHour()
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                jda.getPresence().setGame(getBotGame());
                onHourTimer.schedule(scheduleOneHour(), 3_600_000L);
            }
        };
    }

    public void processCommand(User author, MessageChannel channel, Message message)
    {
//        String text = message.getContentDisplay().substring(1);
//        String command = text.split(" ")[0];
//
//        if (!commands.containsCommand(command))
//            return;
//
//        String argsText = text.substring(command.length());
//        String[] args = argsText.split("\" \"");
//
//        commands.executeCommand(command, author, args);


        String text = message.getContentDisplay().substring(1);
        String commandAlias = text.split(" ")[0];

        if (!this.commands.containsCommand(commandAlias))
            return;

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

        commands.executeCommand(commandAlias, author, channel, argsList);
    }

    private Game getBotGame()
    {
        LocalTime time = LocalTime.now();
        if (time.getHour() > 22 || time.getHour() < 8)
        {
            return Game.of(Game.GameType.DEFAULT, "VTEAM");
        }
        return Game.of(Game.GameType.DEFAULT, "o Bambus");
    }
}
