package com.backend.roadto900.repository;

import com.backend.roadto900.dto.WordAddDto;
import com.backend.roadto900.dto.WordDto;

import com.backend.roadto900.dto.WordSpellDto;
import com.backend.roadto900.req.WordAskReq;
import com.backend.roadto900.req.WordDeleteReq;
import com.backend.roadto900.req.WordInsertReq;

import java.util.List;

public interface WordRepository {
//    WordDto insertWord (WordDto wordDto);  // 단어 추가
    String insertWord(WordInsertReq wordInsertReq);

    WordSpellDto findBySpell(WordInsertReq wordInsertReq);

    List<WordDto> findAll();    // 모든 단어 불러오기
    String deleteWord(WordDeleteReq deleteWordReq);
    List<WordDto> searchWord(String spell);
    String askWord(WordAskReq wordAskReq);

    List<WordAddDto> findAskWord();

    String deleteAskWord(List<Integer> wordIdList);


}


