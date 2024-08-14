package com.example.filmoretst01.service;

public interface IConverteDados {
    public <T> T obterDados(String json, Class<T> classe);
}
