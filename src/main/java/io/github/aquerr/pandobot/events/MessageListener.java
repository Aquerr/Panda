package io.github.aquerr.pandobot.events;

import io.github.aquerr.pandobot.PandoBot;
import io.github.aquerr.pandobot.entities.VTEAMRoles;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.*;

public class MessageListener extends ListenerAdapter
{
    private Map<Long, Timer> confirmationMessages = new HashMap<>();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
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

            //Clear confirmation
            if (messageEmbed.getFields().get(0).getName().startsWith(":warning: Czy na pewno chcesz usunąć"))
            {
                Message message = event.getMessage();
                message.addReaction("\uD83C\uDDFE").queue();
                message.addReaction("\uD83C\uDDF3").queue();

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (confirmationMessages.containsKey(message.getIdLong()))
                        {
                            confirmationMessages.remove(message.getIdLong());
                            List<Message> messagesList = event.getChannel().getHistoryBefore(message.getIdLong(), 2).complete().getRetrievedHistory();
                            messagesList.forEach(x->x.delete().queue());
                        }
                    }
                }, 10000);
                confirmationMessages.put(message.getIdLong(), timer);
            }
        }
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event)
    {
        //Handle Clear Confirmation Message
        if (confirmationMessages.containsKey(event.getMessageIdLong()) && event.getUser().getIdLong() != PandoBot.PANDA_ID)
        {
            String test = event.getReactionEmote().getName();
            Message message = event.getChannel().getMessageById(event.getMessageId()).complete();

            //Yes
            if (test.equals("\uD83C\uDDFE"))
            {
                MessageEmbed messageEmbed = message.getEmbeds().get(0);
                int index = messageEmbed.getFields().get(0).getName().indexOf('(');
                int endIndex = messageEmbed.getFields().get(0).getName().indexOf(')');
                String stringNumber = messageEmbed.getFields().get(0).getName().substring(index + 1, endIndex);
                int number = Integer.valueOf(stringNumber);
                List<Message> messagesList = event.getChannel().getHistoryBefore(message, number + 1).complete().getRetrievedHistory();
                message.delete().queue();
                event.getChannel().deleteMessages(messagesList).complete();
                confirmationMessages.remove(event.getMessageIdLong());
            }
            else //No
            {
                confirmationMessages.remove(event.getMessageIdLong());
                List<Message> messagesList = event.getChannel().getHistoryBefore(message,  1).complete().getRetrievedHistory();
                message.delete().queue();
                event.getChannel().deleteMessages(messagesList).complete();
            }
        }
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event)
    {
        event.getGuild().getController().addSingleRoleToMember(event.getMember(), event.getJDA().getRoleById(VTEAMRoles.GRACZ.getId())).queue();
    }
}
