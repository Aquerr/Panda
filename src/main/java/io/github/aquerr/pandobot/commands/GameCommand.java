package io.github.aquerr.pandobot.commands;

import io.github.aquerr.pandobot.annotations.BotCommand;
import io.github.aquerr.pandobot.entities.VTEAMRoles;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.List;

@BotCommand(minRole = VTEAMRoles.MODERATOR, argsCount = 1)
public class GameCommand implements ICommand
{
    @Override
    public boolean execute(User user, MessageChannel channel, List<String> args)
    {
//        if(args.size() != 1)
//        {
//            channel.sendMessage("Błąd! Zła ilość wymaganych argumentów! Wymagana ilość argumentów: 1").queue();
//            return false;
//        }

        channel.getJDA().getPresence().setGame(Game.of(Game.GameType.DEFAULT, args.get(0)));

        return false;
    }

    @Override
    public String getUsage()
    {
        return "!game \"nazwa_gry\"";
    }
}
