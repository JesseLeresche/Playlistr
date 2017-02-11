package za.co.jesseleresche.model;

/**
 * Created by jesse on 2016/11/14.
 */
public class Track {

    private Long id;

    private String title;

    private String preview;

    private Artist artist;

    private Album album;

    private Boolean shouldAdd;

    public Track() {
        this.shouldAdd = true;
    }

    public Track(Long id, String title, String preview, Artist artist, Album album) {
        this.id = id;
        this.title = title;
        this.preview = preview;
        this.artist = artist;
        this.album = album;
        this.shouldAdd = true;
    }

    public Track(Long id, String title) {
        this.id = id;
        this.title = title;
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

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Boolean getShouldAdd() {
        return shouldAdd;
    }

    public void setShouldAdd(Boolean shouldAdd) {
        this.shouldAdd = shouldAdd;
    }
}
