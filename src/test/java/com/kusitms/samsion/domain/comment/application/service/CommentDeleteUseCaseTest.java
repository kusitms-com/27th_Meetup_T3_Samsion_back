package com.kusitms.samsion.domain.comment.application.service;

import com.kusitms.samsion.common.util.AlbumTestUtils;
import com.kusitms.samsion.common.util.CommentTestUtils;
import com.kusitms.samsion.common.util.UserTestUtils;
import com.kusitms.samsion.common.util.UserUtils;
import com.kusitms.samsion.domain.album.domain.entity.Album;
import com.kusitms.samsion.domain.comment.domain.entity.Comment;
import com.kusitms.samsion.domain.comment.domain.exception.NotSameUserException;
import com.kusitms.samsion.domain.comment.domain.service.CommentDeleteService;
import com.kusitms.samsion.domain.comment.domain.service.CommentQueryService;
import com.kusitms.samsion.domain.comment.domain.service.CommentValidAccessService;
import com.kusitms.samsion.domain.user.domain.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CommentDeleteUseCase 테스트")
public class CommentDeleteUseCaseTest {

    @Mock
    private UserUtils userUtils;
    @Mock
    private CommentQueryService commentQueryService;
    @Mock
    private CommentValidAccessService commentValidAccessService;
    @Mock
    private CommentDeleteService commentDeleteService;
    private CommentDeleteUseCase commentDeleteUseCase;

    @BeforeEach
    public void setUp() { commentDeleteUseCase = new CommentDeleteUseCase(userUtils, commentQueryService, commentValidAccessService, commentDeleteService); }

    @Test
    public void 댓글을_삭제한다() {
        //given
        User mockUser = UserTestUtils.getMockUser();
        Album mockAlbum = AlbumTestUtils.getMockAlbum(mockUser);
        Comment mockComment = CommentTestUtils.getMockComment(mockUser, mockAlbum);
        given(userUtils.getUser()).willReturn(mockUser);
        given(commentQueryService.getCommentById(mockComment.getId())).willReturn(mockComment);
        doNothing().when(commentValidAccessService).validateAccess(mockComment, mockUser.getId());
        //when
        commentDeleteUseCase.deleteComment(mockComment.getId());
        //then
        then(commentDeleteService).should(times(1)).deleteComment(mockComment);
        Assertions.assertThat(mockComment.isDeleted());
    }

    @Test
    public void 댓글_삭제시_유저가_다르면_에러_발생() {
        //given
        User mockUser = UserTestUtils.getMockUser();
        User anotherUser = UserTestUtils.getAnotherMockUser(); // 다른 유저 생성
        Album mockAlbum = AlbumTestUtils.getMockAlbum(mockUser);
        Comment mockComment = CommentTestUtils.getMockComment(anotherUser, mockAlbum); // 다른 유저가 작성한 댓글 생성
        given(userUtils.getUser()).willReturn(mockUser);
        given(commentQueryService.getCommentById(mockComment.getId())).willReturn(mockComment);
        doThrow(NotSameUserException.class).when(commentValidAccessService).validateAccess(mockComment, mockUser.getId());
        //when
        //then
        Assertions.assertThatThrownBy(() -> {
            commentDeleteUseCase.deleteComment(mockComment.getId());
        }).isInstanceOf(NotSameUserException.class);
    }
}
