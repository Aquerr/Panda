package io.github.aquerr.pandobot.commands;

import io.github.aquerr.pandobot.commands.arguments.ICommandArgument;

public interface ICommandSpec
{
    ICommand getCommand();
    ICommandArgument[] getArguments();
    String getDescription();
    String getUsage();
    String getName();
}
