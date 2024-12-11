package com.example.practicapartes;

import com.example.practicapartes.model.ParteIncidencia;

public class GuardarParte {
    private static ParteIncidencia parte_guardado;

    public static ParteIncidencia getParte() {
        return parte_guardado;
    }

    public static void setParte(ParteIncidencia parte) {
        parte_guardado = parte;
    }

    public static void resetParte() {
        parte_guardado = null;
    }
}
