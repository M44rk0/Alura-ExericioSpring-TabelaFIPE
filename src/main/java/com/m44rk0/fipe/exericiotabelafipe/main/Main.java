package com.m44rk0.fipe.exericiotabelafipe.main;

import com.m44rk0.fipe.exericiotabelafipe.model.DadosVeiculo;
import com.m44rk0.fipe.exericiotabelafipe.model.Modelo;
import com.m44rk0.fipe.exericiotabelafipe.model.Veiculo;
import com.m44rk0.fipe.exericiotabelafipe.service.ConsumoAPI;
import com.m44rk0.fipe.exericiotabelafipe.service.ConverteDados;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class Main {

    private final Scanner sc = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConverteDados conversor = new ConverteDados();


    public void exibeMenu(){
        var menu = """
                |---- OPÇÕES ----|
                
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consultar:
                """;

        System.out.print(menu);
        var opcao  = sc.nextLine();

        String endereco;
        String URL = "https://parallelum.com.br/fipe/api/v1/";
        if (opcao.toLowerCase().contains("carr")){
            endereco = URL + "carros/marcas";
        }
        else if(opcao.toLowerCase().contains("mot")){
            endereco = URL + "motos/marcas";
        }
        else{
            endereco = URL + "caminhoes/marcas";
        }

        var json = consumoAPI.obterDados(endereco);
        System.out.println(json);

        var marcas = conversor.obterLista(json, DadosVeiculo.class);
        marcas.stream().sorted(Comparator.comparing(DadosVeiculo::codigo))
                .forEach(System.out::println);

        System.out.println("Informa o código da marca:");
        var codigoMarca = sc.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumoAPI.obterDados(endereco);

        var modeloLista = conversor.obterDados(json,  Modelo.class);
        System.out.println("|---- Modelos da Marca Escolhida ----|");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .forEach(System.out::println);

        System.out.println("Digite um techo do nome do carro a ser buscado: ");
        var nomeVeiculo = sc.nextLine();

        List<DadosVeiculo> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .toList();

        System.out.println("|---- Modelos Filtrados ----|");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o código do modelo para buscar os valores: ");
        var codigoModelo = sc.nextLine();

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumoAPI.obterDados(endereco);

        List<DadosVeiculo> anos = conversor.obterLista(json, DadosVeiculo.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (DadosVeiculo ano : anos) {
            var enderecoAnos = endereco + "/" + ano.codigo();
            json = consumoAPI.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("|---- Veiculos ----|");
        veiculos.forEach(System.out::println);

    }
}
