package io.github.aquerr.pandobot.commands;

import io.github.aquerr.pandobot.annotations.BotCommand;
import io.github.aquerr.pandobot.entities.VTEAMRoles;
import io.github.aquerr.pandobot.secret.SecretProperties;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

@BotCommand(argsCount = 1)
public class GifCommand implements ICommand
{
    private static final String GIPHY_RANDOM = "https://api.giphy.com/v1/gifs/random";
    private static final String CHARSET_UTF_8 = "UTF-8";

    @Override
    public boolean execute(User user, MessageChannel channel, List<String> args)
    {
        try
        {
            String query = String.format("api_key=%s&tag=%s", URLEncoder.encode(SecretProperties.GIPHY_API_KEY, CHARSET_UTF_8),
                    URLEncoder.encode(args.get(0), CHARSET_UTF_8));

            URLConnection connection = new URL(GIPHY_RANDOM + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", CHARSET_UTF_8);

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            JSONObject jsonObject = new JSONObject(result.toString());
            if(jsonObject.has("data"))
            {
                Object object = jsonObject.get("data");

                if(object instanceof JSONObject)
                {
                    JSONObject dataJsonObject = (JSONObject)object;
                    if(dataJsonObject.has("bitly_gif_url"))
                    {
                        String test = dataJsonObject.get("bitly_gif_url").toString();
                        channel.sendMessage(test).queue();
                    }
                    else
                    {
                        printError(channel);
                    }
                }
                else if(object instanceof JSONArray)
                {
                    printError(channel);
                }
            }
            else
            {
                printError(channel);
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public String getUsage()
    {
        return "!gif \"tag\"";
    }

    private void printError(MessageChannel channel){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription(":warning: Nie udało mi się znaleźć żadnego gifa");
        channel.sendMessage(embedBuilder.build());
    }
}
