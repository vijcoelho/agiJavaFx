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
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class CarteiraCorrenteController {
    @FXML
    public Label saldoLabel;
    @FXML
    private Label nomeLabel;

    private Stage stage;
    private Scene scene;
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

    public void telaPix(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/telaPix.fxml"));
        Parent root = loader.load();

        PixController pixController = loader.getController();
        pixController.setClienteController(this.controller, this.carteiraDAO);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }
}
