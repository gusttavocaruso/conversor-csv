package com.trybe.conversorcsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Conversor {

  /**
   * Função utilizada apenas para validação da solução do desafio.
   */
  public static void main(String[] args) throws IOException {
    File pastaDeEntradas = new File("./entradas/");
    File pastaDeSaidas = new File("./saidas/");
    new Conversor().converterPasta(pastaDeEntradas, pastaDeSaidas);
  } 

  /**
   * Converte todos os arquivos CSV da pasta de entradas. Os resultados são gerados
   * na pasta de saídas, deixando os arquivos originais inalterados.
   */
  public void converterPasta(File pastaDeEntradas, File pastaDeSaidas) throws IOException {

    if (!pastaDeSaidas.exists()) {
      pastaDeSaidas.mkdir();
    }

    for (File file : pastaDeEntradas.listFiles()) {
      FileReader fileReader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String lineContent = bufferedReader.readLine();

      String filePath = String.format("%s/%s", pastaDeSaidas, file.getName());
      FileWriter fileWriter = new FileWriter(filePath);
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

      while (lineContent != null) {
        String line = fileFormatter(lineContent);

        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        lineContent = bufferedReader.readLine(); 
      }

      fileReader.close();
      bufferedReader.close();
      fileWriter.close();
      bufferedWriter.close();
    }

  }

  /**
   * Método principal para formatar as linhas do arquivo original no novo formato.
   */
  public static String fileFormatter(String lineContent) {
    String[] line = lineContent.split(",");

    String newName = nameFormatter(line);
    String newDate = dateFormatter(line);
    String newCpf = cpfFormatter(line);

    return String.join(",", newName, newDate, line[2], newCpf);
  }

  /**
   * Método filho responsável por formatar campo nome do arquivo original.
   */
  public static String nameFormatter(String[] eachLine) {
    String nome = eachLine[0];

    if (nome.equals("Nome completo")) {
      return nome;
    }

    return nome.toUpperCase();
  }

  /**
   * Método filho responsável por formatar campo data do arquivo original.
   */
  public static String dateFormatter(String[] eachLine) {
    String dataNasc = eachLine[1];

    if (dataNasc.equals("Data de nascimento")) {
      return dataNasc;
    }

    String[] dmy = dataNasc.split("/");
    String ymd = String.join("-", dmy[2], dmy[1], dmy[0]);

    return ymd;
  }

  /**
   * Método filho responsável por formatar campo cpf do arquivo original.
   */
  public static String cpfFormatter(String[] eachLine) {
    String cpf = eachLine[3];

    if (cpf.equals("CPF")) {
      return cpf;
    }

    String a = cpf.substring(0, 3);
    String b = cpf.substring(3, 6);
    String c = cpf.substring(6, 9);
    String d = cpf.substring(9, 11);

    return a + "." + b + "." + c + "-" + d;
    // https://www.guj.com.br/t/metodo-format-da-classe-string/78623
  }

}