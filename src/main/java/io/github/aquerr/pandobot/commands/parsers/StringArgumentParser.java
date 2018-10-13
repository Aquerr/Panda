package io.github.aquerr.pandobot.commands.parsers;

public class StringArgumentParser
{
    public static String parse(StringBuilder input)
    {
        int firstSpace = input.indexOf(" ");
        String parsedArgument = null;
        if(firstSpace == -1)
        {
            parsedArgument = input.substring(0);
        }
        else
        {
            parsedArgument = input.substring(0, firstSpace);
        }
        input.delete(0, parsedArgument.length());
        return parsedArgument;
    }
}
