package io.github.aquerr.pandobot.annotations;

import io.github.aquerr.pandobot.entities.VTEAMRoles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface BotCommand
{
    VTEAMRoles minRole() default VTEAMRoles.EVERYONE;
    short argsCount() default 0;
}
