package orm.thi_final_orm.common;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Set;

public class ValidationCommon {
    private static Scanner scanner = new Scanner(System.in);

    public static Boolean hibernateValidation(Object obj) {
        try {
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
            if (constraintViolations.size() > 0) {
                for (ConstraintViolation<Object> violation : constraintViolations) {
                    System.err.println(violation.getMessage());
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static LocalDate localDateParse(String value) {
        try {
            if (value != null) {
                return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            return null;

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    public static String localDateParseToString(LocalDate value) {
        try {
            if (value != null) {
                return value.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            }
            return null;

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

    }


    public static Integer integerParse(String value) {
        try {
            if (value != null) {
                return Integer.parseInt(value);
            }
            return null;

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    public static void checkProcess(Boolean check) {
        if (check) {
            System.out.println("SuccessFully!");
        } else {
            System.err.println("Fail!");
        }
    }

    public String regexPhoneNumberVN(String number) {
        String regex = "(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})";
        while (!number.matches(regex)) {
            System.out.print("Wrong please enter phone number: ");
            number = scanner.nextLine();
        }
        return number;
    }

    public String regexEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        try {
            if (!email.matches(regex)) {
                throw new Exception("Exception Email");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        while (!email.matches(regex)) {
            System.out.print("Wrong please enter email: ");
            email = scanner.nextLine();
        }
        return email;
    }

    public Integer regexIsNumber(String number) {
        String regex = "^[1-9]\\d*$";
        while (!number.matches(regex)) {
            System.out.print("Wrong please enter number: ");
            number = scanner.nextLine();
        }
        return Integer.parseInt(number);
    }

    public static void main(String[] args) {
        System.out.println(localDateParse("2024-01-01").equals(localDateParse("2024-01-01")));
    }
}
