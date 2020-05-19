
/**
 * Write a description of DirectorFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class DirectorsFilter implements Filter {
    private ArrayList<String> myDirectors;

    public DirectorsFilter(String directors) {
        String[] elements = directors.split(",");
        List<String> fixedLengthList = Arrays.asList(elements);
        myDirectors = new ArrayList<String>(fixedLengthList);
    }
    
    public boolean satisfies(String id) {
        String directors = MovieDatabase.getDirector(id);
        for (String director : myDirectors) {
            if (directors.contains(director)) {
                return true;
            }
        }
        return false;
    }
}
