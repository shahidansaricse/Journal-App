//package com.journalApp.controller;
//
//import com.journalApp.entity.JournalEntry;
//import com.journalApp.entity.User;
//import com.journalApp.service.JournalEntryService;
//import com.journalApp.service.UserService;
//
//import org.bson.types.ObjectId;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.util.List;
//import java.util.Optional;
//
//
//@RestController
//@RequestMapping("/journal")
//public class JournalEntryController {
//
//    @Autowired
//    private JournalEntryService journalEntryService;
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/user/{userName}")
//    public ResponseEntity<?> getAllJournalEntriesOfUser() {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        System.out.println("Authenticated User: " + authentication.getName());
//
//        User user = userService.findByUserName(authentication.getName());
//
//        System.out.println("Mongo User: " + user);
//
//        List<JournalEntry> entries = user.getJournalEntries();
//
//        if (entries == null || entries.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Journal Entries Found");
//        }
//
//        return ResponseEntity.ok(entries);
//    }
//
//
////    @GetMapping("/user/{userName}")
////    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
////        User user = userService.findByUserName(userName);
////        List<JournalEntry> all = user.getJournalEntries();
////
////        if (all != null || !all.isEmpty()) {
////            return new ResponseEntity<>(all, HttpStatus.OK);
////        }
////
////        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////    }
//
////    @PostMapping("/user/{userName}")
////    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,
////                                                    @PathVariable String userName) {
////        try {
////            journalEntryService.saveEntry(myEntry, userName);
////            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
////        } catch (Exception e) {
////            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
////        }
////
////    }
//
//    @GetMapping("/id/{myId}")
//    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
//
//        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
//        if (journalEntry.isPresent()) {
//            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
////    @DeleteMapping("id/{userName}/{myId}")
////    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName) {
////        journalEntryService.deleteById(myId, userName);
////        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
////    }
//
//        @DeleteMapping("/id/{userName}/{myId}")
//    public ResponseEntity<Void> deleteJournalEntryById(
//            @PathVariable ObjectId myId,
//            @PathVariable String userName) {
//
//        journalEntryService.deleteById(myId, userName);
//
//        return ResponseEntity.noContent().build();
//    }
////    @DeleteMapping("/id/{myId}")
////    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
////
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        String userName = authentication.getName();
////
////        User user = userService.findByUserName(userName);
////
////        if (user == null) {
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
////        }
////
////        journalEntryService.deleteById(myId, userName);
////
////        return ResponseEntity.noContent().build();
////    }
////    @PutMapping("id/{userName}/{myid}")
////    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myid,
////                                               @RequestBody JournalEntry newEntry,
////                                               @PathVariable String userName) {
////        JournalEntry old = journalEntryService.findById(myid).orElse(null);
////        if (old != null) {
////            old.setTitle(newEntry.getTitle() != null && newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
////            old.setContent(newEntry.getContent() != null && newEntry.equals("") ? newEntry.getContent() : old.getContent());
////            journalEntryService.saveEntry(old, userName);
////            return new ResponseEntity<>(HttpStatus.OK);
////        }
////        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////    }
////}
////@PutMapping("/{userName}/{myid}")
////public ResponseEntity<?> updateJournalById(@PathVariable String myid,
////                                           @RequestBody JournalEntry newEntry,
////                                           @PathVariable String userName) {
////
////    ObjectId objectId = new ObjectId(myid); // convert manually
////
////    JournalEntry old = journalEntryService.findById(objectId).orElse(null);
////
////    if (old != null) {
////
////        if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
////            old.setTitle(newEntry.getTitle());
////        }
////
////        if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
////            old.setContent(newEntry.getContent());
////        }
////
////        journalEntryService.saveEntry(old, userName);
////        return new ResponseEntity<>(HttpStatus.OK);
////    }
////
////    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////}
//
//
//    @PutMapping("/id/{myId}")
//    public ResponseEntity<?> updateJournalById(@PathVariable String myId,
//                                               @RequestBody JournalEntry newEntry) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userName = authentication.getName();
//
//        User user = userService.findByUserName(userName);
//
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
//        }
//
//        ObjectId objectId = new ObjectId(myId);
//
//        JournalEntry old = journalEntryService.findById(objectId).orElse(null);
//
//        if (old == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal Entry Not Found");
//        }
//
//        if (newEntry.getTitle() != null && !newEntry.getTitle().isBlank()) {
//            old.setTitle(newEntry.getTitle());
//        }
//
//        if (newEntry.getContent() != null && !newEntry.getContent().isBlank()) {
//            old.setContent(newEntry.getContent());
//        }
//
//        journalEntryService.saveEntry(old, userName);
//
//        return ResponseEntity.ok(old);
//    }
//}



//----------------------------------------------------------------------------
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