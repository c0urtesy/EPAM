package univer.Final.presentation;

import univer.Final.servise.City;
import univer.Final.servise.Country;
import univer.Final.servise.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class ConsoleMenu {
    public static void main(String[] args) throws IOException {
        ConsoleMenu consoleMenu = new ConsoleMenu();
        consoleMenu.start();
    }

    Service service;

    public void start() throws IOException {
        service = new Service();
        boolean isRunning = true;

        while (isRunning) {
            meinMenu();
            String command = new Scanner(System.in).nextLine();
            switch (command) {
                case "1" -> showCountries();
                case "2" -> choiceCountry();
                case "3" -> addCountry();
                case "4" -> deleteCountry();
                case "5" -> changeCountryName();
                case "6" -> search();
                case "7" -> isRunning = false;
                default -> showError("Command not found");
            }
        }
        service.saveDataBase();
    }

    private void meinMenu() {
        System.out.println("\033[0;32m"); // Green foreground color

        System.out.println("1.Show Countries      | 2.Choice Country | 3.Add Country | 4.Delete Country");
        System.out.println("5.Change country name | 6.Search         |               | 7.Quit");

        System.out.println("\033[0m"); // reset foreground color
    }

    private void showError(String message) {
        System.out.println("\033[0;31m"); // Red foreground color
        System.out.println(message);
        System.out.println("\033[0m"); // reset foreground color
    }

    private void showCountries() {
        var countries = service.getCountries();
        if (countries.isEmpty()) {
            showError("Empty");
            return;
        }
        System.out.println("Countries:");
        for (var country : countries) {
            System.out.println("[" + country.getIdentification() + "] " + country.getName());
        }
    }

    private String getName(boolean skip) {
        while (true) {
            System.out.println("Write name:");
            String name = new Scanner(System.in).nextLine();
            if (name.matches("[a-zA-Z-]+")) {
                return name;
            } else if ("".equals(name) && skip) {
                return name;
            } else {
                showError("Name is not format");

            }
        }
    }

    private String getIdentification() {
        while (true) {
            System.out.println("Write identification:");
            String identification = new Scanner(System.in).nextLine();
            if (identification.matches("[0-9]+")) {
                return identification;
            } else {
                showError("Name is not format");

            }
        }
    }

    private void addCountry() {
        if (service.addCountry(new Country(getIdentification(), getName(false)))) {
            System.out.println("Country added");
        } else {
            System.out.println("Country not added. Not Unique Identification");
        }
    }

    private void deleteCountry() {
        if (service.deleteCountry(getIdentification())) {
            System.out.println("Country deleted");
        } else {
            System.out.println("Country not deleted. Country not found");
        }
    }

    private void changeCountryName() {
        var country = service.findCountry(getIdentification());
        if (country == null) {
            showError("Country not found");
            return;
        }

        System.out.println("[" + country.getIdentification() + "] " + country.getName());
        String name = getName(true);
        if (name.equals("")) {
            System.out.println("Country don't changed");
        } else {
            country.setName(name);
            System.out.println("Country name changed");
        }
    }

    private void search() {
        String identification = getIdentification();

        if (service.objectHaveUniqueId(identification)) {
            showError("Not found");
            return;
        }

        var country = service.findCountry(identification);
        if (country != null) {
            System.out.println("Country:\n[" + country.getIdentification() + "] " + country.getName());
        } else {
            var city = service.findCity(identification);
            System.out.println("City:\n[" + city.getIdentification() + "] " + city.getName());
        }
    }

    private void choiceCountry() {
        showCountries();
        String identification = getIdentification();
        var country = service.findCountry(identification);
        if (country == null) {
            showError("Not Found");
        } else {
            country(country);
        }
    }

    private void country(Country country) {
        boolean isRunning = true;
        while (isRunning) {
            countryMenu();
            String command = new Scanner(System.in).nextLine();
            switch (command) {
                case "1" -> showCities(country);
                case "2" -> addCity(country);
                case "3" -> deleteCity(country);
                case "4" -> changeCityInfo(country);
                case "5" -> showCountryInfo(country);
                case "6" -> isRunning = false;
                default -> showError("Command not found");
            }
        }
    }

    private void countryMenu() {
        System.out.println("\033[0;32m"); // Green foreground color

        System.out.println("1.Show Cities | 2.Add City | 3.Delete City");
        System.out.println("4.Change City info | 5.Show country info | 6.Back");

        System.out.println("\033[0m"); // reset foreground color
    }

    private void showCities(Country country) {
        if (country.getCities().isEmpty()) {
            showError("Empty");
            return;
        }

        for (var city : country.getCities()) {
            System.out.println("[" + city.getIdentification() + "] " + city.getName()
                    + " Population: " + city.getPopulation()
                    + " Capital: " + city.isCapital());
        }
    }

    private int getPopulation(boolean skip) {
        while (true) {
            System.out.println("Write population:");
            String populationString = new Scanner(System.in).nextLine();
            if (populationString.matches("[1-9]+[0-9]*")) {
                return Integer.parseInt(populationString);
            } else if (populationString.equals("") && skip) {
                return 0;
            } else {
                showError("Population is not format");

            }
        }
    }

    private boolean getIsCapital() {
        while (true) {
            System.out.println("Write is Capital (y/n):");
            String isCapital = new Scanner(System.in).nextLine();
            if (Objects.equals(isCapital, "y")) {
                return true;
            } else if (Objects.equals(isCapital, "n")) {
                return false;
            } else {
                showError("Capital's Factor is not format");

            }
        }
    }

    private void addCity(Country country) {
        var identification = getIdentification();
        if (!service.objectHaveUniqueId(identification)) {
            showError("Identification is not unique");
            return;
        }

        country.addCity(new City(identification, null, getName(false), getPopulation(false), getIsCapital()));
        System.out.println("City added");
    }

    private void deleteCity(Country country) {
        var identification = getIdentification();
        if (country.deleteCity(identification)) {
            System.out.println("City deleted");
        } else {
            System.out.println("City not deleted");
        }
    }

    private void changeCityInfo(Country country) {
        var city = country.findCity(getIdentification());
        if (city == null) {
            showError("Not found");
            return;
        }
        var name = getName(true);
        if (!name.equals("")) {
            city.setName(name);
        }
        else {
            System.out.println("Use default value");
        }
        var population = getPopulation(true);
        if(population != 0){
            city.setPopulation(population);
        }
        else{
            System.out.println("Use default value");
        }
        city.setCapital(getIsCapital());
    }

    private void showCountryInfo(Country country){
        System.out.println("[" + country.getIdentification() + "] " + country.getName());
    }

}
