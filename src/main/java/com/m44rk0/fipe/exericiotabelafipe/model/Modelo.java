package com.m44rk0.fipe.exericiotabelafipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Modelo(List<DadosVeiculo> modelos) {
}
