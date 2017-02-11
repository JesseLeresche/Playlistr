package za.co.jesseleresche.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by jesse on 2016/11/20.
 */
public class Artists {

    @JsonProperty("data")
    private List<Artist> artists;

    public Artists() {
    }

    public Artists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
