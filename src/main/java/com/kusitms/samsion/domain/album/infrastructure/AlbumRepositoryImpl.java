package com.kusitms.samsion.domain.album.infrastructure;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.kusitms.samsion.common.querydsl.OrderByNull;
import com.kusitms.samsion.domain.album.domain.entity.SortType;
import com.kusitms.samsion.domain.album.domain.entity.Album;
import com.kusitms.samsion.domain.album.domain.entity.EmotionTag;
import com.kusitms.samsion.domain.album.domain.entity.QAlbum;
import com.kusitms.samsion.domain.album.domain.entity.QTag;
import com.kusitms.samsion.domain.album.domain.entity.Tag;
import com.kusitms.samsion.domain.album.domain.entity.Visibility;
import com.kusitms.samsion.domain.album.domain.repository.AlbumRepositoryCustom;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AlbumRepositoryImpl implements AlbumRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Slice<Album> findAlbumList(Pageable pageable, List<EmotionTag> emotionTagList, SortType sortType) {
		List<Album> albumList = jpaQueryFactory
			.select(QAlbum.album).distinct()
			.from(QAlbum.album)
			.where(QAlbum.album.visibility.eq(Visibility.PUBLIC),
				getTagsInQuery(emotionTagList))
			.orderBy(getSortedColumn(sortType),
				QAlbum.album.createdDate.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		boolean hasNext = false;
		if (albumList.size() > pageable.getPageSize()) {
			hasNext = true;
			albumList.remove(albumList.size() - 1);
		}

		return new SliceImpl<>(albumList, pageable, hasNext);
	}

	private static BooleanExpression getTagsInQuery(List<EmotionTag> emotionTagList) {
		if(emotionTagList == null || emotionTagList.isEmpty()) {
			return null;
		}
		return QAlbum.album.tags.any().in(
			getTagsInSubQuery(emotionTagList));
	}

	private static JPQLQuery<Tag> getTagsInSubQuery(List<EmotionTag> emotionTagList) {
		return JPAExpressions.select(QTag.tag)
			.from(QTag.tag)
			.where(QTag.tag.emotionTag.in(emotionTagList));
	}

	private OrderSpecifier<?> getSortedColumn(SortType sortType) {
		switch (sortType) {
			case EMPATHY:
				return QAlbum.album.empathies.size().desc();
			case COMMENT:
				return QAlbum.album.comments.size().desc();
		}
		return OrderByNull.getDefault();
	}

}