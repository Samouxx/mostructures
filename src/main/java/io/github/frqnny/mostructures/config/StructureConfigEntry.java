package io.github.frqnny.mostructures.config;

public class StructureConfigEntry {

    // Missing from the file means activated. Older files also hold "separation" and "spacing": they never changed
    // where structures generate (the game reads the structure_set values), so they are ignored and no longer written.
    public boolean activated = true;
}
