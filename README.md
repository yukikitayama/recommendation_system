# Recommendation system with Java

* Situation
  * I wanted to build a system to recommend movies to someone.
  * Those recommended movies consider the person's preference and analytically recommend something which the person wants to see.
  * We assume that we have the following two databases
    Movie database. It contains data such as when it was filmed and what the genre was. 
    Rating database. It contains data such as who rated how many movies and what the scores were for each movie.  

* Task
  * Calculate average of rating scores of each movie
  * Get similarity score between a person who needs recommendation and people in the rating databse
  * Implement filter and sorting algorithms to recommend appropriate movie list.

* Action
  * Make a class to calculate average score by each movie, to put weight by dot product between recommended person and raters data (Collaborative filtering average), which are managed by HashMap with key movie ID and value scores, and return finally list of movies names Strings after filtering and sorting.
  * To allow many different filtering conditions, first make a single filter interface, and individually implements the interface by overriding boolean methods depending on conditions. These classes eventually are applied to list of movies and we can recommend movies only on certain specific criteria.
  * We want to recommend movies from the highest scores, so classes implement Comparable interface and compareTo methods. So the list can be sorted in Collections sort method.
  * Manage classes to store movies data, to stores rating data, and to process rating scores. Each class has setter and getter methods and some other helper methods. Each uses HashMap to store data. Keys are movie ID and Rater ID, values are another class of movie and rater objects, so it's easier to store and get each information.

* Result
  * Recommendation is based on similarity scores, so first present random movies for a person to rate.
  * This rated data is processed with our movie and rating database, filtering conditions, and return list of movies which is ordered by the highest similarity scores.
  * Movie database stores poster images, so it also shows list of posters as well as recommended movie names, so it can visually tell recommendation.
  * Things which could be improved are minimum number of data to get average and how many movies to be recommended. In this project, I manually set minimum number and numbers to show, but maybe we can make algorithm to consider database size and output environment.

* MovieRunnerSimilarRatings class
  * This class outputs recommendated movies.
  * Read movie data and rating data from CSV files.
  * Use RunnerRatings class to calculate collaborative filtering averages.
