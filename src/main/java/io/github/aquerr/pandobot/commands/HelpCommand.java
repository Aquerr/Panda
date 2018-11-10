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

@BotCommand(argsCount = 0)
public class HelpCommand implements ICommand
{
    @Override
    public boolean execute(User user, MessageChannel channel, List<String> args)
    {
        Map<List<String>, ICommand> commands = PandoBot.getInstance().getCommandManager().getCommands();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(PandoBot.PANDA_EMBED_COLOR_DEFAULT);
        embedBuilder.setTitle("Dostępne komendy u Pandy:  ");

        embedBuilder.addField("Komendy", ":small_blue_diamond: Użytkownik:\n" +
                ":small_orange_diamond: !pomoc · wyświetla spis dostępnych komend\n" +
                ":small_orange_diamond: !gif (txt) · wysyła gifa\n" +
                ":small_orange_diamond: !ankieta (nazwa ankiety) (reakcja A) (reakcja B) (reakcja C) · tworzy ankietę\n" +
                "\n" +
                ":small_blue_diamond: Moderator:\n" +
                ":small_orange_diamond: !usun (ilość) · usuwa wiadomości\n" +
                "\n" +
                ":small_blue_diamond: Właściciel:\n" +
                ":small_orange_diamond: !opis (txt) · ustawia opis bota", false);

//        for (Map.Entry<List<String>, ICommand> commandEntry : commands.entrySet())
//        {
//            String commandAliases = String.join(", ", commandEntry.getKey());
//
//            embedBuilder.addField(commandAliases, "", false);
//        }

//        embedBuilder.setDescription("- - - - - - - - - - - - - - - -" + "\n" + ":heart: - " + args.get(1) + "\n\n" +
//                ":thumbsup: - " + args.get(2) + "\n\n" +
//                ":thumbsdown: - " + args.get(3));
        channel.sendMessage(embedBuilder.build()).queue();

        return true;
    }

    @Override
    public String getUsage()
    {
        return "!help";
    }
}
