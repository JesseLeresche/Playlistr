package za.co.jesseleresche.model;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jesse on 2016/11/10.
 */
public class Playlist {

    private Long id;

    private String title;

    private String tracklist;

    private String picture_small;

    @JsonProperty("public")
    private Boolean isPublic;

    private Boolean collaborative;

    @JsonProperty("creator")
    private User user;

    private Tracks tracks;

    public Playlist() {
    }

    public Playlist(User user) {
        this.user = user;
    }

    public Playlist(String title) {
        this.title = title;
    }

    public Playlist(Long id, String title, User user, String picture_small) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.picture_small = picture_small;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    public String getPicture_small() {
        return picture_small;
    }

    public void setPicture_small(String picture_small) {
        this.picture_small = picture_small;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getCollaborative() {
        return collaborative;
    }

    public void setCollaborative(Boolean collaborative) {
        this.collaborative = collaborative;
    }
}
