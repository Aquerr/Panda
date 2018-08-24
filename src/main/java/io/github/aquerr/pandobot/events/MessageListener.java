package io.github.aquerr.pandobot.events;

import io.github.aquerr.pandobot.PandoBot;
import io.github.aquerr.pandobot.entities.MessageTypes;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter
{
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getMessage().getContentDisplay().startsWith("!"))
        {
            PandoBot.processCommand(event.getAuthor(), event.getChannel(), event.getMessage());
        }






        if(event.getMessage().getContentDisplay().startsWith(MessageTypes.HELLO))
        {
            event.getChannel().sendMessage("Siemaneczko!").queue();
        }
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
