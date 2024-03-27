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

    @BeforeEach
    public void init() {
        ArrayList<Film> listFilm = new ArrayList<>();

        listFilm.add(new Film("dark souls", "comedy", 4, 2));
        listFilm.add(new Film("cyberpunk", "horror", 2, 9));
        listFilm.add(new Film("spongebob", "sci-fi", 1, 100));
        filmManager = new FilmManager(listFilm);
    }

    @AfterEach
    public void teardown() {
        ArrayList<Film> listFilm = new ArrayList<>();

        listFilm.add(new Film("dark souls", "comedy", 4, 2));
        listFilm.add(new Film("cyberpunk", "horror", 2, 9));
        listFilm.add(new Film("spongebob", "sci-fi", 1, 100));
        filmManager = new FilmManager(listFilm);
    }

    /**
     * searchByTitle, normal scenario
     */
    @Test
    void searchByTitle_normal() {
        Film exp = new Film("cyberpunk", "horror", 2, 9);
        Film act = filmManager.searchByTitle("cyberpunk");

        assertEquals(exp, act);
    }

    /**
     * searchByTitle, no title found
     */
    @Test
    void searchByTitle_not_found(){
        Film act = filmManager.searchByTitle("lord of fries");

        assertNull(act);
    }

    /**
     * searchByTitle, empty list
     */
    @Test
    void searchByTitle_empty(){
        filmManager = new FilmManager();
        Film act = filmManager.searchByTitle("spongebob");

        assertNull(act);
    }

    /**
     * searchByGenre, normal scenario
     */
    @Test
    void searchByGenre_normal() {
        List<Film> exp = new ArrayList<>();
        exp.add(new Film("cyberpunk", "horror", 2, 9));


        List<Film> act = filmManager.searchByGenre("horror");
        assertEquals(exp, act);
    }

    /**
     * searchByGenre, no films found by that genre
     */
    @Test
    void searchByGenre_not_found() {
        List<Film> exp = new ArrayList<>();

        List<Film> act = filmManager.searchByGenre("romance");
        assertEquals(exp, act);
    }

    /**
     * searchByGenre, empty list
     */
    @Test
    void searchByGenre_empty() {
        filmManager = new FilmManager();
        List<Film> exp = new ArrayList<>();

        List<Film> act = filmManager.searchByGenre("horror");
        assertEquals(exp, act);
    }

    /**
     * rateFilm, normal scenario
     */
    @Test
    void rateFilm_normal() {
        boolean exp = true;
        boolean act = filmManager.rateFilm("dark souls", 3);
        Film film = filmManager.searchByTitle("dark souls");

        assertEquals(exp, act);
        assertEquals(7, film.getTotalRating());
    }

    /**
     * rateFilm, but the rating is a negative
     */
    @Test
    void rateFilm_negative_rating() {
        boolean exp = false;
        boolean act = filmManager.rateFilm("dark souls", -3);
        Film film = filmManager.searchByTitle("dark souls");

        assertEquals(exp, act);
        assertEquals(4, film.getTotalRating());
    }

    /**
     * rateFilm, but the rating is a way too positive
     */
    @Test
    void rateFilm_tooPositive_rating() {
        boolean exp = false;
        boolean act = filmManager.rateFilm("dark souls", 1000);
        Film film = filmManager.searchByTitle("dark souls");

        assertEquals(exp, act);
        assertEquals(4, film.getTotalRating());
    }

    /**
     * rateFilm, no title found
     */
    @Test
    void rateFilm_not_found() {
        boolean exp = false;
        boolean act = filmManager.rateFilm("light souls", 3);

        assertEquals(exp, act);
    }

    /**
     * rateFilm, empty list
     */
    @Test
    void rateFilm_empty() {
        filmManager = new FilmManager();
        boolean exp = false;
        boolean act = filmManager.rateFilm("dark souls", 3);

        assertEquals(exp, act);
    }

    /**
     * addFilm, normal scenario
     */
    @Test
    void addFilm_normal() {
        boolean exp = true;
        boolean act = filmManager.addFilm(new Film("shoo", "bibs", 3, 3));

        assertEquals(exp, act);
        assertEquals(4, filmManager.getFilms().size());
    }

    /**
     * addFilm, same title placed in, not allowed as title is unique
     */
    @Test
    void addFilm_same_title() {
        boolean exp = false;
        boolean act = filmManager.addFilm(new Film("dark souls", "shooter", 3, 3));

        assertEquals(exp, act);
        assertEquals(3, filmManager.getFilms().size());
    }

    /**
     * removeFilm, normal scenario
     */
    @Test
    void removeFilm_normal() {
        boolean exp = true;
        boolean act = filmManager.removeFilm("dark souls");

        assertEquals(exp, act);
        assertEquals(2, filmManager.getFilms().size());
    }

    /**
     * removeFilm, no title found
     */
    @Test
    void removeFilm_not_found() {
        boolean exp = false;
        boolean act = filmManager.removeFilm("blight souls");

        assertEquals(exp, act);
    }

    /**
     * nothing to remove as the list is empty
     */
    @Test
    void removeFilm_empty() {
        filmManager = new FilmManager();
        boolean exp = false;
        boolean act = filmManager.removeFilm("dark souls");

        assertEquals(exp, act);
    }
}