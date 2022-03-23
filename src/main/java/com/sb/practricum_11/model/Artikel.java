package com.sb.practricum_11.model;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Artikel {
    private String naam;
    private double waarde_usd;

    public Artikel(String line){

        final String regex = "(.*)\\s:\\s(\\d*.\\d*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        while(matcher.find()) {
            if(matcher.groupCount() < 2){
                System.out.println("Invalid line: " + line);
                return;
            }

            setNaam(matcher.group(1));
            setWaarde_usd(Double.parseDouble(matcher.group(2)));
        }

    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public double getWaarde_usd() {
        return waarde_usd;
    }

    public double getWaarde_eur(double koersInCenten) {
        return waarde_usd * (koersInCenten / 100);
    }

    public String getConvertedLine(double koersInCenten){
        double waarde = getWaarde_eur(koersInCenten);
        return naam + " : €" + String.format(Locale.US,"%.2f", waarde);

    }

    public void setWaarde_usd(double waarde_usd) {
        this.waarde_usd = waarde_usd;
    }
}

