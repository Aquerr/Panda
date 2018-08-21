package io.github.aquerr.pandobot;

import events.MessageListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import secret.SecretProperties;

public class PandoBot
{
    public static void main(String[] args)
    {
        try
        {
            JDA jda = new JDABuilder(AccountType.BOT)
                    .setToken(SecretProperties.BOT_TOKEN)
                    .setGame(Game.of(Game.GameType.DEFAULT, "Pandaria", ""))
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
