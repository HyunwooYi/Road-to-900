package com.backend.roadto900.controller;

import com.backend.roadto900.dto.NoteDto;
import com.backend.roadto900.dto.NoteWordDto;
import com.backend.roadto900.dto.QuestionDto;
import com.backend.roadto900.dto.WordDto;
import com.backend.roadto900.service.NoteService;
import com.backend.roadto900.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
    private final TestService testService;

    @GetMapping("note/list")
    public ResponseEntity noteList(){
        List<NoteDto> noteDtoList = noteService.findAll();
        return ResponseEntity.status(200).body(noteDtoList);
    }

    @PostMapping("note/delete")
    public ResponseEntity delete(@RequestParam int noteId){
        List<NoteDto> noteDtoList = noteService.delete(noteId);
        return ResponseEntity.status(200).body(noteDtoList);
    }

    @PostMapping("note/create")
    public ResponseEntity create(@RequestParam String noteName){
        List<NoteDto> noteDtoList = noteService.join(noteName);;
        return ResponseEntity.status(200).body(noteDtoList);
    }

    @GetMapping("note/{noteId}")
    public ResponseEntity noteWordList(@PathVariable int noteId){
        NoteWordDto noteWordDto = noteService.findNoteWord(noteId);
        return ResponseEntity.status(200).body(noteWordDto);
    }

    @GetMapping("note/{noteId}/test")
    public ResponseEntity noteTest(@PathVariable int noteId){
        List<QuestionDto> questionDtoList = testService.makeNoteTest(noteId);
        return ResponseEntity.status(200).body(questionDtoList);
    }

    @PostMapping("note/{noteId}/create")
    public ResponseEntity noteWordCreate(@PathVariable int noteId, @RequestBody List<WordDto> wordDtoList){
        return null;
    }

    @GetMapping("note/{noteId}/delete")
    public ResponseEntity noteWordDelete(){
        return null;
    }
}
