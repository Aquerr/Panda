package io.github.aquerr.pandobot.commands;

import io.github.aquerr.pandobot.annotations.BotCommand;
import io.github.aquerr.pandobot.entities.VTEAMRoles;
import io.github.aquerr.pandobot.secret.SecretProperties;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

@BotCommand(minRole = VTEAMRoles.EVERYONE, argsCount = 1)
public class GifCommand implements ICommand
{
    @Override
    public boolean execute(User user, MessageChannel channel, List<String> args)
    {
//        if (args.size() != 1)
//        {
//            channel.sendMessage("Błąd! Zła ilość wymaganych argumentów! Wymagana ilość argumentów: 1").queue();
//            return false;
//        }

        try
        {
            String stringUrl = "https://api.giphy.com/v1/gifs/random";
            String charset = "UTF-8";

            String query = String.format("api_key=%s&tag=%s", URLEncoder.encode(SecretProperties.GIPHY_API_KEY, charset),
                    URLEncoder.encode(args.get(0), charset));

            URLConnection connection = new URL(stringUrl + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            JSONObject jsonObject = new JSONObject(result.toString());
            JSONObject object = jsonObject.getJSONObject("data");
            String test = object.get("bitly_gif_url").toString();

            channel.sendMessage(test).queue();
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
}
