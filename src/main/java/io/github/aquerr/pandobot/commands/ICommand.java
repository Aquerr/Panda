package io.github.aquerr.pandobot.commands;

import net.dv8tion.jda.core.entities.User;

public interface ICommand
{
    boolean execute(User user, String[] args);
}
