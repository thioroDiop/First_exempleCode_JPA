package co.simplon.springquotesapi.controller;

import co.simplon.springquotesapi.model.Quote;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class QuotesController {

    private JdbcTemplate jdbcTemplate;

    public QuotesController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/api/quotes")
    public List<Quote> getAllQuotes() throws SQLException {
        // Je récupère la connection à la base
        Connection dbConnection = jdbcTemplate.getDataSource().getConnection();

        // Je prépare ma requête
        List<Quote> quoteList = new ArrayList<>();
        String selectReq = "SELECT * FROM quote";

        try (PreparedStatement statement = dbConnection.prepareStatement(selectReq)) {
            // J'exécute ma requête
            ResultSet set = statement.executeQuery();

            // Tant que j'ai des citations, je les ajoute à la liste
            while (set.next()) {
                quoteList.add(new Quote(set.getInt("id"),
                        set.getString("content"),
                        set.getInt("character_idx")));
            }

            set.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        // Je retourne la liste des citations
        return quoteList;
    }

    @GetMapping("/api/quotes/{id}")
    public Quote getQuoteById(@PathVariable int id) throws SQLException {
        // Je récupère la connection à la base
        Connection dbConnection = jdbcTemplate.getDataSource().getConnection();

        // Je prépare ma requête
        Quote quote = null;
        String selectReq = "SELECT * FROM quote where id = ?";

        try (PreparedStatement statement = dbConnection.prepareStatement(selectReq)) {
            // J'exécute ma requête
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();

            // Tant que j'ai des citations, je les ajoute à la liste
            if (set.next()) {
                quote = new Quote(set.getInt("id"),
                        set.getString("content"),
                        set.getInt("character_idx"));
            }

            set.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        // Je retourne la liste des citations
        return quote;
    }

    @PostMapping("/api/quotes")
    public void createQuote(@RequestBody Quote quoteToCreate) throws SQLException {
        // Je récupère la connection à la base
        Connection dbConnection = jdbcTemplate.getDataSource().getConnection();
        String insertCmd= "INSERT INTO quote (content, character_idx) VALUES(?, ?);";
        try (PreparedStatement stmt = dbConnection.prepareStatement(insertCmd, Statement.RETURN_GENERATED_KEYS)){
            // Préparation de la requête
            stmt.setString(1, quoteToCreate.getContent());
            stmt.setInt(2, quoteToCreate.getCharacterIdx());

            // Exécution de la requête
            stmt.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

      // Points d'accès bidon
//    @GetMapping("/api/quotes")
//    public String getDefaultQuote() {
//        return "Ca va couper chérie";
//    }
//
//    @GetMapping("/api/quotes/{id}")
//    public String getQuoteById(@PathVariable int id) {
//        return "Récupère moi la citation " + id;
//    }
//
//    @PostMapping("/api/quotes")
//    public void createQuote(@RequestBody Quote quoteToCreate) {
//        System.out.println(quoteToCreate);
//    }
//
//    @GetMapping("/horloge")
//    public LocalDateTime getNow() {
//        return LocalDateTime.now();
//    }

}
