package io.github.aquerr.pandobot.commands;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.*;

public class Commands
{
    private Map<List<String>, ICommand> commands = new HashMap();

    public Commands()
    {
        initCommands();
    }

    private void initCommands()
    {
        //TODO: Add new commands here...
        addCommand(Collections.singletonList("ankieta"), new SurveyCommand());
        addCommand(Arrays.asList("wyczysc", "clear"), new ClearCommand());
        addCommand(Collections.singletonList("game"), new GameCommand());
    }

    public void addCommand(List<String> aliases, ICommand command)
    {
        if (this.commands.containsKey(aliases))
            throw new IllegalArgumentException("Commands already contain a command with following alias: " + aliases);

        this.commands.put(aliases, command);
    }

    public boolean containsCommand(String alias)
    {
        for (List<String> commandAliases : this.commands.keySet())
        {
            if (commandAliases.contains(alias))
                return true;
        }
        return false;
    }

    public boolean executeCommand(String commandAlias, User user, MessageChannel channel, List<String> args)
    {
        return this.commands.get(commandAlias).execute(user,channel, args);
    }
}
