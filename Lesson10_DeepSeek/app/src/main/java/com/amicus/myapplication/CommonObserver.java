package com.amicus.myapplication;

public interface CommonObserver {
    void onSystemPromptChanged(String newPrompt);
    void onTemperatureChanged(double newTemperature);
}