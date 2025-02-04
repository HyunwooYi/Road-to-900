package com.backend.roadto900.repository;

import com.backend.roadto900.dto.WordAddDto;
import com.backend.roadto900.dto.WordDto;

import com.backend.roadto900.dto.WordSpellDto;

import com.backend.roadto900.req.WordAskReq;
import com.backend.roadto900.req.WordDeleteReq;
import com.backend.roadto900.req.WordInsertReq;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class WordRepositoryImpl implements WordRepository{
    private final JdbcTemplate jdbcTemplate;
    private static Map<Long, WordDto> store = new HashMap<>();

    private final RowMapper<WordDto> wordRowMapper = new RowMapper<WordDto>() {
        @Override
        public WordDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new WordDto(
                    rs.getInt("word_id"),
                    rs.getString("spell"),
                    rs.getString("mean")
            );
        }

    };

    private final RowMapper<WordSpellDto> wordSpellDtoRowMapper = new RowMapper<WordSpellDto>() {
        @Override
        public WordSpellDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new WordSpellDto(
                    rs.getString("spell"),
                    rs.getString("mean")
            );
        }

    };

    //word_add 테이블을 위한 RowMapper
    private final RowMapper<WordAddDto> wordAddDtoRowMapper = new RowMapper<WordAddDto>() {
        @Override
        public WordAddDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new WordAddDto(
                    rs.getInt("word_add_id"),
                    rs.getString("word_add_spell")
            );
        }

    };


    @Override
    public String insertWord(WordInsertReq wordInsertReq) {
            String sql = "INSERT INTO word (spell, mean) VALUES (?, ?)";
            jdbcTemplate.update(sql, wordInsertReq.getSpell(), wordInsertReq.getMean());
            WordDto newWordDto = jdbcTemplate.queryForObject("SELECT * FROM word WHERE spell = ? AND mean = ?", new Object[]{wordInsertReq.getSpell(), wordInsertReq.getMean()}, wordRowMapper);
            return "Created. 단어 추가 완료";
    }

    @Override
    public WordSpellDto findBySpell(WordInsertReq wordInsertReq) {
        List<WordSpellDto> wordSpellDto = jdbcTemplate.query("SELECT * FROM word WHERE spell = ?", new Object[] {wordInsertReq.getSpell()}, wordSpellDtoRowMapper);
        return wordSpellDto.isEmpty() ? null : wordSpellDto.get(0);
    }

    @Override
    public List<WordDto> findAll() {
        List<WordDto> wordDtoList = jdbcTemplate.query("SELECT word_id,spell, mean FROM word", wordRowMapper);
        return wordDtoList;
    }

    @Override
    public String deleteWord(WordDeleteReq deleteWordReq) {
        jdbcTemplate.update("DELETE FROM WORD WHERE word_id= ?", deleteWordReq.getWordId());
        return "No Content. 단어 삭제 완료.";
    }

    @Override
    public List<WordDto> searchWord(String spell) {
        List<WordDto> wordDto = jdbcTemplate.query("SELECT * FROM word WHERE spell = ?", new Object[]{spell}, wordRowMapper);
        return wordDto;
    }

    @Override
    public String askWord(WordAskReq wordAskReq) {
        jdbcTemplate.update("INSERT INTO word_add (word_add_spell) VALUES (?)",wordAskReq.getSpell());
        return "단어 요청 추가 완료";
    }

    @Override
    public List<WordAddDto> findAskWord() {
        List<WordAddDto> wordAddDtoList = jdbcTemplate.query("SELECT * FROM word_add",wordAddDtoRowMapper);
        System.out.println("wordAddDtoList.size() = " + wordAddDtoList.size());
        return wordAddDtoList;
    }

    public String deleteAskWord(List<Integer> wordIdList){
        for(int wordAddId : wordIdList){
            System.out.println("wordAddId = " + wordAddId);
            jdbcTemplate.execute("DELETE FROM word_add WHERE word_add_id=" + wordAddId );
        }
        return "단어 요청 삭제 완료";
    }
}
