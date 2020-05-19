
public class TrueFilter implements Filter {
    // select every movie from MovieDatabase
    @Override
    public boolean satisfies(String id) {
	return true;
    }
}
