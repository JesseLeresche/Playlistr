package za.co.jesseleresche.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import za.co.jesseleresche.model.Playlist;
import za.co.jesseleresche.model.Playlists;
import za.co.jesseleresche.model.Track;
import za.co.jesseleresche.model.Tracks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This service handles all of the operations with playlists, such as making requests to Deezer to create neqw playlists
 * edit playlists and so forht.
 */
@Service
public class PlaylistService {

    private OAuth2RestTemplate oAuth2RestTemplate;

    @Value("${deezer.base.url}")
    private String deezerUrl;

    public Playlists getPlaylists() {
        return oAuth2RestTemplate.getForObject(deezerUrl +"/user/me/playlists", Playlists.class);
    }

    public Playlist createPlaylist(Playlist playlist) {
        Playlist returnedPlaylist = oAuth2RestTemplate.postForObject(deezerUrl + "/user/{userId}/playlists?title={title}", null, Playlist.class, playlist.getUser().getId(), playlist.getTitle());
        playlist.setId(returnedPlaylist.getId());
        return playlist;
    }

    public List<Tracks> searchForSongs(String songs) {
        List<String> rows = Arrays.asList(songs.split("\n"));
        List<Tracks> tracksList = new ArrayList<>();
        for (String row : rows) {
            String artist = "\"" + row.substring(0, row.indexOf("-")).trim() + "\"";
            String song = "\"" + row.substring(row.indexOf("-") + 1).trim() + "\"";
            String queryParam = "/search?q=artist:" + artist + " track:" + song + "&order=RANKING";
            Tracks tracks = oAuth2RestTemplate.getForObject(deezerUrl + queryParam, Tracks.class);
            tracksList.add(tracks);
        }
        return tracksList;
    }

    public Boolean addSongs(Long playlistId, Tracks songsList) {
        String trackIds = normaliseSongsList(songsList, true);
        return oAuth2RestTemplate.postForObject(deezerUrl + "/playlist/{playlistId}/tracks?&songs={songs}", null, Boolean.class, playlistId, trackIds);
    }


    public void editPlaylist(Long playlistId, Playlist playlist) {
        String trackIds = normaliseSongsList(playlist.getTracks(), false);
        oAuth2RestTemplate.delete(deezerUrl + "/playlist/{playlistId}/tracks?songs={songs}", playlistId, trackIds);
    }

    private String normaliseSongsList(Tracks songsList, Boolean isAddOperation) {
        String trackIds = null;
        for (Track track : songsList.getTracklist()) {
            if (track.getShouldAdd() == isAddOperation && track.getId() != null) {
                if (trackIds == null) {
                    trackIds = track.getId().toString();
                } else {
                    trackIds += "," + track.getId().toString();
                }
            }
        }
        return trackIds;
    }

    public Playlist getPlaylist(Long playlistId) {
        return oAuth2RestTemplate.getForObject(deezerUrl + "/playlist/{playlistId}", Playlist.class, playlistId);
    }


    @Autowired
    public void setOAuth2RestTemplate(OAuth2RestTemplate oAuth2RestTemplate) {
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }
}
