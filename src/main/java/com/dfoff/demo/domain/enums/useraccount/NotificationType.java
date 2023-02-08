package com.dfoff.demo.domain.enums.useraccount;

public enum NotificationType {
    COMMENT_LIKE("좋아요"),
    BOARD_LIKE("좋아요"),
    COMMENT_UNLIKE("좋아요 취소"),
    BOARD_UNLIKE("좋아요 취소"),
    COMMENT("댓글"),
    CHILDREN_COMMENT("대댓글"),
    DELETE_COMMENT("댓글 삭제"),
    DELETE_CHILDREN_COMMENT("대댓글 삭제"),
    ;

    NotificationType(String description) {
    }
    public String getDescription() {
        return this.name();
    }


}
