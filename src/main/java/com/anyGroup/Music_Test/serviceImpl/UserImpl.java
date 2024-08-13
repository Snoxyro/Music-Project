package com.anyGroup.Music_Test.serviceImpl;

import com.anyGroup.Music_Test.dto.PlaylistRequest;
import com.anyGroup.Music_Test.dto.PlaylistResponse1;
import com.anyGroup.Music_Test.dto.UserResponse1;

import java.util.List;

public interface UserImpl {

    List<UserResponse1> allUsers();

    UserResponse1 userGetById(Long userId);

    List<PlaylistResponse1> userGetAllPlaylists(Long userId);

    PlaylistResponse1 userGetPlaylistById(Long userId, Long playlistId);

    void userCreatePlaylist(Long userId, PlaylistRequest playlistRequest);

    void userAddMusicToPlaylistById(Long userId, Long playlistId, Long musicId);

}
