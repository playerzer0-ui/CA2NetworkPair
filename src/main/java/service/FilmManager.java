package service;

import business.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmManager {
    private List<Film> films = new ArrayList<>();

    public FilmManager(ArrayList<Film> films) {
        this.films = films;
    }

    public FilmManager() {
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(ArrayList<Film> films) {
        this.films = films;
    }

    public Film searchByTitle(String title){
        Film film;
        int found = films.indexOf(new Film(title));
        if(found >= 0){
            film = films.get(found);
        }
        else{
            film = null;
        }
        return film;
    }

    public List<Film> searchByGenre(ArrayList<String> genres){
        List<Film> filteredFilms = new ArrayList<>();
        List<String> retain = new ArrayList<>();

        for(Film f : films){
            retain.addAll(f.getGenres());
            retain.retainAll(genres);

            if(!retain.isEmpty()){
                filteredFilms.add(f);
            }
            retain.clear();
        }

        return filteredFilms;
    }

    public boolean rateFilm(String title, int rating){
        Film film = searchByTitle(title);
        if(film == null){
            return false;
        }

        film.setRateCount(film.getRateCount() + 1);
        films.set(films.indexOf(film), film);

        return true;
    }

    public boolean addFilm(Film film){
        if(films.contains(film)){
            return false;
        }

        return films.add(film);
    }

    public boolean removeFilm(Film film){
        if(films.isEmpty()){
            return false;
        }

        return films.remove(film);
    }
}
