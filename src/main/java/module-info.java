module com.board.config.boardconfiggui {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.board.config.boardconfiggui to javafx.fxml;
    exports com.board.config.boardconfiggui;
}
