package com.example.androidrecupracticarroomconjaime.data;

import androidx.room.*;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ElementDao {
    @Insert(onConflict = REPLACE)
    void insert(Element element);

    @Query("UPDATE elemento_de_prueba SET text = :text WHERE id = :id")
    void update(String text, int id);

    @Query("UPDATE elemento_de_prueba SET text = :text, imagen_url = :image WHERE id = :id")
    void updateWithImage(String text, int image, int id);

    @Delete
    void delete(Element element);

    @Delete
    void deleteAll(List<Element> list);

    @Query("SELECT * FROM elemento_de_prueba")
    List<Element> findAll();

    @Query("SELECT * FROM elemento_de_prueba WHERE text = :name")
    Element findByName(String name);
}
