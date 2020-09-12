package com.go.tiny.rest.mapper;

import com.go.tiny.business.exception.GoTinyDomainException;
import com.go.tiny.business.model.Card;
import com.go.tiny.rest.model.CardRequest;
import com.go.tiny.rest.model.CreateCardResponse;
import com.go.tiny.rest.model.GetCardResponse;
import com.go.tiny.rest.model.GetCards;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.go.tiny.rest.constant.GoTinyRestConstants.CANT_READ_MULTIPART_FILE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;

public enum CardMapper {
  CARD_MAPPER;

  public Card constructCardRequest(final CardRequest cardRequest) {
    return Card.builder()
        .name(cardRequest.getName())
        .title(cardRequest.getTitle())
        .description(cardRequest.getDescription())
        .actualUrl(cardRequest.getActualUrl())
        .expiresIn(cardRequest.getExpiresIn())
        .createdBy(cardRequest.getCreatedBy())
        .picture(convertMultipartFile(cardRequest.getPicture()))
        .build();
  }

  public Card constructCardRequestToUpdate(final CardRequest cardRequest) {
    return Card.builder()
        .title(cardRequest.getTitle())
        .description(cardRequest.getDescription())
        .actualUrl(cardRequest.getActualUrl())
        .expiresIn(cardRequest.getExpiresIn())
        .picture(convertMultipartFile(cardRequest.getPicture()))
        .build();
  }

  public GetCardResponse constructGetCardResponse(final Card card) {
    return nonNull(card)
        ? GetCardResponse.builder()
            .name(card.getName())
            .title(card.getTitle())
            .description(card.getDescription())
            .tinyUrl(card.getTinyUrl())
            .expiresIn(card.getExpiresIn())
            .createdBy(card.getCreatedBy())
            .picture(card.getPicture())
            .build()
        : null;
  }

  public GetCards constructGetCards(final List<Card> cards) {
    return isEmpty(cards)
        ? null
        : GetCards.builder()
            .cardResponses(
                cards.stream().map(this::constructGetCardResponse).collect(Collectors.toList()))
            .build();
  }

  public CreateCardResponse constructCreateCardResponse(final Card card) {
    return nonNull(card)
        ? CreateCardResponse.builder().cardName(card.getName()).tinyUrl(card.getTinyUrl()).build()
        : null;
  }

  private byte[] convertMultipartFile(final MultipartFile multipartFile) {
    if (isNull(multipartFile)) {
      return null;
    }
    try {
      return multipartFile.getBytes();
    } catch (IOException ioException) {
      throw new GoTinyDomainException(CANT_READ_MULTIPART_FILE);
    }
  }
}
