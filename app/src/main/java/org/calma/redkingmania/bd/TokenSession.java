package org.calma.redkingmania.bd;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TokenSession {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenSession(String token){
        this.token = token;
    }
}
