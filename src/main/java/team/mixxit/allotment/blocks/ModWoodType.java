package team.mixxit.allotment.blocks;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;

import java.util.Set;
import java.util.stream.Stream;

public class ModWoodType {
    public static final Set<ModWoodType> VALUES = new ObjectArraySet<>();
    public static final ModWoodType ELDER = register(new ModWoodType("elder"));
    private final String name;

    protected ModWoodType(String nameIn) {
        this.name = nameIn;
    }

    private static ModWoodType register(ModWoodType woodTypeIn) {
        VALUES.add(woodTypeIn);
        return woodTypeIn;
    }

    public static Stream<ModWoodType> getValues() {
        return VALUES.stream();
    }

    public String getName() {
        return this.name;
    }
}
