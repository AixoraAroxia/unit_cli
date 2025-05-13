package com.unitTestGenerator.util.random.servicesRandom;


import java.time.LocalDate;
import java.util.Date;

public interface IRandomService {

    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public Integer generateRandomNumber(Integer maxNumber);
    public Integer generateRandomNumber(Integer minNumber, Integer maxNumber);
    public LocalDate generateRandomDate(LocalDate startDate, LocalDate endDate);
    public String generateRandomText(int length);
    public String generateRandomName();
    public Date generateRandomDate();
    public String generateRandomLastName();
    public Long generatePositiveRandomLong();
    public Long generatePositiveRandomLong(Long minNumber, Long maxNumber);
    public Double getRandomNumeroDouble(Double valorMinimo, Double valorMaximo);
    public String generateCode();
    public Boolean generateRamdonBoolean();
    public String generateRandomRealWordText(Integer wordNumbers);
}
