public class Movie {
    private String title;
    private String[] cast;
    private String director;
    private String overview;
    private String runtime;
    private String userRating;

    public Movie(String movieInfo) {
        String[] movieArr = movieInfo.split(",");
        title = movieArr[0];
        cast = movieArr[1].split("\\|");
        director = movieArr[2];
        overview = movieArr[3];
        runtime = movieArr[4];
        userRating = movieArr[5];
    }

    public String getTitle() {
        return title;
    }

    public String[] getCast() {
        return cast;
    }

    public String getDirector() {
        return director;
    }

    public String getOverview() {
        return overview;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getUserRating() {
        return userRating;
    }
}
