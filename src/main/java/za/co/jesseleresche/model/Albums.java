package za.co.jesseleresche.model;

import java.util.List;

/**
 * Created by jesse on 2016/11/20.
 */
public class Albums {

    private List<Album> albums;

    public Albums() {
    }

    public Albums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
