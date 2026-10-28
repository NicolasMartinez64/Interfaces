package com.proyectodi;

// Clase lanzadora secundaria que no extiende de Application para que VS Code no de el error de runtime components (Registro IA #4 en docs/IA.md)
public class Main {
    public static void main(String[] args) {
        App.main(args);
    }
}
