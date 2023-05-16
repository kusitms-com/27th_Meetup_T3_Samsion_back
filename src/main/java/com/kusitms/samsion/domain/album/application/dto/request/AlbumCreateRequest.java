package com.kusitms.samsion.domain.album.application.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kusitms.samsion.domain.album.domain.entity.EmotionTag;
import com.kusitms.samsion.domain.album.domain.entity.Visibility;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AlbumCreateRequest {

	private final String description;
	private final Visibility visibility;
	private final List<MultipartFile> albumImages;
	private final List<EmotionTag> emotionTags;

	@Builder
	public AlbumCreateRequest(String description, Visibility visibility, List<MultipartFile> albumImages,
		List<EmotionTag> emotionTags) {
		this.description = description;
		this.visibility = visibility;
		this.albumImages = albumImages;
		this.emotionTags = emotionTags;
	}
}
