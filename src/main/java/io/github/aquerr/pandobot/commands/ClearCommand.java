package io.github.aquerr.pandobot.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.impl.ReceivedMessage;

import java.util.List;

public class ClearCommand implements ICommand
{
    @Override
    public boolean execute(User user, MessageChannel channel, List<String> args)
    {
        if(args.size() != 1)
        {
            channel.sendMessage("Błąd! Zła ilość wymaganych argumentów! Wymagana ilość argumentów: 1").queue();
            return false;
        }

        List<Message> messagesList = channel.getHistory().retrievePast(Integer.parseInt(args.get(0)) + 1).complete();

        for(Message message : messagesList)
        {
            channel.deleteMessageById(message.getId()).queue();
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription("Usunięto " + Integer.parseInt(args.get(0)) + " wiadomości.");
        channel.sendMessage(embedBuilder.build()).queue();
        return true;
    }
}
