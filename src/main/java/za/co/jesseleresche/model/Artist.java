package za.co.jesseleresche.model;

/**
 * Created by jesse on 2016/11/14.
 */
public class Artist {

    private Long id;

    private String name;

    private String link;

    public Artist() {
    }

    public Artist(Long id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
