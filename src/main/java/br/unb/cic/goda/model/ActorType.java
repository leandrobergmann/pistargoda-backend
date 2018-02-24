package br.unb.cic.goda.model;

import java.io.Serializable;

public class ActorType implements Serializable {

    private int value;
    private String name;

    public static final int ACTOR = 0;
    public static final int ROLE = 1;
    public static final int POSITION = 2;
    public static final int AGENT = 3;
    public static final ActorType ACTOR_LITERAL = new ActorType(ACTOR, "ACTOR");
    public static final ActorType ROLE_LITERAL = new ActorType(ROLE, "ROLE");
    public static final ActorType POSITION_LITERAL = new ActorType(POSITION, "POSITION");
    public static final ActorType AGENT_LITERAL = new ActorType(AGENT, "AGENT");

    private static final ActorType[] VALUES_ARRAY =
            new ActorType[]{
                    ACTOR_LITERAL,
                    ROLE_LITERAL,
                    POSITION_LITERAL,
                    AGENT_LITERAL,
            };

    public static ActorType get(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ActorType result = VALUES_ARRAY[i];
            if (result.toString().equals(name)) {
                return result;
            }
        }
        return null;
    }

    public static ActorType get(int value) {
        switch (value) {
            case ACTOR:
                return ACTOR_LITERAL;
            case ROLE:
                return ROLE_LITERAL;
            case POSITION:
                return POSITION_LITERAL;
            case AGENT:
                return AGENT_LITERAL;
        }
        return null;
    }

    private ActorType(int value, String name) {
        this.value = value;
        this.name = name;
    }

}
