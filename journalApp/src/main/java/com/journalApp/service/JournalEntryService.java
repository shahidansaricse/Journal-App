package com.journalApp.service;

import com.journalApp.entity.JournalEntry;
import com.journalApp.entity.User;
import com.journalApp.repository.JournalEntryRepository;
import com.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private UserService userService;
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(JournalEntry journalEntry, String userName) {

        User user = userRepository.findByUserName(userName);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        journalEntry.setDate(LocalDateTime.now());

        JournalEntry savedEntry = journalEntryRepository.save(journalEntry);

        user.getJournalEntries().add(savedEntry);

        userRepository.save(user);
    }

    // Save only journal entry
    public void saveEntry(JournalEntry journalEntry) {

        journalEntryRepository.save(journalEntry);
    }
    // Get all entries
    public List<JournalEntry> getAll() {

        return journalEntryRepository.findAll();
    }
    // Find by id
    public Optional<JournalEntry> findById(ObjectId id) {

        return journalEntryRepository.findById(id);
    }

    public boolean deleteById(ObjectId id, String userName) {

        User user = userRepository.findByUserName(userName);

        if (user == null) {
            return false;
        }

        // Check journal belongs to logged-in user
        boolean exists = user.getJournalEntries()
                .stream()
                .anyMatch(entry -> entry.getId().equals(id));

        if (!exists) {
            return false;
        }

        // Delete from journal_entries collection
        if (journalEntryRepository.existsById(id)) {
            journalEntryRepository.deleteById(id);
        }

        // Remove reference from user's journalEntries
        user.getJournalEntries()
                .removeIf(entry -> entry.getId().equals(id));

        userRepository.save(user);

        return true;
    }
    public boolean updateEntry(ObjectId id,
                               JournalEntry updatedEntry,
                               String userName) {

        User user = userRepository.findByUserName(userName);

        if (user == null) {
            return false;
        }

        // Check journal belongs to logged-in user
        boolean journalExists = user.getJournalEntries()
                .stream()
                .anyMatch(entry -> entry.getId().equals(id));

        if (!journalExists) {
            return false;
        }

        // Find journal from MongoDB
        Optional<JournalEntry> optionalEntry =
                journalEntryRepository.findById(id);

        if (optionalEntry.isPresent()) return false;

        JournalEntry existingEntry = optionalEntry.get();

        existingEntry.setTitle(updatedEntry.getTitle());
        existingEntry.setContent(updatedEntry.getContent());
        existingEntry.setDate(LocalDateTime.now());

        journalEntryRepository.save(existingEntry);

        return true;
    }}