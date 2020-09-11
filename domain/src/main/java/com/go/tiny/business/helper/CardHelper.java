package com.go.tiny.business.helper;

import com.go.tiny.business.exception.GoTinyDomainException;
import com.go.tiny.business.model.Card;
import com.go.tiny.business.port.ObtainCard;

import java.util.List;
import java.util.regex.Pattern;

import static com.go.tiny.business.constant.GoTinyDomainConstant.ONE;
import static com.go.tiny.business.constant.GoTinyDomainConstant.BASE_62;
import static com.go.tiny.business.constant.GoTinyDomainConstant.ZERO_STR;
import static com.go.tiny.business.constant.GoTinyDomainConstant.ZERO;
import static com.go.tiny.business.constant.GoTinyDomainConstant.EMPTY;
import static com.go.tiny.business.constant.GoTinyDomainConstant.BASE_DIGITS;
import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.CARD_RIGHT_SIDE_PORT_UNAVAILABLE;
import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.INVALID_URL;
import static java.util.Objects.isNull;

public enum CardHelper {
  CARD_HELPER;
  private ObtainCard obtainCard;
  private static final String URL_REGEX =
      "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
  private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

  public void initialize(ObtainCard obtainCard) {
    this.obtainCard = obtainCard;
  }

  public Card create(final Card card) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    if (!validateUrl(card.getActualUrl())) {
      throw new GoTinyDomainException(INVALID_URL);
    }
    long uniqueId = obtainCard.getUniqueId();
    card.setTinyUrl(constructShortUrl(BASE_62, uniqueId));
    return obtainCard.create(card).orElse(null);
  }

  public void update(final Card card) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    obtainCard.update(card);
  }

  public void delete(final String name) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    obtainCard.delete(name);
  }

  public Card get(final String name) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainCard.get(name).orElse(null);
  }

  public List<Card> getAll() {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainCard.getAll();
  }

  public List<Card> getCardsBelongToGroup(final String groupName) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainCard.getCardsBelongToGroup(groupName);
  }

  public List<Card> getCardsBelongToGroupByStatus(final String groupName, final String status) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainCard.getCardsBelongToGroupByStatus(groupName, status);
  }

  public String updateCardInTheGroup(final Card card, final String groupName) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainCard.updateCardInTheGroup(card, groupName);
  }

  public String deleteCardInTheGroup(final Card card, final String groupName) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainCard.deleteCardInTheGroup(card, groupName);
  }

  public String getActualUrl(final String tinyUrl) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainCard.getActualUrl(tinyUrl);
  }

  private Boolean isPortNotAvailable() {
    return isNull(obtainCard);
  }

  private Boolean validateUrl(final String url) {
    return URL_PATTERN.matcher(url).matches();
  }

  public String constructShortUrl(int base62, long uniqueId) {
    int base = base62;
    long decimalNumber = uniqueId;
    String shortUrl = decimalNumber == ZERO ? ZERO_STR : EMPTY;
    long mod = ZERO;
    String baseDigits = BASE_DIGITS;
    while (decimalNumber != ZERO) {
      mod = decimalNumber % base;
      shortUrl = baseDigits.substring((int) mod, (int) mod + ONE) + shortUrl;
      decimalNumber = decimalNumber / base;
    }
    return shortUrl;
  }
}
