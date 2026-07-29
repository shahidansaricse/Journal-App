//package com.journalApp.service;
//
//import com.journalApp.entity.JournalEntry;
//import com.journalApp.entity.User;
//import com.journalApp.repository.JournalEntryRepository;
//import com.journalApp.repository.UserRepository;
//import org.bson.types.ObjectId;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class JournalEntryService {
//
//   @Autowired
//    private JournalEntryRepository journalEntryRepository;
//
//   @Autowired
//   private UserService userService;
//    private UserRepository userRepository;
//
////    public void saveEntry(JournalEntry entry, String userName) {
////        User user = userService.findByUserName(userName);
////
////        JournalEntry saved = journalEntryRepository.save(entry);
////
////        user.getJournalEntries().add(saved);
////        userService.saveEntry(user);
////    }
//   @Transactional
//    public void saveEntry (JournalEntry journalEntry, String userName) {
//       try {
//           User user = userService.findByUserName(userName);
//           journalEntry.setDate(LocalDateTime.now());
//           JournalEntry saved = journalEntryRepository.save(journalEntry);
//           user.getJournalEntries().add(saved);
//           userService.saveEntry(user);
//       } catch (Exception e) {
//            System.out.println(e);
//            throw new RuntimeException("An Error occurred while saving entry",e);
//       }
//   }
//    public void saveEntry(JournalEntry journalEntry){
//       journalEntryRepository.save(journalEntry);
//    }
//
//    public List<JournalEntry> getAll() {
//        return journalEntryRepository.findAll();
//    }
//    public Optional<JournalEntry> findById(ObjectId id) {
//        return journalEntryRepository.findById(id);
//    }
//    public void deleteById(ObjectId id,String userName) {
//        User user = userService.findByUserName(userName);
//        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
//        userService.saveEntry(user);
//        journalEntryRepository.deleteById(id);
//    }
//
//    public void updateEntry(JournalEntry old, String userName) {
//
//        User user = userRepository.findByUserName(userName);
//
//        if (user == null) {
//            throw new RuntimeException("User not found ❌");
//        }
//
//        List<JournalEntry> entries = user.getJournalEntries();
//
//        for (int i = 0; i < entries.size(); i++) {
//            if (entries.get(i).getId().equals(old.getId())) {
//
//                entries.set(i, old); // ✅ replace (UPDATE, not ADD)
//                break;
//            }
//        }
//
//        userRepository.save(user); // ✅ save updated user
//    }
//}
//
//
//// controller  ---> service  ---> repository

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
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserRepository userRepository;


    // Create Journal Entry
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


    // Delete journal entry
    public void deleteById(ObjectId id, String userName) {

        User user = userRepository.findByUserName(userName);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        user.getJournalEntries()
                .removeIf(entry -> entry.getId().equals(id));

        userRepository.save(user);

        journalEntryRepository.deleteById(id);
    }


    // Update journal entry
    public void updateEntry(JournalEntry oldEntry, String userName) {

        User user = userRepository.findByUserName(userName);

        if (user == null) {
            throw new RuntimeException("User not found");
        }


        List<JournalEntry> entries = user.getJournalEntries();

        for(int i = 0; i < entries.size(); i++) {

            if(entries.get(i).getId().equals(oldEntry.getId())) {

                entries.set(i, oldEntry);
                break;
            }
        }

        userRepository.save(user);
    }
}