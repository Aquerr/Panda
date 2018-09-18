package io.github.aquerr.pandobot;

import io.github.aquerr.pandobot.annotations.BotCommand;
import io.github.aquerr.pandobot.commands.CommandManager;
import io.github.aquerr.pandobot.commands.ICommand;
import io.github.aquerr.pandobot.events.MessageListener;
import io.github.aquerr.pandobot.secret.SecretProperties;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;

import java.time.LocalTime;
import java.util.*;

public class PandoBot
{
    private static PandoBot pandoBot;

    private CommandManager commandManager;
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

    public CommandManager getCommandManager()
    {
        return this.commandManager;
    }

    public static void main(String[] args)
    {
        pandoBot = new PandoBot();
    }

    public void processCommand(Member author, MessageChannel channel, Message message)
    {
        //Create a new class "parser" for commandManager that will take care of arguments.
        //Arguments can be different for each command. E.g. not every command needs to take arguments inside " "

        String text = message.getContentDisplay().substring(1);
        String commandAlias = text.split(" ")[0];

        Optional<ICommand> optionalCommand = this.commandManager.getCommand(commandAlias);
        if (!optionalCommand.isPresent())
            return;

        if(!this.commandManager.hasPermissions(author, optionalCommand.get()))
            return;

        List<String> argsList = parseCommandArguments(text, commandAlias);

        //Check arguments count
        short expectedArgsCount = optionalCommand.get().getClass().getAnnotation(BotCommand.class).argsCount();
        if (expectedArgsCount != 0 && expectedArgsCount != argsList.size())
        {
            channel.sendMessage(":warning: Zła ilość wymaganych argumentów (" + expectedArgsCount).queue()")";
            return;
        }

        commandManager.executeCommand(commandAlias, author.getUser(), channel, argsList);
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
        LocalTime time = LocalTime.now();
        if (time.getHour() > 22 || time.getHour() < 7)
        {
            return Game.of(Game.GameType.DEFAULT, "#VTEAMPO22");
        }
        return Game.of(Game.GameType.DEFAULT, "o Bambus");
    }
}
