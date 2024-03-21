package service;

import business.Film;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class FilmManagerTest {

    private FilmManager filmManager;
    private ArrayList<String> genres1;
    private ArrayList<String> genres2;
    private ArrayList<String> genres3;
    @BeforeEach
    public void init() {
        ArrayList<Film> listFilm = new ArrayList<>();

        genres1 = new ArrayList<>();
        genres1.add("comedy");
        genres1.add("horror");
        genres2 = new ArrayList<>();
        genres2.add("sci-fi");
        genres2.add("horror");
        genres3 = new ArrayList<>();
        genres3.add("romance");
        genres3.add("comedy");

        listFilm.add(new Film("dark souls", genres1, 4, 2));
        listFilm.add(new Film("cyberpunk", genres2, 2, 9));
        listFilm.add(new Film("spongebob", genres3, 1, 100));
        filmManager = new FilmManager(listFilm);
    }

    @AfterEach
    public void teardown() {
        ArrayList<Film> listFilm = new ArrayList<>();

        genres1 = new ArrayList<>();
        genres1.add("comedy");
        genres1.add("horror");
        genres2 = new ArrayList<>();
        genres1.add("sci-fi");
        genres1.add("horror");
        genres3 = new ArrayList<>();
        genres1.add("romance");
        genres1.add("comedy");

        listFilm.add(new Film("dark souls", genres1, 4, 2));
        listFilm.add(new Film("cyberpunk", genres2, 2, 9));
        listFilm.add(new Film("spongebob", genres3, 1, 100));
        filmManager = new FilmManager(listFilm);
    }

    @Test
    void searchByTitle_normal() {
        Film exp = new Film("cyberpunk", genres2, 2, 9);
        Film act = filmManager.searchByTitle("cyberpunk");

        assertEquals(exp, act);
    }

    @Test
    void searchByTitle_not_found(){
        Film exp = null;
        Film act = filmManager.searchByTitle("lord of fries");

        assertNull(act);
    }

    @Test
    void searchByTitle_empty(){
        filmManager = new FilmManager();
        Film exp = null;
        Film act = filmManager.searchByTitle("spongebob");

        assertNull(act);
    }

    @Test
    void searchByGenre_normal() {
        List<Film> exp = new ArrayList<>();
        exp.add(new Film("dark souls", genres1, 4, 2));
        exp.add(new Film("cyberpunk", genres2, 2, 9));

        ArrayList<String> genres = new ArrayList<>();
        genres.add("horror");

        List<Film> act = filmManager.searchByGenre(genres);
        assertEquals(exp, act);
    }

    @Test
    void searchByGenre_not_found() {
        List<Film> exp = new ArrayList<>();

        ArrayList<String> genres = new ArrayList<>();
        genres.add("sum ting wong");

        List<Film> act = filmManager.searchByGenre(genres);
        assertEquals(exp, act);
    }

    @Test
    void searchByGenre_empty() {
        filmManager = new FilmManager();
        List<Film> exp = new ArrayList<>();

        ArrayList<String> genres = new ArrayList<>();
        genres.add("sum ting wong");

        List<Film> act = filmManager.searchByGenre(genres);
        assertEquals(exp, act);
    }

    @Test
    void rateFilm_normal() {
        boolean exp = true;
        boolean act = filmManager.rateFilm("dark souls", 3);
        Film film = filmManager.searchByTitle("dark souls");

        assertEquals(exp, act);
        assertEquals(7, film.getTotalRating());
    }

    @Test
    void rateFilm_negative_rating() {
        boolean exp = false;
        boolean act = filmManager.rateFilm("dark souls", -3);
        Film film = filmManager.searchByTitle("dark souls");

        assertEquals(exp, act);
        assertEquals(4, film.getTotalRating());
    }

    @Test
    void rateFilm_not_found() {
        boolean exp = false;
        boolean act = filmManager.rateFilm("light souls", 3);

        assertEquals(exp, act);
    }

    @Test
    void rateFilm_empty() {
        filmManager = new FilmManager();
        boolean exp = false;
        boolean act = filmManager.rateFilm("dark souls", 3);

        assertEquals(exp, act);
    }

    @Test
    void addFilm_normal() {
        boolean exp = true;
        boolean act = filmManager.addFilm(new Film("shoo", genres1, 3, 3));

        assertEquals(exp, act);
        assertEquals(4, filmManager.getFilms().size());
    }

    @Test
    void addFilm_same_title() {
        boolean exp = false;
        boolean act = filmManager.addFilm(new Film("dark souls", genres1, 3, 3));

        assertEquals(exp, act);
        assertEquals(3, filmManager.getFilms().size());
    }

    @Test
    void removeFilm_normal() {
        boolean exp = true;
        boolean act = filmManager.removeFilm("dark souls");

        assertEquals(exp, act);
        assertEquals(2, filmManager.getFilms().size());
    }

    @Test
    void removeFilm_not_found() {
        boolean exp = false;
        boolean act = filmManager.removeFilm("blight souls");

        assertEquals(exp, act);
    }

    @Test
    void removeFilm_empty() {
        filmManager = new FilmManager();
        boolean exp = false;
        boolean act = filmManager.removeFilm("dark souls");

        assertEquals(exp, act);
    }
}