package com.example.td1_plantes.controler.activities.plantpage;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.activities.WichActivitiesWeAre;
import com.example.td1_plantes.controler.fragments.MyBottomBarFragment;
import com.example.td1_plantes.controler.fragments.OpenStreetMapFragment;
import com.example.td1_plantes.controler.fragments.TitleYellowDescriberDivFragment;
import com.example.td1_plantes.model.DownloadImageTask;
import com.example.td1_plantes.model.GestionDatabase;
import com.example.td1_plantes.model.appobjects.Plant;
import com.example.td1_plantes.model.appobjects.User;
import com.example.td1_plantes.model.appobjects.smallelements.Fiability;
import com.example.td1_plantes.model.appobjects.smallelements.StatusUser;
import com.example.td1_plantes.notification.NotificationObject;
import com.example.td1_plantes.notification.NotificationType;
import com.google.android.material.textfield.TextInputLayout;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;



public class PlantPageActivity extends AppCompatActivity {

    Plant currentPlant;
    User currentUser;

    boolean dispContribReviewFiability = false;
    boolean dispAddReview = false;
    boolean dispDescriptionEdit = false;
    boolean dispContributors = false;
    boolean dispSources = false;
    boolean dispAddSource = false;

    String newDescritpion;
    String newSource;


    // Pour créer la nouvelle activité et passer un paramètres, utiliser ces lignes :
    // Intent i = new Intent(this, PlantPageActivity.class);
    // i.putExtra("plantID", id); -> id doit etre egal a l'UUID de la plante
    // startActivity(i);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_page);

        // Récuperation du paramètre plantID && du currentUser
        Bundle bundle = getIntent().getExtras();
        UUID plantID = (UUID) bundle.get("plantID");

        GestionDatabase.getRealPlant(plantID, plant -> {
            plant.ifPresent(value -> currentPlant = value);
            currentUser = GestionDatabase.getCurrentUser();

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            initializeTheInformations();

            // PARAMETRAGE DE L'AFFICHAGE
            dispOrHideElements();


            // Dynamisme de la page (modifs, pouces verts, rouges ...)
            editElementsInPage();
        });

