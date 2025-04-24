package org.calma.redkingmania.utils;

import java.util.List;
import java.util.Map;

public class ResultResponse {
    private boolean result;
    private String message;
    private String token;

    private Map<String, Object> user;
    private List<Map<String, Object>> items_sans_construction;
    private List<Map<String, Object>> constructions;

    private List<Map<String, String>> shop_items;
    private List<Map<String, String>> shop_constructions;

    // === Token ===
    public String getToken() {
        return token;
    }

    public List<Map<String, String>> getShop_items() {
        return shop_items;
    }

    public void setShop_items(List<Map<String, String>> shop_items) {
        this.shop_items = shop_items;
    }

    public List<Map<String, String>> getShop_constructions() {
        return shop_constructions;
    }

    public void setShop_constructions(List<Map<String, String>> shop_constructions) {
        this.shop_constructions = shop_constructions;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // === Result ===
    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    // === Message ===
    public String getmsg() {
        return message;
    }

    public void setmsg(String msg) {
        this.message = msg;
    }

    // === User ===
    public Map<String, Object> getUser() {
        return user;
    }

    public void setUser(Map<String, Object> user) {
        this.user = user;
    }

    // === Items sans construction ===
    public List<Map<String, Object>> getItems_sans_construction() {
        return items_sans_construction;
    }

    public void setItems_sans_construction(List<Map<String, Object>> items_sans_construction) {
        this.items_sans_construction = items_sans_construction;
    }

    // === Constructions ===
    public List<Map<String, Object>> getConstructions() {
        return constructions;
    }

    public void setConstructions(List<Map<String, Object>> constructions) {
        this.constructions = constructions;
    }
}
