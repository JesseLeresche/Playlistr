package za.co.jesseleresche.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by jesse on 2016/11/14.
 */
public class Tracks {

    @JsonProperty("data")
    private List<Track> tracklist;

    public Tracks() {
    }

    public Tracks(List<Track> tracklist) {
        this.tracklist = tracklist;
    }

    public List<Track> getTracklist() {
        return tracklist;
    }

    public void setTracklist(List<Track> tracklist) {
        this.tracklist = tracklist;
    }
}