        //bottom_app_bar
        FragmentManager fm3 = getSupportFragmentManager();
        FragmentTransaction ft3 = fm3.beginTransaction();
        ft3.add(R.id.bottom_app_bar, new MyBottomBarFragment(WichActivitiesWeAre.NOTHING));
        ft3.commit();
    }



    private void editElementsInPage() {

        // AJOUT D'UN BON / MAUVAIS AVIS
        ImageButton addPositiveReview = (ImageButton) findViewById(R.id.plant_page_good_review_id);
        ImageButton addNegativeReview = (ImageButton) findViewById(R.id.plant_page_bad_review_id);

        addPositiveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionDatabase.addPositiveReviewToOnePlant(currentPlant.getIdPlant());
                initializeTheInformations();
            }
        });

        addNegativeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionDatabase.addNegativeReviewToOnePlant(currentPlant.getIdPlant());
                initializeTheInformations();
            }
        });


        // MODIFICATION DE LA DESCRIPTION
        TextInputLayout descriptionInput = (TextInputLayout) findViewById(R.id.plant_page_intern_edit_description);

        descriptionInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                newDescritpion = text.toString();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        Button validateDescriptionButton = (Button) findViewById(R.id.plant_page_intern_edit_description_validate_button);
        validateDescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionDatabase.setOnePlantDescription(currentPlant.getIdPlant(), newDescritpion, () -> {
                    TextView description = findViewById(R.id.plant_page_description_id);
                    description.setText(newDescritpion);
                    Toast.makeText(validateDescriptionButton.getContext(), "Saved successfully !", Toast.LENGTH_SHORT).show();
                }, err -> {
                    Toast.makeText(validateDescriptionButton.getContext(), "An error occurred", Toast.LENGTH_LONG).show();
                });
                initializeTheInformations();
            }
        });

        // AJOUT DE LA MAP

        // plant_page_localisation_title
        // plant_page_localisation_map




        GestionDatabase.getAllPublicPlants(plantsPublic -> {
            GestionDatabase.getAllPrivatePlants(plantsPrivate -> {

                List<Plant> goodPlants = plantsPublic;
                goodPlants.addAll(plantsPrivate);

                List<OverlayItem> pointsOnMap = new ArrayList<>();

                for (Plant plant : goodPlants) {

                    pointsOnMap.add(new OverlayItem(plant.getTitle(), plant.getPublicationDate(), new GeoPoint(plant.getMyPosition().getLattitude(), plant.getMyPosition().getLongitude())));

                }

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.plant_page_localisation_title, new TitleYellowDescriberDivFragment("Emplacement"));
                transaction.add(R.id.plant_page_localisation_map, new OpenStreetMapFragment(currentPlant.getMyPosition(), pointsOnMap));

                transaction.commit();

            });
        });











        // AJOUT D'UNE SOURCE
        TextInputLayout sourceInput = (TextInputLayout) findViewById(R.id.plant_page_intern_edit_sources);

        sourceInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                newSource = text.toString();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        Button validateSourceButton = (Button) findViewById(R.id.plant_page_intern_edit_sources_validate);
        validateSourceButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                GestionDatabase.addOnePlantSource(currentPlant.getIdPlant(), newSource);
                initializeTheInformations();


                NotificationObject notification = new NotificationObject()
                        .setTitle("Sourced Added")
                        .setMessage("This is an example")
                        .setType(NotificationType.TYPE_MODIFICATIONS_DETECTED);
                notification.sendFrom(PlantPageActivity.this);
            }
        });
    }


    private void dispOrHideElements() {

        // 1 // SI L'UTILISATEUR EST UN EXPERT
        if (currentUser.getStatusUser() == StatusUser.EXPERT) {

            // Il peut tout modifier et ajouter un avis
            dispContribReviewFiability = true;
            dispAddReview = true;
            dispDescriptionEdit = true;
            dispContributors = true;
            dispSources = true;
            dispAddSource = true;


        } else {
            // L'utilisateur n'est pas un expert, il ne peut que regarder
            dispContribReviewFiability = true;
            dispAddReview = false;
            dispDescriptionEdit = false;
            dispContributors = true;
            dispSources = true;
            dispAddSource = false;
        }

        GestionDatabase.getAllPlantsCreateByCurrentUser(plants -> {
            // 1 // SI L'UTILISATEUR EST L'AUTEUR DE LA PLANTE
            if (plants.stream().map(pl -> pl.getIdPlant()).collect(Collectors.toList()).contains(currentPlant.getIdPlant())) {

                // 1.1 // SI L'UTILISATEUR EST L'AUTEUR DE LA PLANTE ET SI LA PLANTE EST PRIVEE
                if (!currentPlant.isPublic()) {
                    // On affiche que la description
                    dispContribReviewFiability = false;
                    dispAddReview = false;
                    dispDescriptionEdit = true;
                    dispContributors = false;
                    dispSources = false;
                    dispAddSource = false;
                }

                // 1.2 // SI L'UTILISATEUR EST L'AUTEUR DE LA PLANTE ET SI LA PLANTE EST PUBLIC
                if (currentPlant.isPublic()) {

                    // 1.21 // SI C'EST UN EXPERT, IL A LE DROIT DE TOUT MODIFIER + AJOUER AVIS, SINON, IL A RIEN LE DROIT
                    if (currentUser.getStatusUser() == StatusUser.EXPERT) {
                        dispContribReviewFiability = true;
                        dispAddReview = true;
                        dispDescriptionEdit = true;
                        dispContributors = true;
                        dispSources = true;
                        dispAddSource = true;
                    }
                }
            }

            // dispContribReviewFiability = true;
            LinearLayout contribReviewFiability = findViewById(R.id.plante_page_header_review_infos);
            // dispAddReview = true;
            LinearLayout addReview = findViewById(R.id.plant_page_intern_bloc_22);
            // dispDescriptionEdit = true;
            LinearLayout descriptionEdit = findViewById(R.id.plant_page_description_edit);
            // dispContributors = true;
            LinearLayout contributors = findViewById(R.id.plant_page_contributors_main_id);
            // dispSources = true;
            LinearLayout sources = findViewById(R.id.plant_page_sources_main_id);
            // dispAddSource = true;
            LinearLayout editSources = findViewById(R.id.plant_page_intern_edit_sources_main);

            if (dispContribReviewFiability) {
                contribReviewFiability.setVisibility(View.VISIBLE);
            } else {
                contribReviewFiability.setVisibility(View.GONE);
            }

            if (dispAddReview) {
                addReview.setVisibility(View.VISIBLE);
            } else {
                addReview.setVisibility(View.GONE);
            }

            if (dispDescriptionEdit) {
                descriptionEdit.setVisibility(View.VISIBLE);
            } else {
                descriptionEdit.setVisibility(View.GONE);
            }

            if (dispContributors) {
                contributors.setVisibility(View.VISIBLE);
            } else {
                contributors.setVisibility(View.GONE);
            }

            if (dispSources) {
                sources.setVisibility(View.VISIBLE);
            } else {
                sources.setVisibility(View.GONE);
            }

            if (dispAddSource) {
                editSources.setVisibility(View.VISIBLE);
            } else {
                editSources.setVisibility(View.GONE);
            }
        });


    }



    private void initializeTheInformations() {

        // INITIALISATION DE L'IMAGE
        ImageView imagePlant = findViewById(R.id.plant_page_image_id);
        new DownloadImageTask((ImageView) imagePlant).execute(currentPlant.getImageURL());

        // INITIALISATION DU TITRE
        TextView titre = findViewById(R.id.plant_page_title_id);
        titre.setText(currentPlant.getTitle());

        // INITIALISATION DE L'AUTHOR ET LA DATE
        GestionDatabase.findAuthorOfOnePlant(currentPlant.getIdPlant(), users -> {
            String authorAndDateString = "Créée le "
                    + currentPlant.getPublicationDate()
                    + " par "
                    + users.get(0).getSurname()
                    + " "
                    + users.get(0).getName();
            TextView authorAndDate = findViewById(R.id.plant_page_date_and_user);
            authorAndDate.setText(authorAndDateString);
        });

        // INITIALISATION DE LA DESCRIPTION
        TextView description = findViewById(R.id.plant_page_description_id);
        description.setText(currentPlant.getDescription());


        // INITIALISATION DES CONTRIBUTEURS
        GestionDatabase.getContributorsForOnePlant(currentPlant.getIdPlant(), contributors -> {
            String contributeursString = "";

            for (User contributor : contributors){
                contributeursString += contributor.getSurname();
                contributeursString += " ";
                contributeursString += contributor.getName();
                contributeursString += "\n";
            }
            TextView contributeurs = findViewById(R.id.plant_page_contributors_id);
            contributeurs.setText(contributeursString);
        });



        // INITIALISATION SOURCES
        String sourcesString = "";
        for (String contri : currentPlant.getSources()){
            sourcesString += contri;
            sourcesString += "\n";
        }
        TextView sources = findViewById(R.id.plant_page_sources_id);
        sources.setText(sourcesString);


        // INITIALISATION DU NOMBRE DE CONTRIBUTEURS
        GestionDatabase.getNumberOfContributionForOnePlant(currentPlant.getIdPlant(), noc -> {
            TextView numberOfContributors = findViewById(R.id.plant_page_number_of_contributors_id);
            String numberOfContributorsString = "" + noc;
            numberOfContributors.setText(numberOfContributorsString);
        });



        // INITIALISATION DU POURCENTAGE D'AVIS POSITIFS

        GestionDatabase.getNumberOfContributionForOnePlant(currentPlant.getIdPlant(), numberOfContribution -> {
            GestionDatabase.getNumberOfPositiveReviewForOnePlant(currentPlant.getIdPlant(), numberOfPositiveContribution -> {
                TextView positiviteReview = findViewById(R.id.plant_page_positive_review_id);
                double percentagePositiv = (int) (((double)numberOfPositiveContribution / (double)numberOfContribution) * 100.0);
                String percentageStringFinal = "" + percentagePositiv + "%";
                positiviteReview.setText(percentageStringFinal);
            });
        });


        // INITIALISATION DE LA FIABILITE
        GestionDatabase.getFiabilityForOnePlant(currentPlant.getIdPlant(), fiabilityElemObj -> {
            TextView fiabilityElem = findViewById(R.id.plant_page_fiability_level_id);
            String fiabilityElemString = "";
            if (fiabilityElemObj == Fiability.LOW)
                fiabilityElemString = "NON";
            if (fiabilityElemObj == Fiability.MEDIUM)
                fiabilityElemString = "PEU";
            if (fiabilityElemObj == Fiability.HIGH)
                fiabilityElemString = "TRES";
            fiabilityElem.setText(fiabilityElemString);
        });

    }

}