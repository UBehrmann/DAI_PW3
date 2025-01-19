package ch.heigvd.dai.services;

import ch.heigvd.dai.models.Appareil;
import ch.heigvd.dai.models.Serie;
import ch.heigvd.dai.repositories.AppareilRepository;

import java.util.List;

public class AppareilService {
    private final AppareilRepository appareilRepository = new AppareilRepository();

    public List<Appareil> getAllAppareils() {
        return appareilRepository.getAllAppareils();
    }

    public List<Appareil> getAppareilsForUser(String username) {
        return appareilRepository.getAppareilsForUser(username);
    }

    public Appareil getAppareilByIp(String ip) {
        return appareilRepository.getAppareilByIp(ip);
    }

    public List<Serie> getSeriesForAppareil(String ip) {
        return appareilRepository.getSeriesForAppareil(ip);
    }

    public void createAppareil(Appareil appareil) {
        appareilRepository.createAppareil(appareil);
    }

    public void updateAppareil(String ip, Appareil appareil) {
        appareilRepository.updateAppareil(ip, appareil);
    }

    public void deleteAppareil(String ip) {
        appareilRepository.deleteAppareil(ip);
    }
}
