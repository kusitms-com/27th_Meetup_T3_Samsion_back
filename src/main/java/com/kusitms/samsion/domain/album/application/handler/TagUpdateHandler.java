package com.kusitms.samsion.domain.album.application.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.kusitms.samsion.common.annotation.UseCase;
import com.kusitms.samsion.domain.album.application.dto.request.TagUpdateRequest;
import com.kusitms.samsion.domain.album.domain.entity.Album;
import com.kusitms.samsion.domain.album.domain.entity.Tag;
import com.kusitms.samsion.domain.album.domain.service.AlbumQueryService;
import com.kusitms.samsion.domain.album.domain.service.TagUpdateService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TagUpdateHandler {

	private final AlbumQueryService albumQueryService;
	private final TagUpdateService tagUpdateService;

	@TransactionalEventListener
	public void updateTag(TagUpdateRequest tagUpdateRequest) {
		final Album album = albumQueryService.findAlbumById(tagUpdateRequest.getAlbumId());
		final List<Tag> tagList = tagUpdateRequest.getTagList().stream()
			.map(tag -> new Tag(tag, album))
			.collect(Collectors.toList());
		tagUpdateService.updateTag(album, tagList);
	}
}
