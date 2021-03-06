package com.teamtreehouse.flashy.controllers;

import com.teamtreehouse.flashy.domain.FlashCard;
import com.teamtreehouse.flashy.services.FlashCardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
  private FlashCardService flashCardService;

  @Autowired
  public void setFlashCardService(FlashCardService flashCardService) {
    this.flashCardService = flashCardService;
  }

  @RequestMapping("/")
  public String index(Model model) {
    StringBuilder ctaBuilder = new StringBuilder();
    int noOfSampleWords = 3;
    List<FlashCard> cards = flashCardService.getRandomFlashCards(noOfSampleWords);
    ctaBuilder.append("Refresh your memory about ");
    for (FlashCard card : cards) {
      ctaBuilder.append(card.getTerm());
      if (card != cards.get(cards.size() - 1)) {
        ctaBuilder.append(", ");
      }
    }
    Long totalCount = flashCardService.getCurrentCount();
    int remainingCards = (int) (totalCount-noOfSampleWords);
    if(remainingCards > 0) {
    ctaBuilder.append(" and ");
    ctaBuilder.append(totalCount-noOfSampleWords);
    ctaBuilder.append(" more");
    }
    model.addAttribute("cta", ctaBuilder.toString());
    model.addAttribute("flashCardCount", totalCount);
    return "index";
  }

}
