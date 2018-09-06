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
    public static Commands commands = new Commands();

    private static JDA pandoBot;
    private static Timer onHourTimer;

    public static void main(String[] args)
    {
        try
        {
            //START TIMER HERE.
            onHourTimer = new Timer();
            onHourTimer.schedule(scheduleOneHour(), 3_600_000L);

            JDA jda = new JDABuilder(AccountType.BOT)
                    .setToken(SecretProperties.BOT_TOKEN)
                    .setGame(Game.of(Game.GameType.DEFAULT, "o Bambus", "https://github.com/Aquerr/PandoBot"))
                    .buildBlocking();

            pandoBot = jda;

            System.out.println("Setting up commands...");


            System.out.println("Connectd!");
            jda.addEventListener(new MessageListener());
            jda.setAutoReconnect(true);

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private static TimerTask scheduleOneHour()
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                pandoBot.getPresence().setGame(getBotGame());
                onHourTimer.schedule(scheduleOneHour(), 3_600_000L);
            }
        };
    }

    public static void processCommand(User author, MessageChannel channel, Message message)
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

        if (!commands.containsCommand(commandAlias))
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

    private static void setup()
    {

    }

    private static Game getBotGame()
    {
        LocalTime time = LocalTime.now();
        if (time.getHour() > 22 || time.getHour() < 8)
        {
            return Game.of(Game.GameType.DEFAULT, "VTEAM");
        }
        return Game.of(Game.GameType.DEFAULT, "o Bambus");
    }
}
