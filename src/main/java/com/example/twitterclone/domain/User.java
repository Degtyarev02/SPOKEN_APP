package com.example.twitterclone.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Username can't be empty")
	@Length(max = 30, message = "Username is too long")
	private String username;

	@NotBlank(message = "Password can't be empty")
	private String password;

	@Transient
	private String password2;

	private String status;
	private String iconname;

	private boolean active;

	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
			foreignKey = @ForeignKey(
					name = "user_fk",
					foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE")
	)
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;


	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinTable(name = "subscriptions_table",
			joinColumns = {@JoinColumn(name = "subscriber_id")},
			inverseJoinColumns = {@JoinColumn(name = "subscription_id")})
	private Set<User> subscribers = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinTable(name = "subscriptions_table",
			joinColumns = {@JoinColumn(name = "subscription_id")},
			inverseJoinColumns = {@JoinColumn(name = "subscriber_id")})
	private Set<User> subscriptions = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinTable(name = "likes_table",
			joinColumns = @JoinColumn(name = "person_id"),
			inverseJoinColumns = @JoinColumn(name = "post_id"))
	private Set<Message> likedPosts = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public boolean isAdmin() {
		return roles.contains(Role.ADMIN);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIconname() {
		return iconname;
	}

	public void setIconname(String iconname) {
		this.iconname = iconname;
	}

	public Set<User> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Set<User> subscribers) {
		this.subscribers = subscribers;
	}

	public Set<User> getSubscriptions() {
		return subscriptions;
	}

	public int getSubscribersSize() {
		return subscribers.size();
	}

	public int getSubscriptionsSize() {
		return subscriptions.size();
	}

	public void setSubscriptions(Set<User> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public Set<Message> getLikedPosts() {
		return likedPosts;
	}

	public void setLikedPosts(Set<Message> likedPosts) {
		this.likedPosts = likedPosts;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isActive();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
