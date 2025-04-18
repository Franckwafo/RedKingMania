package org.calma.redkingmania.bd;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TokenSessionDao {
    @Insert
    void inserer(TokenSession ts);

    @Query("SELECT * FROM TokenSession ORDER BY id DESC LIMIT 1;")
    TokenSession getlastToken();
}
