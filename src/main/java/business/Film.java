package business;

import java.util.ArrayList;

public class Film {
    private String title;
    private String genre;
    private int totalRating;
    private int rateCount;

    public Film(String title, String genre, int totalRating, int rateCount) {
        this.title = title;
        this.genre = genre;
        this.totalRating = totalRating;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
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
                ", Genre=" + genre +
                ", totalRating=" + totalRating +
                ", rateCount=" + rateCount +
                '}';
    }
}
