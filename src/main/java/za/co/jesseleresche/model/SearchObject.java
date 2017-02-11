package za.co.jesseleresche.model;

/**
 * Created by jesse on 2016/11/21.
 */
public class SearchObject {

    private String searchString;

    private Long id;

    public SearchObject() {
    }

    public SearchObject(Long id) {
        this.id = id;
    }

    public SearchObject(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
