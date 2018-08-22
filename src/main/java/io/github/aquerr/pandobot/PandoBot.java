package io.github.aquerr.pandobot;

import io.github.aquerr.pandobot.events.MessageListener;
import io.github.aquerr.pandobot.secret.SecretProperties;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

public class PandoBot
{
    public static void main(String[] args)
    {
        try
        {
            JDA jda = new JDABuilder(AccountType.BOT)
                    .setToken(SecretProperties.BOT_TOKEN)
                    .setGame(Game.of(Game.GameType.DEFAULT, "Pandaria", "https://github.com/Aquerr/PandoBot"))
                    .buildBlocking();

            System.out.println("Connectd!");
            jda.addEventListener(new MessageListener());
            jda.setAutoReconnect(true);

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
