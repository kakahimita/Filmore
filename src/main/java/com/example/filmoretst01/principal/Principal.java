package com.example.filmoretst01.principal;

import com.example.filmoretst01.model.DadosSerie;
import com.example.filmoretst01.model.Serie;
import com.example.filmoretst01.service.ConsumoAPI;
import com.example.filmoretst01.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = System.getenv("APIKEY");
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private List<Serie> listasDeSerie = new ArrayList<>();

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
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
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

    private void buscarEpisodioPorSerie() {

    }

    private void listarSeriesBuscadas() {
        System.out.println("*** SÉRIES ***");
        listasDeSerie.forEach(System.out::println);
    }

    private Serie buscarSerieWeb() {
        System.out.print("qual série deseja buscar: ");
        String nomeSerie = leitura.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeSerie
                .replace(" ", "+") + API_KEY);

        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);

        Serie serie = new Serie(dadosSerie);

        // verificando se a série já foi buscada alguma vez!

        Optional<Serie> serieBuscada = listasDeSerie.stream()
                        .filter(s -> s.getTitulo().equalsIgnoreCase(nomeSerie))
                                .findFirst();

        if (serieBuscada.isPresent()) {
            System.out.println(" ");
            System.out.println(serie);
        } else {
            System.out.println("""
                    
                    *** Uau uma série nova foi adicionada! :D ***""");
            listasDeSerie.add(serie);
            System.out.println(serie);
        }
        return serie;
    }
}
