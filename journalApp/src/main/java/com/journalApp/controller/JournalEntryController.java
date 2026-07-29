package com.journalApp.controller;

import com.journalApp.entity.JournalEntry;
import com.journalApp.entity.User;
import com.journalApp.service.JournalEntryService;
import com.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    // Get all journal entries of a user
    @GetMapping("/user/{userName}")
    public ResponseEntity<?> getAllJournalEntries(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        System.out.println(user);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        List<JournalEntry> entries = user.getJournalEntries();

        if (entries == null || entries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No journal entries found");
        }

        return ResponseEntity.ok(entries);
    }

    // Create journal entry
    @PostMapping("/user/{userName}")
    public ResponseEntity<?> createJournalEntry(
            @RequestBody JournalEntry journalEntry,
            @PathVariable String userName) {

        try {
            journalEntryService.saveEntry(journalEntry, userName);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(journalEntry);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // Get journal entry by id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId id) {

        Optional<JournalEntry> journalEntry = journalEntryService.findById(id);

        if (journalEntry.isPresent()) {
            return ResponseEntity.ok(journalEntry.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Journal entry not found");
    }

    // Delete journal entry
    @DeleteMapping("/id/{userName}/{id}")
    public ResponseEntity<?> deleteJournalEntry(
            @PathVariable String userName,
            @PathVariable ObjectId id) {

        journalEntryService.deleteById(id, userName);

        return ResponseEntity.ok("Journal entry deleted successfully");
    }

    // Update journal entry
    @PutMapping("/id/{userName}/{id}")
    public ResponseEntity<?> updateJournalEntry(
            @PathVariable String userName,
            @PathVariable ObjectId id,
            @RequestBody JournalEntry newEntry) {

        Optional<JournalEntry> optionalEntry = journalEntryService.findById(id);

        if (optionalEntry.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Journal entry not found");
        }

        JournalEntry oldEntry = optionalEntry.get();

        if (newEntry.getTitle() != null && !newEntry.getTitle().isBlank()) {
            oldEntry.setTitle(newEntry.getTitle());
        }

        if (newEntry.getContent() != null && !newEntry.getContent().isBlank()) {
            oldEntry.setContent(newEntry.getContent());
        }

        journalEntryService.saveEntry(oldEntry, userName);

        return ResponseEntity.ok(oldEntry);
    }
}