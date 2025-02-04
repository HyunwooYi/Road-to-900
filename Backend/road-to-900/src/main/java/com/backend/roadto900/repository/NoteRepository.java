package com.backend.roadto900.repository;

import com.backend.roadto900.dto.NoteDto;
import com.backend.roadto900.dto.NoteWordDto;
import com.backend.roadto900.dto.WordDto;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface NoteRepository {
    List<NoteDto> findAll();

    List<NoteDto> save(String noteName);

    List<NoteDto> delete(int noteId);

    NoteWordDto findNoteWord(int noteId);

    String createNoteWord(int noteId, List<Integer> wordIdList);

    String deleteNoteWord(int noteId, List<Integer> wordIdList);

    int countByNoteId(int noteId);

    int countByNoteName(String noteName);

    NoteDto findByNoteId(int noteId);
}
