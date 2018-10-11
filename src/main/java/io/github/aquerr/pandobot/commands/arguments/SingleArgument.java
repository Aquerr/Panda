package io.github.aquerr.pandobot.commands.arguments;

public final class SingleArgument<T> implements ICommandArgument
{
    final private T key;

    public SingleArgument(T key)
    {
        this.key = key;
    }

    public T getKey()
    {
        return key;
    }
}
