package za.co.jesseleresche.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private RestOperations restOperations;

    @Value("${deezer.base.url}")
    private String deezerUrl;

    public Playlists getPlaylists(String accessToken) {
        return restOperations.getForObject(deezerUrl + "/user/me/playlists?access_token={accessToken}", Playlists.class, accessToken);
    }

    public Playlist createPlaylist(Playlist playlist, String accessToken) {
        Playlist returnedPlaylist = restOperations.postForObject(deezerUrl + "/user/{userId}/playlists?title={title}&access_token={accessToken}", null, Playlist.class, playlist.getUser().getId(), playlist.getTitle(), accessToken);
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
            Tracks tracks = restOperations.getForObject(deezerUrl + queryParam, Tracks.class);
            tracksList.add(tracks);
        }
        return tracksList;
    }

    public Boolean addSongs(Long playlistId, Tracks songsList, String accessToken) {
        String trackIds = normaliseSongsList(songsList, true);
        return restOperations.postForObject(deezerUrl + "/playlist/{playlistId}/tracks?access_token={accessToken}&songs={songs}", null, Boolean.class, playlistId, accessToken, trackIds);
    }


    public void editPlaylist(Long playlistId, Playlist playlist, String accessToken) {
        String trackIds = normaliseSongsList(playlist.getTracks(), false);
        restOperations.delete(deezerUrl + "/playlist/{playlistId}/tracks?access_token={accessToken}&songs={songs}", playlistId, accessToken, trackIds);
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
        return restOperations.getForObject(deezerUrl + "/playlist/{playlistId}", Playlist.class, playlistId);
    }

    @Autowired
    public void setRestOperations(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

}
