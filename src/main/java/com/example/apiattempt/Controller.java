package com.example.apiattempt;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Controller {

    @Autowired
    private MovieRepo movieRepo;
    @RequestMapping("/moviesdetails")
    public String movie() throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new GsonBuilder().create();
        StringBuilder result = new StringBuilder();

        for (int movieId = 550; movieId <= 550; movieId++) {
            String uri = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=a37b4cb3ae6aa5cc3bb9ed882c3e341e&language=en-US";
            String json = restTemplate.getForObject(uri, String.class);
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

            Movie movie = new Movie();
            movie.setTitle(jsonObject.get("title").getAsString());

            String overview = jsonObject.get("overview").getAsString();
            if (overview.length() > 255) {
                overview = overview.substring(0, 255);
            }
            movie.setOverview(overview);

            List<Genre> genres = gson.fromJson(jsonObject.get("genres"), getGenresListType());
            movie.setGenres(String.join(", ", genres.stream().map(Genre::getName).collect(Collectors.toList())));

            movieRepo.save(movie); // Save the movie to the database

            result.append("Movie ID: ").append(movieId).append("\n")
                    .append("Title: ").append(movie.getTitle()).append("\n")
                    .append("Overview: ").append(movie.getOverview()).append("\n")
                    .append("Genres: ").append(movie.getGenres()).append("\n\n");
        }

        return result.toString();
    }

    private Type getGenresListType() {
        return new TypeToken<List<Genre>>() {}.getType();
    }
}
