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

    private static final int COLUNA_TITULO_WIDTH = 30;
    private static final int COLUNA_TEMPORADAS_WIDTH = 20;
    private static final int COLUNA_AVALIACAO_WIDTH = 10;
    private static final int COLUNA_ANO_WIDTH = 15;

    private String escolhas;

    private String menu = """
            ╔══════════════════════════════════════════════════╗
            ║   #######                                        ║
            ║   #       # #      #    #  ####  #####  ######   ║
            ║   #       # #      ##  ## #    # #    # #        ║
            ║   #####   # #      # ## # #    # #    # #####    ║
            ║   #       # #      #    # #    # #####  #        ║
            ║   #       # #      #    # #    # #   #  #        ║
            ║   #       # ###### #    #  ####  #    # ######   ║
            ║                                                  ║
            ║          Coders 2024       versão: 1.0.0         ║
            ╠══════════════════════════════════════════════════╣
            ║                  >>>> MENU <<<<                  ║
            ║                                                  ║
            ║        [1] - Buscar Série                        ║
            ║        [2] - Buscar Episódios por Série          ║
            ║        [3] - Listar Séries Buscadas              ║
            ║        [6] - Sobre                               ║
            ║                                                  ║
            ║        [0] - Sair                                ║
            ╚══════════════════════════════════════════════════╝
            """;

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0) {
            System.out.print(menu);
            System.out.print("Informe a operação desejada: ");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    System.out.print("Retornar para tela Inicial?(S/N): ");
                    escolhas = leitura.nextLine();
                    if (escolhas.equalsIgnoreCase("S")) {
                        break;
                    } else {
                        opcao = 0;
                    }
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 6:
                    exibirSobre();
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
//        System.out.println("*** SÉRIES ***");
//        listasDeSerie.forEach(System.out::println);

        System.out.println("═════════════════ Lista de Séries Buscadas ═════════════════");
        if (listasDeSerie.isEmpty()) {
            System.out.println("Nenhuma série buscada ainda!");
            return;
        }

        // Imprimir cabeçalho da "tabela"
        System.out.printf("| %-" + COLUNA_TITULO_WIDTH + "s | %-" + COLUNA_TEMPORADAS_WIDTH + "s | %-" + COLUNA_AVALIACAO_WIDTH + "s | %-" + COLUNA_ANO_WIDTH + "s |%n",
                "Título", "Total de Temporadas", "Avaliação", "Ano de Lançamento");

        imprimirLinhaTabela(COLUNA_TITULO_WIDTH, COLUNA_TEMPORADAS_WIDTH, COLUNA_AVALIACAO_WIDTH, COLUNA_ANO_WIDTH);

        // Iterar sobre a lista de séries e imprimir os detalhes
        for (Serie serie : listasDeSerie) {
            System.out.printf("| %-" + COLUNA_TITULO_WIDTH + "s | %-" + COLUNA_TEMPORADAS_WIDTH + "d | %-" + COLUNA_AVALIACAO_WIDTH + "s | %-" + COLUNA_ANO_WIDTH + "s |%n",
                    serie.getTitulo(),
                    serie.getTotalTemporadas(),
                    serie.getAvaliacao(),
                    serie.getAnoLancamento());
            imprimirLinhaTabela(COLUNA_TITULO_WIDTH, COLUNA_TEMPORADAS_WIDTH, COLUNA_AVALIACAO_WIDTH, COLUNA_ANO_WIDTH);
        }
    }

    private Serie buscarSerieWeb() {
        System.out.println("════════ Qual série deseja buscar ════════");
        System.out.print("Nome: ");
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
            exibirDetalhesSerie(serie);
        }
        return serie;
    }

    private void exibirDetalhesSerie(Serie serie) {
        imprimirLinhaTabela(COLUNA_TITULO_WIDTH, COLUNA_TEMPORADAS_WIDTH, COLUNA_AVALIACAO_WIDTH, COLUNA_ANO_WIDTH);
        System.out.printf("| %-" + COLUNA_TITULO_WIDTH + "s | %-" + COLUNA_TEMPORADAS_WIDTH + "s | %-" + COLUNA_AVALIACAO_WIDTH + "s | %-" + COLUNA_ANO_WIDTH + "s |%n",
                "Título", "Total de Temporadas", "Avaliação", "Ano de Lançamento");

        imprimirLinhaTabela(COLUNA_TITULO_WIDTH, COLUNA_TEMPORADAS_WIDTH, COLUNA_AVALIACAO_WIDTH, COLUNA_ANO_WIDTH);

        System.out.printf("| %-" + COLUNA_TITULO_WIDTH + "s | %-" + COLUNA_TEMPORADAS_WIDTH + "d | %-" + COLUNA_AVALIACAO_WIDTH + "s | %-" + COLUNA_ANO_WIDTH + "s |%n",
                serie.getTitulo(),
                serie.getTotalTemporadas(),
                serie.getAvaliacao(),
                serie.getAnoLancamento());

        imprimirLinhaTabela(COLUNA_TITULO_WIDTH, COLUNA_TEMPORADAS_WIDTH, COLUNA_AVALIACAO_WIDTH, COLUNA_ANO_WIDTH);
    }


    static void exibirSobre() {
        System.out.println("""
                ╔══════════════════════════════════════════════════╗
                ║   #######                                        ║
                ║   #       # #      #    #  ####  #####  ######   ║
                ║   #       # #      ##  ## #    # #    # #        ║
                ║   #####   # #      # ## # #    # #    # #####    ║
                ║   #       # #      #    # #    # #####  #        ║
                ║   #       # #      #    # #    # #   #  #        ║
                ║   #       # ###### #    #  ####  #    # ######   ║
                ║                                                  ║
                ║          Coders 2024       versão: 1.0.0         ║
                ╠══════════════════════════════════════════════════╣
                ║                  >>>> SOBRE <<<<                 ║
                ║                                                  ║
                ║    A aplicação Filmora foi desenvolvida como     ║
                ║  projeto pessoal inspirado no curso de Java      ║
                ║  pela 'Alura'.                                   ║
                ║                                                  ║
                ║  2024.9 - Back-End'.                             ║
                ║                                                  ║
                ║                 COLABORADORES                    ║
                ║      -  Luís Henrique Machado                    ║
                ║                                                  ║
                ╚══════════════════════════════════════════════════╝
                """);
    }

    private void imprimirLinhaTabela(int... largurasColunas) {
        for (int largura : largurasColunas) {
            System.out.print("+");
            System.out.print("-".repeat(largura + 2).replace(" ", "+"));
        }
        System.out.println("+");
    }
}
