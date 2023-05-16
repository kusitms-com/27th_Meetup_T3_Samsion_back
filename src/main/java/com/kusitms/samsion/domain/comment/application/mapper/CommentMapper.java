package com.kusitms.samsion.domain.comment.application.mapper;

import com.kusitms.samsion.domain.comment.application.dto.response.CommentInfoResponse;
import com.kusitms.samsion.common.annotation.Mapper;
import com.kusitms.samsion.domain.album.domain.entity.Album;
import com.kusitms.samsion.domain.comment.domain.entity.Comment;
import com.kusitms.samsion.domain.user.domain.entity.User;

@Mapper
public class CommentMapper {

    public static Comment mapToCommentWithUserAndAlbum(String description, Album album, User user) {
        Comment comment = Comment.builder()
                .description(description)
                .album(album)
                .writer(user)
                .build();
        return comment;
    }

    public static Comment mapToCommentWithUserAndAlbumAndParent(String description, Album album, User user, Comment parent) {
        Comment comment = Comment.builder()
                .description(description)
                .album(album)
                .writer(user)
                .parent(parent)
                .build();
        return comment;
    }

    public static CommentInfoResponse mapToCommentInfoResponse(Comment comment) {
        final User writer = comment.getWriter();
        return CommentInfoResponse.builder()
                .description(comment.getDescription())
                .writer(writer.getNickname())
                .writerProfileImageUrl(writer.getMypet().getPetImageUrl())
                .build();
    }
}