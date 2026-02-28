package curs3.io.service;

import curs3.io.domain.Duck;
import curs3.io.domain.DuckType;
import curs3.io.domain.Utilizator;
import curs3.io.repository.RepositoryBD;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceUser{
    private final RepositoryBD<Long, Utilizator> userRepo;

    public ServiceUser(RepositoryBD<Long, Utilizator> userRepo) {
        this.userRepo = userRepo;
    }

    public void deleteUser(Long userID) {
        userRepo.delete(userID);
    }

    public Iterable<Utilizator> getAll() {
        return userRepo.getAll();
    }

    public Optional<Utilizator> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public Optional<Utilizator> findById(Long id) {
        return userRepo.findById(id);
    }


    public class PasswordUtils {

        public static String hashPassword(String password) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");

                byte[] hashed = md.digest(password.getBytes());

                StringBuilder sb = new StringBuilder();
                for (byte b : hashed) {
                    sb.append(String.format("%02x", b));
                }

                return sb.toString();

            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("SHA-256 not available", e);
            }
        }
    }
}
