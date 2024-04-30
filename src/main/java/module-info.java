module com.board.config.boardconfiggui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;

    opens com.board.config.boardconfiggui to javafx.fxml;
    exports com.board.config.boardconfiggui;

    exports com.board.config.boardconfiggui.controllers;
    opens com.board.config.boardconfiggui.controllers to javafx.fxml;
}
