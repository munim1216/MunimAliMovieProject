import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MovieCollection {
    Movie[] movies;
    Scanner scan;
    ArrayList<String> actorList;

    public MovieCollection() {
        movies = new Movie[4999];
        scan = new Scanner(System.in);
        actorList = new ArrayList<String>();

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

        for (Movie movie : movies) {
            for (String actor : movie.getCast()) {
                if (!actorList.contains(actor)) {
                    actorList.add(actor);
                }
            }
        }
        insertionSort(actorList);

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

        movieLookUp(choices);
    }

    public void searchCast() {
        System.out.print("Enter a person to search for: ");
        String menuOption = scan.nextLine().toLowerCase();

        ArrayList<String> actorChoices = new ArrayList<String>();
        for (String person : actorList) {
            if (person.toLowerCase().contains(menuOption)) {
                actorChoices.add(person);
            }
        }

        if (actorChoices.isEmpty()) {
            System.out.println("No people match that search term");
            return;
        }

        int num = 1;
        for (String actor : actorChoices) {
            System.out.println(num + ". " + actor);
            num++;
        }

        System.out.print("Which person would you like to learn more about? Or type 0 to exit.\nEnter number: ");

        while (!scan.hasNextInt()) {
            System.out.print("Try again, that isn't a number");
            scan.nextLine();
        }
        int choice = scan.nextInt();

        if (choice == 0) {
            System.out.println("Back to the menu!");
            return;
        }

        String chosenPerson = actorChoices.get(choice - 1);

        ArrayList<Movie> movieChoices = new ArrayList<Movie>();

        for (Movie movie : movies) {
            for (String actor : movie.getCast()) {
                if (actor.equals(chosenPerson)) {
                    movieChoices.add(movie);
                }
            }
        }

        movieLookUp(movieChoices);
    }

    private void movieLookUp(ArrayList<Movie> choices) {
        int num = 1;
        for (Movie movie : choices) {
            System.out.println(num + ". " + movie.getTitle());
            num++;
        }

        System.out.print("Which movie would you like to learn more about? Or type 0 to exit.\nEnter number: ");

        while (!scan.hasNextInt()) {
            System.out.println("Try again, that isn't a number");
            scan.nextLine();
        }
        int choice = scan.nextInt();


        if (choice == 0) {
            System.out.println("Back to the menu!");
            return;
        }

        Movie chosenMovie = choices.get(choice - 1);

        String cast = "";
        for (String member : chosenMovie.getCast()) {
            cast += member + ", ";
        }
        cast = cast.substring(0, cast.length() - 2);

        System.out.println("Title: " + chosenMovie.getTitle() +
                "\nRuntime: " + chosenMovie.getRuntime() + "minutes" +
                "\nDirected by: " + chosenMovie.getDirector() +
                "\nCast: " + cast +
                "\nOverview: " + chosenMovie.getOverview() +
                "\nUser rating: " + chosenMovie.getUserRating()
        );
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

    private void insertionSort(ArrayList<String> unsorted) {
        for (int i = 1; i < unsorted.size(); i++) {
            int idx = i;
            while (idx > 0 && unsorted.get(idx).compareTo(unsorted.get(idx - 1)) < 0) {
                unsorted.set(idx, unsorted.set(idx - 1, unsorted.get(idx)));
                idx--;
            }
        }
    }
}
