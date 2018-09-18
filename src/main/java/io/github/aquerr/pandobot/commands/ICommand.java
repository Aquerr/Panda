package io.github.aquerr.pandobot.commands;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.List;

public interface ICommand
{
    boolean execute(User user, MessageChannel channel, List<String> args);
    String getUsage();
}
