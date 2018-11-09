package io.github.aquerr.pandobot.commands;

import io.github.aquerr.pandobot.annotations.BotCommand;
import io.github.aquerr.pandobot.commands.arguments.ArgumentType;
import io.github.aquerr.pandobot.commands.parsers.NumberArgumentParser;
import io.github.aquerr.pandobot.commands.parsers.QuotationsArgumentParser;
import io.github.aquerr.pandobot.commands.parsers.StringArgumentParser;
import io.github.aquerr.pandobot.entities.VTEAMRoles;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CommandManager
{
    private Map<List<String>, ICommandSpec> commands = new HashMap<>();

    public CommandManager()
    {
        initCommands();
    }

    private void initCommands()
    {
        //TODO: Add new commands here...
        addCommand(Collections.singletonList("ankieta"), CommandSpec.builder()
                .setName("Ankieta")
                .setDescription("Wyświetla ankietę")
                .setCommand(new SurveyCommand())
                .setArguments(ArgumentType.QUOTED_STRING,
                        ArgumentType.QUOTED_STRING,
                        ArgumentType.QUOTED_STRING,
                        ArgumentType.QUOTED_STRING)
                .setUsage("!ankieta \"Tytuł ankiety\" \"Reakcja 1\" \"Reakcja 2\" \"Reakcja 3\"")
                .build());

        addCommand(Collections.singletonList("usun"), CommandSpec.builder()
                .setName("Usuń")
                .setDescription("Czyści czat")
                .setCommand(new ClearCommand())
                .setArguments(ArgumentType.NUMBER)
                .setUsage("!usun ilość")
                .build());

        addCommand(Collections.singletonList("gif"), CommandSpec.builder()
                .setName("Gif")
                .setDescription("Wyszukaj losowego gifa na Giphy z podanym tagiem")
                .setCommand(new GifCommand())
                .setArguments(ArgumentType.STRING)
                .setUsage("!gif tag")
                .build());

        addCommand(Collections.singletonList("pomoc"), CommandSpec.builder()
                .setName("Pomoc")
                .setDescription("Pokazuje wszystkie dostępne komendy")
                .setCommand(new HelpCommand())
                .setUsage("!gif tag")
                .build());

        addCommand(Collections.singletonList("gra"), CommandSpec.builder()
                .setName("Gra")
                .setDescription("Zmienia opis pandy")
                .setCommand(new GameCommand())
                .setArguments(ArgumentType.REMINDED_STRINGS)
                .setUsage("!gra opis")
                .build());
    }

    public void addCommand(List<String> aliases, ICommandSpec commandSpec)
    {
        if(this.commands.containsKey(aliases))
            throw new IllegalArgumentException("CommandManager already contain a command with following alias: " + aliases);

        this.commands.put(aliases, commandSpec);
    }

    public Optional<ICommand> getCommand(String alias)
    {
        for (List<String> commandAliases : this.commands.keySet())
        {
            if (commandAliases.contains(alias))
                return Optional.of(this.commands.get(commandAliases).getCommand());
        }
        return Optional.empty();
    }

    public boolean executeCommand(String commandAlias, User user, MessageChannel channel, List<String> args)
    {
        for (List<String> commandAliases : this.commands.keySet())
        {
            if (commandAliases.contains(commandAlias))
                return this.commands.get(commandAliases).getCommand().execute(user,channel, args);
        }
        return false;
    }

    public boolean hasPermissions(Member member, ICommand command)
    {
        if(!command.getClass().isAnnotationPresent(BotCommand.class))
            return true;

        //Access for Everyone
        if(command.getClass().getAnnotation(BotCommand.class).minRole() == VTEAMRoles.EVERYONE)
            return true;

        int minRank = VTEAMRoles.getLadder().get(command.getClass().getAnnotation(BotCommand.class).minRole().getId());

        for(Role role : member.getRoles())
        {
            if(VTEAMRoles.getLadder().get(role.getIdLong()) != null && VTEAMRoles.getLadder().get(role.getIdLong()) >= minRank)
            {
                return true;
            }
        }

        return false;
    }

    private List<String> parseCommandArguments(ICommandSpec commandSpec, String rowArgs)
    {
        List<String> parsedArguments = new ArrayList<>();
        ArgumentType[] argumentTypes = commandSpec.getArguments();
        if(argumentTypes == null)
            return Collections.emptyList();
        StringBuilder remindingArgs = new StringBuilder();
        remindingArgs.append(rowArgs);

        for(ArgumentType argumentType : argumentTypes)
        {
            if(remindingArgs.charAt(0) == ' ')
                remindingArgs.deleteCharAt(0);

            switch(argumentType)
            {
                case QUOTED_STRING:
                {
                    String parsedArgument = QuotationsArgumentParser.parse(remindingArgs);
                    if(parsedArgument == null)
                        return null;

                    parsedArguments.add(parsedArgument);
                    break;
                }
                case STRING:
                {
                    String parsedArgument = StringArgumentParser.parse(remindingArgs);
                    if(parsedArgument == null)
                        return null;

                    parsedArguments.add(parsedArgument);
                    break;
                }
                case REMINDED_STRINGS:
                {
                    String parsedArgument = remindingArgs.toString();
                    remindingArgs.setLength(0);

                    parsedArguments.add(parsedArgument);
                    break;
                }
                case NUMBER:
                {
                    String parsedArgument = NumberArgumentParser.parse(remindingArgs);
                    if(parsedArgument == null)
                        return null;

                    parsedArguments.add(parsedArgument);
                    break;
                }
            }
        }

        return parsedArguments;
    }

    public Map<List<String>, ICommand> getCommands()
    {
        return null;
        //return this.commands;
    }

    public void processCommand(Member member, MessageChannel channel, Message message)
    {
        //Create a new class "parser" for commandManager that will take care of arguments.
        //Arguments can be different for each command. E.g. not every command needs to take arguments inside " "

        String text = message.getContentDisplay().substring(1);
        String commandAlias = text.split(" ")[0];
        String arguments = text.substring(commandAlias.length());

//        Optional<ICommand> optionalCommand = getCommand(commandAlias);
//        if (!optionalCommand.isPresent())
//            return;

        Optional<ICommandSpec> optionalCommandSpec = getCommandSpec(commandAlias);
        if(!optionalCommandSpec.isPresent())
            return;

        if(!hasPermissions(member, optionalCommandSpec.get().getCommand()))
            return;

        List<String> argsList = parseCommandArguments(optionalCommandSpec.get(), arguments);
        if(argsList == null)
        {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(new Color(68, 158, 226));
//            embedBuilder.addField("",":warning: Poprawne użycie komendy: " + optionalCommandSpec.get().getUsage(),true);
            embedBuilder.setDescription(":warning: Poprawne użycie komendy: " + optionalCommandSpec.get().getUsage());
            channel.sendMessage(embedBuilder.build()).queue();

//            channel.sendMessage(":warning: Poprawne użycie komendy: " + optionalCommandSpec.get().getUsage()).queue();
            System.out.println("[DEBUG] Error during parsing arguments for command: " + optionalCommandSpec.get().getName());
            System.out.println("[DEBUG] Used arguemnts: " + arguments);
            return;
        }

        //Check arguments count
        short expectedArgsCount = optionalCommandSpec.get().getCommand().getClass().getAnnotation(BotCommand.class).argsCount();
        if (expectedArgsCount != 0 && expectedArgsCount != argsList.size())
        {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(new Color(68, 158, 226));
            embedBuilder.setDescription(":warning: Poprawne użycie komendy: " + optionalCommandSpec.get().getUsage());
            channel.sendMessage(embedBuilder.build()).queue();
//            channel.sendMessage(":warning: Poprawne użycie komendy: " + optionalCommandSpec.get().getUsage()).queue();
//            channel.sendMessage(":warning: Zła ilość wymaganych argumentów (" + expectedArgsCount + ")").queue();
            return;
        }

        //Log
        logCommandUsage(commandAlias, member, channel, message);

        //Execute
        executeCommand(commandAlias, member.getUser(), channel, argsList);
    }

    private void logCommandUsage(String commandAlias, Member member, MessageChannel channel, Message message)
    {
        System.out.println("User " + member.getEffectiveName() + " used command " + commandAlias);
        System.out.println("Channel: " + channel.getName());
        System.out.println("Raw message: " + message);
    }

    private Optional<ICommandSpec> getCommandSpec(String commandAlias)
    {
        for (List<String> commandAliases : this.commands.keySet())
        {
            if (commandAliases.contains(commandAlias))
                return Optional.of(this.commands.get(commandAliases));
        }
        return Optional.empty();
    }
}
