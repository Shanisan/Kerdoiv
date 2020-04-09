package hu.alkfejl.model;

public enum KerdesTipus {
    TEXT, SINGLE_CHOICE, MULTIPLE_CHOICE, DATE, INTEGER;
    public static final String[] tipusStringek = {"Szöveges", "Feleletválasztós",
            "Feleletválasztós több válasszal", "Dátum", "Szám"};
}