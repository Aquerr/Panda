package io.github.aquerr.pandobot.entities;

import java.util.HashMap;
import java.util.Map;

public enum VTEAMRoles
{
    DEBUG(510532992679149578L),
    MODERATOR(492802623213404163L),
    WLASCICIEL(492802402760654861L),
    GRACZ(492805123278635018L),
    EVERYONE(0L);

    public static Map<Long, Integer> rolesLadder = new HashMap<>();

    static
    {
        rolesLadder.put(EVERYONE.getId(), 0);
        rolesLadder.put(GRACZ.getId(), 1);
        rolesLadder.put(MODERATOR.getId(), 2);
        rolesLadder.put(WLASCICIEL.getId(), 3);
        rolesLadder.put(DEBUG.getId(), 4);
    }

    private long id;

    VTEAMRoles(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public static Map<Long, Integer> getLadder()
    {
        return rolesLadder;
    }
}
