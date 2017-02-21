package za.co.jesseleresche.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import za.co.jesseleresche.model.Playlist;
import za.co.jesseleresche.model.Playlists;
import za.co.jesseleresche.model.SearchObject;
import za.co.jesseleresche.model.Tracks;
import za.co.jesseleresche.service.PlaylistService;
import za.co.jesseleresche.service.UserService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static za.co.jesseleresche.helper.DataCreationHelper.*;

/**
 * This class tests the Playlist Controller to ensure that it provides the required
 * responses to the various endpoints on available.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PlaylistControllerTest {

    private static final String LOGIN_REDIRECT_URL = "http://localhost/login/deezer";

    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private PlaylistService playlistService;

    @MockBean
    private UserService userService;

    private Playlists playlists;

    @Before
    public void setUp() throws Exception {
        playlists = createPlaylists(3);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void testGetPlaylistsForAuthenticatedUser() throws Exception {
        given(userService.getUser()).willReturn(playlists.getPlaylists().get(0).getUser());
        given(playlistService.getPlaylists()).willReturn(playlists);
        mockMvc.perform(get("/playlists/").with(user("user")).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(model().attribute("playlists", playlists.getPlaylists()))
                .andExpect(model().attribute("newPlaylist", Matchers.notNullValue()))
                .andExpect(view().name("playlists"))
                .andExpect(content().string(containsString("<td><img src=\"" + playlists.getPlaylists().get(0).getPicture_small() + "\" /></td>")))
                .andExpect(content().string(containsString("<a href=\"/playlists/" + playlists.getPlaylists().get(0).getId() + "\">" + playlists.getPlaylists().get(0).getTitle() + "</a>")))
                .andExpect(content().string(containsString("<input type=\"hidden\" id=\"user.id\" name=\"user.id\" value=\"" + playlists.getPlaylists().get(0).getUser().getId() + "\" />")));
    }

    @Test
    public void testGetPlaylistsWithIncorrectToken() throws Exception {
        mockMvc.perform(get("/playlists"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(LOGIN_REDIRECT_URL));
    }

    @Test
    public void testCreatePlaylistWithCorrectToken() throws Exception {
        Playlist playlist = createPlaylist();
        Playlist mergedPlaylist = createPlaylist();

        given(playlistService.createPlaylist(any(Playlist.class))).willReturn(mergedPlaylist);
        mockMvc.perform(post("/playlists/new").with(user("user")).with(csrf()).accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", playlist.getTitle())
                .param("user.id", playlist.getUser().getId().toString())
                .param("id", playlist.getId().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/playlists/" + mergedPlaylist.getId()));
    }

    @Test
    public void testCreatePlaylistWithIncorrectToken() throws Exception {
        mockMvc.perform(post("/playlists/new").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(LOGIN_REDIRECT_URL));
    }

    @Test
    public void testGetPlaylist() throws Exception {
        Playlist playlist = createPlaylist();
        given(playlistService.getPlaylist(playlist.getId())).willReturn(playlist);
        mockMvc.perform(get("/playlists/" + playlist.getId()).with(user("user")))
                .andExpect(status().isOk())
                .andExpect(view().name("getPlaylist"))
                .andExpect(model().attribute("playlist", playlist));
    }

    @Test
    public void testSearch() throws Exception {
        Playlist playlist = createPlaylist();
        List<Tracks> tracksList = createTracksList(5);
        SearchObject searchObject = new SearchObject();
        given(playlistService.searchForSongs(searchObject.getSearchString())).willReturn(tracksList);
        given(playlistService.getPlaylist(searchObject.getId())).willReturn(playlist);
        mockMvc.perform(post("/playlists/search").with(user("user")).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("getPlaylist"))
                .andExpect(model().attribute("playlist", playlist))
                .andExpect(model().attribute("tracksList", tracksList));
//        TODO: Test that elements are displayed correctly on front-end
    }

    @Test
    public void testAddSongsWithCorrectToken() throws Exception {
        Playlist playlist = createPlaylist();
        Tracks tracks = createTracks(3, true);
        given(playlistService.addSongs(playlist.getId(), tracks)).willReturn(true);
        mockMvc.perform(post("/playlists/" + playlist.getId()).with(user("user")).with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testAddSongsWithIncorrectToken() throws Exception {
        mockMvc.perform(post("/playlists/1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(LOGIN_REDIRECT_URL));
    }

    @Test
    public void testEditSongsWithCorrectToken() throws Exception {
        Playlist playlist = createPlaylist();
        mockMvc.perform(put("/playlists/" + playlist.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testEditSongsWithIncorrectToken() throws Exception {
        mockMvc.perform(put("/playlists/1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(LOGIN_REDIRECT_URL));
    }

    @Autowired
    public void setContext(WebApplicationContext context) {
        this.context = context;
    }
}
