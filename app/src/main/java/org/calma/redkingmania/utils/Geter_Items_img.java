package org.calma.redkingmania.utils;

public class Geter_Items_img {
    public static String get_img_src(String idItem) {
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

}
