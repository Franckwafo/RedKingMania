package org.calma.redkingmania.utils;

public class Geter_img {
    public static String get_item_img_src(String idItem) {
        switch (idItem) {
            case "ef1975f7-181b-11f0-9c00-5260b7eeecb9":
                return "item_cuillere";
            case "ef1979fb-181b-11f0-9c00-5260b7eeecb9":
                return "item_hache";
            case "ef1982b3-181b-11f0-9c00-5260b7eeecb9":
                return "item_sucre";
            case "ef19832d-181b-11f0-9c00-5260b7eeecb9":
                return "item_pioche";
            case "ef198373-181b-11f0-9c00-5260b7eeecb9":
                return "item_epice";
            default:
                return "item_test"; // une image par défaut si non trouvée
        }
    }

    public static String get_construction_img_src(String idConstruction) {
        switch (idConstruction) {
            case "ef164b7d-181b-11f0-9c00-5260b7eeecb9":
                return "bg_erable";
            case "ef16528c-181b-11f0-9c00-5260b7eeecb9":
                return "bg_critale";
            case "ef165d16-181b-11f0-9c00-5260b7eeecb9":
                return "bg_foret";
            default:
                return "item_test"; // une image par défaut si non trouvée
        }
    }

}
