package io.github.aquerr.pandobot.commands;

import io.github.aquerr.pandobot.commands.arguments.ICommandArgument;

public class CommandSpec implements ICommandSpec
{
    private final String name;
    private final String description;
    private final String usage;
    private final ICommandArgument[] arguments;
    private final ICommand command;

    private CommandSpec(String name, String description, String usage, ICommandArgument[] arguments, ICommand command)
    {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.arguments = arguments;
        this.command = command;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    @Override
    public ICommand getCommand()
    {
        return this.command;
    }

    @Override
    public ICommandArgument[] getArguments()
    {
        return this.arguments;
    }

    @Override
    public String getDescription()
    {
        return this.description;
    }

    @Override
    public String getUsage()
    {
        return this.usage;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    public static class Builder
    {
        private String name;
        private String description;
        private String usage;
        private ICommandArgument[] arguments;
        private ICommand command;

        public Builder setName(String name)
        {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description)
        {
            this.description = description;
            return this;
        }

        public Builder setUsage(String usage)
        {
            this.usage = usage;
            return this;
        }

        public Builder setArguments(ICommandArgument... arguments)
        {
            this.arguments = arguments;
            return this;
        }

        public Builder setCommand(ICommand command)
        {
            this.command = command;
            return this;
        }

        public CommandSpec build()
        {
            if(name == null)
                throw new NullPointerException("Name cannot be null");

            if(command == null)
                throw new NullPointerException("Command cannot be null!");

            return new CommandSpec(name, description, usage, arguments, command);
        }
    }
}
