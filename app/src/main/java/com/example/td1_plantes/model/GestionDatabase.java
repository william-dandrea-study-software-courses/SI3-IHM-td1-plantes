package com.example.td1_plantes.model;

import com.example.td1_plantes.model.appobjects.Contribution;
import com.example.td1_plantes.model.appobjects.Plant;
import com.example.td1_plantes.model.appobjects.User;
import com.example.td1_plantes.model.appobjects.UserAndPlant;
import com.example.td1_plantes.model.appobjects.smallelements.Fiability;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class GestionDatabase {

    public static User getCurrentUser() {
        return Mocks.user4;
    }

    public static List<User> getAllUsers() {
        return Mocks.users;
    }

    public static List<Plant> getAllPlants() {
        return Mocks.plants;
    }

    public static List<UserAndPlant> getAllUserAndPlant() {
        return Mocks.userAndPlants;
    }

    public static List<Contribution> getAllContributions() {
        return Mocks.contributions;
    }

    public static Optional<User> getRealUser(UUID idUser) {
        return getAllUsers().stream().filter(us -> us.isSameUser(idUser)).findFirst();
    }

    public static Optional<Plant> getRealPlant(UUID idPlant) {
        return getAllPlants().stream().filter(us -> us.isSamePlant(idPlant)).findFirst();
    }


    public static List<Plant> getAllPublicPlants() {
        return getAllPlants().stream().filter(Plant::isPublic).collect(Collectors.toList());
    }

    public static List<Plant> getAllPrivatePlants() {
        return getAllPlants().stream().filter(pl -> !pl.isPublic()).collect(Collectors.toList());
    }


    /**
     * TESTED
     * @param userID
     * @return
     */
    public static List<Plant> getAllPrivatePlantsFromOneUser(UUID userID) {

        List<Plant> finalList = new ArrayList<>();

        for (UserAndPlant userAndPlant : getAllUserAndPlant()) {

            if (userAndPlant.getUser().equals(userID)) {

                System.out.println("Bon User");


                for (Plant plant : getAllPlants()) {

                    if (plant.isSamePlant(userAndPlant.getPlant()) && !plant.isPublic()) {


                        finalList.add(plant);

                    }

                }

            }
        }


        return finalList;
    }





    public static List<Contribution> getContributionsForOnePlant(UUID plantID) {

        return getAllContributions().stream().filter(con -> con.getPlant().equals(plantID)).collect(Collectors.toList());

    }

    public static List<Contribution> getContributionsForOneContributor(UUID userID) {

        return getAllContributions().stream().filter(con -> con.getContributor().equals(userID)).collect(Collectors.toList());

    }

    public static int getNumberOfContributionForOnePlant(UUID plantID) {

        return (int) getAllContributions().stream().filter(contri -> contri.getPlant().equals(plantID)).count();
    }

    public static int getNumberOfPositiveReviewForOnePlant(UUID plantID) {
        return (int) getContributionsForOnePlant(plantID).stream().filter(Contribution::isPositiveAdvice).count();
    }

    public static int getNumberOfContributionForOneUser(UUID userID) {

        return (int) getAllContributions().stream().filter(contri -> contri.getContributor().equals(userID)).count();
    }



    public static Fiability getFiabilityForOnePlant(UUID plantID) {

        double numberOfContribution = (double) getNumberOfContributionForOnePlant(plantID);
        double numberOfPositiveContribution = (double) getNumberOfPositiveReviewForOnePlant(plantID);

        double percentage = numberOfPositiveContribution / numberOfContribution;

        if (percentage < 0.5) {
            return Fiability.LOW;
        }
        if (percentage < 0.75) {
            return Fiability.MEDIUM;
        }

        return Fiability.HIGH;
    }


    public static Optional<User> findAuthorOfOnePlant(UUID idPlant) {

        for (UserAndPlant userAndPlant : getAllUserAndPlant()) {

            if (userAndPlant.getPlant().equals(idPlant)) {
                return getRealUser(userAndPlant.getUser());
            }
        }

        return Optional.empty();
    }



    public static List<Plant> getAllPlantsCreateByCurrentUser() {

        List<UserAndPlant> allUserAnPlants = getAllUserAndPlant();
        List<Plant> finalList = new ArrayList<>();

        for (UserAndPlant uAp : allUserAnPlants) {

            if (uAp.getUser().equals(getCurrentUser().getUserId())) {

                finalList.add(getRealPlant(uAp.getPlant()).get());

            }
        }
        return finalList;
    }













    // SET D'ELEMNTS
    public static void addContributon(Contribution contribution) {

        Mocks.contributions.add(contribution);
    }

    public static void setOnePlantDescription(UUID plantID, String newDescription) {

        Plant plant = getRealPlant(plantID).get();

        plant.setDescription(newDescription);

    }

    public static void addOnePlantSource(UUID plantID, String newSource) {

        Plant plant = getRealPlant(plantID).get();

        plant.getSources().add(newSource);

    }



    public static void addPositiveReviewToOnePlant(UUID plantID) {


        Optional<Contribution> contribOp = getAllContributions().stream().filter(contri -> contri.getContributor().equals(getCurrentUser().getUserId()) && contri.getPlant().equals(plantID)).findAny();

        if (contribOp.isPresent()) {
            contribOp.get().setPositiveAdvice(true);
        } else {
            addContributon(new Contribution(getCurrentUser().getUserId(), plantID, true));
        }

    }

    public static void addNegativeReviewToOnePlant(UUID plantID) {

        Optional<Contribution> contribOp = getAllContributions().stream().filter(contri -> contri.getContributor().equals(getCurrentUser().getUserId()) && contri.getPlant().equals(plantID)).findAny();

        if (contribOp.isPresent()) {
            contribOp.get().setPositiveAdvice(false);
        } else {
            addContributon(new Contribution(getCurrentUser().getUserId(), plantID, false));
        }
    }


}
