package com.example.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

public class Login {

    @FXML
    private Button login;
    @FXML
    private Label wrong;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button signup;

    File file = new File("datos.ax");

    HashMap<String, String> loginInfo = new HashMap<>();
    Encryptor encryptor = new Encryptor();


    @FXML
    void loginHandler(ActionEvent event) throws IOException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String user = username.getText();
        String pass = password.getText();
        updateLoginUsernamesAndPasswords();

        String encryptedPassword = loginInfo.get(user);
        if(encryptor.encryptString(pass).equals(encryptedPassword)){
            System.out.println("successfully login!");
        } else {
            wrong.setVisible(true);
        }

    }

    @FXML
    void createAccount(ActionEvent event) throws IOException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        wrong.setVisible(false);
        writeToFile();
    }

    private void updateLoginUsernamesAndPasswords() throws IOException {
        Scanner scanner = new Scanner(file);
        loginInfo.clear();
        loginInfo = new HashMap<>();
        while (scanner.hasNext()){
            String[] usernameAndPassword = scanner.nextLine().split(",");
            loginInfo.put(usernameAndPassword[0],usernameAndPassword[1]);
        }
    }

    private void writeToFile() throws IOException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String user = username.getText();
        String pass = password.getText();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));

        writer.write(user + "," + encryptor.encryptString(pass) + "\n");
        writer.close();
    }
}


