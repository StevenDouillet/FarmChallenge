package fr.albus.farmchallenge.dao;

import fr.albus.farmchallenge.models.ChallengeStep;

import java.util.List;

public interface GlobalDataConfigurationDAO {

    void incrementGlobalProgress(int amount);

    int getGlobalProgress();

    List<ChallengeStep> getPersonalChallengeSteps();

    List<ChallengeStep> getCommunityChallengeSteps();
}
