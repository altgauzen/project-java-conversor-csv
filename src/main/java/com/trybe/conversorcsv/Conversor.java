package com.trybe.conversorcsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Conversor {

  /**
   * Função utilizada apenas para validação da solução do desafio.
   *
   * @param args Não utilizado.
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou gravar os
   *         arquivos de saída.
   */
  public static void main(String[] args) throws IOException {
    File pastaDeEntradas = new File("./entradas/");
    File pastaDeSaidas = new File("./saidas/");
    new Conversor().converterPasta(pastaDeEntradas, pastaDeSaidas);
  }

  /**
   * Converte todos os arquivos CSV da pasta de entradas. Os resultados são gerados na pasta de
   * saídas, deixando os arquivos originais inalterados.
   *
   * @param pastaDeEntradas Pasta contendo os arquivos CSV gerados pela página web.
   * @param pastaDeSaidas Pasta em que serão colocados os arquivos gerados no formato requerido pelo
   *        subsistema.
   *
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou gravar os
   *         arquivos de saída.
   */
  public void converterPasta(File pastaDeEntradas, File pastaDeSaidas) throws IOException {
    // Se a pasta de saida nao existir, criar um novo diretorio.
    if (!pastaDeSaidas.exists()) {
      pastaDeSaidas.mkdir();
    }

    // para cada linha do arquivo de entrada, ler a linha, adicionar a linha
    // em um arrayList até que nao hajam mais linhas.

    if (pastaDeEntradas.isDirectory() && pastaDeEntradas.canRead()) {
      for (File arquivoDeEntrada : pastaDeEntradas.listFiles()) {

        // Cria leitor do arquivo com seu buffer de leitura.
        FileReader leitorArquivo = new FileReader(arquivoDeEntrada);
        BufferedReader bufferedLeitor = new BufferedReader(leitorArquivo);

        // Le a linha do arquivo de entrada.
        String conteudoLinha = bufferedLeitor.readLine();

        ArrayList<String> conteudoArquivo = new ArrayList<String>();

        // enquanto existir linha no arquivo de entrada, adicione linha ao
        // arrayList e leia a linha do arquivo.
        while (conteudoLinha != null) {
          conteudoArquivo.add(conteudoLinha);
          conteudoLinha = bufferedLeitor.readLine();
        }
        // fechando buffer e leitor.
        bufferedLeitor.close();
        leitorArquivo.close();

        // pega o nome do arquivo de entrada, cria arquivo de saida com mesmo nome.
        String nomeDoArquivo = arquivoDeEntrada.getName();
        File arquivoDeSaida = new File(pastaDeSaidas, nomeDoArquivo);

        // Cria escritor do arquivo com seu buffer de escrita.
        FileWriter escritorArquivo = new FileWriter(arquivoDeSaida);
        BufferedWriter bufferedEscritor = new BufferedWriter(escritorArquivo);

        // para cada linha do arquivo lida, se nao for o cabeçalho,
        // converter ao novo formato de acordo com regras de negocio.
        for (String linhaSaida : conteudoArquivo) {
          if (conteudoArquivo.indexOf(linhaSaida) != 0) {
            linhaSaida = Conversor.converterNovaLinha(linhaSaida);
          }

          // Escreve a linha no arquivo de saida e pula para nova linha
          bufferedEscritor.write(linhaSaida);
          bufferedEscritor.newLine();
        }

        // obtem conteudo e escreve no arquivo, fechando buffer e escritor.
        bufferedEscritor.flush();
        bufferedEscritor.close();
        escritorArquivo.close();
      }
    }
  }

  /**
   * Metodo que converte linhas ao novo formato.
   */
  public static String converterNovaLinha(String line) {
    String nome = line.split(",")[0].toUpperCase();

    String data = line.split(",")[1];
    data = data.substring(6) + "-" + data.substring(3, 5) + "-" + data.substring(0, 2);

    String email = line.split(",")[2];

    String cpf = line.split(",")[3];
    cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-"
        + cpf.substring(9, 11);

    String linhaConvertida = nome + "," + data + "," + email + "," + cpf;
    return linhaConvertida;
  }
}
