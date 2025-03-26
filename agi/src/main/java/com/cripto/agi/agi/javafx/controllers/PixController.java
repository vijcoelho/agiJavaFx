package com.cripto.agi.agi.javafx.controllers;

import com.cripto.agi.agi.controller.ClienteController;
import com.cripto.agi.agi.dao.CarteiraDAO;
import com.cripto.agi.agi.model.Carteira;
import com.cripto.agi.agi.model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PixController {
    @FXML
    public Label saldoLabel;
    @FXML
    public Label nomeLabel;
    @FXML
    public TextField pixText;

    private ClienteController controller;
    private CarteiraDAO carteiraDAO;

    public void setClienteController(ClienteController controller, CarteiraDAO carteiraDAO) {
        this.controller = controller;
        this.carteiraDAO = carteiraDAO;
        this.carregarInfos();
    }

    public void carregarInfos() {
        Cliente cliente = controller.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());

        nomeLabel.setText(cliente.getNome());
        saldoLabel.setText(String.valueOf(carteira.getSaldoContaCorrente()));
    }

    public void fazerPix(ActionEvent actionEvent) {
        String valor = pixText.getText();
        Double valorEmDouble = Double.valueOf(valor);

        if (controller.comprar(valorEmDouble)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pix realizado");
            alert.setHeaderText(null);
            alert.setContentText("Pix realizado com sucesso!");
            alert.showAndWait();
        } else {
            System.out.println("Pix nao realizado!");
        }
    }

    public void voltarParaCarteira(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/carteiraCorrente.fxml"));
        Parent root = loader.load();

        CarteiraCorrenteController carteiraCorrenteController = loader.getController();
        carteiraCorrenteController.setClienteController(this.controller, this.carteiraDAO);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }
}
