package com.go.tiny.business.helper;

import com.go.tiny.business.exception.GoTinyDomainException;
import com.go.tiny.business.model.Card;
import com.go.tiny.business.port.ObtainCard;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static com.go.tiny.business.constant.GoTinyDomainConstant.BASE_62;
import static com.go.tiny.business.constant.GoTinyDomainConstant.BASE_DIGITS;
import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.CARD_RIGHT_SIDE_PORT_UNAVAILABLE;
import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.INVALID_URL;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public enum CardHelper {
  CARD_HELPER;
  private ObtainCard obtainCard;
  private static final String URL_REGEX =
      "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/|www.){1}[a-zA-Z0-9!#&'()*-/:=?_,]+";
  private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
  private static final String GROUP_TINY = "tiny";
  private static final String MACHINE_IP = "121.0.0.1";
  private static final String PROTOCOL = "http://";
  private static final String SLASH = "/";

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
    long uniqueId = this.obtainCard.getUniqueId();
    String uniqueShortUrl = constructUniqueShortUrl(BASE_62, uniqueId);
    Set<String> shortUrls = new HashSet<>();
    shortUrls.add(uniqueShortUrl);
    card.setTinyUrl(buildShortUrl(shortUrls, GROUP_TINY, new HashSet<>(), uniqueShortUrl));
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
    return obtainCard.getCardsNotBelongToGroup();
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

  private Boolean isPortNotAvailable() {
    return isNull(obtainCard);
  }

  private Boolean validateUrl(final String url) {
    return URL_PATTERN.matcher(url).matches();
  }

  /* public String constructShortUrl(int base62, long uniqueId) {
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
  }*/

  public String uploadAvatar(final byte[] fileData, final String cardName) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainCard.uploadAvatar(fileData, cardName);
  }

  public String constructUniqueShortUrl(int base, long decimalNumber) {
    String tempVal = decimalNumber == 0 ? "0" : "";
    long mod = 0;
    String baseDigits = BASE_DIGITS;
    while (decimalNumber != 0) {
      mod = decimalNumber % base;
      tempVal = baseDigits.substring((int) mod, (int) mod + 1) + tempVal;
      decimalNumber = decimalNumber / base;
    }
    return tempVal;
  }

  public String buildShortUrl(
      Set<String> shortUrls,
      String groupName,
      Set<String> alreadyExistingUrls,
      String shortUrlToUse) {
    if (shortUrls.size() == 1) {
      String shortUrl = shortUrls.stream().findFirst().orElse(null);
      if (shortUrl.length() == 6) {
        boolean isNotAvailable = obtainCard.checkTinyUrlAlreadyExist(groupName, shortUrlToUse);
        if (!isNotAvailable) {
          return shortUrl;
        }
        alreadyExistingUrls.add(shortUrl);
        return buildShortUrl(
            generateShortURLS(shortUrl, alreadyExistingUrls),
            groupName,
            alreadyExistingUrls,
            shortUrl);
      } else if (shortUrl.length() > 6) {
        boolean isUrlAvailableForMoreThanSixChar = false;
        String shortUrlForMoreThanSixChar = null;
        Set<String> urlsGeneratedForMoreThanSixChars = new HashSet<>();
        while (!isUrlAvailableForMoreThanSixChar) {
          String tempUrlForMoreThanSixChar = null;
          boolean isNotGeneratedBefore = false;
          while (!isNotGeneratedBefore) {
            tempUrlForMoreThanSixChar = RandomStringUtils.random(6, shortUrlToUse);
            if (!urlsGeneratedForMoreThanSixChars.contains(tempUrlForMoreThanSixChar)) {
              urlsGeneratedForMoreThanSixChars.add(tempUrlForMoreThanSixChar);
              isNotGeneratedBefore = true;
            }
          }
          shortUrlForMoreThanSixChar =
              buildShortUrl(
                  urlsGeneratedForMoreThanSixChars,
                  groupName,
                  new HashSet<>(),
                  tempUrlForMoreThanSixChar);
          isUrlAvailableForMoreThanSixChar = nonNull(shortUrlForMoreThanSixChar);
        }
        return shortUrlForMoreThanSixChar;
      } else {
        Set<String> availableShortUrlsInDBLike =
            this.obtainCard.getAvailableShortUrls(groupName, shortUrl);
        boolean isGeneratedForLessthanSixChar = false;
        String generatedShortUrlForLessThanSixChar = null;
        while (!isGeneratedForLessthanSixChar) {
          generatedShortUrlForLessThanSixChar =
              shortUrl + RandomStringUtils.random(6 - shortUrl.length(), BASE_DIGITS);
          if (!availableShortUrlsInDBLike.contains(generatedShortUrlForLessThanSixChar)) {
            isGeneratedForLessthanSixChar = true;
          }
        }
        return generatedShortUrlForLessThanSixChar;
      }

    } else if (shortUrls.size() == 10) {
      Set<String> availableToUse =
          this.obtainCard.getAvailableShortURLsByFilteringExistingUrls(groupName, shortUrls);
      if (availableToUse.size() > 0) {
        return availableToUse.stream().findFirst().orElse(null);
      } else {
        alreadyExistingUrls.addAll(shortUrls);
        return buildShortUrl(
            generateShortURLS(shortUrlToUse, alreadyExistingUrls),
            groupName,
            alreadyExistingUrls,
            shortUrlToUse);
      }
    }
    return null;
  }

  private Set<String> generateShortURLS(String shortUrlToUse, Set<String> alreadyExistingUrls) {
    Set<String> randomShortUrls = new HashSet<>();
    int maxRandomStringToQuery = 1;
    while (maxRandomStringToQuery <= 10) {
      String generatedShortUrl = RandomStringUtils.random(6, shortUrlToUse);
      if (!alreadyExistingUrls.contains(generatedShortUrl)) {
        randomShortUrls.add(generatedShortUrl);
        maxRandomStringToQuery++;
      }
    }
    return randomShortUrls;
  }
}
