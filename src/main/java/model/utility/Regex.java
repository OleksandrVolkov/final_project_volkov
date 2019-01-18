package model.utility;

public interface Regex {
    String NAME_REGEX = "[A-Z]{1,1}[a-z]{2,19}";
    String SURNAME_REGEX = "[A-Z]{1,1}[a-z]{2,19}";
    String PHONE_NUMBER_REGEX = "^\\+?(38)?\\(?050\\)?[0-9]{3}\\-?[0-9]{2}\\-?[0-9]{2}$";
    String DATE_REGEX = "([0-9]{2})/([0-9]{2})/([0-9]{4})";
    String EMAIL_REGEX = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
    String LOGIN_REGEX = "^[a-z0-9_-]{3,15}$";
    String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})";
    String PRICE_REGEX = "[0-9]{1,13}(.[0-9]*)?";
}
