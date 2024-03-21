package business;

import java.util.ArrayList;

public class Film {
    private String title;
    private ArrayList<String> genres;
    private double ratings;
    private int rateCount;

    public Film(String title, ArrayList<String> genres, double ratings, int rateCount) {
        this.title = title;
        this.genres = genres;
        this.ratings = ratings;
        this.rateCount = rateCount;
    }

    public Film(String title){
        this.title = title;
    }

    public Film(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public int getRateCount() {
        return rateCount;
    }

    public void setRateCount(int rateCount) {
        this.rateCount = rateCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        return title.equals(film.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", Genre=" + genres +
                ", ratings=" + ratings +
                ", rateCount=" + rateCount +
                '}';
    }
}
