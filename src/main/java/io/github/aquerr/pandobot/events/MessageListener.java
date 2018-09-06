package io.github.aquerr.pandobot.events;

import io.github.aquerr.pandobot.PandoBot;
import io.github.aquerr.pandobot.entities.MessageTypes;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageEmbedEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageListener extends ListenerAdapter
{
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getMessage().getContentDisplay().startsWith("!"))
        {
            PandoBot.getInstance().processCommand(event.getAuthor(), event.getChannel(), event.getMessage());
        }

        if(event.getMessage().getEmbeds().size() > 0 && event.getAuthor().getIdLong() == 481489722003161119L)
        {
            MessageEmbed messageEmbed = event.getMessage().getEmbeds().get(0);

            //Survey
            if(messageEmbed.getTitle() != null && messageEmbed.getTitle().startsWith(":bookmark_tabs:"))
            {
                event.getMessage().addReaction("❤").queue();
                event.getMessage().addReaction("\uD83D\uDC4D").queue();
                event.getMessage().addReaction("\uD83D\uDC4E").queue();
            }
        }
    }

    @Override
    public void onMessageEmbed(MessageEmbedEvent event)
    {

    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event)
    {
        //String below = 👍
//        if("\uD83D\uDC4D".equals(event.getReaction().getReactionEmote().getName()))
//        {
//            event.getGuild().getController().addRolesToMember(event.getMember(), event.getJDA().getRoleById(481503508814626826L)).queue();
//            event.getChannel().sendMessage("Co mi kciuki dajesz kurcze?!").queue();
//        }
    }
}
