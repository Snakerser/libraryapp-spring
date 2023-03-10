package ua.dmytro.libraryapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.dmytro.libraryapp.models.Book;
import ua.dmytro.libraryapp.models.Person;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM people", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM people WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO people(name, age) VALUES(?, ?)", person.getName(), person.getAge());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE people SET name=?, age=? WHERE id=?", updatedPerson.getName(),
                updatedPerson.getAge(), id);
    }

    public List<Book> getBooksByPerson(int id){
        return jdbcTemplate.query("SELECT * FROM books WHERE person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM people WHERE id=?", id);
    }
}
