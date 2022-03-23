package com.sb.practricum_11.ui.controllers;

import com.sb.practricum_11.model.Artikel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class MainView {

    @FXML
    private Button CB_Convert;

    @FXML
    private TextField TB_DollarValue;

    @FXML
    private TextField TB_InputPath;

    @FXML
    private TextField TB_OutputPath;

    public void initialize(){
        TB_DollarValue.setText("91.8720");
    }

    public void Convert(ActionEvent actionEvent) throws IOException {
        if(!PassedChecks()){
            return;
        }

        ArrayList<String> oldlist = FileAsList(TB_InputPath.getText());
        ArrayList<Artikel> newList =  new ArrayList<>();
        for(String line : oldlist){
            Artikel a = new Artikel(line);
            newList.add(a);
        }
        double koers = Double.parseDouble(TB_DollarValue.getText());
        boolean succes = WriteArtikelListToFile(newList, koers, TB_OutputPath.getText());

        if(succes){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Converted.");
            alert.showAndWait();
        }
    }

    private ArrayList<String> FileAsList(String path) throws IOException {
        BufferedReader bufReader = new BufferedReader(new FileReader(path));
        ArrayList<String> list = new ArrayList<String>();

        String line = bufReader.readLine();
        while (line != null) {
            list.add(line);
            line = bufReader.readLine();
        }

        bufReader.close();
        return list;
    }

    private boolean WriteArtikelListToFile(ArrayList<Artikel> list, double koersInCenten, String outputPath) throws IOException {
        FileWriter writer = new FileWriter(outputPath, false);
        for(Artikel a: list) {
            writer.write(a.getConvertedLine(koersInCenten) + System.lineSeparator());
        }
        writer.close();
        return true;
    }

    private boolean PassedChecks(){
        if(Objects.equals(TB_InputPath.getText(), "")){
            ShowAlert("Input path is empty");
            return false;
        } else {
            if (!Files.exists(Path.of(TB_InputPath.getText()))) {
                ShowAlert("Input file doesn't exist");
                return false;
            }
        }

        if(Objects.equals(TB_OutputPath.getText(), "")){
            ShowAlert("Output path is empty");
            return false;
        } else {
            try{
                if (!Files.exists(Paths.get(TB_OutputPath.getText()).getParent())) {
                    ShowAlert("Output path doesn't exist");
                    return false;
                }
            } catch (Exception e){
                ShowAlert("Output path doesn't exist");
                return false;
            }
        }

        if(Objects.equals(TB_DollarValue.getText(), "")){
            ShowAlert("Dollar value is empty");
            return false;
        } else {
            try {
                Double.parseDouble(TB_DollarValue.getText());
            }
            catch(NumberFormatException e){
                ShowAlert("Dollar value is not a number");
                return false;
            }
        }

        return true;
    }

    private void ShowAlert(String box){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Missende informatie");
        alert.setHeaderText("Missende informatie");
        alert.setContentText(box);

        alert.showAndWait();
    }
}
