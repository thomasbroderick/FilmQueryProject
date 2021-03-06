package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();
	Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
		// app.test();
		app.launch();

	}

	// private void test() throws SQLException {
	// Film film = db.getFilmById(900);
	// System.out.println(film);
	// }

	private void launch() throws SQLException {

		startUserInterface();
		input.close();

	}

	private void startUserInterface() throws SQLException {
		String choiceString = "";
		int choice = 0;
		// Initial menu for user choice
		do {

			System.out.println("Welcome to Blockbuster Simulator BS-9000");
			System.out.println("Please choose an option from the menu");
			System.out.println("1. Look up a film by ID #");
			System.out.println("2. Search for a film by title or description");
			System.out.println("3. Exit");
			try {
				choiceString = input.nextLine();
				choice = Integer.parseInt(choiceString);
				if (choice > 3) {
					System.out.println("Number must be between 1 and 3");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("That is not a valid option");
				continue;

			}
			switch (choice) {
			case 1:
				lookUpById();
				break;
			case 2:
				lookUpByString();
				break;
			case 3:
				System.out.println("Goodbye");
				System.exit(0);
			}
		} while (choice != 3);

	}

	private void lookUpByString() throws SQLException {
		String searchInput = "";
		System.out.println("Text to search for: ");
		searchInput = input.nextLine();
		List<Film> f = db.getFilmBySearch(searchInput);

		if (f.size() == 0) {
			System.out.println("Film not found");
		} else if (f.size() == 1) {
			Film singleFilm = f.get(0);
			System.out.println(singleFilm.limitedString());
			showSubMenu(singleFilm);
		}

		else {
			for (Film film : f) {
				System.out.println(film.limitedString());

			}
		}

	}

	private void lookUpById() throws SQLException {
		String filmIdString = "";
		int filmId = 0;

		System.out.println("What is the film's ID #?");
		filmIdString = input.nextLine();
		filmId = Integer.parseInt(filmIdString);

		Film f = db.getFilmById(filmId);
		if (f == null) {
			System.out.println("Film not found");
		} else {
			System.out.println(f.limitedString());
			showSubMenu(f);
		}

	}

	private void showSubMenu(Film singleFilm) {
		String userChoice = "";
		int userChoiceInt = 0;
		System.out.println("Would you like to:");
		System.out.println("1. Return to the main menu");
		System.out.println("2. See all details for this film?");
		userChoice = input.nextLine();
		userChoiceInt = Integer.parseInt(userChoice);
		switch (userChoiceInt) {
		case 1:
			break;
		case 2:
			System.out.println(singleFilm.toString());
			break;
		default:
			break;
		}
	}

}