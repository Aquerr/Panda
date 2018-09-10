package io.github.aquerr.pandobot.commands;

import io.github.aquerr.pandobot.annotations.BotCommand;
import io.github.aquerr.pandobot.entities.VTEAMRoles;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;

@BotCommand(minRole = VTEAMRoles.EVERYONE, argsCount = 0)
public class GifCommand implements ICommand
{
    @Override
    public boolean execute(User user, MessageChannel channel, List<String> args)
    {
        try
        {
            String stringUrl = "https://api.giphy.com/v1/gifs/random";
            String charset = "UTF-8";

//            String query = String.format("api_key=%s&limit=%s", URLEncoder.encode("MFDpeb0LvfIRu4RIkDPrEQamYMLywmBS", charset),
//                    URLEncoder.encode("1", charset));

            String query = String.format("api_key=%s", URLEncoder.encode("MFDpeb0LvfIRu4RIkDPrEQamYMLywmBS", charset));

            URLConnection connection = new URL(stringUrl + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);

            //InputStream stream = connection.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            JSONObject jsonObject = new JSONObject(result.toString());
            rd.close();

            JSONObject object = jsonObject.getJSONObject("data");
            String test = object.get("bitly_gif_url").toString();

//            JSONArray array = jsonObject.getJSONArray("data");
//            JSONObject object = array.getJSONObject(0);
//            String test = object.get("bitly_gif_url").toString();

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
