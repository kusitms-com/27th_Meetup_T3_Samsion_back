package com.kusitms.samsion.domain.album.domain.entity;

import com.kusitms.samsion.common.domain.BaseEntity;
import com.kusitms.samsion.domain.comment.domain.entity.Comment;
import com.kusitms.samsion.domain.empathy.domain.entity.Empathy;
import com.kusitms.samsion.domain.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE album SET is_deleted = true WHERE album_id = ?")
@Where(clause = "is_deleted = false")
public class Album extends BaseEntity {

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "album_id")
	private Long id;

	private String title;
	private String description;

	private boolean isDeleted = Boolean.FALSE;

	@Enumerated(EnumType.STRING)
	private Visibility visibility;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User writer;

	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
	private List<AlbumImage> albumImages = new ArrayList<>();

	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
	private List<Empathy> empathies = new ArrayList<>();

	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
	private List<Tag> tags = new ArrayList<>();

	@Builder
	public Album(String title, String description, Visibility visibility, User writer) {
		this.title = title;
		this.description = description;
		this.visibility = visibility;
		this.writer = writer;
	}

	public void updateTitle(String title) {
		if(!Objects.equals(this.title, title)&& title != null) {
			this.title = title;
		}
	}

	public void updateDescription(String description) {
		if(!Objects.equals(this.description, description)&& description != null) {
			this.description = description;
		}
	}

	public void updateVisibility(Visibility visibility) {
		if(!Objects.equals(this.visibility, visibility)&& visibility != null) {
			this.visibility = visibility;
		}
	}

	public void addImage(AlbumImage albumImage){
		albumImages.add(albumImage);
	}

	public void addComment(Comment comment) {comments.add(comment);}


	public void addTag(Tag tag) {tags.add(tag);}

	public void changeAllImage(List<AlbumImage> albumImageList){
		this.albumImages = albumImageList;
	}

	public void changeAllTag(List<Tag> tagList){
		this.tags = tagList;
	}

	public void clearAllImage(){
		this.albumImages= new ArrayList<>();
	}

	public void clearAllTag() {
		this.tags = new ArrayList<>();
	}
}
