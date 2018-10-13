package io.github.aquerr.pandobot.commands;

import io.github.aquerr.pandobot.commands.arguments.ArgumentType;

import java.util.List;

public interface ICommandSpec
{
    ICommand getCommand();
//    ICommandArgument[] getArguments();
    ArgumentType[] getArguments();
    String getDescription();
    String getUsage();
    String getName();
    List<String> parseArguments();
}
