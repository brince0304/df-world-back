package com.dfoff.demo.Util;

public class UserLogUtil {
    static final String DELETE_COMMENT_CONTENT = "님의 게시글이 삭제되어 댓글이 삭제되었습니다.";

    static final String COMMENT_CONTENT = "님이 댓글을 남기셨습니다.";

    static final String CHILDREN_COMMENT_CONTENT = "님이 대댓글을 남기셨습니다.";

    static final String DELETE_CHILDREN_COMMENT_CONTENT = "님의 댓글이 삭제되어 대댓글이 삭제되었습니다.";

    static final String BOARD_LIKE_CONTENT = "님이 회원님의 게시글에 좋아요를 누르셨습니다.";

    static final String BOARD_UNLIKE_CONTENT = "님이 회원님의 게시글 좋아요를 취소하셨습니다.";

    static final String COMMENT_LIKE_CONTENT = "님이 회원님의 댓글에 좋아요를 누르셨습니다.";

    static final String COMMENT_UNLIKE_CONTENT = "님이 회원님의 댓글 좋아요를 취소하셨습니다.";

    public static  String getLogContent(String logType, String userName){
        return switch (logType) {
            case "COMMENT" -> userName + COMMENT_CONTENT;
            case "CHILDREN_COMMENT" -> userName + CHILDREN_COMMENT_CONTENT;
            case "DELETE_COMMENT" -> userName + DELETE_COMMENT_CONTENT;
            case "DELETE_CHILDREN_COMMENT" -> userName + DELETE_CHILDREN_COMMENT_CONTENT;
            case "BOARD_LIKE" -> userName + BOARD_LIKE_CONTENT;
            case "BOARD_UNLIKE" -> userName + BOARD_UNLIKE_CONTENT;
            case "COMMENT_LIKE" -> userName + COMMENT_LIKE_CONTENT;
            case "COMMENT_UNLIKE" -> userName + COMMENT_UNLIKE_CONTENT;
            default -> "";
        };
    }


}
