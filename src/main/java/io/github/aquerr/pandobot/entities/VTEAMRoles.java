package io.github.aquerr.pandobot.entities;

import java.util.HashMap;
import java.util.Map;

public enum VTEAMRoles
{
    DEBUG(500777686487138329L),
    MODERATOR(461615101213016064L),
    WLASCICIEL(429412197194530816L),
    VIP(464900772904304671L),
    GRACZ(492805123278635018L),
    EVERYONE(0L);

    public static Map<Long, Integer> rolesLadder = new HashMap<>();

    //    public static final long DEBUG = 500777686487138329L;
//    public static final long MODERATOR = 461615101213016064L;
//    public static final long WLASCICIEL = 429412197194530816L;
//    public static final long VIP = 464900772904304671L;
//    public static final long GRACZ = 492805123278635018L;
//    public static final long EVERYONE = 0L;
//
//    private static Map<Long, Integer> rolesLadder = new HashMap();
//
    static
    {
        rolesLadder.put(EVERYONE.getId(), 0);
        rolesLadder.put(GRACZ.getId(), 1);
        rolesLadder.put(VIP.getId(), 2);
        rolesLadder.put(MODERATOR.getId(), 3);
        rolesLadder.put(WLASCICIEL.getId(), 4);
        rolesLadder.put(DEBUG.getId(), 5);
    }
//
//    public static Map<Long, Integer> getLadder()
//    {
//        return rolesLadder;
//    }

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
