package com.backend.roadto900.repository;

import com.backend.roadto900.config.NowUser;
import com.backend.roadto900.dto.NoteDto;
import com.backend.roadto900.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoteRepositoryImpl implements  NoteRepository{

    private final JdbcTemplate jdbcTemplate;
    private final NowUser nowUser;

    private final RowMapper<NoteDto> noteRowMapper = new RowMapper<NoteDto>() {
        @Override
        public NoteDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new NoteDto(
                    rs.getInt("note_id"),
                    rs.getInt("user_id"),
                    rs.getString("note_name")
            );
        }
    };

    @Override
    public List<NoteDto> findAll() {
        List<NoteDto> noteDtoList = jdbcTemplate.query("select * from note where user_id="+
                nowUser.getUserId(),noteRowMapper);
        return noteDtoList;
    }

    @Override
    public List<NoteDto> save(String noteName) {
        jdbcTemplate.execute("INSERT INTO NOTE(user_id, note_name) VALUES("+
                nowUser.getUserId() +
                ",'" + noteName + "')");
        return findAll();
    }

    @Override
    public List<NoteDto> delete(int noteId) {
        jdbcTemplate.execute("DELETE FROM NOTE WHERE note_id="+noteId);
        return findAll();
    }

    @Override
    public int countByNoteName(String noteName) {
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM note WHERE note_name='"
                        + noteName + "'" +
                        "and user_id=" + nowUser.getUserId()
                , Integer.class);
        return count;
    }
}