package io.github.aquerr.pandobot.commands;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Commands
{
    private Map<String, ICommand> commands = new HashMap();

    public Commands()
    {
        initCommands();
    }

    private void initCommands()
    {
        //TODO: Add new commands here...
        addCommand("ankieta", new SurveyCommand());
        addCommand("wyczysc", new ClearCommand());
    }

    public void addCommand(String alias, ICommand command)
    {
        if (this.commands.containsKey(alias))
            throw new IllegalArgumentException("Commands already contain a command with following alias: " + alias);

        this.commands.put(alias, command);
    }

    public boolean containsCommand(String alias)
    {
        return this.commands.containsKey(alias);
    }

    public boolean executeCommand(String commandAlias, User user, MessageChannel channel, List<String> args)
    {
        return this.commands.get(commandAlias).execute(user,channel, args);
    }
}
