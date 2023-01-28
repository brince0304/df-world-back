package com.dfoff.demo.Util;

public class NotificationUtil {
    static final String DELETE_COMMENT_CONTENT = "님의 게시글이 삭제되어 댓글이 삭제되었습니다.";

    static final String COMMENT_CONTENT = "님께서 회원님의 게시글에 댓글을 남기셨습니다.";

    static final String CHILDREN_COMMENT_CONTENT = "님께서 회원님의 댓글에 대댓글을 남기셨습니다.";

    static final String DELETE_CHILDREN_COMMENT_CONTENT = "님의 댓글이 삭제되어 대댓글이 삭제되었습니다.";

    static final String BOARD_LIKE_CONTENT = "누군가가 회원님의 게시글에 좋아요를 누르셨습니다.";


    static final String COMMENT_LIKE_CONTENT = "누군가가 회원님의 댓글에 좋아요를 누르셨습니다.";


    public static  String getNotificationContentFrom(String logType, String userName, String commentContent){
        commentContent = commentContent.length() > 20 ? "   '"+commentContent.substring(0, 20) + "...'" : "   '"+commentContent+"'";
        return switch (logType) {
            case "COMMENT" -> userName + COMMENT_CONTENT + commentContent;
            case "CHILDREN_COMMENT" -> userName + CHILDREN_COMMENT_CONTENT + commentContent;
            case "DELETE_COMMENT" -> userName + DELETE_COMMENT_CONTENT;
            case "DELETE_CHILDREN_COMMENT" -> userName + DELETE_CHILDREN_COMMENT_CONTENT;
            case "BOARD_LIKE" -> userName + BOARD_LIKE_CONTENT;
            case "COMMENT_LIKE" -> userName + COMMENT_LIKE_CONTENT;
            default -> "";
        };
    }


}
