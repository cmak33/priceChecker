module com.example.pricechecker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.slf4j;
    requires org.jsoup;


    opens com.example.pricechecker to javafx.fxml;
    exports com.example.pricechecker;
}