package za.co.jesseleresche.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jesse on 2016/11/10.
 */
public class Playlists {

    @JsonProperty("data")
    private List<Playlist> playlists;

    public Playlists() {
        playlists = new ArrayList<>();
    }

    public Playlists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }
}
