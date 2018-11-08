package io.github.aquerr.pandobot.commands;

import io.github.aquerr.pandobot.annotations.BotCommand;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.List;

@BotCommand(argsCount = 1)
public class RankCommand implements ICommand{

    @Override
    public boolean execute(User user, MessageChannel channel, List<String> args)
    {
        channel.sendMessage("ERROR!!!!!!!!!!!!!!!!").queue();
        return false;
    }

    @Override
    public String getUsage() {
        return "!ranga \"nazwa rangi\"";
    }
}
