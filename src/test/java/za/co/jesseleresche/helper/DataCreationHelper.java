package za.co.jesseleresche.helper;

import org.springframework.mock.web.MockHttpSession;
import za.co.jesseleresche.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jesse on 2017/01/11.
 */
public class DataCreationHelper {

    public static Playlist createPlaylist() {
        return createPlaylist(1L, true);
    }

    public static Playlist createPlaylist(Long id, Boolean shoulAdd) {
        Playlist playlist = new Playlist(id, "Playlist " + id, createUser(), "Picture " + id);
        playlist.setTracks(createTracks(3, shoulAdd));
        return playlist;
    }

    public static List<Playlist> createListOfPlaylist(Integer numberOfPlaylists) {
        List<Playlist> playlists = new ArrayList<>();
        for (int i = 0; i < numberOfPlaylists; i++) {
            playlists.add(createPlaylist((long) i, true));
        }
        return playlists;
    }

    public static Playlists createPlaylists(Integer numberOfPlaylist) {
        Playlists playlists = new Playlists();
        playlists.setPlaylists(createListOfPlaylist(numberOfPlaylist));
        return playlists;
    }

    public static User createUser() {
        return new User(1L, "Joe Soap", "http://placehold.it/350x150");
    }

    public static List<Track> createTrackList(Integer numberOfTracks, Boolean shouldAdd) {
        List<Track> trackList = new ArrayList<>();
        for (int i = 0; i < numberOfTracks; i++) {
            trackList.add(createTrack((long) i, shouldAdd));
        }
        return trackList;
    }

    private static Track createTrack(Long id, Boolean shouldAdd) {
        Track track = new Track(id, "Track " + id);
        Album album = new Album(id, "Album " + id, "cover_string");
        Artist artist = new Artist(id, "Artist " + id, "Artist link");
        track.setAlbum(album);
        track.setArtist(artist);
        track.setShouldAdd(shouldAdd);
        return track;
    }

    public static Tracks createTracks(Integer numberOfTrackslists, Boolean shouldAdd) {
        return new Tracks(createTrackList(numberOfTrackslists, shouldAdd));
    }

    public static List<Tracks> createTracksList(Integer numberOfTracks) {
        List<Tracks> tracksList = new ArrayList<>();
        for (int i = 0; i < numberOfTracks; i++) {
            tracksList.add(createTracks(numberOfTracks, true));
        }
        return tracksList;
    }

}
