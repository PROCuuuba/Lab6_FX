module com.example.lab6_fx {
    requires javafx.controls;
    requires javafx.fxml;

    requires telegrambots;
    requires telegrambots.meta;
    requires telegrambots.client;
    requires telegrambots.longpolling;


    opens com.example.lab6_fx to javafx.fxml;
    exports com.example.lab6_fx;
}