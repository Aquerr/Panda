package io.github.aquerr.pandobot.commands;

import io.github.aquerr.pandobot.annotations.BotCommand;
import io.github.aquerr.pandobot.entities.VTEAMRoles;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.util.List;

@BotCommand(argsCount = 4)
public class SurveyCommand implements ICommand
{
    @Override
    public boolean execute(User user, MessageChannel channel, List<String> args)
    {
//        if(args.size() < 4)
//        {
//            channel.sendMessage("Błąd! Zbyt mała ilość wymaganych argumentów! Minimum: 4").queue();
//            return false;
//        }

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(new Color(68, 158, 226));
        embedBuilder.setTitle(":bookmark_tabs: " + args.get(0));
        embedBuilder.setDescription("- - - - - - - - - - - - - - - -" + "\n" + ":heart: - " + args.get(1) + "\n\n" +
                                    ":thumbsup: - " + args.get(2) + "\n\n" +
                                    ":thumbsdown: - " + args.get(3));
        channel.sendMessage(embedBuilder.build()).queue();
        return true;
    }

    @Override
    public String getUsage()
    {
        return "!ankieta \"Tytuł ankiety\" \"Reakcja 1\" \"Reakcja 2\" \"Reakcja 3\"";
    }
}
