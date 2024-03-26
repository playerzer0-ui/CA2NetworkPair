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


    /**
     * get a list of films
     * @return list of films
     */
    public List<Film> getFilms() {
        return films;
    }

    /**
     * set a list of films
     */
    public void setFilms(ArrayList<Film> films) {
        this.films = films;
    }


    /**
     * search a film by title
     * @param title the title of the film
     * @return the film or null of not found
     */
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


    /**
     * search films by genres
     * @param genres the list of genres
     * @return a list of films by the genres or an empty list if none were found
     */
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

    /**
     * rate a film from 1 to 5
     * @param title the title of the film
     * @param rating number rating
     * @return true or false, if successful or not
     */
    public boolean rateFilm(String title, int rating){
        Film film = searchByTitle(title);
        if(film == null || rating <= 0 || rating > 5){
            return false;
        }

        film.setRateCount(film.getRateCount() + 1);
        film.setTotalRating(film.getTotalRating() + rating);
        films.set(films.indexOf(film), film);

        return true;
    }

    /**
     * add a film to the list
     * @param film the film to be added
     * @return true or false, if successful or not
     */
    public boolean addFilm(Film film){
        if(films.contains(film)){
            return false;
        }

        return films.add(film);
    }

    /**
     *  remove a film from the list
     * @param title the film to be removed
     * @return true or false, if successful or not
     */
    public boolean removeFilm(String title){
        if(films.isEmpty()){
            return false;
        }

        return films.remove(new Film(title));
    }
}
