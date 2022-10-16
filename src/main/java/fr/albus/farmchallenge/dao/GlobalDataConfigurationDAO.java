package fr.albus.farmchallenge.dao;

public interface GlobalDataConfigurationDAO {

    void incrementGlobalProgress(int amount);

    int getGlobalProgress();
}
