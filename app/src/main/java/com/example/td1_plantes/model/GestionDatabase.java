package com.example.td1_plantes.model;

import com.example.td1_plantes.model.appobjects.Contribution;
import com.example.td1_plantes.model.appobjects.Plant;
import com.example.td1_plantes.model.appobjects.User;
import com.example.td1_plantes.model.appobjects.UserAndPlant;
import com.example.td1_plantes.model.appobjects.smallelements.Fiability;
import com.example.td1_plantes.model.database.FirebaseFactories.ContributionFactory;
import com.example.td1_plantes.model.database.FirebaseFactories.PlantFactory;
import com.example.td1_plantes.model.database.FirebaseFactories.UserAndPlantFactory;
import com.example.td1_plantes.model.database.FirebaseFactories.UserFactory;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class GestionDatabase {



    private static final UserAndPlantFactory userAndPlantFactory = new UserAndPlantFactory();
    private static final UserFactory userFactory = new UserFactory();
    private static final PlantFactory plantFactory = new PlantFactory();
    private static final ContributionFactory contribFactory = new ContributionFactory();

    private static User currentUser = Mocks.user4;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void loadUser(String username) {
        userFactory.getBySurname("Delaroche",
                user -> currentUser = user,
                err -> { throw (RuntimeException)err; }
                );
    }

    public static void getAllUsers(IEventHandler<List<User>> callback) {
        userFactory.getAll(callback, err -> {
            throw new RuntimeException("Error while retrieving Users");
        });
    }

    public static void getAllPlants(IEventHandler<List<Plant>> callback) {
        plantFactory.getAll(callback, err -> {
            throw new RuntimeException("Error while retrieving plants");
        });
    }

    public static void getAllUserAndPlant(IEventHandler<List<UserAndPlant>> callback) {
        userAndPlantFactory.getAll(callback, err -> {
            throw new RuntimeException("Error while retrieving UserAndPlants");
        });
    }

    public static void getAllContributions(IEventHandler<List<Contribution>> callback) {
        contribFactory.getAll(callback, err -> {
            throw new RuntimeException("Error while retrieving contributions");
        });
    }

    public static void getRealUser(UUID idUser, IEventHandler<Optional<User>> callback) {
        userFactory.loadFromFirebase(idUser.toString(), res -> callback.onTrigger(Optional.of(res)), err -> callback.onTrigger(Optional.empty()));
        //getAllUsers(users -> callback.onTrigger(Arrays.stream(users).filter(u -> u.getUserId().equals(idUser)).findAny()));
    }

    public static void getRealPlant(UUID idPlant, IEventHandler<Optional<Plant>> callback) {
        //getAllPlants().stream().filter(us -> us.isSamePlant(idPlant)).findFirst();
        plantFactory.loadFromFirebase(idPlant.toString(), res -> callback.onTrigger(Optional.of(res)), err -> callback.onTrigger(Optional.empty()));
    }


    public static void getAllPublicPlants(IEventHandler<List<Plant>> callback) {
        plantFactory.getPublicPlants(callback, err -> {
            throw new RuntimeException("Error while retrieving plants");
        });
    }

    public static void getAllPrivatePlants(IEventHandler<List<Plant>> callback) {
        plantFactory.getPrivatePlants(callback, err -> {
            throw new RuntimeException("Error while retrieving plants");
        });
    }


    /**
     * TESTED
     * @param userID
     * @return
     */
    public static void getAllPrivatePlantsFromOneUser(UUID userID, IEventHandler<List<Plant>> callback) {
        userAndPlantFactory.getPlantsFor(userID, plantIds -> {
            plantFactory.getManyPlants(plantIds, callback, err -> {
                throw new RuntimeException("Error while retrieving plants");
            });
        });
    }





    public static void getContributionsForOnePlant(UUID plantID, IEventHandler<List<Contribution>> callback) {
        contribFactory.getContributionsForPlant(plantID, callback);
    }

    public static void getContributorsForOnePlant(UUID plantId, IEventHandler<List<User>> callback) {
        getContributionsForOnePlant(plantId, contribs -> {
            userFactory.getMany(contribs.stream().map(Contribution::getContributor).collect(Collectors.toList()), callback);
        });
    }

    public static void getContributionsForOneContributor(UUID userID, IEventHandler<List<Contribution>> callback) {
        contribFactory.getContributionsForContributor(userID, callback);
    }

    public static void getNumberOfContributionForOnePlant(UUID plantID, IEventHandler<Integer> callback) {
        contribFactory.getContributionsForPlant(plantID, contribs -> callback.onTrigger(contribs.size()));
    }

    public static void getNumberOfPositiveReviewForOnePlant(UUID plantID, IEventHandler<Integer> callback) {
        contribFactory.getContributionsForPlant(plantID, contribs -> callback.onTrigger((int)contribs.stream().filter(Contribution::isPositiveAdvice).count()));
    }

    public static void getNumberOfContributionForOneUser(UUID userID, IEventHandler<Integer> callback) {
        contribFactory.getContributionsForContributor(userID, contribs -> callback.onTrigger(contribs.size()));
    }



    public static void getFiabilityForOnePlant(UUID plantID, IEventHandler<Fiability> callback) {

        getNumberOfContributionForOnePlant(plantID, numberOfContribution -> {
            getNumberOfPositiveReviewForOnePlant(plantID, numberOfPositiveContribution -> {
                double percentage = (double)numberOfPositiveContribution / (double)numberOfContribution;
                if (percentage < 0.5) {
                    callback.onTrigger(Fiability.LOW);
                }
                if (percentage < 0.75) {
                    callback.onTrigger(Fiability.MEDIUM);
                }

                callback.onTrigger(Fiability.HIGH);
            });
        });




    }


    public static void findAuthorOfOnePlant(UUID idPlant, IEventHandler<List<User>> callback) {
        userAndPlantFactory.getContributorFor(idPlant, userIds -> {
            userFactory.getMany(userIds, callback);
        });
    }



    public static void getAllPlantsCreateByCurrentUser(IEventHandler<List<Plant>> callback) {
        getAllUserAndPlant(uaps -> {
            List<UUID> plants = new ArrayList<>();
            uaps.forEach(uap -> plants.add(uap.getPlant()));
            plantFactory.getManyPlants(plants, callback, err -> {
                throw new RuntimeException("Error while retrieving plants");
            });
        });
    }



    public static void getAllPublicPlantsOnMap(IEventHandler<List<OverlayItem>> callback) {

        List<OverlayItem> finalList = new ArrayList<>();
        // new OverlayItem("My Title", "My SubTittle", new GeoPoint(43.65020, 7.00517))

        getAllPublicPlants(plants -> {
            for (Plant plant : plants) {
                findAuthorOfOnePlant(plant.getIdPlant(), users -> {
                    OverlayItem item = new OverlayItem(
                            plant.getTitle(),

                            users.size() == 0 ? "Anonymous" : users.get(0).getSurname() + " " + users.get(0).getName(),
                            new GeoPoint(plant.getMyPosition().getLattitude(), plant.getMyPosition().getLongitude())
                    );

                    finalList.add(item);

                    callback.onTrigger(finalList);
                });
            }
        });
    }

    public static void getPrivatePlantsFromCurrentUserOnMap(IEventHandler<List<OverlayItem>> callback) {

        List<OverlayItem> finalList = new ArrayList<>();
        // new OverlayItem("My Title", "My SubTittle", new GeoPoint(43.65020, 7.00517))

        getAllPrivatePlantsFromOneUser(getCurrentUser().getUserId(), plants -> {
            for (Plant plant : plants) {

                OverlayItem item = new OverlayItem(
                        plant.getTitle(),
                        getCurrentUser().getSurname() + " " + getCurrentUser().getName(),
                        new GeoPoint(plant.getMyPosition().getLattitude(), plant.getMyPosition().getLongitude())
                );

                finalList.add(item);
            }

            callback.onTrigger(finalList);
        });

    }

    public static void getAllPlantsFromCurrentUserOnMap(IEventHandler<List<OverlayItem>> callback) {
        getAllPublicPlantsOnMap(publics -> {
            getPrivatePlantsFromCurrentUserOnMap(privates -> {
                publics.addAll(privates);
                callback.onTrigger(publics);
            });
        });
    }









    // SET D'ELEMNTS
    public static void addContributon(Contribution contribution) {
        contribution.save(() -> {}, err -> {
            throw new RuntimeException("Error while saving contribution");
        });
        //Mocks.contributions.add(contribution);
    }

    public static void setOnePlantDescription(UUID plantID, String newDescription, IVoidEventHandler success, IEventHandler<Throwable> fail) {
        getRealPlant(plantID, plant -> {
            if(plant.isPresent()) {
                plant.get().setDescription(newDescription);
                plant.get().save(success, fail);
            }
            else throw new RuntimeException("No plant found");
        });

    }

    public static void addOnePlantSource(UUID plantID, String newSource) {
        getRealPlant(plantID, plant -> {
            if(plant.isPresent()) plant.get().getSources().add(newSource);
            else throw new RuntimeException("No plant found");
        });

    }



    public static void addPositiveReviewToOnePlant(UUID plantID) {

        getAllContributions(contribs -> {
            Optional<Contribution> contribOp = contribs.stream().filter(contri -> contri.getContributor().equals(getCurrentUser().getUserId()) && contri.getPlant().equals(plantID)).findAny();
            if (contribOp.isPresent()) {
                contribOp.get().setPositiveAdvice(true);
            } else {
                addContributon(new Contribution(getCurrentUser().getUserId(), plantID, true));
            }
        });




    }

    public static void addNegativeReviewToOnePlant(UUID plantID) {
        getAllContributions(contribs -> {
            Optional<Contribution> contribOp = contribs.stream().filter(contri -> contri.getContributor().equals(getCurrentUser().getUserId()) && contri.getPlant().equals(plantID)).findAny();
            if (contribOp.isPresent()) {
                contribOp.get().setPositiveAdvice(false);
            } else {
                addContributon(new Contribution(getCurrentUser().getUserId(), plantID, false));
            }
        });
    }


}
