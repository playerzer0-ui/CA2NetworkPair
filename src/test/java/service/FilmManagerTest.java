package service;

import business.Film;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FilmManagerTest {

    private FilmManager filmManager;
    @Before
    public void init() {
        ArrayList<Film> listFilm = new ArrayList<>();

        ArrayList<String> genres1 = new ArrayList<>();
        genres1.add("comedy");
        genres1.add("horror");
        ArrayList<String> genres2 = new ArrayList<>();
        genres1.add("sci-fi");
        genres1.add("horror");
        ArrayList<String> genres3 = new ArrayList<>();
        genres1.add("romance");
        genres1.add("comedy");

        listFilm.add(new Film("dark souls", genres1, 4.1, 2));
        listFilm.add(new Film("cyberpunk", genres2, 2.1, 9));
        listFilm.add(new Film("spongebob", genres3, 1.5, 100));
        filmManager = new FilmManager(listFilm);
    }

    @After
    public void teardown() {
        ArrayList<Film> listFilm = new ArrayList<>();

        ArrayList<String> genres1 = new ArrayList<>();
        genres1.add("comedy");
        genres1.add("horror");
        ArrayList<String> genres2 = new ArrayList<>();
        genres1.add("sci-fi");
        genres1.add("horror");
        ArrayList<String> genres3 = new ArrayList<>();
        genres1.add("romance");
        genres1.add("comedy");

        listFilm.add(new Film("dark souls", genres1, 4.1, 2));
        listFilm.add(new Film("cyberpunk", genres2, 2.1, 9));
        listFilm.add(new Film("spongebob", genres3, 1.5, 100));
        filmManager = new FilmManager(listFilm);
    }

    @Test
    void searchByTitle_normal() {

    }

    @Test
    void searchByGenre() {
    }

    @Test
    void rateFilm() {
    }

    @Test
    void addFilm() {
    }

    @Test
    void removeFilm() {
    }
}