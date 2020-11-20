package pl.Lukasz.charity.service;

import pl.Lukasz.charity.entity.Donation;

import java.util.List;

public interface DonationService {
    List<Donation> findAll();
    Donation save(Donation donation);
}
