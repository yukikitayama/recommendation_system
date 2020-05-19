
/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MovieRunnerSimilarRatings {
    public void printAverageRatings() {
        // String ratingsfile = "ratings_short.csv";
        // String moviefile = "ratedmovies_short.csv";
        String ratingsfile = "ratings.csv";
        String moviefile = "ratedmoviesfull.csv";
        RaterDatabase.initialize(ratingsfile);
        MovieDatabase.initialize(moviefile);
        RunnerRatings rr = new RunnerRatings();
        
        int numRatings = RaterDatabase.size();
        System.out.println("read data for " + numRatings + " raters");
        
        int numMovies = MovieDatabase.size();
        System.out.println("read data for " + numMovies + " movies");
        
        int minimalRaters = 1;
        ArrayList<Rating> averageRatingList = rr.getAverageRatings(minimalRaters);
        System.out.println("found " + averageRatingList.size() + " movies");
        // Rating has compareTo method, so we can use Collections.sort()
        Collections.sort(averageRatingList);
        for (Rating rating : averageRatingList) {
            String currId = rating.getItem();
            String currTitle = MovieDatabase.getTitle(currId);
            // debug
            // System.out.println(rating);
            
            System.out.println(rating.getValue() + " " + currTitle);
        }
    }
    
    public void printAverageRatingsByYearAfterAndGenre() {
        String ratingsfile = "ratings_short.csv";
        String moviefile = "ratedmovies_short.csv";
        RaterDatabase.initialize(ratingsfile);
        MovieDatabase.initialize(moviefile);
        RunnerRatings rr = new RunnerRatings();
        
        int numRatings = RaterDatabase.size();
        System.out.println("read data for " + numRatings + " raters");
        
        int numMovies = MovieDatabase.size();
        System.out.println("read data for " + numMovies + " movies");
        
        int minimalRaters = 1;
        String genre = "Romance";
        int year = 1980;
        Filter filterGenre = new GenreFilter(genre);
        Filter filterYear = new YearAfterFilter(year);
        AllFilters filterAll = new AllFilters();
        filterAll.addFilter(filterGenre);
        filterAll.addFilter(filterYear);
        ArrayList<Rating> list = rr.getAverageRatingsByFilter(minimalRaters, filterAll);
        System.out.println(list.size() + " movie(s) matched");
        for (Rating rating : list) {
            String currMovieId = rating.getItem();
            double currMovieAverageRating = rating.getValue();
            int currMovieYear = MovieDatabase.getYear(currMovieId);
            String currMovieTitle = MovieDatabase.getTitle(currMovieId);
            String currMovieGenre = MovieDatabase.getGenres(currMovieId);
            System.out.println(currMovieAverageRating + " " + 
                               currMovieYear + " " + 
                               currMovieTitle + " ");
            System.out.println("\t" + currMovieGenre);
        }
    }

    public void printSimilarRatings() {
        String ratingsfile = "ratings.csv";
        String moviefile = "ratedmoviesfull.csv";
        RaterDatabase.initialize(ratingsfile);
        int numRatings = RaterDatabase.size();
        System.out.println("read data for " + numRatings + " raters");
        MovieDatabase.initialize(moviefile);
        int numMovies = MovieDatabase.size();
        System.out.println("read data for " + numMovies + " movies");
        RunnerRatings rr = new RunnerRatings();
        
        // String raterId = "65";
        // String raterId = "337";
        String raterId = "71";
        
        int minimalRaters = 5;
        // int minimalRaters = 3;
        
        int numSimilarRaters = 20;
        // int numSimilarRaters = 10;
        
        ArrayList<Rating> list = rr.getSimilarRatings(raterId, 
                                                      numSimilarRaters,
                                                      minimalRaters);
        System.out.println("get " + list.size() + " recommendations");
        Rating rating = list.get(0);
        String movieId = rating.getItem();
        String movieTitle = MovieDatabase.getTitle(movieId);
        System.out.println("top recommendation: " + movieTitle);
    }
    
    public void printSimilarRatingsByGenre() {
        String ratingsfile = "ratings.csv";
        String moviefile = "ratedmoviesfull.csv";
        RaterDatabase.initialize(ratingsfile);
        int numRatings = RaterDatabase.size();
        System.out.println("read data for " + numRatings + " raters");
        MovieDatabase.initialize(moviefile);
        int numMovies = MovieDatabase.size();
        System.out.println("read data for " + numMovies + " movies");
        RunnerRatings rr = new RunnerRatings();

        // String raterId = "65";
        String raterId = "964";
        
        int minimalRaters = 5;
        int numSimilarRaters = 20;
        // String genre = "Action";
        String genre = "Mystery";
        
        Filter filterGenre = new GenreFilter(genre);
        ArrayList<Rating> list = rr.getSimilarRatingsByFilter(raterId,
                                                              numSimilarRaters,
                                                              minimalRaters,
                                                              filterGenre);
        System.out.println("get " + list.size() + " recommendations");        
        for (int k = 0; k < 10; k++) {
            Rating rating = list.get(k);
            String movieId = rating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);
            System.out.println("recommendation rank: " + k + "\t" +
                               "movie: " + movieTitle + "\t" +
                               "score: " + rating.getValue());
        }
    }
    
    public void printSimilarRatingsByDirector() {
        String ratingsfile = "ratings.csv";
        String moviefile = "ratedmoviesfull.csv";
        RaterDatabase.initialize(ratingsfile);
        int numRatings = RaterDatabase.size();
        System.out.println("read data for " + numRatings + " raters");
        MovieDatabase.initialize(moviefile);
        int numMovies = MovieDatabase.size();
        System.out.println("read data for " + numMovies + " movies");
        RunnerRatings rr = new RunnerRatings();

        // String raterId = "1034";
        String raterId = "120";
        
        // int minimalRaters = 3;
        int minimalRaters = 2;
        
        int numSimilarRaters = 10;
        // String directors = "Clint Eastwood,Sydney Pollack,David Cronenberg,Oliver Stone";
        String directors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
        
        Filter filterDirectors = new DirectorsFilter(directors);
        ArrayList<Rating> list = rr.getSimilarRatingsByFilter(raterId,
                                                              numSimilarRaters,
                                                              minimalRaters,
                                                              filterDirectors);
        System.out.println("get " + list.size() + " recommendations");        
        for (int k = 0; k < list.size(); k++) {
            Rating rating = list.get(k);
            String movieId = rating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);
            System.out.println("recommendation rank: " + k + "\t" +
                               "movie: " + movieTitle + "\t" +
                               "score: " + rating.getValue());
        }
    }
    
    public void printSimilarRatingsByGenreAndMinutes() {
        String ratingsfile = "ratings.csv";
        String moviefile = "ratedmoviesfull.csv";
        RaterDatabase.initialize(ratingsfile);
        int numRatings = RaterDatabase.size();
        System.out.println("read data for " + numRatings + " raters");
        MovieDatabase.initialize(moviefile);
        int numMovies = MovieDatabase.size();
        System.out.println("read data for " + numMovies + " movies");
        RunnerRatings rr = new RunnerRatings();

        // String raterId = "65";
        String raterId = "168";
        
        // int minimalRaters = 5;
        int minimalRaters = 3;
        
        int numSimilarRaters = 10;
        // String genre = "Adventure";
        String genre = "Drama";
        
        // int minMinutes = 100;
        int minMinutes = 80;
        
        // int maxMinutes = 200;
        int maxMinutes = 160;
        
        Filter filterGenre = new GenreFilter(genre);
        Filter filterMinute = new MinutesFilter(minMinutes, maxMinutes);
        AllFilters filterAll = new AllFilters();
        filterAll.addFilter(filterGenre);
        filterAll.addFilter(filterMinute);
        ArrayList<Rating> list = rr.getSimilarRatingsByFilter(raterId,
                                                              numSimilarRaters,
                                                              minimalRaters,
                                                              filterAll);
        System.out.println("get " + list.size() + " recommendations");        
        for (int k = 0; k < list.size(); k++) {
            Rating rating = list.get(k);
            String movieId = rating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);
            System.out.println("recommendation rank: " + k + "\t" +
                               "movie: " + movieTitle + "\t" +
                               "score: " + rating.getValue());
        }
    }
    
    public void printSimilarRatingsByYearAfterAndMinutes() {
        String ratingsfile = "ratings.csv";
        String moviefile = "ratedmoviesfull.csv";
        RaterDatabase.initialize(ratingsfile);
        int numRatings = RaterDatabase.size();
        System.out.println("read data for " + numRatings + " raters");
        MovieDatabase.initialize(moviefile);
        int numMovies = MovieDatabase.size();
        System.out.println("read data for " + numMovies + " movies");
        RunnerRatings rr = new RunnerRatings();

        // String raterId = "65";
        String raterId = "314";
        
        int minimalRaters = 5;
        int numSimilarRaters = 10;
        // int year = 2000;
        int year = 1975;
        
        // int minMinutes = 80;
        int minMinutes = 70;
        
        // int maxMinutes = 100;
        int maxMinutes = 200;
        
        Filter filterYear = new YearAfterFilter(year);
        Filter filterMinute = new MinutesFilter(minMinutes, maxMinutes);
        AllFilters filterAll = new AllFilters();
        filterAll.addFilter(filterYear);
        filterAll.addFilter(filterMinute);
        ArrayList<Rating> list = rr.getSimilarRatingsByFilter(raterId,
                                                              numSimilarRaters,
                                                              minimalRaters,
                                                              filterAll);
        System.out.println("get " + list.size() + " recommendations");        
        for (int k = 0; k < list.size(); k++) {
            Rating rating = list.get(k);
            String movieId = rating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);
            System.out.println("recommendation rank: " + k + "\t" +
                               "movie: " + movieTitle + "\t" +
                               "score: " + rating.getValue());
        }    
    }
}
