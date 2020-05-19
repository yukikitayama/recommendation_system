
/**
 * Write a description of FourthRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class RunnerRatings {   
    private int dotProduct(Rater me, Rater r) {
        int answer = 0;
        int scaler = 5;
        String meRaterId = me.getID();
        String rRaterId = r.getID();
        // get movies that they both rated
        ArrayList<String> meMoviesRated = me.getItemsRated();
        ArrayList<String> rMoviesRated = r.getItemsRated();
        ArrayList<String> bothMoviesRated = new ArrayList<String>();
        for (String movieId : meMoviesRated) {
            if (rMoviesRated.contains(movieId)) {
                bothMoviesRated.add(movieId);
            }
        }
        for (String movieId : bothMoviesRated) {
            // get rating
            double meRating = me.getRating(movieId);
            double rRating = r.getRating(movieId);
            // scale and dot product
            answer += (meRating - scaler) * (rRating - scaler);
        }
        return answer;
    }
    
    // id is Rater ID of me
    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> similarityList = new ArrayList<Rating>();
        Rater me = RaterDatabase.getRater(id);
        ArrayList<Rater> raterList = RaterDatabase.getRaters();
        for (Rater rater : raterList) {
            String raterId = rater.getID();
            if (!id.equals(raterId)) {
                int dp = dotProduct(me, rater);
                if (dp > 0) {
                    // debug
                    // System.out.println("dotProduct stored: " + dp);
                    
                    Rating rating = new Rating(raterId, dp);
                    similarityList.add(rating);
                }
            }
        }
        // sort rating from highest to lowest
        Collections.sort(similarityList, Collections.reverseOrder());
        return similarityList;
    }
    
    // id is rater ID
    public ArrayList<Rating> getSimilarRatings(String id, 
                                               int numSimilarRaters, 
                                               int minimalRaters) {
        ArrayList<Rating> dotProductList = getSimilarities(id);
        ArrayList<Rating> ret = new ArrayList<Rating>();
        ArrayList<String> movieList = MovieDatabase.filterBy(new TrueFilter());
        // debug
        // System.out.println("movie list size: " + movieList.size());
        for (String currMovieId : movieList) {
            int sumRating = 0;
            int countRating = 0;
            double weightedAverage = 0.0;
            // debug
            // System.out.println("movie ID: " + currMovieId);
            
            for (int k = 0; k < numSimilarRaters; k++) {
                Rating currRatingDp = dotProductList.get(k);
                String currRaterId = currRatingDp.getItem();
                double currWeight = currRatingDp.getValue();
                Rater rater = RaterDatabase.getRater(currRaterId);
                double currRating = rater.getRating(currMovieId);
                if (currRating != -1) {
                    sumRating += currWeight * currRating;
                    countRating += 1;
                }
                // debug
                // if (sumRating < 0) {
                    // System.out.println("sumRating: " + sumRating + "\t" +
                                       // "countRating: " + countRating);
                // }
            }
            
            if (countRating >= minimalRaters) {
                weightedAverage = sumRating / countRating;
                // debug
                // System.out.println("calcurated weighted average: " + weightedAverage +
                                   // "\tcountRating: " + countRating);
                // if (weightedAverage < 0) {
                    // System.out.println("weighted average:" + weightedAverage);
                // }
                                   
                Rating rating = new Rating(currMovieId, weightedAverage);
                ret.add(rating);
            }
        }
        Collections.sort(ret, Collections.reverseOrder());
        return ret;
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String id,
                                                       int numSimilarRaters,
                                                       int minimalRaters,
                                                       Filter filterCriteria) {
        ArrayList<Rating> dotProductList = getSimilarities(id);
        ArrayList<Rating> ret = new ArrayList<Rating>();
        ArrayList<String> movieList = MovieDatabase.filterBy(filterCriteria);
        for (String currMovieId : movieList) {
            int sumRating = 0;
            int countRating = 0;
            double weightedAverage = 0.0;
            // debug
            // System.out.println("movie ID: " + currMovieId);
            
            for (int k = 0; k < numSimilarRaters; k++) {
                Rating currRatingDp = dotProductList.get(k);
                String currRaterId = currRatingDp.getItem();
                double currWeight = currRatingDp.getValue();
                Rater rater = RaterDatabase.getRater(currRaterId);
                double currRating = rater.getRating(currMovieId);
                if (currRating != -1) {
                    sumRating += currWeight * currRating;
                    countRating += 1;
                }
                // debug
                if (sumRating < 0) {
                    System.out.println("sumRating: " + sumRating + "\t" +
                                       "countRating: " + countRating);
                }
            }
            
            if (countRating >= minimalRaters) {
                weightedAverage = sumRating / countRating;
                // debug
                // System.out.println("calcurated weighted average: " + weightedAverage +
                                   // "\tcountRating: " + countRating);
                if (weightedAverage < 0) {
                    System.out.println("weighted average:" + weightedAverage);
                }
                                   
                Rating rating = new Rating(currMovieId, weightedAverage);
                ret.add(rating);
            }
        }
        Collections.sort(ret, Collections.reverseOrder());
        return ret;        
    }
    
    private double getAverageByID(String id, int minimalRaters) {
        int sumRating = 0;
        int countRating = 0;
        double average = 0.0;
        ArrayList<Rater> myRaters = RaterDatabase.getRaters();
        for (Rater r : myRaters) {
            double currRating = r.getRating(id);
            if (currRating != -1) {
                sumRating += currRating;
                countRating += 1;
            }
        }
        if (countRating >= minimalRaters) {
            average = (double) sumRating / countRating;
        }
        // debug
        // System.out.print("**debug** id: " + id + "\tsum: " + sumRating + 
                         // "\tcount: " + countRating + "\n");
        
        return average;
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<Rating> averageRatingList = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for (String currId : movies) {
            double average = getAverageByID(currId, minimalRaters);
            if (average != 0) {
                Rating rating = new Rating(currId, average);
                averageRatingList.add(rating);
            }
        }
        return averageRatingList;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(
        int minimalRaters, 
        Filter filterCriteria) {
        ArrayList<Rating> averageRatings = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        for (String currId : movies) {
            double average = getAverageByID(currId, minimalRaters);
            if (average != 0) {
                Rating rating = new Rating(currId, average);
                averageRatings.add(rating);
            }
        }
        return averageRatings;
    }
}
