package com.cripto.agi.agi.javafx.controllers;

import com.cripto.agi.agi.controller.CarteiraCriptoController;
import com.cripto.agi.agi.controller.ClienteController;
import com.cripto.agi.agi.dao.CarteiraDAO;
import com.cripto.agi.agi.model.Carteira;
import com.cripto.agi.agi.model.CarteiraCripto;
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

public class CriptoController {
    @FXML
    private Label saldoLabel;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label btcValor;
    @FXML
    private Label ethValor;
    @FXML
    private Label solValor;
    @FXML
    private Label saldoGeral;
    @FXML
    private Label agicoinValor;

    private Stage stage;
    private Scene scene;
    private ClienteController controller;
    private CarteiraDAO carteiraDAO;
    private CarteiraCriptoController carteiraCriptoController;

    public void setClienteController(ClienteController controller, CarteiraDAO carteiraDAO, CarteiraCriptoController carteiraCriptoController) {
        this.controller = controller;
        this.carteiraDAO = carteiraDAO;
        this.carteiraCriptoController = carteiraCriptoController;
        this.carregarInfos();
    }

    public void carregarInfos() {
        Cliente cliente = controller.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());
        CarteiraCripto carteiraCripto = carteiraCriptoController.pegarCarteiraCripto(cliente.getId_cliente());

        nomeLabel.setText(cliente.getNome());
        saldoLabel.setText(String.valueOf(carteira.getSaldoContaCorrente()));

        btcValor.setText(String.valueOf(carteiraCripto.getSaldoBTC()));
        ethValor.setText(String.valueOf(carteiraCripto.getSaldoETH()));
        solValor.setText(String.valueOf(carteiraCripto.getSaldoSOl()));
        agicoinValor.setText(String.valueOf(carteiraCripto.getSaldoAGICOIN()));
        saldoGeral.setText(String.valueOf(carteiraCripto.getSaldoBRL()));
    }

    public void carteiraCorrente(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/carteiraCorrente.fxml"));
        Parent root = loader.load();

        CarteiraCorrenteController carteiraCorrenteController = loader.getController();
        carteiraCorrenteController.setClienteController(this.controller, this.carteiraDAO, this.carteiraCriptoController);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }
}
