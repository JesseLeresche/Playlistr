package za.co.jesseleresche.model;

/**
 * Created by jesse on 2016/11/14.
 */
public class Album {

    private Long id;

    private String title;

    private String cover_small;

    public Album() {
    }

    public Album(Long id, String title, String cover_small) {
        this.id = id;
        this.title = title;
        this.cover_small = cover_small;
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

    public String getCover_small() {
        return cover_small;
    }

    public void setCover_small(String cover_small) {
        this.cover_small = cover_small;
    }
}
