package za.co.jesseleresche.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.co.jesseleresche.model.Playlist;
import za.co.jesseleresche.model.Playlists;
import za.co.jesseleresche.model.SearchObject;
import za.co.jesseleresche.model.Tracks;
import za.co.jesseleresche.service.PlaylistService;
import za.co.jesseleresche.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * This controller handles the playlist operations, such as querying details for a
 * playlist, editing a playlist or adding a new one.
 */
@Controller
@RequestMapping("/playlists")
public class PlaylistController {

    private static final String PLAYLISTS = "/playlists/";
    private static final String REDIRECT = "redirect:";
    private static final String GET_PLAYLIST = "getPlaylist";

    private PlaylistService playlistService;

    private UserService userService;

    private Logger logger = Logger.getLogger(PlaylistController.class);

    @GetMapping(value = {"", "/"})
    public String getPlaylists(Model model) {
        Playlists playlists = playlistService.getPlaylists();
        Playlist playlist = new Playlist(userService.getUser());
        model.addAttribute("playlists", playlists.getPlaylists());
        model.addAttribute("newPlaylist", playlist);
        return "playlists";
    }

    @PostMapping(value = "/new")
    public String createPlaylist(@ModelAttribute(value = "playlist") Playlist playlist) {
        Playlist createdPlaylist = playlistService.createPlaylist(playlist);
        return REDIRECT + PLAYLISTS + createdPlaylist.getId();
    }

    @GetMapping(value = "/{playlistId}")
    public String getPlaylist(@PathVariable("playlistId") Long playlistId, Model model) {
        model.addAttribute("playlist", playlistService.getPlaylist(playlistId));
        model.addAttribute("searchObject", new SearchObject(playlistId));
        return GET_PLAYLIST;
    }

    @PostMapping(value = "/search")
    public String search(@ModelAttribute("searchObject") SearchObject searchObject, Model model) {
        List<Tracks> tracksList = playlistService.searchForSongs(searchObject.getSearchString());
        model.addAttribute("playlist", playlistService.getPlaylist(searchObject.getId()));
        model.addAttribute("songsList", new Tracks());
        model.addAttribute("tracksList", tracksList);
        searchObject.setSearchString(null);
        return GET_PLAYLIST;
    }

    @PostMapping(value = "/{playlistId}")
    public String addSongs(@ModelAttribute("songsList") Tracks songsList, @PathVariable("playlistId") Long playlistId, HttpSession httpSession) {
        playlistService.addSongs(playlistId, songsList);
        return REDIRECT + PLAYLISTS + playlistId;
    }

    @PutMapping(value = "/{playlistId}")
    public String editSongs(@ModelAttribute("playlist") Playlist playlist, @PathVariable("playlistId") Long playlistId, HttpSession httpSession) {
        playlistService.editPlaylist(playlistId, playlist);
        return REDIRECT + PLAYLISTS + playlistId;
    }

    @Autowired
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
