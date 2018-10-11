package io.github.aquerr.pandobot.commands;

import io.github.aquerr.pandobot.annotations.BotCommand;
import io.github.aquerr.pandobot.commands.arguments.SingleArgument;
import io.github.aquerr.pandobot.entities.VTEAMRoles;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

import java.util.*;

public class CommandManager
{
    private Map<List<String>, ICommandSpec> commands = new HashMap();

    public CommandManager()
    {
        initCommands();
    }

    private void initCommands()
    {
        //TODO: Add new commandManager here...
//        addCommand(Collections.singletonList("ankieta"), new SurveyCommand());
//        addCommand(Collections.singletonList("clear"), new ClearCommand());
//        addCommand(Collections.singletonList("game"), new GameCommand());
//        addCommand(Collections.singletonList("gif"), new GifCommand());
//        addCommand(Collections.singletonList("pomoc"), new HelpCommand());

        addCommand(Collections.singletonList("ankieta"), CommandSpec.builder()
                .setName("Ankieta")
                .setDescription("Wyświetla ankietę")
                .setCommand(new SurveyCommand())
                .setArguments(new SingleArgument<>("nazwa"), new SingleArgument<>("reakcja 1"), new SingleArgument<>("reakcja 2"), new SingleArgument<>("reakcja 3"))
                .setUsage("!ankieta \"Tytuł ankiety\" \"Reakcja 1\" \"Reakcja 2\" \"Reakcja 3\"")
                .build());

//        addCommand(Collections.singletonList("clear"), new ClearCommand());
//        addCommand(Collections.singletonList("game"), new GameCommand());
//        addCommand(Collections.singletonList("gif"), new GifCommand());
//        addCommand(Collections.singletonList("pomoc"), new HelpCommand());
    }

//    public void addCommand(List<String> aliases, ICommand command)
//    {
//        if (this.commands.containsKey(aliases))
//            throw new IllegalArgumentException("CommandManager already contain a command with following alias: " + aliases);
//
//        this.commands.put(aliases, command);
//    }

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

    public boolean hasPermissions(Member author, ICommand command)
    {
        if(!command.getClass().isAnnotationPresent(BotCommand.class))
            return true;

        //Access for Everyone
        if(command.getClass().getAnnotation(BotCommand.class).minRole() == 0)
            return true;

        int minRank = VTEAMRoles.getLadder().get(command.getClass().getAnnotation(BotCommand.class).minRole());

        for(Role role : author.getRoles())
        {
            if(VTEAMRoles.getLadder().get(role.getIdLong()) != null && VTEAMRoles.getLadder().get(role.getIdLong()) >= minRank)
            {
                return true;
            }
        }

        return false;
    }

    public Object parseArguemnts()
    {


        return null;
    }

    public Map<List<String>, ICommand> getCommands()
    {
        return this.commands;
    }
}
