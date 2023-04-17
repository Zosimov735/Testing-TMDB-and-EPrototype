package com.example.apiattempt;

import com.google.gson.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Contoller {

    @RequestMapping("/moviesdetails")
    public String movie() throws Exception{
        String uri = "https://api.themoviedb.org/3/movie/550?api_key=a37b4cb3ae6aa5cc3bb9ed882c3e341e&language=en-US";

        String json = new RestTemplate().getForObject(uri, String.class);

        Gson gson = new Gson();
        Movie movie = gson.fromJson(json,Movie.class);
        String title = movie.getTitle();


        return title;
    }
}
