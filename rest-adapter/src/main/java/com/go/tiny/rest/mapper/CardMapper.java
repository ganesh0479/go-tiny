package com.go.tiny.rest.mapper;

import com.go.tiny.business.exception.GoTinyDomainException;
import com.go.tiny.business.model.Card;
import com.go.tiny.rest.model.CardRequest;
import com.go.tiny.rest.model.CreateCardResponse;
import com.go.tiny.rest.model.GetCardResponse;
import com.go.tiny.rest.model.GetCards;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static com.go.tiny.rest.constant.GoTinyRestConstants.UNABLE_TO_LOAD_AVATAR;
import static com.go.tiny.rest.constant.GoTinyRestConstants.UNABLE_TO_RETRIEVE_AVATAR;
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
        .build();
  }

  public Card constructCardRequestToUpdate(final CardRequest cardRequest) {
    return Card.builder()
        .title(cardRequest.getTitle())
        .name(cardRequest.getName())
        .description(cardRequest.getDescription())
        .actualUrl(cardRequest.getActualUrl())
        .expiresIn(cardRequest.getExpiresIn())
        .build();
  }

  public GetCardResponse constructGetCardResponse(final Card card, final String serverAddress) {
    return nonNull(card)
        ? GetCardResponse.builder()
            .name(card.getName())
            .title(card.getTitle())
            .description(card.getDescription())
            .tinyUrl(serverAddress + "/go/tiny/" + card.getTinyUrl())
            .expiresIn(card.getExpiresIn())
            .createdBy(card.getCreatedBy())
            .picture(decompressBytes(card.getPicture()))
            .build()
        : null;
  }

  public GetCards constructGetCards(final List<Card> cards, final String serverAddress) {
    return isEmpty(cards)
        ? null
        : GetCards.builder()
            .cardResponses(
                cards.stream()
                    .map(card -> this.constructGetCardResponse(card, serverAddress))
                    .collect(Collectors.toList()))
            .build();
  }

  public CreateCardResponse constructCreateCardResponse(final Card card) {
    return nonNull(card)
        ? CreateCardResponse.builder().cardName(card.getName()).tinyUrl(card.getTinyUrl()).build()
        : null;
  }

  public byte[] compressBytes(final MultipartFile file) {
    ByteArrayOutputStream outputStream = null;
    try {
      byte[] data = file.getBytes();
      Deflater deflater = new Deflater();
      deflater.setInput(data);
      deflater.finish();
      outputStream = new ByteArrayOutputStream(data.length);
      byte[] buffer = new byte[1024];
      while (!deflater.finished()) {
        int count = deflater.deflate(buffer);
        outputStream.write(buffer, 0, count);
      }
      outputStream.close();
    } catch (IOException e) {
      throw new GoTinyDomainException(UNABLE_TO_LOAD_AVATAR);
    }
    return outputStream.toByteArray();
  }

  public static byte[] decompressBytes(byte[] data) {
    if (isNull(data)) {
      return null;
    }
    Inflater inflater = new Inflater();
    inflater.setInput(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    try {
      while (!inflater.finished()) {
        int count = inflater.inflate(buffer);
        outputStream.write(buffer, 0, count);
      }
      outputStream.close();
    } catch (IOException | DataFormatException ioe) {
      throw new GoTinyDomainException(UNABLE_TO_RETRIEVE_AVATAR);
    }
    return outputStream.toByteArray();
  }
}
