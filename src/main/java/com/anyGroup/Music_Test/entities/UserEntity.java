package com.anyGroup.Music_Test.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", unique = true, length = 20, nullable = false)
    private String username;

    @Column(name = "email", unique = true, length = 100, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private RoleEntity role;

    @OneToMany
    @JoinTable(name = "users-playlists",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "playlist_id")})
    private List<PlaylistEntity> playlists;

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    // Getters and setters
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toString());

        return List.of(authority);
    }

    @Override
    public String getUsername() { return username; }
    public UserEntity setUsername(String username) { this.username = username; return this; }

    public Long getId() { return id; }
    public UserEntity setId(Long id) { this.id = id; return this; }

    public String getEmail() { return email; }
    public UserEntity setEmail(String email) { this.email = email; return this; }

    public String getPassword() { return password; }
    public UserEntity setPassword(String password) { this.password = password; return this;}

    public RoleEntity getRole() { return role; }
    public UserEntity setRole(RoleEntity role) { this.role = role; return this; }

    public List<PlaylistEntity> getPlaylists() { return this.playlists; }
    public UserEntity setPlaylists(List<PlaylistEntity> playlists) { this.playlists = playlists; return this; }
    // Getters and setters
}
