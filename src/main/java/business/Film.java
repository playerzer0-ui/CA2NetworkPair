package business;

import service.TCProtocol;

public class Film {
    private String title;
    private String genre;
    private int totalRating;
    private int numRaters;

    public Film(String title, String genre, int totalRating, int numRaters) {
        this.title = title;
        this.genre = genre;
        this.totalRating = totalRating;
        this.numRaters = numRaters;
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

    public int getNumRaters() {
        return numRaters;
    }

    public void setNumRaters(int numRaters) {
        this.numRaters = numRaters;
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
        return title + TCProtocol.DELIMITER + genre + TCProtocol.DELIMITER + TCProtocol.DELIMITER
                + totalRating + TCProtocol.DELIMITER + numRaters;
    }
}
