package com.example.td1_plantes;

import com.example.td1_plantes.model.GestionDatabase;
import com.example.td1_plantes.model.Mocks;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }



    @Test
    public void getCurrentUser() {
        assertEquals(GestionDatabase.getCurrentUser().getName(), "Jean");
    }

    @Test
    public void getAllUsers() {
        System.out.println(GestionDatabase.getAllUsers());
    }


    @Test
    public void getAllPlants() {
        System.out.println(GestionDatabase.getAllPlants());
    }

    @Test
    public void getAllUserAndPlant() {
        System.out.println(Mocks.user1.toString());
        System.out.println(Mocks.plant5.toString());

        System.out.println(GestionDatabase.getAllUserAndPlant());
    }


    @Test
    public void getAllContributions() {
        System.out.println(Mocks.user4.toString());
        System.out.println(Mocks.plant1.toString());

        System.out.println(GestionDatabase.getAllContributions());
    }



    @Test
    public void getRealUserTest() {

        UUID idUser1 = Mocks.user1.getUserId();
        System.out.println(idUser1);

        System.out.println(GestionDatabase.getRealUser(idUser1));
    }


    @Test
    public void getRealPlantTest() {

        UUID idPlant1 = Mocks.plant1.getIdPlant();
        System.out.println(idPlant1);

        System.out.println(GestionDatabase.getRealPlant(idPlant1));
    }


    @Test
    public void getAllPublicPlantsTest() {
        System.out.println(GestionDatabase.getAllPublicPlants());
    }

    @Test
    public void getAllPrivatePlants() {
        System.out.println(GestionDatabase.getAllPrivatePlants().size());
    }

    @Test
    public void getAllPrivatePlantsFromOneUserTest() {
        UUID idUser1 = Mocks.user1.getUserId();
        System.out.println(idUser1);

        System.out.println(GestionDatabase.getAllPrivatePlantsFromOneUser(idUser1));
    }




}