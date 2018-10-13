package io.github.aquerr.pandobot.entities;

import java.util.HashMap;
import java.util.Map;

public class VTEAMRoles
{
    public static final long DEBUG = 500777686487138329L;
    public static final long MODERATOR = 461615101213016064L;
    public static final long WLASCICIEL = 429412197194530816L;
    public static final long VIP = 464900772904304671L;
    public static final long EVERYONE = 0L;

    private static Map<Long, Integer> rolesLadder = new HashMap();

    static
    {
        rolesLadder.put(EVERYONE, 0);
        rolesLadder.put(VIP, 1);
        rolesLadder.put(MODERATOR, 2);
        rolesLadder.put(WLASCICIEL, 3);
        rolesLadder.put(DEBUG, 4);
    }

    public static Map<Long, Integer> getLadder()
    {
        return rolesLadder;
    }
}
