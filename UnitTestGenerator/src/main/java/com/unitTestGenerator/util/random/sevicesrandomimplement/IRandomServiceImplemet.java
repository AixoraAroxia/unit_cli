package com.unitTestGenerator.util.random.sevicesrandomimplement;


import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Inyect;
import com.unitTestGenerator.util.random.servicesRandom.IRandomService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class IRandomServiceImplemet implements IRandomService {

    @Inyect
    private IAddressRandomServiceImplement iAddressRandomService;

    @Inyect
    private RealWordTextRandom realWordTextRandom;

    private Map<String, String> clientesMap = new HashMap<String, String>();

    private String[] FIRST_NAMES = {
            "John", "Jane", "Michael", "Emily", "David", "Sarah", "Robert", "Olivia", "William", "Sophia"
            , "Michael", "James", "John", "Robert", "David", "William", "Mary", "Christopher", "Joseph",
            "Richard", "Daniel", "Thomas", "Patricia", "Jennifer", "Linda", "Elizabeth", "Charles", "Matthew",
            "Anthony", "Jessica"
    };

    private String[] LAST_NAMES = {
            "Smith", "Johnson", "Brown", "Lee", "Miller", "Davis", "Garcia", "Martinez", "Clark", "Lopez"
            , "Williams", "Jones", "Rodriguez", "Hernandez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor",
            "Moore", "Jackson", "White"
    };

    private String[] accountType = {"Savings Account", "Checking Account", "Money Market Account", "Certificate of Deposit Account", "Individual Retirement Account"};


    public IRandomServiceImplemet() {
//        iAddressRandomService = new IAddressRandomServiceImplement();
    }

    @Override
    public Long generatePositiveRandomLong() {
        long randomNumber = new Random().nextLong();
        return Math.abs(randomNumber);
    }

    @Override
    public Long generatePositiveRandomLong(Long minNumber, Long maxNumber) {
        if (minNumber > maxNumber) {
            Long tempMinimo = maxNumber;
            Long tempMaximo = minNumber;
            minNumber = tempMinimo;
            maxNumber = tempMaximo;
        }
        Long range = maxNumber - minNumber + 1;
        return minNumber + Math.abs(new Random().nextLong() % range);
    }


    public Double generateValues(Double value){
      return  BigDecimal.valueOf(value).setScale(4, RoundingMode.HALF_EVEN).doubleValue();
    }

    public Date converteLocalDateToDate(LocalDate localDate) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

    @Override
    public Integer generateRandomNumber(Integer minNumber, Integer maxNumber) {
        Integer b = 0;
        if(minNumber > maxNumber){
             b = new Random().nextInt( minNumber  - maxNumber + 1) + maxNumber;
        }else {
            b = new Random().nextInt(maxNumber - minNumber + 1) + minNumber;
        }
        return b;
    }

    @Override
    public Integer generateRandomNumber(Integer maxNumber) {
        return new Random().nextInt(maxNumber);
    }

    private LocalDate getRandomDate(int year) {
        int month = this.generateRandomNumber(13);
        if (year < 1980){
            year = 2000;
        }

        if(month > 12 || month < 1 ){
            month = 12;
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();
        int dayOfMonth = this.generateRandomNumber(1, daysInMonth);

        if(dayOfMonth <= 0 ){
            dayOfMonth ++;
        }

        if(dayOfMonth > 31){
            dayOfMonth = 25;
        }

        LocalDate startDate = LocalDate.of(year, month, dayOfMonth);
        return startDate;
    }

    @Override
    public Date generateRandomDate() {
        int year = this.generateRandomNumber(1980, LocalDate.now().getYear());
        int yearUp = this.generateRandomNumber(1, 23) + year;
        int yearRandom = this.generateRandomNumber(yearUp);
        LocalDate startDate = getRandomDate(year);
        LocalDate endDate = getRandomDate(yearRandom);
        LocalDate randomDate = generateRandomDate(startDate, endDate);
        return this. converteLocalDateToDate(randomDate);
    }

    @Override
    public LocalDate generateRandomDate(LocalDate startDate, LocalDate endDate) {
        long startOrigin = startDate.toEpochDay();
        long endBound = endDate.toEpochDay();
        long randomEpochDay =0l;

       if(startOrigin > endBound){
           randomEpochDay = ThreadLocalRandom.current().nextLong(endBound, startOrigin);
       }else if (startOrigin == endBound){
           endBound+=12L;
           randomEpochDay = ThreadLocalRandom.current().nextLong(startOrigin, endBound);
       }else {
           randomEpochDay = ThreadLocalRandom.current().nextLong(startOrigin, endBound);
       }

        return LocalDate.ofEpochDay(randomEpochDay);
    }

    @Override
    public String generateRandomText(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randomChar = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    @Override
    public String generateRandomName() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        return firstName;
    }

    @Override
    public String generateRandomLastName() {
        Random random = new Random();
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return lastName;
    }

    public String generateAddress() {
        String address = iAddressRandomService.generateAddress();
        return address;
    }

    @Override
    public Double getRandomNumeroDouble(Double valorMinimo, Double valorMaximo) {
        Random rand = new Random();
        Double valor = valorMinimo + (valorMaximo - valorMinimo) * rand.nextDouble();
        return BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    @Override
    public String generateCode() {
        String code = UUID.randomUUID().toString();
        try {
            boolean exist = clientesMap.containsKey(code);
            if (!exist) {
                return code;
            } else {
                generateCode();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return code;
    }

    @Override
    public Boolean generateRamdonBoolean() {
        return this.generateRandomNumber(0, 1) == 1;
    }

    @Override
    public String generateRandomRealWordText(Integer wordNumbers) {
        return realWordTextRandom.generateMeaningfulText(wordNumbers);
    }


}







