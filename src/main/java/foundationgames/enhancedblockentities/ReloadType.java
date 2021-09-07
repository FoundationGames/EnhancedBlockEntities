package foundationgames.enhancedblockentities;

public enum ReloadType {
    NONE(0),
    WORLD(1),
    RESOURCES(2);

    private final int pertinence;

    ReloadType(int pertinence) {
        this.pertinence = pertinence;
    }

    public ReloadType or(ReloadType type) {
        return type.pertinence > this.pertinence ? type : this;
    }
}
