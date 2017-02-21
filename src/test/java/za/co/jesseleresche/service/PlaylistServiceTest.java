package za.co.jesseleresche.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.client.RestOperations;
import za.co.jesseleresche.model.Playlist;
import za.co.jesseleresche.model.Playlists;
import za.co.jesseleresche.model.Tracks;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static za.co.jesseleresche.helper.DataCreationHelper.*;

/**
 * Created by jesse on 2016/11/10.
 */
public class PlaylistServiceTest {

    private OAuth2RestTemplate oAuth2RestTemplate;

    private PlaylistService playlistService;

    @Before
    public void setup() {
        oAuth2RestTemplate = Mockito.mock(OAuth2RestTemplate.class);

        playlistService = new PlaylistService();
        playlistService.setOAuth2RestTemplate(oAuth2RestTemplate);
    }

    @Test
    public void testGetPlaylists() {
        Playlists playlists = createPlaylists(3);

        when(oAuth2RestTemplate.getForObject(anyString(), eq(Playlists.class))).thenReturn(playlists);

        Playlists returnedPlaylists = playlistService.getPlaylists();

        assertNotNull(returnedPlaylists);
        assertEquals(3, returnedPlaylists.getPlaylists().size());
        assertEquals(playlists.getPlaylists().get(0).getTitle(), returnedPlaylists.getPlaylists().get(0).getTitle());
        assertEquals(playlists.getPlaylists().get(0).getId(), returnedPlaylists.getPlaylists().get(0).getId());
    }

    @Test
    public void testCreatePlaylist() {
        Playlist playlist = createPlaylist();
        Playlist returnedPlaylist = createPlaylist();

        when(oAuth2RestTemplate.postForObject(anyObject(), eq(null), eq(Playlist.class), anyLong(), anyString())).thenReturn(returnedPlaylist);

        Playlist mergedPlaylist = playlistService.createPlaylist(playlist);

        assertNotNull(playlist);
        assertEquals(returnedPlaylist.getId(), mergedPlaylist.getId());
        assertEquals(playlist.getTitle(), mergedPlaylist.getTitle());
        assertEquals(playlist.getUser(), mergedPlaylist.getUser());
    }

    @Test
    public void testGetPlaylist() {
        Playlist requestPlaylist = createPlaylist();

        when(oAuth2RestTemplate.getForObject(anyString(), eq(Playlist.class), anyLong())).thenReturn(requestPlaylist);

        Playlist playlist = playlistService.getPlaylist(requestPlaylist.getId());

        assertNotNull(playlist);
        assertEquals(requestPlaylist.getTitle(), playlist.getTitle());
    }

    @Test
    public void testSearchForSongs() {
        String searchSongs = "Adele - Hello\nNirvana - Smells like teen spirit\nBlur - Song 2";
        Tracks tracks = createTracks(3, true);

        when(oAuth2RestTemplate.getForObject(anyString(), anyObject())).thenReturn(tracks);

        List<Tracks> returnedTracks = playlistService.searchForSongs(searchSongs);

        assertNotNull(returnedTracks);
        assertEquals(tracks.getTracklist().size(), returnedTracks.size());
        assertEquals(tracks.getTracklist().size(), returnedTracks.get(1).getTracklist().size());
    }

    @Test
    public void testAddSongsToPlaylist() {
        Playlist playlist = createPlaylist(1L, true);

        playlistService.addSongs(playlist.getId(), playlist.getTracks());

        verify(oAuth2RestTemplate, times(1)).postForObject(anyString(), eq(null), eq(Boolean.class), eq(playlist.getId()), anyString());
    }

    @Test
    public void testEditPlaylist() {
        Playlist playlist = createPlaylist(1L, false);

        playlistService.editPlaylist(playlist.getId(), playlist);

        verify(oAuth2RestTemplate, times(1)).delete(anyString(), eq(playlist.getId()), anyString());
    }
}
