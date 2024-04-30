package com.board.config.boardconfiggui.data.repo;

import com.board.config.boardconfiggui.data.outputmodels.BoardResult;

/**
 * The InputConfigRepo class represents a repository for storing Board results data
 * <p>
 * This class follows the singleton pattern, ensuring that only one instance exists
 * throughout the application.
 */
public class BoardResultsRepo {

    private static BoardResultsRepo instance;
    private BoardResult boardResult;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private BoardResultsRepo() {
    }

    /**
     * Returns the singleton instance of InputConfigRepo.
     * If the instance does not exist, a new one is created.
     *
     * @return The singleton instance of InputConfigRepo.
     */
    public static BoardResultsRepo getInstance() {
        if (instance == null) {
            instance = new BoardResultsRepo();
        }
        return instance;
    }

    /**
     * Creates new Board Result data model
     */
    public void createBoardResult() {
        this.boardResult = new BoardResult();
    }

    /**
     * Gets the Board Result data model
     *
     * @return Board result model
     */
    public BoardResult getBoardResult() {
        return boardResult;
    }
}
