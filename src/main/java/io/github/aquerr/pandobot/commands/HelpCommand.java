package io.github.aquerr.pandobot.commands;

import io.github.aquerr.pandobot.PandoBot;
import io.github.aquerr.pandobot.annotations.BotCommand;
import io.github.aquerr.pandobot.entities.VTEAMRoles;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.util.List;
import java.util.Map;

@BotCommand(minRole = VTEAMRoles.EVERYONE, argsCount = 0)
public class HelpCommand implements ICommand
{
    @Override
    public boolean execute(User user, MessageChannel channel, List<String> args)
    {
        Map<List<String>, ICommand> commands = PandoBot.getInstance().getCommandManager().getCommands();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(new Color(68, 158, 226));
        embedBuilder.setTitle("Dostępne komendy u Pandy:  ");

        for (Map.Entry<List<String>, ICommand> commandEntry : commands.entrySet())
        {
            String commandAliases = String.join(", ", commandEntry.getKey());

            embedBuilder.addField(commandEntry.getValue());
        }



//        embedBuilder.setDescription("- - - - - - - - - - - - - - - -" + "\n" + ":heart: - " + args.get(1) + "\n\n" +
//                ":thumbsup: - " + args.get(2) + "\n\n" +
//                ":thumbsdown: - " + args.get(3));
        embedBuilder.addField("", "", false);
        channel.sendMessage(embedBuilder.build()).queue();

        return true;
    }
}
