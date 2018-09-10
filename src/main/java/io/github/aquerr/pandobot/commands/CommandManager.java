package io.github.aquerr.pandobot.commands;

import io.github.aquerr.pandobot.annotations.BotCommand;
import io.github.aquerr.pandobot.entities.VTEAMRoles;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

import java.util.*;

public class CommandManager
{
    private Map<List<String>, ICommand> commands = new HashMap();

    public CommandManager()
    {
        initCommands();
    }

    private void initCommands()
    {
        //TODO: Add new commandManager here...
        addCommand(Collections.singletonList("ankieta"), new SurveyCommand());
        addCommand(Arrays.asList("wyczysc", "clear"), new ClearCommand());
        addCommand(Collections.singletonList("game"), new GameCommand());
        addCommand(Collections.singletonList("gif"), new GifCommand());
    }

    public void addCommand(List<String> aliases, ICommand command)
    {
        if (this.commands.containsKey(aliases))
            throw new IllegalArgumentException("CommandManager already contain a command with following alias: " + aliases);

        this.commands.put(aliases, command);
    }

    public Optional<ICommand> getCommand(String alias)
    {
        for (List<String> commandAliases : this.commands.keySet())
        {
            if (commandAliases.contains(alias))
                return Optional.of(this.commands.get(commandAliases));
        }
        return Optional.empty();
    }

    public boolean executeCommand(String commandAlias, User user, MessageChannel channel, List<String> args)
    {
        for (List<String> commandAliases : this.commands.keySet())
        {
            if (commandAliases.contains(commandAlias))
                return this.commands.get(commandAliases).execute(user,channel, args);
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
}
