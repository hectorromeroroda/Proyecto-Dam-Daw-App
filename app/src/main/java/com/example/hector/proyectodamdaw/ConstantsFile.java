package com.example.hector.proyectodamdaw;

/**
 * Created by Ivan on 11/04/2018.
 */

public class ConstantsFile {
    private static final String COMUNITY_TABLE = "Community";
    private static final String COMUNITY_ID = "_id";
    private static final String COMUNITY_NAME = "Name";
    private static final String COMUNITY_MAX_USERS = "Max_Users";
    private static final String COMUNITY_MEDIA_ID = "MediaId";

    private static final String POST_TABLE = "Post";
    private static final String POST_ID = "_id";
    private static final String POST_TITLE = "Title";
    private static final String POST_TEXT = "Text";
    private static final String POST_PUBLICATION_DATE = "PublicationDate";
    private static final String POST_USER_ID = "UserId";
    private static final String POST_COMMUNITY_ID = "Community";

    private static final String POLL_TABLE = "Poll";
    private static final String POLL_ID = "_id";
    private static final String POLL_TITLE = "Title";
    private static final String POLL_TEXT = "Text";
    private static final String POLL_PUBLICATION_DATE = "PublicationDate";
    private static final String POLL_USER_ID = "UserId";
    private static final String POLL_COMMUNITY_ID = "Community";

    private static final String PROPOSITION_TABLE = "Proposition";
    private static final String PROPOSITION_ID = "_id";
    private static final String PROPOSITION_TITLE = "Title";
    private static final String PROPOSITION_TEXT = "Text";
    private static final String PROPOSITION_PUBLICATION_DATE = "PublicationDate";
    private static final String PROPOSITION_USER_ID = "UserId";
    private static final String PROPOSITION_COMMUNITY_ID = "Community";

    private static final String POST_COMMENT_TABLE = "PostComment";
    private static final String POST_COMMENT_ID = "_id";
    private static final String POST_COMMENT_TEXT = "CommentText";
    private static final String POST_COMMENT_PUBLICATION = "CommentPublicationDate";
    private static final String POST_COMMENT_POST_ID = "PostId";
    private static final String POST_COMMENT_USER_ID = "UserId";

    private static final String POLL_COMMENT_TABLE = "PollComment";
    private static final String POLL_COMMENT_ID = "_id";
    private static final String POLL_COMMENT_TEXT = "CommentText";
    private static final String POLL_COMMENT_PUBLICATION = "CommentPublicationDate";
    private static final String POLL_COMMENT_POST_ID = "PostId";
    private static final String POLL_COMMENT_USER_ID = "UserId";

    private static final String PROPOSITION_COMMENT_TABLE = "PropositionComment";
    private static final String PROPOSITION_COMMENT_ID = "_id";
    private static final String PROPOSITION_COMMENT_TEXT = "CommentText";
    private static final String PROPOSITION_COMMENT_PUBLICATION = "CommentPublicationDate";
    private static final String PROPOSITION_COMMENT_POST_ID = "PostId";
    private static final String PROPOSITION_COMMENT_USER_ID = "UserId";

    private static final String USER_TABLE = "User";
    private static final String USER_ID = "_id";
    private static final String USER_NAME = "UserName";
    private static final String USER_FIRST_SURNAME = "UserFirstSurname";
    private static final String USER_SECOND_SURNAME = "UserSecondSurname";
    private static final String USER_LOGIN_NAME = "UserLoginName";
    private static final String USER_PASSWORD = "UserPassword";
    private static final String USER_EMAIL = "UserMail";
    private static final String USER_STICKIES = "UserStickies";
    private static final String USER_PUBLIC_PROFILE = "UserPublicProfile";
    private static final String USER_ACTIVATED = "UserActivated";
    private static final String USER_TELEPHONE = "UserTelephone";
    private static final String USER_MEDIA_ID = "MediaId";

    private static final String MESSAGE_TABLE = "Message";
    private static final String MESSAGE_ID = "_id";
    private static final String MESSAGE_TEXT  = "Text";
    private static final String MESSAGE_SENDED = "Sended";
    private static final String MESSAGE_RECIVED  = "Recived";
    private static final String MESSAGE_USER_SENDER_ID = "UserSenderId";
    private static final String MESSAGE_USER_RECEIVER_ID = "UserReceiverId";
}
