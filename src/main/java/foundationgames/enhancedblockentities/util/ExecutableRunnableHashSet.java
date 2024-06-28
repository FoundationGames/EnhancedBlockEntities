package foundationgames.enhancedblockentities.util;

import java.util.HashSet;

public class ExecutableRunnableHashSet extends HashSet<Runnable> implements Runnable {
    @Override
    public void run() {
        this.forEach(Runnable::run);
    }
}
