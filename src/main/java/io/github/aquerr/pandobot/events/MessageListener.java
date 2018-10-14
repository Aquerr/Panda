package io.github.aquerr.pandobot.events;

import io.github.aquerr.pandobot.PandoBot;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter
{
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getMessage().getContentDisplay().startsWith("!"))
        {
            PandoBot.getInstance().getCommandManager().processCommand(event.getMember(), event.getChannel(), event.getMessage());
        }

        if(event.getMessage().getEmbeds().size() > 0 && event.getAuthor().getIdLong() == PandoBot.PANDA_ID)
        {
            MessageEmbed messageEmbed = event.getMessage().getEmbeds().get(0);

            //Survey
            if(messageEmbed.getTitle() != null && messageEmbed.getTitle().startsWith(":bookmark_tabs:"))
            {
                event.getMessage().addReaction("\uD83D\uDC4D").queue();
                event.getMessage().addReaction("\uD83D\uDC4E").queue();
                event.getMessage().addReaction("❤").queue();
            }
        }
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event)
    {
//        event.getMember().getJoinDate().get
    }
}
