
/**
 * Write a description of RecommendationRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class RecommendationRunner implements Recommender {
    private ArrayList<String> list;
    
    public RecommendationRunner() {
        list = new ArrayList<String>();
    }

    // String in ArrayList is movie IDs
    public ArrayList<String> getItemsToRate() {
    // movie title, movie id
        // ArrayList<String> list = new ArrayList<String>();
        list.add("2637276"); // "Ted 2", "2637276"
        list.add("2361509"); // "The Intern", "2361509"
        list.add("2279373"); // "Pitch Perfect 2", "2279373"
        list.add("0369610"); // "Jurassic Workd", "0369610"
        list.add("4178092"); // "The Gift", "4178092"
        list.add("1872181"); // "The Amazing Spider-Man 2", "1872181"
        list.add("2294449"); // "22 Jump Street", "2294449"
        list.add("2802144"); // "Kingsman: The Secret Service", "2802144"
        list.add("1843866"); // "Captain America: The Winter Soldier", "1843866"
        list.add("2267998"); // "Gone Girl", "2267998"
        
        return list;
    }

    public void printRecommendationsFor(String webRaterID) {
        RunnerRatings runner = new RunnerRatings();
        int numSimilarRaters = 5;
        int minimalRaters = 2;
        ArrayList<Rating> recommendList = runner.getSimilarRatings(webRaterID,
                                                                   numSimilarRaters,
                                                                   minimalRaters);
        int maxRecommend = 10;
        if (recommendList.size() < maxRecommend) {
            maxRecommend = recommendList.size();
        }
        
        // CSS
        System.out.println("<style>");
        System.out.println("table, th, td {");
        System.out.println("border: 1px solid black;");
        System.out.println("text-align: center;");
        System.out.println("}");
        System.out.println("</style>");
        
        // table header
        System.out.println("<table>");
        System.out.println("<tr>");
        System.out.println("<th>Ranking</th>");
        System.out.println("<th>Title</th>");
        System.out.println("<th>Poster</th>");
        System.out.println("</tr>");
        
        if (recommendList.size() == 0) {
            System.out.println("Sorry, there's no recommendation...");
        }
        else {
            int ranking = 1;
            for (int k = 0; k < maxRecommend; k++) {
                Rating rating = recommendList.get(k);
                String movieId = rating.getItem();
                
                // only recommend the movies that webRater did not rate
                if (!list.contains(movieId)) {
                    String movieTitle = MovieDatabase.getTitle(movieId);
                    String poster = MovieDatabase.getPoster(movieId);
                    String replacedURL = poster.replace("http://ia.media-imdb.com/",
                                                        "https://m.media-amazon.com/");
                    
                    // table content
                    System.out.println("<tr>");
                    System.out.println("<td>" + ranking + "</td>");
                    System.out.println("<td>" + movieTitle + "</td>");
                    System.out.println("<td><img src= \"" + replacedURL + "\" target=_blank></td>");
                    System.out.println("</tr>");
                    
                    ranking += 1;
                }
            }
            System.out.println("</table>");
        }
    }
}
