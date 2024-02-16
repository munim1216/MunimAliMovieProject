import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
    Movie[] movies;
    Scanner scan;

    public MovieCollection() {
        movies = new Movie[4999];
        scan = new Scanner(System.in);

        try(BufferedReader br = new BufferedReader(new FileReader("src/movies_data.csv"))) {
            br.readLine();
            String nextLine = br.readLine();
            int nextAdded = 0;
            while (nextLine != null) {
                movies[nextAdded] = new Movie(nextLine);
                nextLine = br.readLine();
                nextAdded++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        insertionSort(movies);

        System.out.println("Welcome to the movie collection!");
        menu();
    }

    public void menu() {
        String menuOption = "";
        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scan.nextLine().trim().toLowerCase();

            if (menuOption.equals("t")) {
                searchTitles();
            } else if (menuOption.equals("c")) {
                searchCast();
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    public void searchTitles() {
        System.out.print("Enter a title search term: ");
        String menuOption = scan.nextLine().toLowerCase();

        ArrayList<Movie> choices = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getTitle().contains(menuOption)) {
                choices.add(movie);
            }
        }

        if (choices.isEmpty()) {
            System.out.println("No movie titles match that search term");
            return;
        }

        int num = 1;
        for (Movie movie : choices) {
            System.out.println(num + ". " + movie.getTitle());
            num++;
        }

        System.out.println("Which movie would you like to learn more about? Or type 0 to exit.\nEnter number: ");

        while (!scan.hasNextInt()) {
            System.out.println("Try again, that isn't a number");

        }
        menuOption = scan.nextLine().toLowerCase();

        int choice = Integer.parseInt(menuOption);

        if (choice == 0) {
            System.out.println("Back to the menu!");
            return;
        }

        Movie chosenMovie = choices.get(choice - 1);

        String cast = "";
        for (String member : chosenMovie.getCast()) {
            cast += member + "\\|";
        }

        System.out.println("Title: " + chosenMovie.getTitle() +
                "\nRuntime: " + chosenMovie.getRuntime() + "minutes" +
                "\nDirected by: " + chosenMovie.getDirector() +
                "\nCast: " + cast +
                "\nOverview: " + chosenMovie.getOverview() +
                "\nUser rating: " + chosenMovie.getUserRating()
        );
    }

    public void searchCast() {

    }

    private void insertionSort(Movie[] unsorted) {
        for (int i = 1; i < unsorted.length; i++) {
            int idx = i;
            while (idx > 0 && unsorted[idx].getTitle().compareTo(unsorted[idx - 1].getTitle()) < 0) {
                Movie temp = unsorted[idx];
                unsorted[idx] = unsorted[idx - 1];
                unsorted[idx - 1] = temp;
                idx--;
            }
        }

    }
}
