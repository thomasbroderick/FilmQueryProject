package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid";

	@Override
	public Film getFilmById(int filmId) throws SQLException {
		Film film = null;
		Connection conn = DriverManager.getConnection(URL, "student", "student");
		String sql = "SELECT film.id, title, description, release_year, language_id, "
				+ "rental_duration, rental_rate, length, replacement_cost, rating, special_features, "
				+ "language.name, category.name FROM film JOIN language ON film.language_id = language.id "
				+ "JOIN film_category ON film.id = film_category.film_id JOIN category ON film_category.category_id = category.id "
				+ "WHERE film.id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet filmResult = stmt.executeQuery();
		if (filmResult.next()) {
			film = new Film(); // Create the object
			// Here is our mapping of query columns to our object fields:
			film.setId(filmResult.getInt(1));
			film.setTitle(filmResult.getString(2));
			film.setDescription(filmResult.getString(3));
			film.setReleaseYear(filmResult.getInt(4));
			film.setLanguageId(filmResult.getInt(5));
			film.setRentalDuration(filmResult.getInt(6));
			film.setRentalRate(filmResult.getDouble(7));
			film.setLength(filmResult.getInt(8));
			film.setReplacementCost(filmResult.getDouble(9));
			film.setRating(filmResult.getString(10));
			film.setSpecialFeatures(filmResult.getString(11));
			film.setActors(getActorsByFilmId(film.getId()));
			film.setLanguage(filmResult.getString(12));
			film.setCategory(filmResult.getString(13));
		}
		filmResult.close();
		stmt.close();
		conn.close();
		return film;

	}

	@Override
	public Actor getActorById(int actorId) throws SQLException {
		Actor actor = null;
		Connection conn = DriverManager.getConnection(URL, "student", "student");
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();
		if (actorResult.next()) {
			actor = new Actor(); // Create the object
			actor.setId(actorResult.getInt(1));
			actor.setFirstName(actorResult.getString(2));
			actor.setLastName(actorResult.getString(3));
		}
		actorResult.close();
		stmt.close();
		conn.close();
		return actor;
	}

	@Override
	public List<Actor> getActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, "student", "student");
			String sql = "SELECT id, first_name, last_name"
					+ " FROM actor JOIN film_actor ON actor.id = film_actor.actor_id " + " WHERE film_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int actorId = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);

				Actor actor = new Actor(actorId, firstName, lastName);
				actors.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

	@Override
	public List<Film> getFilmBySearch(String searchInput) throws SQLException {
		List<Film> films = new ArrayList<>();
		Film film = null;
		Connection conn = DriverManager.getConnection(URL, "student", "student");
		String sql = "SELECT film.id, title, description, release_year, language_id, rental_duration,"
				+ " rental_rate, length, replacement_cost, rating, special_features, language.name, category.name FROM film "
				+ "JOIN language ON film.language_id = language.id JOIN film_category ON film.id = "
				+ "film_category.film_id JOIN category ON film_category.category_id = category.id WHERE title LIKE ? OR description LIKE ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, "%" + searchInput + "%");
		stmt.setString(2, "%" + searchInput + "%");
		ResultSet filmResult = stmt.executeQuery();
		while (filmResult.next()) {
			film = new Film(); // Create the object
			// Here is our mapping of query columns to our object fields:
			film.setId(filmResult.getInt(1));
			film.setTitle(filmResult.getString(2));
			film.setDescription(filmResult.getString(3));
			film.setReleaseYear(filmResult.getInt(4));
			film.setLanguageId(filmResult.getInt(5));
			film.setRentalDuration(filmResult.getInt(6));
			film.setRentalRate(filmResult.getDouble(7));
			film.setLength(filmResult.getInt(8));
			film.setReplacementCost(filmResult.getDouble(9));
			film.setRating(filmResult.getString(10));
			film.setSpecialFeatures(filmResult.getString(11));
			film.setActors(getActorsByFilmId(film.getId()));
			film.setLanguage(filmResult.getString(12));
			film.setCategory(filmResult.getString(13));
			films.add(film);
		}
		filmResult.close();
		stmt.close();
		conn.close();
		return films;
	}

}
