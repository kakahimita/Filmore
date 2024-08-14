package com.example.filmoretst01.principal;

import com.example.filmoretst01.model.DadosSerie;
import com.example.filmoretst01.model.Serie;
import com.example.filmoretst01.service.ConsumoAPI;
import com.example.filmoretst01.service.ConverteDados;

import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=82df851d";
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private String menu = """
            *** Filmore ***
                        
            1 -> buscar série
            2 -> buscar episódio por serie
            3 -> listar séries buscadas
                        
            0 -> sair
            """;

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0) {
            System.out.println(menu);
            System.out.print("opção: ");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
//                    buscarEpisodioPorSerie();
                    break;
                case 3:
//                    listarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("saindo...");
                    break;
                default:
                    System.out.println("opção não foi encontrada! :(");
                    break;
            }
        }
    }

    private void buscarSerieWeb() {
        System.out.print("qual série deseja buscar: ");
        String nomeSerie = leitura.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeSerie
                .replace(" ", "+") + API_KEY);

        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);

        Serie serie = new Serie(dadosSerie);

        System.out.println(" ");
        System.out.println(serie);
    }
}
