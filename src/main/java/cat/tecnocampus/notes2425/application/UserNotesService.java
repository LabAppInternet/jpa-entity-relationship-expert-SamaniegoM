package cat.tecnocampus.notes2425.application;

import cat.tecnocampus.notes2425.application.dtos.CreateNoteDTO;
import cat.tecnocampus.notes2425.application.dtos.NoteDTO;
import cat.tecnocampus.notes2425.application.dtos.UserDTO;
import cat.tecnocampus.notes2425.application.exceptions.UserHasNoEditPermission;
import cat.tecnocampus.notes2425.application.exceptions.UserNotFoundException;
import cat.tecnocampus.notes2425.domain.Note;
import cat.tecnocampus.notes2425.domain.Tag;
import cat.tecnocampus.notes2425.domain.UserLab;
import cat.tecnocampus.notes2425.persistence.NoteRepository;
import cat.tecnocampus.notes2425.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserNotesService {
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    public UserNotesService(UserRepository userRepository, NoteRepository noteRepository) {
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
    }

    public UserDTO findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public long createNote(long userId, CreateNoteDTO note) {
        UserLab owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
        Note noteToBeStored = new Note(null, owner, note.title(), note.content(), LocalDateTime.now(), note.tags().stream().map(tag -> new Tag(tag)).collect(Collectors.toSet()));
        return noteRepository.save(noteToBeStored);
    }

    protected boolean isUserOwnerOfNote(long userId, long noteId) {
        return noteRepository.isUserOwnerOfNote(userId, noteId);
    }

    protected boolean userCanEditNote(long userId, long noteId) {
        return noteRepository.userCanEditNote(userId, noteId);
    }

    public void updateNote(long userId, long noteId, CreateNoteDTO note) {
        if (!isUserOwnerOfNote(userId, noteId) && !userCanEditNote(userId, noteId)) {
            throw new UserHasNoEditPermission(userId, noteId);
        }
        noteRepository.update(note, noteId);
    }

    public void createTag(String tagName) {
        //TODO 2.1 implement the method
        Tag tag = new Tag(tagName);
        noteRepository.saveTag(tag);
    }

    public void addTagToNote(long noteId, Tag newtag) {
        //TODO 2.1 implement the method
        Set<Tag> tagSet = Set.of(newtag);
        noteRepository.saveTagsIfExist(noteId, tagSet);
    }

    public void addNoteToUser(long userId, long noteId) {
        //TODO 2.1 implement the method
        noteRepository.addUserToNote(userId, noteId);
    }


    public List<UserDTO> findUsersRatedByNotes() {
        //TODO 2.1 implement the method

        List<UserDTO> users = userRepository.findAll();
        return users.stream()
                .sorted((u1, u2) -> Integer.compare(noteRepository.findUserNotes(u2.id()).size(), noteRepository.findUserNotes(u1.id()).size()))
                .collect(Collectors.toList());
    }


    public List<NoteDTO> findUserNotes(long ownerId) {
        return noteRepository.findUserNotes(ownerId).stream()
                .map(noteDTO -> new NoteDTO(noteDTO.id(), noteDTO.title(), noteDTO.content(), noteDTO.creationDate(),
                        noteRepository.getNoteTags(noteDTO.id()))).toList();
    }
}
