package sample;


import Invoice.SDK.REST.*;
import Invoice.SDK.REST.common.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    Button authButton;
    @FXML
    Button payButton;
    @FXML
    Button checkButton;
    @FXML
    Button cancelButton;
    @FXML
    Button refundButton;

    @FXML
    TextField login;
    @FXML
    TextField apiKey;
    @FXML
    Label link;

    private RestClient client;
    private TerminalInfo terminalInfo;
    private PaymentInfo paymentInfo;

    @FXML
    public void initialize() {
        authButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                authClicked();
            }
        });
        payButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                payClicked();
            }
        });
        checkButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                checkClicked();
            }
        });
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cancelClicked();
            }
        });
        refundButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                refundClicked();
            }
        });
    }

    public void authClicked()
    {
        if(terminalInfo == null || terminalInfo.id == null)
        {
            client = new RestClient(login.getText(), apiKey.getText());
            GET_TERMINAL request = new GET_TERMINAL("1:1");
            terminalInfo = client.GetTerminal(request);

            if(terminalInfo == null || terminalInfo.id == null)
            {
                CREATE_TERMINAL create_terminal = new CREATE_TERMINAL();
                create_terminal.alias = "1:1"; //уникальный id в пределах вашей учетной записи. Например НомерМагазина:НомерКассы
                create_terminal.name = "Название магазина";
                create_terminal.description = "Описание магазина";
                create_terminal.type = TERMINAL_TYPE.dynamical;
                create_terminal.defaultPrice = 0;

                terminalInfo = client.CreateTerminal(create_terminal);
            }

            link.setText(terminalInfo.link);
        }
    }

    public void payClicked()
    {
        CREATE_PAYMENT create_payment = new CREATE_PAYMENT();

        List<ITEM> receipt = new ArrayList<>();

        ITEM soup = new ITEM();

        soup.name = "Суп";
        soup.price = new BigDecimal("5.0");
        soup.quantity = 2;
        soup.resultPrice = new BigDecimal("10.0");
        soup.discount = "0";

        ITEM kefir = new ITEM();
        
        kefir.name = "Кефир";
        kefir.price = new BigDecimal("1000.0");
        kefir.quantity = 1;
        kefir.resultPrice = new BigDecimal("990.0");
        kefir.discount = "10";

        receipt.add(soup);
        receipt.add(kefir);

        SETTINGS settings = new SETTINGS();
        settings.terminal_id = terminalInfo.id;

        create_payment.receipt = receipt;
        create_payment.settings = settings;

        paymentInfo = client.CreatePayment(create_payment);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(paymentInfo != null && paymentInfo.id != null)
        {
            alert.setTitle("OK");
            alert.setHeaderText(null);
            alert.setContentText("платеж создан");
        }else {
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText(paymentInfo.description);

        }
        alert.showAndWait();
    }

    public void checkClicked() {
        GET_PAYMENT get_payment = new GET_PAYMENT(paymentInfo.id);

        paymentInfo = client.GetPayment(get_payment);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(paymentInfo.status == PAYMENT_STATE.successful)
        {
            alert.setTitle("OK");
            alert.setHeaderText(null);
            alert.setContentText("Successful");
        } else if (paymentInfo.status == PAYMENT_STATE.error){
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("ERROR");
        }
        alert.showAndWait();
    }

    public void cancelClicked() {
        CLOSE_PAYMENT close_payment = new CLOSE_PAYMENT(paymentInfo.id);
        client.ClosePayment(close_payment);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("OK");
        alert.setHeaderText(null);
        alert.setContentText("Платеж отменен");
        alert.showAndWait();
    }

    public void refundClicked() {
        CREATE_REFUND create_refund = new CREATE_REFUND();

        create_refund.id = paymentInfo.id;

        List<ITEM> receipt = new ArrayList<>();

        ITEM soup = new ITEM();

        soup.name = "Суп";
        soup.price = new BigDecimal("5.0");
        soup.quantity = 2;
        soup.resultPrice = new BigDecimal("10.0");
        soup.discount = "0";

        ITEM kefir = new ITEM();

        kefir.name = "Кефир";
        kefir.price = new BigDecimal("1000.0");
        kefir.quantity = 1;
        kefir.resultPrice = new BigDecimal("990.0");
        kefir.discount = "10";

        receipt.add(soup);
        receipt.add(kefir);

        create_refund.receipt = receipt;

        REFUND_INFO refund_info = new REFUND_INFO();
        refund_info.amount = new BigDecimal("20.0");
        refund_info.reason = "В супе нашли муху";

        RefundInfo refundInfo = client.CreateRefund(create_refund);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(refundInfo.error != null) {
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText(refundInfo.error);
        } else {
            alert.setTitle("OK");
            alert.setHeaderText(null);
            alert.setContentText("Возврат запрошен");
        }

        alert.showAndWait();
    }
}
