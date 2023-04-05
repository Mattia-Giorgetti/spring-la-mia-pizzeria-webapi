package org.learning.springpizzeria.SpringPizzeria.service;

import org.learning.springpizzeria.SpringPizzeria.model.Offer;
import org.learning.springpizzeria.SpringPizzeria.model.Pizza;
import org.learning.springpizzeria.SpringPizzeria.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OfferService {

    @Autowired
    OfferRepository offerRepository;

    public Offer getOfferById(Integer id){
        Optional<Offer> offer = offerRepository.findById(id);
        if (offer.isPresent()){
            return offer.get();
        } else throw new RuntimeException();
    }
    public Offer createOffer(Offer formOffer){
        return offerRepository.save(formOffer);
    }

    public Offer updateOffer(Offer formOffer, Integer id) throws RuntimeException {
        Offer updatedOffer = getOfferById(id);
        updatedOffer.setTitle(formOffer.getTitle());;
        updatedOffer.setStartingDate(formOffer.getStartingDate());
        updatedOffer.setFinishDate(formOffer.getFinishDate());
        return offerRepository.save(updatedOffer);
    }

    public boolean deleteByID(Integer id) throws RuntimeException{
        offerRepository.findById(id).orElseThrow(() -> new RuntimeException(Integer.toString(id)));
        try {
            offerRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
