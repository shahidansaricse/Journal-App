package com.journalApp.controller;

import com.journalApp.entity.JournalEntry;
import com.journalApp.entity.User;
import com.journalApp.service.JournalEntryService;
import com.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    // Get all journal entries of a user
    @GetMapping
    public ResponseEntity<?> getAllJournalEntries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
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
    @PostMapping
    public ResponseEntity<?> createJournalEntry(
            @RequestBody JournalEntry journalEntry) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(journalEntry, userName);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(journalEntry);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // Get journal entry by id
    @GetMapping("/id/{myid}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user =userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x-> x.getId().equals(myid)).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myid);

            if (journalEntry.isPresent()) {
                return ResponseEntity.ok(journalEntry.get());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Journal entry not found");
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        boolean deleted =
                journalEntryService.deleteById(id, userName);

        if (deleted) {
            return ResponseEntity.ok("Journal entry deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Journal entry not found");
    }
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateById(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry updatedEntry) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        boolean updated =
                journalEntryService.updateEntry(id, updatedEntry, userName);

        if (updated) {
            return ResponseEntity.ok("Journal entry updated successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Journal entry not found");
    }
}