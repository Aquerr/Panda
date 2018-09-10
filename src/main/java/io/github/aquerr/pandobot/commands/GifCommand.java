package io.github.aquerr.pandobot.commands;

import io.github.aquerr.pandobot.annotations.BotCommand;
import io.github.aquerr.pandobot.entities.VTEAMRoles;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@BotCommand(minRole = VTEAMRoles.EVERYONE, argsCount = 0)
public class GifCommand implements ICommand
{
    @Override
    public boolean execute(User user, MessageChannel channel, List<String> args)
    {
        try
        {
            StringBuilder result = new StringBuilder();
            URL url = new URL("api.giphy.com/v1/gifs/trending");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("api_key", "FDpeb0LvfIRu4RIkDPrEQamYMLywmBS");
            connection.setRequestProperty("limit", "1");
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
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
