package fr.albus.farmchallenge.dao;

import fr.albus.farmchallenge.models.ChallengeStep;
import fr.albus.farmchallenge.models.ChallengeStepType;

import java.util.List;

public interface GlobalDataConfigurationDAO {

    void incrementGlobalProgress(int amount);

    int getGlobalProgress();

    List<ChallengeStep> getChallengeSteps(ChallengeStepType stepType);
}
