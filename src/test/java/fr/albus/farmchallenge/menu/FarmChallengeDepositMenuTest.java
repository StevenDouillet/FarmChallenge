package fr.albus.farmchallenge.menu;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class FarmChallengeDepositMenuTest {

    public FarmChallengeDepositMenuTest() {}

    @Test
    public void shouldReturnTheCorrectStartedStepByPage() {
        Assertions.assertEquals(1, FarmChallengeDepositMenu.getInventoryStartedStep(50, 1));
        Assertions.assertEquals(5, FarmChallengeDepositMenu.getInventoryStartedStep(50, 2));
        Assertions.assertEquals(9, FarmChallengeDepositMenu.getInventoryStartedStep(50, 3));
        Assertions.assertEquals(13, FarmChallengeDepositMenu.getInventoryStartedStep(50, 4));
        Assertions.assertEquals(17, FarmChallengeDepositMenu.getInventoryStartedStep(50, 5));
    }

    @Test
    public void shouldReturnTheCorrectStepsLeft() {
        Assertions.assertEquals(false, FarmChallengeDepositMenu.stepsLeft(4, 1, 4));
        Assertions.assertEquals(true, FarmChallengeDepositMenu.stepsLeft(6, 2, 4));
        Assertions.assertEquals(false, FarmChallengeDepositMenu.stepsLeft(8, 2, 4));
        Assertions.assertEquals(true, FarmChallengeDepositMenu.stepsLeft(10, 3, 4));
        Assertions.assertEquals(false, FarmChallengeDepositMenu.stepsLeft(12, 3, 4));
        Assertions.assertEquals(false, FarmChallengeDepositMenu.stepsLeft(20, 5, 4));
    }
}