package mx.alura.api.model;

/**
 * Represents the status of a post or message.
 */
public enum Status {
    /**
     * The issue or post has been solved.
     */
    Solved,

    /**
     * The issue or post has not been solved.
     */
    NotSolved,

    /**
     * An answer has been provided to the post.
     */
    Answered,

    /**
     * The post or issue is closed.
     */
    Close,

    /**
     * The post or issue is open and active.
     */
    Opened
}
