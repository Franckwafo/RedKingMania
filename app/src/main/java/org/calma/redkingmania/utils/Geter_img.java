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

            case "cb613346-289b-11f0-b087-5260b7eeecb9":
                return "item_alembique";
            case "cb613b41-289b-11f0-b087-5260b7eeecb9":
                return "item_arome";
            case "cb613e4b-289b-11f0-b087-5260b7eeecb9":
                return "item_artisant";
            case "cb61406c-289b-11f0-b087-5260b7eeecb9":
                return "item_assortiment";
            case "cb61423b-289b-11f0-b087-5260b7eeecb9":
                return "item_aventurier";
            case "cb6143f2-289b-11f0-b087-5260b7eeecb9":
                return "item_bucheron";
            case "cb6145d5-289b-11f0-b087-5260b7eeecb9":
                return "item_chapeux";
            case "cb614886-289b-11f0-b087-5260b7eeecb9":
                return "item_chef";
            case "cb614aef-289b-11f0-b087-5260b7eeecb9":
                return "item_chemise";
            case "cb614cff-289b-11f0-b087-5260b7eeecb9":
                return "item_compote";
            case "cb614f09-289b-11f0-b087-5260b7eeecb9":
                return "item_equipement";
            case "cb6150dd-289b-11f0-b087-5260b7eeecb9":
                return "item_fiole";
            case "cb61528b-289b-11f0-b087-5260b7eeecb9":
                return "item_loche_en_bronze";
            case "cb615439-289b-11f0-b087-5260b7eeecb9":
                return "item_louche_en_or";
            case "cb6155ed-289b-11f0-b087-5260b7eeecb9":
                return "item_magiciene";
            case "cb6157a3-289b-11f0-b087-5260b7eeecb9":
                return "item_maturation";
            case "cb615a83-289b-11f0-b087-5260b7eeecb9":
                return "item_mineur";
            case "cb615c3e-289b-11f0-b087-5260b7eeecb9":
                return "item_model";
            case "cb615e4f-289b-11f0-b087-5260b7eeecb9":
                return "item_mortier";
            case "cb616242-289b-11f0-b087-5260b7eeecb9":
                return "item_pioche_simple";
            case "cb6163f3-289b-11f0-b087-5260b7eeecb9":
                return "item_recette";
            case "cb6165b6-289b-11f0-b087-5260b7eeecb9":
                return "item_sorcirer";
            case "cb61677a-289b-11f0-b087-5260b7eeecb9":
                return "item_tourtier";
            case "cb616928-289b-11f0-b087-5260b7eeecb9":
                return "item_tourtiere";
            case "cb616adb-289b-11f0-b087-5260b7eeecb9":
                return "item_valkirie";
            case "cb616087-289b-11f0-b087-5260b7eeecb9":
                return "item_ouvriere";

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

            case "1311050d-288d-11f0-b087-5260b7eeecb9":
                return "bg_etendue_bois";
            case "13110b8d-288d-11f0-b087-5260b7eeecb9":
                return "bg_filon_cristaux";
            case "1e15384f-288d-11f0-b087-5260b7eeecb9":
                return "bg_preparation_erable";

            case "3bbab03d-288c-11f0-b087-5260b7eeecb9":
                return "bg_coffre_cristaux";
            case "3bbab79f-288c-11f0-b087-5260b7eeecb9":
                return "bg_clairiere_bois";
            case "7865661c-288c-11f0-b087-5260b7eeecb9":
                return "bg_clairiere_erable";

            case "78656f2e-288c-11f0-b087-5260b7eeecb9":
                return "bg_gisement_cristaux";
            case "cb1e525c-288b-11f0-b087-5260b7eeecb9":
                return "bg_atelier_erable";
            case "cb1e5fd5-288b-11f0-b087-5260b7eeecb9":
                return "bg_atelier_bois";
            default:
                return "item_test"; // une image par défaut si non trouvée
        }
    }

}
